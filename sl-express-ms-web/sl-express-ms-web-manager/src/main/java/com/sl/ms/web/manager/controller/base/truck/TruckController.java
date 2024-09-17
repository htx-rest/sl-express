package com.sl.ms.web.manager.controller.base.truck;

import com.sl.ms.web.manager.service.BaseTruckService;
import com.sl.ms.web.manager.vo.baseTruck.*;
import com.sl.transport.common.util.PageResponse;
import com.sl.transport.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 车辆管理
 */
@RestController
@Api(tags = "车辆管理")
@Slf4j
public class TruckController {
    @Resource
    private BaseTruckService baseTruckService;

    @ApiOperation(value = "添加车辆")
    @PostMapping("/truck")
    public R<TruckVO> saveTruck(@RequestBody TruckVO vo) {
        baseTruckService.saveTruck(vo);
        return R.success();
    }

    @ApiOperation(value = "更新车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @PutMapping("/truck/{id}")
    public R<TruckVO> updateTruck(@PathVariable(name = "id") Long id, @RequestBody TruckVO vo) {
        baseTruckService.updateTruck(id, vo);
        return R.success();
    }

    @ApiOperation(value = "获取车辆分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页尺寸", required = true, example = "10"),
            @ApiImplicitParam(name = "truckTypeId", value = "车辆类型id"),
            @ApiImplicitParam(name = "workStatus", value = "状态"),
            @ApiImplicitParam(name = "licensePlate", value = "车牌号码")
    })
    @GetMapping("/truck/page")
    public R<PageResponse<TruckVO>> findTruckByPage(@RequestParam(name = "page", required = false) Integer page,
                                                    @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                    @RequestParam(name = "truckTypeId", required = false) Long truckTypeId,
                                                    @RequestParam(name = "workStatus", required = false) Integer workStatus,
                                                    @RequestParam(name = "licensePlate", required = false) String licensePlate) {
        PageResponse<TruckVO> truckByPage = baseTruckService.findTruckByPage(page, pageSize, truckTypeId, workStatus, licensePlate);
        return R.success(truckByPage);
    }

    @ApiOperation(value = "获取车辆详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @GetMapping("/truck/{id}")
    public R<TruckVO> findTruckById(@PathVariable(name = "id") Long id) {
        TruckVO truckById = baseTruckService.findTruckDetail(id);
        return R.success(truckById);
    }

    @ApiOperation(value = "统计车辆")
    @GetMapping("/count")
    public R<Map<Integer, Long>> count() {
        Map<Integer, Long> count = baseTruckService.countTruck();
        return R.success(count);
    }

    @ApiOperation(value = "停用车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @PutMapping("/disable/{id}")
    public R disable(@PathVariable(name = "id") Long id) {
        baseTruckService.disableTruck(id);
        return R.success();
    }

    @ApiOperation(value = "启用车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @PutMapping("/enable/{id}")
    public R enable(@PathVariable(name = "id") Long id) {
        baseTruckService.enableTruck(id);
        return R.success();
    }

    @ApiOperation(value = "删除车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @DeleteMapping("/del/{id}")
    public R del(@PathVariable(name = "id") Long id) {
        baseTruckService.delTruck(id);
        return R.success();
    }

    @ApiOperation(value = "保存车辆行驶证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @PostMapping("/truck/{id}/license")
    public R<TruckLicenseVO> saveTruckLicense(@PathVariable(name = "id") Long id, @RequestBody TruckLicenseVO vo) {
        baseTruckService.saveTruckLicense(id, vo);
        return R.success();
    }

    @ApiOperation(value = "获取车辆行驶证详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @GetMapping("/truck/{id}/license")
    public R<TruckLicenseVO> findTruckLicenseById(@PathVariable(name = "id") Long id) {
        TruckLicenseVO truckLicenseById = baseTruckService.findTruckLicenseById(id);
        return R.success(truckLicenseById);
    }

    @ApiOperation(value = "获取车辆车次信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = true, example = "1")
    })
    @GetMapping("/truck/{id}/transportTrips")
    public R<List<TruckDriverTripsVO>> findTruckTransportTrips(@PathVariable(name = "id") Long id) {
        List<TruckDriverTripsVO> truckTransportTrips = baseTruckService.findTruckTrips(id);
        return R.success(truckTransportTrips);
    }

    /**
     * 获取绑定司机
     *
     * @param truckId 车辆ID
     * @return 司机数量
     */
    @ApiOperation(value = "获取绑定司机")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "truckId", value = "车辆id", required = true, example = "1")
    })
    @GetMapping("/bindingDrivers/{truckId}")
    R<List<DriverVO>> bindingDrivers(@PathVariable(name = "truckId") Long truckId) {
        List<DriverVO> driverVOS = baseTruckService.bindingTrucks(truckId);
        return R.success(driverVOS);
    }

    /**
     * 获取未绑定车辆的司机
     *
     * @return 司机数量
     */
    @ApiOperation(value = "获取未绑定车辆的司机")
    @GetMapping("/unBindingDrivers/")
    R<List<DriverVO>> unBindingDrivers() {
        List<DriverVO> driverVOS = baseTruckService.unBindingDrivers();
        return R.success(driverVOS);
    }

    /**
     * 获取已经启用的车辆
     * 没有绑定其他车次的车辆
     *
     * @return 车辆
     */
    @ApiOperation(value = "获取已经启用的车辆")
    @GetMapping("/workingTrucks")
    R<List<TruckVO>> workingTrucks() {
        List<TruckVO> truckVOS = baseTruckService.workingTrucks();
        return R.success(truckVOS);
    }

    /**
     * 获取已经停用的车辆
     * 没有绑定2个司机的车辆
     *
     * @return 车辆
     */
    @ApiOperation(value = "获取已经停用的车辆")
    @GetMapping("/unWorkingTrucks")
    R<List<TruckVO>> unWorkingTrucks() {
        List<TruckVO> truckVOS = baseTruckService.unWorkingTrucks();
        return R.success(truckVOS);
    }

    @ApiOperation(value = "车辆绑定司机 【司机：车辆 = n:1】")
    @PostMapping("/truck/truckDrivers")
    public R BindingDrivers(@RequestBody BindingDriversVO bindingDriversVO) {
        baseTruckService.bindingDrivers(bindingDriversVO);
        return R.success();
    }
}
