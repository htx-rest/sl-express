package com.sl.ms.web.courier.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.sl.ms.base.api.common.AreaFeign;
import com.sl.ms.base.domain.base.AreaDto;
import com.sl.ms.web.courier.service.AreaService;
import com.sl.ms.web.courier.vo.area.AreaSimpleVO;
import com.sl.transport.common.util.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaFeign areaFeign;

    @Override
    public List<AreaSimpleVO> findChildrenAreaByParentId(Long parentId) {

        List<AreaDto> areas = areaFeign.findAll(parentId, null);
        if (CollUtil.isEmpty(areas)) {
            return Collections.emptyList();
        }
        List<AreaSimpleVO> areaSimpleVOS = new ArrayList<>();
        areas.forEach(area -> {
            AreaSimpleVO areaSimpleVO = ObjectUtils.convert(area, AreaSimpleVO.class);
            areaSimpleVOS.add(areaSimpleVO);
        });
        return areaSimpleVOS;
    }
}
