package com.sl.ms.web.manager.controller.transportLine;

import com.sl.ms.web.manager.service.TransportLineService;
import com.sl.ms.web.manager.vo.transportLine.TransportLineQueryVO;
import com.sl.ms.web.manager.vo.transportLine.TransportLineVO;
import com.sl.transport.common.util.PageResponse;
import com.sl.transport.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 线路管理
 */
@RestController
@Api(tags = "线路管理")
@Slf4j
public class TransportLineController {

    @Resource
    private TransportLineService transportLineService;

    @ApiOperation(value = "添加线路")
    @PostMapping("/transportLine")
    public R<TransportLineVO> saveTransportLine(@RequestBody TransportLineVO vo) {
        transportLineService.saveTransportLine(vo);
        return R.success();
    }

    @ApiOperation(value = "更新线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", required = true, example = "1")
    })
    @PutMapping("/transportLine/{id}")
    public R<TransportLineVO> updateTransportLine(@PathVariable(name = "id") Long id, @RequestBody TransportLineVO vo) {
        transportLineService.updateTransportLine(id, vo);
        return R.success();
    }

    @ApiOperation(value = "获取线路分页数据")
    @PostMapping("/transportLine/page")
    public R<PageResponse<TransportLineVO>> findTransportLineByPage(@RequestBody TransportLineQueryVO transportLineQueryVO) {
        PageResponse<TransportLineVO> transportLineByPage = transportLineService.findTransportLineByPage(transportLineQueryVO);
        return R.success(transportLineByPage);
    }

    @ApiOperation(value = "获取线路详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", required = true, example = "1")
    })
    @GetMapping("/transportLine/{id}")
    public R<TransportLineVO> findTransportLineById(@PathVariable(name = "id") Long id) {
        TransportLineVO transportLineById = transportLineService.findTransportLineById(id);
        return R.success(transportLineById);
    }

    @ApiOperation(value = "删除线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", required = true, example = "1")
    })
    @DeleteMapping("/transportLine/{id}")
    public R deleteTransportLine(@PathVariable(name = "id") Long id) {
        transportLineService.deleteTransportLine(id);
        return R.success();
    }
}
