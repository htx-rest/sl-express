package com.sl.transport.service;

import com.sl.transport.domain.OrganDTO;

import java.util.List;

/**
 * 机构业务操作
 */
public interface OrganService {

    /**
     * 无需指定type，根据id查询
     *
     * @param bid
     * @return
     */
    OrganDTO findByBid(Long bid);

    /**
     * 查询所有的机构，如果name不为空的按照name模糊查询
     *
     * @param name 机构名称
     * @return 机构列表
     */
    List<OrganDTO> findAll(String name);
}
