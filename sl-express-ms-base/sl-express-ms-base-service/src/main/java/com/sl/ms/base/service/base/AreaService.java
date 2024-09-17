package com.sl.ms.base.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sl.ms.base.entity.base.AreaEntity;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 行政区域
 * </p>
 *
 */
public interface AreaService extends IService<AreaEntity> {
    /**
     * 获取行政区域列表
     *
     * @param parentId 父级id
     * @param ids      id列表
     * @return 行政区域列表
     */
    List<AreaEntity> findAll(Long parentId, List<Long> ids);

    /**
     * 根据编码查询行政区域
     * @param code 行政编码
     * @return 行政区域
     */
    AreaEntity  getByCode(String code);
}
