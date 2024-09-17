package com.sl.transport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.sl.transport.common.util.ObjectUtil;
import com.sl.transport.entity.node.BaseEntity;
import com.sl.transport.repository.BaseRepository;
import com.sl.transport.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础服务的实现
 *
 * @author zzj
 * @version 1.0
 */
public class ServiceImpl<R extends BaseRepository, T extends BaseEntity> implements IService<T> {

    @Autowired
    private R repository;

    @Override
    public T queryByBid(Long bid) {
        return (T) this.repository.findByBid(bid).orElse(null);
    }

    @Override
    public T create(T t) {
        t.setId(null);//id由neo4j自动生成
        return (T) this.repository.save(t);
    }

    @Override
    public T update(T t) {
        //先查询，再更新
        T tData = this.queryByBid(t.getBid());
        if (ObjectUtil.isEmpty(tData)) {
            return null;
        }
        BeanUtil.copyProperties(t, tData, CopyOptions.create().ignoreNullValue().setIgnoreProperties("id", "bid"));
        return (T) this.repository.save(tData);
    }

    @Override
    public Boolean deleteByBid(Long bid) {
        return this.repository.deleteByBid(bid) > 0;
    }
}
