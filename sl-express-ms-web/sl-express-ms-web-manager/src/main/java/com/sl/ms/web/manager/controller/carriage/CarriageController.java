package com.sl.ms.web.manager.controller.carriage;

import com.sl.ms.carriage.appi.CarriageFeign;
import com.sl.ms.carriage.domain.dto.CarriageDTO;
import com.sl.ms.web.manager.vo.carriage.CarriageVO;
import com.sl.transport.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carriages")
@Api(tags = "运费管理")
@Slf4j
public class CarriageController {
    @Autowired
    private CarriageFeign carriageFeign;

    @ApiOperation(value = "运费模板列表")
    @GetMapping("")
    public R<List<CarriageVO>> findAll() {
        List<CarriageDTO> dto = carriageFeign.findAll();
        return R.success(dto.stream().map(x -> {
            CarriageVO carriageVO = new CarriageVO();
            BeanUtils.copyProperties(x, carriageVO);
            return carriageVO;
        }).collect(Collectors.toList()));
    }

    @ApiOperation(value = "新增/修改运费模板")
    @PostMapping("")
    public R<CarriageVO> saveOrUpdate(@RequestBody CarriageDTO carriageDto) {
        CarriageDTO dto = carriageFeign.saveOrUpdate(carriageDto);
        if (dto == null) {
            return R.error("运费模板存在重复");
        }
        CarriageVO vo = new CarriageVO();
        BeanUtils.copyProperties(dto, vo);
        return R.success(vo);
    }

    @ApiOperation(value = "删除运费模板")
    @DeleteMapping("{id}")
    public R<CarriageVO> delete(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        carriageFeign.delete(id);
        return R.success();
    }
}
