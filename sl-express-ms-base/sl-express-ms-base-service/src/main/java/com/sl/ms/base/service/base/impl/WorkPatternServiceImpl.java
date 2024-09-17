package com.sl.ms.base.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sl.ms.base.domain.base.WorkPatternAddDTO;
import com.sl.ms.base.domain.base.WorkPatternDTO;
import com.sl.ms.base.domain.base.WorkPatternQueryDTO;
import com.sl.ms.base.domain.base.WorkPatternUpdateDTO;
import com.sl.ms.base.domain.enums.WorkPatternEnum;
import com.sl.ms.base.entity.base.WorkPatternEntity;
import com.sl.ms.base.entity.base.WorkSchedulingEntity;
import com.sl.ms.base.mapper.base.WorkPatternMapper;
import com.sl.ms.base.service.base.WorkPatternService;
import com.sl.ms.base.service.base.WorkSchedulingService;
import com.sl.ms.base.utils.Constants;
import com.sl.ms.base.utils.WorkPatternUtils;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.common.util.ObjectUtils;
import com.sl.transport.common.util.PageResponse;
import com.sl.transport.common.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 工作模式服务
 */
@Slf4j
@Service
public class WorkPatternServiceImpl extends ServiceImpl<WorkPatternMapper, WorkPatternEntity> implements WorkPatternService {

    @Resource
    private WorkSchedulingService workSchedulingService;

    /**
     * 分页查询工作模式
     * @param workPatternQueryDTO 查询条件
     * @return 工作模式数据
     */
    @Override
    public PageResponse<WorkPatternDTO> page(WorkPatternQueryDTO workPatternQueryDTO) {
        WorkPatternMapper workPatternMapper = getBaseMapper();

        QueryWrapper<WorkPatternEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("is_delete", 0);
        queryWrapper.orderByDesc("created");
        Page<WorkPatternEntity> page = new Page<>(workPatternQueryDTO.getPage(), workPatternQueryDTO.getPageSize());

        IPage<WorkPatternEntity> iPage = workPatternMapper.selectPage(page, queryWrapper);

        return PageUtils.toResponsePage(iPage, WorkPatternDTO.class, (entity, workPatternDTO) -> {
            workPatternDTO.setWorkDate(WorkPatternUtils.toWorkDate(entity));
            workPatternDTO.setWorkPatternTypeDesc(WorkPatternEnum.desc(entity.getWorkPatternType()));
        });

    }

    /**
     * 工作模式ID查询
     * @param id 工作模式ID
     * @return 工作模式
     */
    @Override
    public WorkPatternDTO findById(Long id) {
        WorkPatternEntity workPatternEntity = getBaseMapper().selectById(id);
        return ObjectUtils.convert(workPatternEntity, WorkPatternDTO.class, (entity, workPatternDTO) -> {
            workPatternDTO.setWorkDate(WorkPatternUtils.toWorkDate(entity));
            workPatternDTO.setWorkPatternTypeDesc(WorkPatternEnum.desc(entity.getWorkPatternType()));
        });
    }

    /**
     * 删除工作模式
     * @param id 工作模式ID
     */
    @Override
    @Transactional(rollbackFor = {SLException.class, Exception.class})
    public void delete(long id) {

        // 删除限制
        long count = workSchedulingService.count(Wrappers.<WorkSchedulingEntity>lambdaQuery().eq(WorkSchedulingEntity::getWorkPatternId, id));
        if (count > 0) {
            throw new SLException("改工作模式下有排班，请先把排班修改为其他工作模式后删除");
        }
        int number = getBaseMapper().deleteById(id);
        if (number <= 0) {
            throw new SLException("工作模式删除失败");
        }

    }

    /**
     * 更新工作模式
     * @param workPatternUpdateDTO 工作模式
     */
    @Override
    @Transactional(rollbackFor = {SLException.class, Exception.class})
    public void update(WorkPatternUpdateDTO workPatternUpdateDTO) {
        WorkPatternMapper workPatternMapper = getBaseMapper();
        WorkPatternEntity workPatternEntity = ObjectUtils.convert(workPatternUpdateDTO, WorkPatternEntity.class, (origin, entity) -> {
            entity.setName(origin.getName());
            entity.setUpdated(LocalDateTime.now());
            entity.setStatus(Constants.WorkStatus.STOP);
        });
        int result = workPatternMapper.updateById(workPatternEntity);
        if (result <= 0) {
            throw new SLException("工作模式更新失败");
        }
    }

    /**
     * 新增工作模式
     * @param workPatternAddDTO 工作模式
     */
    @Override
    @Transactional(rollbackFor = {SLException.class, Exception.class})
    public void add(WorkPatternAddDTO workPatternAddDTO) {

        WorkPatternEntity workPatternEntity = ObjectUtils.convert(workPatternAddDTO, WorkPatternEntity.class, (dto, entity) -> {
            entity.setId(IdWorker.getId());
            entity.setCreater(dto.getOperator());
            entity.setUpdater(dto.getOperator());
            entity.setCreated(LocalDateTime.now());
            entity.setUpdated(LocalDateTime.now());
            entity.setStatus(Constants.WorkStatus.USING);
        });
        ObjectUtils.setDefault(workPatternEntity);
        int result = getBaseMapper().insert(workPatternEntity);
        if (result <= 0) {
            throw new SLException("工作模式新增失败");
        }
    }
}
