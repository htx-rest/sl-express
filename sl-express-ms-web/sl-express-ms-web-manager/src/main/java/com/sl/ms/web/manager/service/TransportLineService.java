package com.sl.ms.web.manager.service;

import com.sl.ms.web.manager.vo.transportLine.TransportLineQueryVO;
import com.sl.ms.web.manager.vo.transportLine.TransportLineVO;
import com.sl.transport.common.util.PageResponse;

import java.util.List;

public interface TransportLineService {
    /**
     * 新增线路
     *
     * @param vo 线路信息
     */
    void saveTransportLine(TransportLineVO vo);

    /**
     * 更新线路
     *
     * @param id 线路ID
     * @param vo 线路信息
     */
    void updateTransportLine(Long id, TransportLineVO vo);

    /**
     * 线路分页
     *
     * @param transportLineQueryVO 查询条件
     * @return 线路列表
     */
    PageResponse<TransportLineVO> findTransportLineByPage(TransportLineQueryVO transportLineQueryVO);

    /**
     * 线路详情
     *
     * @param id 线路ID
     * @return 线路信息
     */
    TransportLineVO findTransportLineById(Long id);

    /**
     * 线路详情
     *
     * @param ids 线路IDs
     * @return 线路信息列表
     */
    List<TransportLineVO> findTransportLineByIds(List<Long> ids);

    /**
     * 删除线路
     *
     * @param id 线路ID
     */
    void deleteTransportLine(Long id);

}
