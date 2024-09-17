package com.sl.transport.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.domain.OrganDTO;
import com.sl.transport.enums.ExceptionEnum;
import com.sl.transport.repository.OrganRepository;
import com.sl.transport.service.OrganService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrganServiceImpl implements OrganService {

    @Resource
    private OrganRepository organRepository;

    @Override
    public OrganDTO findByBid(Long bid) {
        OrganDTO organDTO = this.organRepository.findByBid(bid);
        if (ObjectUtil.isNotEmpty(organDTO)) {
            return organDTO;
        }
        throw new SLException(ExceptionEnum.ORGAN_NOT_FOUND);
    }

    @Override
    public List<OrganDTO> findAll(String name) {
        return this.organRepository.findAll(name);
    }
}
