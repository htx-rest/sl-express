package com.sl.ms.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.auth.sdk.dto.UserDTO;
import com.sl.ms.base.api.common.AreaFeign;
import com.sl.ms.base.api.common.AuthFeign;
import com.sl.ms.base.api.common.MQFeign;
import com.sl.ms.base.api.common.MessageFeign;
import com.sl.ms.base.domain.base.MessageAddDTO;
import com.sl.ms.base.domain.constants.MessageConstants;
import com.sl.ms.base.domain.enums.MessageBussinessTypeEnum;
import com.sl.ms.base.domain.enums.MessageContentTypeEnum;
import com.sl.ms.oms.api.OrderFeign;
import com.sl.ms.oms.dto.OrderDTO;
import com.sl.ms.oms.enums.OrderStatus;
import com.sl.ms.search.api.CourierTaskFeign;
import com.sl.ms.search.domain.dto.CourierTaskDTO;
import com.sl.ms.work.domain.dto.CourierTaskCountDTO;
import com.sl.ms.work.domain.dto.PickupDispatchTaskDTO;
import com.sl.ms.work.domain.dto.request.PickupDispatchTaskPageQueryDTO;
import com.sl.ms.work.domain.enums.WorkExceptionEnum;
import com.sl.ms.work.domain.enums.pickupDispatchtask.*;
import com.sl.ms.work.entity.PickupDispatchTaskEntity;
import com.sl.ms.work.entity.TransportOrderEntity;
import com.sl.ms.work.mapper.TaskPickupDispatchMapper;
import com.sl.ms.work.service.PickupDispatchTaskService;
import com.sl.ms.work.service.TransportOrderService;
import com.sl.transport.common.constant.Constants;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.common.util.PageResponse;
import com.sl.transport.common.vo.OrderMsg;
import com.sl.transport.common.vo.TransportInfoMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 取件、派件任务信息表 服务实现类
 */
@Service
@Slf4j
public class PickupDispatchTaskServiceImpl extends ServiceImpl<TaskPickupDispatchMapper, PickupDispatchTaskEntity> implements PickupDispatchTaskService {

    @Resource
    private TaskPickupDispatchMapper taskPickupDispatchMapper;
    @Resource
    private TransportOrderService transportOrderService;
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private AuthFeign authFeign;
    @Resource
    private MQFeign mqFeign;
    @Resource
    private AreaFeign areaFeign;
    @Resource
    private MessageFeign messageFeign;
    @Resource
    private CourierTaskFeign courierTaskFeign;

    @Override
    @Transactional
    public Boolean updateStatus(PickupDispatchTaskDTO pickupDispatchTaskDTO) {
        WorkExceptionEnum paramError = WorkExceptionEnum.PICKUP_DISPATCH_TASK_PARAM_ERROR;
        if (ObjectUtil.hasEmpty(pickupDispatchTaskDTO.getId(), pickupDispatchTaskDTO.getStatus())) {
            throw new SLException("更新取派件任务状态，id或status不能为空", paramError.getCode());
        }

        PickupDispatchTaskEntity pickupDispatchTask = super.getById(pickupDispatchTaskDTO.getId());

        switch (pickupDispatchTaskDTO.getStatus()) {
            case NEW: {
                throw new SLException(WorkExceptionEnum.PICKUP_DISPATCH_TASK_STATUS_NOT_NEW);
            }
            case COMPLETED: {
                //任务完成
                pickupDispatchTask.setStatus(PickupDispatchTaskStatus.COMPLETED);
                //设置完成时间
                pickupDispatchTask.setActualEndTime(LocalDateTime.now());

                if (PickupDispatchTaskType.DISPATCH == pickupDispatchTask.getTaskType()) {
                    //如果是派件任务的完成，已签收需要设置签收状态和签收人，拒收只需要设置签收状态
                    if (ObjectUtil.isEmpty(pickupDispatchTaskDTO.getSignStatus())) {
                        throw new SLException("完成派件任务，签收状态不能为空", paramError.getCode());
                    }
                    pickupDispatchTask.setSignStatus(pickupDispatchTaskDTO.getSignStatus());

                    if (PickupDispatchTaskSignStatus.RECEIVED == pickupDispatchTaskDTO.getSignStatus()) {
                        if (ObjectUtil.isEmpty(pickupDispatchTaskDTO.getSignRecipient())) {
                            throw new SLException("完成派件任务，签收人不能为空", paramError.getCode());
                        }
                        pickupDispatchTask.setSignRecipient(pickupDispatchTaskDTO.getSignRecipient());
                    }
                }
                break;
            }
            case CANCELLED: {
                //任务取消
                if (ObjectUtil.isEmpty(pickupDispatchTaskDTO.getCancelReason())) {
                    throw new SLException("取消任务，原因不能为空", paramError.getCode());
                }
                pickupDispatchTask.setStatus(PickupDispatchTaskStatus.CANCELLED);
                pickupDispatchTask.setCancelReason(pickupDispatchTaskDTO.getCancelReason());
                pickupDispatchTask.setCancelReasonDescription(pickupDispatchTaskDTO.getCancelReasonDescription());
                pickupDispatchTask.setCancelTime(LocalDateTime.now());

                if (pickupDispatchTaskDTO.getCancelReason() == PickupDispatchTaskCancelReason.RETURN_TO_AGENCY) {
                    //发送分配快递员派件任务的消息
                    OrderMsg orderMsg = OrderMsg.builder()
                            .agencyId(pickupDispatchTask.getAgencyId())
                            .orderId(pickupDispatchTask.getOrderId())
                            .created(DateUtil.current())
                            .taskType(PickupDispatchTaskType.PICKUP.getCode()) //取件任务
                            .mark(pickupDispatchTask.getMark())
                            .estimatedEndTime(pickupDispatchTask.getEstimatedEndTime()).build();

                    //发送消息（取消任务发生在取件之前，没有运单，参数直接填入null）
                    this.transportOrderService.sendPickupDispatchTaskMsgToDispatch(null, orderMsg);
                } else if (pickupDispatchTaskDTO.getCancelReason() == PickupDispatchTaskCancelReason.CANCEL_BY_USER) {
                    //原因是用户取消，则订单状态改为取消
                    orderFeign.updateStatus(ListUtil.of(pickupDispatchTask.getOrderId()), OrderStatus.CANCELLED.getCode());
                } else {
                    //其他原因则关闭订单
                    orderFeign.updateStatus(ListUtil.of(pickupDispatchTask.getOrderId()), OrderStatus.CLOSE.getCode());
                }
                break;
            }
            default: {
                throw new SLException("其他未知状态，不能完成更新操作", paramError.getCode());
            }
        }

        //发送消息，同步更新快递员任务
        CourierTaskDTO courierTaskDTO = courierTaskFeign.findById(pickupDispatchTaskDTO.getId());
        courierTaskDTO.setStatus(pickupDispatchTaskDTO.getStatus().getCode());
        courierTaskDTO.setUpdated(LocalDateTime.now());
        mqFeign.sendMsg(Constants.MQ.Exchanges.COURIER_TASK, Constants.MQ.RoutingKeys.COURIER_TASK_SAVE_OR_UPDATE, JSONUtil.toJsonStr(courierTaskDTO));

        return super.updateById(pickupDispatchTask);
    }

    @Override
    public Boolean updateCourierId(Long id, Long originalCourierId, Long targetCourierId) {
        if (ObjectUtil.hasEmpty(id, targetCourierId)) {
            throw new SLException(WorkExceptionEnum.UPDATE_COURIER_PARAM_ERROR);
        }
        if (ObjectUtil.equal(originalCourierId, targetCourierId)) {
            throw new SLException(WorkExceptionEnum.UPDATE_COURIER_EQUAL_PARAM_ERROR);
        }

        PickupDispatchTaskEntity pickupDispatchTask = super.getById(id);
        if (ObjectUtil.isEmpty(pickupDispatchTask)) {
            throw new SLException(WorkExceptionEnum.PICKUP_DISPATCH_TASK_NOT_FOUND);
        }

        //校验原快递id是否正确（本来无快递员id的情况除外）
        if (ObjectUtil.isNotEmpty(pickupDispatchTask.getCourierId())
                && ObjectUtil.notEqual(pickupDispatchTask.getCourierId(), originalCourierId)) {
            throw new SLException(WorkExceptionEnum.UPDATE_COURIER_ID_PARAM_ERROR);
        }

        //更改快递员id
        pickupDispatchTask.setCourierId(targetCourierId);

        // 标识已分配状态
        pickupDispatchTask.setAssignedStatus(PickupDispatchTaskAssignedStatus.DISTRIBUTED);

        //发送消息，同步更新快递员任务
        CourierTaskDTO courierTaskDTO = courierTaskFeign.findById(id);
        courierTaskDTO.setCourierId(targetCourierId);
        courierTaskDTO.setUpdated(LocalDateTime.now());
        mqFeign.sendMsg(Constants.MQ.Exchanges.COURIER_TASK, Constants.MQ.RoutingKeys.COURIER_TASK_SAVE_OR_UPDATE, JSONUtil.toJsonStr(courierTaskDTO));

        return super.updateById(pickupDispatchTask);
    }

    @Override
    public PickupDispatchTaskEntity saveTaskPickupDispatch(PickupDispatchTaskEntity taskPickupDispatch) {
        // 设置任务状态为新任务
        taskPickupDispatch.setStatus(PickupDispatchTaskStatus.NEW);
        boolean result = super.save(taskPickupDispatch);

        if (result) {
            //同步快递员任务到es
            this.syncCourierTask2Es(taskPickupDispatch);

            //生成运单跟踪消息和快递员端取件/派件消息通知
            this.generateMsg(taskPickupDispatch);

            return taskPickupDispatch;
        }
        throw new SLException(WorkExceptionEnum.PICKUP_DISPATCH_TASK_SAVE_ERROR);
    }

    /**
     * 同步快递员任务到es
     *
     * @param taskPickupDispatch 取派件任务
     */
    private void syncCourierTask2Es(PickupDispatchTaskEntity taskPickupDispatch) {
        //构建快递员任务
        CourierTaskDTO courierTaskDTO = this.getCourierTask(taskPickupDispatch);
        //为快递员任务set运单号
        try {
            TransportOrderEntity transportOrderEntity = transportOrderService.findByOrderId(taskPickupDispatch.getOrderId());
            courierTaskDTO.setTransportOrderId(transportOrderEntity.getId());
        } catch (Exception e) {
            log.info("id为{}的取派件任务没有运单！", taskPickupDispatch.getId());
        }
        //发送消息，快递员任务存入es
        mqFeign.sendMsg(Constants.MQ.Exchanges.COURIER_TASK, Constants.MQ.RoutingKeys.COURIER_TASK_SAVE_OR_UPDATE, JSONUtil.toJsonStr(courierTaskDTO));
    }

    /**
     * 构建快递员任务
     *
     * @return 快递员任务
     */
    private CourierTaskDTO getCourierTask(PickupDispatchTaskEntity taskPickupDispatch) {
        //查询订单
        OrderDTO orderDTO = orderFeign.findById(taskPickupDispatch.getOrderId());

        //取派件任务字段复制到快递员任务
        CourierTaskDTO courierTaskDTO = BeanUtil.toBean(taskPickupDispatch, CourierTaskDTO.class);
        courierTaskDTO.setTaskType(taskPickupDispatch.getTaskType().getCode());
        courierTaskDTO.setStatus(taskPickupDispatch.getStatus().getCode());
        courierTaskDTO.setIsDeleted(PickupDispatchTaskIsDeleted.NOT_DELETED.getCode());

        //设置快递员任务的姓名、电话、地址字段
        setNameAndPhoneAndAddress(orderDTO, courierTaskDTO);
        return courierTaskDTO;
    }

    /**
     * 设置快递员任务的姓名、电话、地址字段
     *
     * @param orderDTO       订单
     * @param courierTaskDTO 快递员任务
     */
    private void setNameAndPhoneAndAddress(OrderDTO orderDTO, CourierTaskDTO courierTaskDTO) {
        String name;
        String phone;
        String address;
        if (courierTaskDTO.getTaskType().equals(PickupDispatchTaskType.PICKUP.getCode())) {
            name = orderDTO.getSenderName();
            phone = orderDTO.getSenderPhone();
            address = orderDTO.getSenderAddress();
        } else {
            name = orderDTO.getReceiverName();
            phone = orderDTO.getReceiverPhone();
            address = orderDTO.getReceiverAddress();
        }
        courierTaskDTO.setAddress(name);
        courierTaskDTO.setName(phone);
        courierTaskDTO.setPhone(address);
    }

    /**
     * 生成运单跟踪消息和快递员端取件/派件消息通知
     *
     * @param taskPickupDispatch 取派件任务
     */
    private void generateMsg(PickupDispatchTaskEntity taskPickupDispatch) {
        OrderDTO orderDTO = orderFeign.findById(taskPickupDispatch.getOrderId());
        //分配给快递员的取派件任务需要发送消息
        if (ObjectUtil.isNotEmpty(taskPickupDispatch.getCourierId())) {
            String title;
            String content;
            Integer contentType;
            String address;
            if (ObjectUtil.equal(taskPickupDispatch.getTaskType(), PickupDispatchTaskType.DISPATCH)) {
                //派件任务需要发送运单跟踪消息
                this.sendTransportInfoMsg(taskPickupDispatch);

                //派件任务需要生成派件相关通知消息
                //组装消息内容
                address = areaFeign.get(orderDTO.getReceiverProvinceId()).getName()
                        + areaFeign.get(orderDTO.getReceiverCityId()).getName()
                        + areaFeign.get(orderDTO.getReceiverCountyId()).getName()
                        + orderDTO.getSenderAddress();
                content = CharSequenceUtil.format("地址：{}，收件人：{}，电话：{}", address, orderDTO.getReceiverName(), orderDTO.getReceiverPhone());

                title = MessageConstants.MessageTitle.DISPATCH;
                contentType = MessageContentTypeEnum.COURIER_DISPATCH.getCode();
            } else {
                //取件任务需要生成取件相关通知消息
                //组装消息内容
                address = areaFeign.get(orderDTO.getSenderProvinceId()).getName()
                        + areaFeign.get(orderDTO.getSenderCityId()).getName()
                        + areaFeign.get(orderDTO.getSenderCountyId()).getName()
                        + orderDTO.getSenderAddress();
                content = CharSequenceUtil.format("地址：{}，寄件人：{}，电话：{}", address, orderDTO.getSenderName(), orderDTO.getSenderPhone());

                title = MessageConstants.MessageTitle.PICKUP;
                contentType = MessageContentTypeEnum.COURIER_PICKUP.getCode();
            }
            MessageAddDTO messageAddDTO = MessageAddDTO.builder()
                    .title(title)
                    .content(content)
                    .bussinessType(MessageBussinessTypeEnum.COURIER.getCode())
                    .userId(taskPickupDispatch.getCourierId())
                    .contentType(contentType)
                    .relevantId(taskPickupDispatch.getId())
                    .build();
            messageFeign.add(messageAddDTO);
        }
    }


    /**
     * 发送派件相关运单跟踪消息
     *
     * @param taskPickupDispatch 取派件任务
     */
    private void sendTransportInfoMsg(PickupDispatchTaskEntity taskPickupDispatch) {
        UserDTO userDTO = authFeign.getByUserId(taskPickupDispatch.getCourierId());
        TransportOrderEntity transportOrderEntity = transportOrderService.findByOrderId(taskPickupDispatch.getOrderId());
        String info = CharSequenceUtil.format("您的快递正在派送途中，派件人【{}，电话 {}】", userDTO.getName(), userDTO.getMobile());

        //构建消息实体类
        String transportInfoMsg = TransportInfoMsg.builder()
                .transportOrderId(transportOrderEntity.getId())
                .status("派送中")
                .info(info)
                .created(DateUtil.current())
                .build().toJson();
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRANSPORT_INFO, Constants.MQ.RoutingKeys.TRANSPORT_INFO_APPEND, transportInfoMsg);
    }

    /**
     * 分页查询取派件任务
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @Override
    public PageResponse<PickupDispatchTaskDTO> findByPage(PickupDispatchTaskPageQueryDTO dto) {
        //1.构造条件
        Page<PickupDispatchTaskEntity> iPage = new Page<>(dto.getPage(), dto.getPageSize());
        LambdaQueryWrapper<PickupDispatchTaskEntity> queryWrapper = Wrappers.<PickupDispatchTaskEntity>lambdaQuery()
                .like(ObjectUtil.isNotEmpty(dto.getId()), PickupDispatchTaskEntity::getId, dto.getId())
                .like(ObjectUtil.isNotEmpty(dto.getOrderId()), PickupDispatchTaskEntity::getOrderId, dto.getOrderId())
                .eq(ObjectUtil.isNotEmpty(dto.getAgencyId()), PickupDispatchTaskEntity::getAgencyId, dto.getAgencyId())
                .eq(ObjectUtil.isNotEmpty(dto.getCourierId()), PickupDispatchTaskEntity::getCourierId, dto.getCourierId())
                .eq(ObjectUtil.isNotEmpty(dto.getTaskType()), PickupDispatchTaskEntity::getTaskType, dto.getTaskType())
                .eq(ObjectUtil.isNotEmpty(dto.getStatus()), PickupDispatchTaskEntity::getStatus, dto.getStatus())
                .eq(ObjectUtil.isNotEmpty(dto.getAssignedStatus()), PickupDispatchTaskEntity::getAssignedStatus, dto.getAssignedStatus())
                .eq(ObjectUtil.isNotEmpty(dto.getSignStatus()), PickupDispatchTaskEntity::getSignStatus, dto.getSignStatus())
                .eq(ObjectUtil.isNotEmpty(dto.getIsDeleted()), PickupDispatchTaskEntity::getIsDeleted, dto.getIsDeleted())
                .between(ObjectUtil.isNotEmpty(dto.getMinEstimatedEndTime()), PickupDispatchTaskEntity::getEstimatedEndTime, dto.getMinEstimatedEndTime(), dto.getMaxEstimatedEndTime())
                .between(ObjectUtil.isNotEmpty(dto.getMinActualEndTime()), PickupDispatchTaskEntity::getActualEndTime, dto.getMinActualEndTime(), dto.getMaxActualEndTime())
                .orderByDesc(PickupDispatchTaskEntity::getUpdated);

        //2.分页查询
        Page<PickupDispatchTaskEntity> result = super.page(iPage, queryWrapper);
        if (ObjectUtil.isEmpty(result.getRecords())) {
            return new PageResponse<>(result);
        }

        //3.实体类转为dto
        List<PickupDispatchTaskDTO> list = BeanUtil.copyToList(result.getRecords(), PickupDispatchTaskDTO.class);

        //4.组装分页数据
        return PageResponse.<PickupDispatchTaskDTO>builder()
                .page(dto.getPage())
                .pageSize(dto.getPageSize())
                .pages(result.getPages())
                .counts(result.getTotal())
                .items(list)
                .build();
    }

    @Override
    public List<CourierTaskCountDTO> findCountByCourierIds(List<Long> courierIds, PickupDispatchTaskType pickupDispatchTaskType, String date) {
        //计算一天的时间的边界
        DateTime dateTime = DateUtil.parse(date);
        LocalDateTime startDateTime = DateUtil.beginOfDay(dateTime).toLocalDateTime();
        LocalDateTime endDateTime = DateUtil.endOfDay(dateTime).toLocalDateTime();
        return this.taskPickupDispatchMapper
                .findCountByCourierIds(courierIds, pickupDispatchTaskType.getCode(), startDateTime, endDateTime);
    }

    @Override
    public List<PickupDispatchTaskDTO> findTodayTaskByCourierId(Long courierId) {
        //查询指定快递员当天所有的派件取件任务
        LambdaQueryWrapper<PickupDispatchTaskEntity> queryWrapper = Wrappers.<PickupDispatchTaskEntity>lambdaQuery()
                .eq(PickupDispatchTaskEntity::getCourierId, courierId)
                .ge(PickupDispatchTaskEntity::getEstimatedStartTime, LocalDateTimeUtil.beginOfDay(LocalDateTime.now()))
                .le(PickupDispatchTaskEntity::getEstimatedStartTime, LocalDateTimeUtil.endOfDay(LocalDateTime.now()))
                .eq(PickupDispatchTaskEntity::getIsDeleted, PickupDispatchTaskIsDeleted.NOT_DELETED);

        List<PickupDispatchTaskEntity> list = super.list(queryWrapper);
        return BeanUtil.copyToList(list, PickupDispatchTaskDTO.class);
    }

    @Override
    public List<PickupDispatchTaskEntity> findByOrderId(Long orderId, PickupDispatchTaskType taskType) {
        LambdaQueryWrapper<PickupDispatchTaskEntity> wrapper = Wrappers.<PickupDispatchTaskEntity>lambdaQuery()
                .eq(PickupDispatchTaskEntity::getOrderId, orderId)
                .eq(PickupDispatchTaskEntity::getTaskType, taskType)
                .orderByAsc(PickupDispatchTaskEntity::getCreated);
        return this.list(wrapper);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }

        // 通过id列表构造对象列表
        List<PickupDispatchTaskEntity> list = ids.stream().map(id -> {
            PickupDispatchTaskEntity dispatchTaskEntity = new PickupDispatchTaskEntity();
            dispatchTaskEntity.setId(id);
            dispatchTaskEntity.setIsDeleted(PickupDispatchTaskIsDeleted.IS_DELETED);

            //发送消息，同步更新快递员任务
            CourierTaskDTO courierTaskDTO = courierTaskFeign.findById(id);
            courierTaskDTO.setIsDeleted(PickupDispatchTaskIsDeleted.IS_DELETED.getCode());
            courierTaskDTO.setUpdated(LocalDateTime.now());
            mqFeign.sendMsg(Constants.MQ.Exchanges.COURIER_TASK, Constants.MQ.RoutingKeys.COURIER_TASK_SAVE_OR_UPDATE, JSONUtil.toJsonStr(courierTaskDTO));

            return dispatchTaskEntity;
        }).collect(Collectors.toList());

        return super.updateBatchById(list);
    }

    /**
     * 今日任务分类计数
     *
     * @param courierId 快递员id
     * @param taskType  任务类型，1为取件任务，2为派件任务
     * @param status    任务状态,1新任务，2已完成，3已取消
     * @param isDeleted 是否逻辑删除
     * @return 任务数量
     */
    @Override
    public Integer todayTasksCount(Long courierId, PickupDispatchTaskType taskType, PickupDispatchTaskStatus status, PickupDispatchTaskIsDeleted isDeleted) {
        //构建查询条件
        LambdaQueryWrapper<PickupDispatchTaskEntity> queryWrapper = Wrappers.<PickupDispatchTaskEntity>lambdaQuery()
                .eq(courierId != null, PickupDispatchTaskEntity::getCourierId, courierId)
                .eq(taskType != null, PickupDispatchTaskEntity::getTaskType, taskType)
                .eq(status != null, PickupDispatchTaskEntity::getStatus, status)
                .eq(isDeleted != null, PickupDispatchTaskEntity::getIsDeleted, isDeleted);

        //根据任务状态限定查询的日期条件
        LocalDateTime startTime = LocalDateTimeUtil.of(DateUtil.beginOfDay(new Date()));
        LocalDateTime endTime = LocalDateTimeUtil.of(DateUtil.endOfDay(new Date()));
        if (status == null) {
            //没有任务状态,查询任务创建时间
            queryWrapper.between(PickupDispatchTaskEntity::getCreated, startTime, endTime);
        } else if (status == PickupDispatchTaskStatus.NEW) {
            //新任务状态，查询预计结束时间
            queryWrapper.between(PickupDispatchTaskEntity::getEstimatedEndTime, startTime, endTime);
        } else if (status == PickupDispatchTaskStatus.COMPLETED) {
            //完成状态，查询实际完成时间
            queryWrapper.between(PickupDispatchTaskEntity::getActualEndTime, startTime, endTime);
        } else if (status == PickupDispatchTaskStatus.CANCELLED) {
            //取消状态，查询取消时间
            queryWrapper.between(PickupDispatchTaskEntity::getCancelTime, startTime, endTime);
        }

        //结果返回integer类型值
        return Math.toIntExact(this.count(queryWrapper));
    }
}
