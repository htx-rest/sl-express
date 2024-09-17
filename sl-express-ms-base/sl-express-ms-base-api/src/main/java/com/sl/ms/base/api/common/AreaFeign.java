package com.sl.ms.base.api.common;

import com.sl.ms.base.domain.base.AreaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 角色API
 */
@FeignClient(name = "sl-express-ms-base", contextId = "Area", path = "/area")
@ApiIgnore
public interface AreaFeign {
    /**
     * 根据id获取行政区域详情
     *
     * @param id 行政区域id
     * @return 行政区域id
     */
    @GetMapping("/{id}")
    AreaDto get(@PathVariable(value = "id") Long id);

    /**
     * 根据区域编码获取区域
     *
     * @param code
     * @return
     */
    @GetMapping("/code/{code}")
    AreaDto getByCode(@PathVariable(value = "code") String code);

    /**
     * 获取行政区域信息列表
     *
     * @param parentId 父级id
     * @return 信息列表
     */
    @GetMapping
    List<AreaDto> findAll(@RequestParam(value = "parentId", required = false) Long parentId, @RequestParam(value = "ids", required = false) List<Long> ids);
}
