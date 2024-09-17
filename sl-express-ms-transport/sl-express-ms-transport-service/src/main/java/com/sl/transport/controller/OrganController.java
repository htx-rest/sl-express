package com.sl.transport.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.domain.OrganDTO;
import com.sl.transport.entity.node.BaseEntity;
import com.sl.transport.service.OrganService;
import com.sl.transport.utils.OrganServiceFactory;
import com.sl.transport.utils.OrganUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 对外提供机构服务，机构、一级转运中心、二级转运中心统称为机构
 *
 * @author zzj
 * @version 1.0
 */
@Api(tags = "机构服务")
@RequestMapping("organs")
@Validated
@RestController
public class OrganController {

    @Resource
    private OrganService organService;
    @Resource
    private ObjectMapper objectMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机构id", dataTypeClass = String.class)
    })
    @ApiOperation(value = "查询", notes = "根据业务id查询机构数据")
    @GetMapping("{id}")
    public OrganDTO queryById(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        return this.organService.findByBid(id);
    }

    @ApiOperation(value = "更新", notes = "更新机构")
    @PutMapping
    public void update(@RequestBody OrganDTO organDTO) {
        BaseEntity entity = OrganUtils.toEntity(organDTO);
        BaseEntity entityData = OrganServiceFactory.getBean(organDTO.getType()).update(entity);
        if (null == entityData) {
            throw new SLException("更新机构失败！", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @ApiOperation(value = "查询所有的机构", notes = "查询所有的机构，如果name不为空的按照name模糊查询")
    @GetMapping("all")
    public List<OrganDTO> findAll(@RequestParam(value = "name", required = false) String name) {
        return this.organService.findAll(name);
    }

    @ApiOperation(value = "查询机构树", notes = "查询机构树")
    @GetMapping("tree")
    public String findAllTree() {
        List<OrganDTO> organList = this.organService.findAll(null);
        if (CollUtil.isEmpty(organList)) {
            return "";
        }

        //构造树结构
        List<Tree<Long>> treeNodes = TreeUtil.build(organList, 0L,
                (organDTO, tree) -> {
                    tree.setId(organDTO.getId());
                    tree.setParentId(organDTO.getParentId());
                    tree.putAll(BeanUtil.beanToMap(organDTO));
                    tree.remove("bid");
                });

        try {
            return this.objectMapper.writeValueAsString(treeNodes);
        } catch (JsonProcessingException e) {
            throw new SLException("序列化json出错！", e);
        }
    }
}
