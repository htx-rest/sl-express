package com.sl.transport.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sl.transport.domain.OrganDTO;
import com.sl.transport.enums.OrganTypeEnum;
import com.sl.transport.repository.OrganRepository;
import org.neo4j.driver.internal.InternalPoint2D;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class OrganRepositoryImpl implements OrganRepository {

    @Resource
    private Neo4jClient neo4jClient;

    @Override
    public OrganDTO findByBid(Long bid) {
        String cypherQuery = StrUtil.format("MATCH (n)\n" +
                "WHERE n.bid = {}\n" +
                "RETURN n", bid);
        return CollUtil.getFirst(executeQuery(cypherQuery));
    }

    @Override
    public List<OrganDTO> findAll(String name) {
        name = StrUtil.removeAll(name, '\'', '"');
        String cypherQuery = StrUtil.isEmpty(name) ?
                "MATCH (n) RETURN n" :
                StrUtil.format("MATCH (n) WHERE n.name CONTAINS '{}' RETURN n", name);
        return executeQuery(cypherQuery);
    }

    private List<OrganDTO> executeQuery(String cypherQuery) {
        return ListUtil.toList(this.neo4jClient.query(cypherQuery)
                .fetchAs(OrganDTO.class) //设置响应的类型
                .mappedBy((typeSystem, record) -> { //对结果进行封装处理
                    Map<String, Object> map = record.get("n").asMap();
                    OrganDTO organDTO = BeanUtil.toBean(map, OrganDTO.class);
                    InternalPoint2D location = (InternalPoint2D) map.get("location");
                    if (ObjectUtil.isNotEmpty(location)) {
                        organDTO.setLongitude(location.x());
                        organDTO.setLatitude(location.y());
                    }
                    //获取类型
                    String type = CollUtil.getFirst(record.get("n").asNode().labels());
                    organDTO.setType(OrganTypeEnum.valueOf(type).getCode());
                    return organDTO;
                }).all());
    }
}
