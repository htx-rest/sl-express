package com.sl.ms.base.controller.base;

import cn.hutool.core.bean.BeanUtil;
import com.sl.ms.base.domain.base.AreaDto;
import com.sl.ms.base.entity.base.AreaEntity;
import com.sl.ms.base.service.base.AreaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * 系统日志
 * </p>
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/area")
@Api(value = "Area", tags = "行政区域")
public class AreaController {
    @Autowired
    private AreaService areaService;


    /**
     * 根据id获取行政区域详情
     *
     * @param id 行政区域id
     * @return 行政区域id
     */
    @GetMapping("/{id}")
    public AreaDto get(@PathVariable Long id) {
        AreaDto areaDto = BeanUtil.toBean(areaService.getById(id), AreaDto.class);
        areaDto.setName(areaDto.getShortName());
        return areaDto;
    }

    @GetMapping("/code/{code}")
    public AreaDto getByCode(@PathVariable String code) {
        AreaDto areaDto = BeanUtil.toBean((areaService.getByCode(code)), AreaDto.class);
        areaDto.setName(areaDto.getShortName());
        return areaDto;
    }

    /**
     * 获取行政区域简要信息列表
     *
     * @param parentId 父级id
     * @return 简要信息列表
     */
    @GetMapping
    public List<AreaDto> findAll(@RequestParam(value = "parentId", required = false) Long parentId, @RequestParam(value = "ids", required = false) List<Long> ids) {
        List<AreaEntity> all = areaService.findAll(parentId, ids);
        return all.stream().map(v -> {
            if (v.getLevel().equals(2) && "市辖区".equals(v.getName())) {
                return null;
            }
            AreaDto areaDto = BeanUtil.toBean(v, AreaDto.class);
            areaDto.setName(areaDto.getShortName());
            return areaDto;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
