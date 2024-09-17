package com.sl.ms.base.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sl.ms.base.entity.base.AreaEntity;
import com.sl.ms.base.mapper.base.AreaMapper;
import com.sl.ms.base.service.base.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务实现类
 * 行政区域
 */
@Slf4j
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaEntity> implements AreaService {

    /**
     * 获取行政区域列表
     *
     * @param parentId 父级id
     * @param ids      id列表
     * @return 行政区域列表
     */
    @Override
    public List<AreaEntity> findAll(Long parentId, List<Long> ids) {
        LambdaQueryWrapper<AreaEntity> queryWrapper = new LambdaQueryWrapper<>();
        boolean isLimit = true;
        if (parentId != null) {
            queryWrapper.eq(AreaEntity::getParentId, parentId);
            isLimit = false;
        }
        if (ids != null && ids.size() > 0) {
            queryWrapper.in(AreaEntity::getId, ids);
            isLimit = false;
        }
        if (isLimit) {
            queryWrapper.last("limit 100");
        }
        queryWrapper.orderBy(true, true, AreaEntity::getId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据编码查询行政区域
     * @param code 行政编码
     * @return 行政区域
     */
    @Override
    public AreaEntity getByCode(String code) {
        return baseMapper.selectOne(new QueryWrapper<AreaEntity>().eq("area_code", code).last(" limit 1"));
    }
}
