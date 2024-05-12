package com.warehouse.controller;

import com.warehouse.entity.InWarehouse;
import com.warehouse.entity.Result;
import com.warehouse.entity.Warehouse;
import com.warehouse.page.Page;
import com.warehouse.service.InWarehouseService;
import com.warehouse.service.WarehouseService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/in-warehouse")
@RestController
@RequiredArgsConstructor
@Api(tags = "07-入库管理")
@CrossOrigin(origins = "*")
public class InWarehouseController {

    //注入WarehouseService
    private final WarehouseService WarehouseService;

    //注入InWarehouseService
    private final InWarehouseService inWarehouseService;

    private final TokenUtils tokenUtils;

    /**
     * 查询所有仓库的url接口/inwarehouse/warehouse-list
     */
    @GetMapping("/warehouse-list")
    @ApiOperation("查询所有仓库")
    public Result warehouseList() {
        //执行业务
        List<Warehouse> warehouseList = WarehouseService.queryAllWarehouse();
        //响应
        return Result.ok(warehouseList);
    }

    @PostMapping("/add")
    public Result addInWarehouse(@RequestBody InWarehouse inWarehouse,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        inWarehouse.setCreateBy(createBy);

        return inWarehouseService.saveInWarehouse(inWarehouse);
    }

    /**
     * 分页查询入库单的url接口/inwarehouse/inwarehouse-page-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数InWarehouse对象用于接收请求参数仓库id warehouseId、商品名称productName、
     * 起止时间startTime和endTime;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/page-list")
    @ApiOperation("分页查询入库单")
    public Result inWarehousePageList(Page page, InWarehouse inWarehouse) {
        //执行业务
        page = inWarehouseService.queryInWarehousePage(page, inWarehouse);
        //响应
        return Result.ok(page);
    }

    /**
     * 确定入库的url接口/inwarehouse/inwarehouse-confirm
     *
     * @param inWarehouse 将请求传递的json数据封装到参数InWarehouse对象;
     */
    @PutMapping("/confirm")
    @ApiOperation("确定入库")
    public Result confirmInWarehouse(@RequestBody InWarehouse inWarehouse) {
        return inWarehouseService.confirmInWarehouse(inWarehouse);
    }
}
