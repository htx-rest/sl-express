package com.sl.ms.search.api;


import com.sl.ms.search.domain.dto.CourierTaskDTO;
import com.sl.ms.search.domain.dto.CourierTaskPageQueryDTO;
import com.sl.transport.common.util.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "sl-express-ms-search", contextId = "CourierTask", path = "courierSearch")
public interface CourierTaskFeign {

    /**
     * 分页查询
     *
     * @param pageQueryDTO 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("pageQuery")
    PageResponse<CourierTaskDTO> pageQuery(@RequestBody CourierTaskPageQueryDTO pageQueryDTO);

    /**
     * 新增快递员任务
     *
     * @param courierTaskDTO 快递员任务
     */
    @PostMapping("")
    void saveOrUpdate(@RequestBody CourierTaskDTO courierTaskDTO);

    /**
     * 根据取派件id查询快递员任务
     *
     * @param id 取派件id
     * @return 快递员任务
     */
    @GetMapping("/{id}")
    CourierTaskDTO findById(@PathVariable("id") Long id);

    /**
     * 根据订单id查询快递员任务
     *
     * @param orderId 订单id
     * @return 快递员任务列表
     */
    @GetMapping("/findByOrderId/{orderId}")
    @ApiOperation(value = "根据订单id查询快递员任务")
    List<CourierTaskDTO> findByOrderId(@PathVariable("orderId") Long orderId);
}
