package com.sl.ms.search.mapper;

import com.sl.ms.search.entity.CourierTaskEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierTaskMapper extends ElasticsearchRepository<CourierTaskEntity, Long> {

    /**
     * 根据订单id查询快递员任务
     *
     * @param orderId 订单id
     * @return 快递员任务列表
     */
    List<CourierTaskEntity> findCourierTaskEntitiesByOrderId(Long orderId);
}
