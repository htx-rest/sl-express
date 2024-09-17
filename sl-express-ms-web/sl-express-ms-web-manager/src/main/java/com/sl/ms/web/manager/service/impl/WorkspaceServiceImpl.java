package com.sl.ms.web.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.sl.ms.web.manager.service.WorkspaceService;
import com.sl.ms.web.manager.vo.workspace.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作台服务
 */
@Slf4j
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    /**
     * 工作台redisKey
     */
    private static final String WORKBENCH_REDIS_KEY = "WORKSPACE:WORKBENCH";
    /**
     * 订单总量redisKey
     */
    private static final String MONTHLY_ORDER_REDIS_KEY = "WORKSPACE:MONTHLY_ORDER";
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 新增数据
     *
     * @param workbenchVO 工作台
     */
    @Override
    public void saveWorkbench(WorkbenchAddVO workbenchVO) {
        String json = JSONUtil.toJsonStr(workbenchVO);
        redisTemplate.opsForList().leftPush(WORKBENCH_REDIS_KEY, json);
    }

    /**
     * 新增订单总量数据
     *
     * @param monthlyOrderList 月度订单数据
     */
    @Override
    public void saveOrderNumber(List<MonthlyOrderAddVO> monthlyOrderList) {
        //先删除所有
        redisTemplate.delete(MONTHLY_ORDER_REDIS_KEY);

        //订单月度数据转为json
        List<String> monthlyOrderJson = monthlyOrderList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());

        //订单数据存到redis
        redisTemplate.opsForList().leftPushAll(MONTHLY_ORDER_REDIS_KEY, monthlyOrderJson);
    }

    /**
     * 查询一条数据
     *
     * @return 工作台数据
     */
    @Override
    public WorkbenchVO findOne() {
        //以当前日期位于本年度的天数进行轮询，mod3的结果取redis索引
        int count = DateUtil.dayOfYear(new Date());
        int index = count % 3;

        //获取redis中的数据
        String json = redisTemplate.opsForList().index(WORKBENCH_REDIS_KEY, index);

        //json格式化
        WorkbenchAddVO workbenchAddVO = JSONUtil.toBean(json, WorkbenchAddVO.class);

        //组装月度订单数量
        //前6个月月份封装为list（注意DateTime的月份从0开始，如当前为8月份，实际endMonth为7）
        int endMonth = DateTime.now().month();
        List<Integer> monthList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int month = endMonth - i == 0 ? 12 : Math.floorMod(endMonth - i, 12);
            monthList.add(month);
        }

        //年度开始时间
        DateTime yearStart = DateUtil.beginOfYear(new Date());
        OrderLineChartVO orderLineChartVO = new OrderLineChartVO();

        //redis中获取月度订单数据
        List<String> monthlyOrderJson = redisTemplate.opsForList().range(MONTHLY_ORDER_REDIS_KEY, 0, -1);
        if (ObjectUtil.isNotEmpty(monthlyOrderJson)) {
            //json数据转换为对象
            List<MonthlyOrderAddVO> monthlyOrderAddList = monthlyOrderJson.stream().map(x -> JSONUtil.toBean(x, MonthlyOrderAddVO.class)).collect(Collectors.toList());

            //筛选出满足月份的数据
            monthlyOrderAddList = monthlyOrderAddList.stream().filter(x -> monthList.contains(x.getMonth())).sorted(Comparator.comparing(MonthlyOrderAddVO::getMonth)).collect(Collectors.toList());

            //月度订单数据封装
            List<MonthlyOrderVO> monthlyOrderList = monthlyOrderAddList.stream().map(x -> {
                LocalDate dateTime = DateUtil.offsetMonth(yearStart, x.getMonth() - 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                MonthlyOrderVO monthlyOrderVO = BeanUtil.toBean(x, MonthlyOrderVO.class);
                monthlyOrderVO.setDateTime(dateTime);
                return monthlyOrderVO;
            }).collect(Collectors.toList());

            //求最大值、最小值、平均值
            Integer max = monthlyOrderList.stream().mapToInt(MonthlyOrderVO::getOrderNumber).max().orElse(0);
            Integer min = monthlyOrderList.stream().mapToInt(MonthlyOrderVO::getOrderNumber).min().orElse(0);
            double average = monthlyOrderList.stream().mapToInt(MonthlyOrderVO::getOrderNumber).average().getAsDouble();

            //封装最大值、最小值、平均值
            orderLineChartVO.setMonthlyOrderList(monthlyOrderList);
            orderLineChartVO.setOrderMaxNumber(max);
            orderLineChartVO.setOrderAverageNumber(min);
            orderLineChartVO.setOrderMinNumber((int) Math.round(average));
        }

        //组装工作台数据
        WorkbenchVO workbenchVO = BeanUtil.toBean(workbenchAddVO, WorkbenchVO.class);
        workbenchVO.setOrderLineChart(orderLineChartVO);
        return workbenchVO;
    }

    /**
     * 删除所有
     */
    @Override
    public void deleteAll() {
        redisTemplate.delete(WORKBENCH_REDIS_KEY);
    }
}
