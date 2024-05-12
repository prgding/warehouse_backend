package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.Warehouse;
import com.warehouse.page.Page;
import com.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/warehouse")
@RestController
@RequiredArgsConstructor
@Api(tags = "09-仓库管理")
@CrossOrigin(origins = "*")
public class WarehouseController {

    //注入WarehouseService
    private final WarehouseService warehouseService;

    /**
     * 分页查询仓库的url接口/warehouse/warehouse-page-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Warehouse对象用于接收请求参数仓库名称warehouseName、仓库地址warehouseAddress、
     * 联系人concat、联系电话phone;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/warehouse-page-list")
    public Result warehousePageList(Page page, Warehouse warehouse) {
        //执行业务
        page = warehouseService.queryWarehousePage(page, warehouse);
        //响应
        return Result.ok(page);
    }

    /**
     * 校验仓库编号是否已存在的url接口/warehouse/warehouse-num-check
     */
    @GetMapping("/warehouse-num-check")
    public Result checkWarehouseNum(String warehouseNum) {
        //执行业务
        //响应
        return warehouseService.checkWarehouseNum(warehouseNum);
    }

    /**
     * 添加仓库的url接口/warehouse/warehouse-add
     *
     * @param warehouse 将请求传递的json数据封装到参数Warehouse对象;
     */
    @PostMapping("/warehouse-add")
    public Result addWarehouse(@RequestBody Warehouse warehouse) {
        //执行业务
        //响应
        return warehouseService.saveWarehouse(warehouse);
    }

    /**
     * 修改仓库的url接口/warehouse/warehouse-update
     *
     * @param warehouse 将请求传递的json数据封装到参数Warehouse对象;
     */
    @PutMapping("/warehouse-update")
    public Result updateWarehouse(@RequestBody Warehouse warehouse) {
        //执行业务
        //响应
        return warehouseService.updateWarehouse(warehouse);
    }

    /**
     * 删除仓库的url接口/warehouse/warehouse-delete/{warehouseId}
     *
     * @param warehouseId 将路径占位符warehouseId的值赋值给参数变量warehouseId;
     */
    @DeleteMapping("/warehouse-delete/{warehouseId}")
    public Result deleteWarehouse(@PathVariable Integer warehouseId) {
        //执行业务
        //响应
        return warehouseService.deleteWarehouse(warehouseId);
    }
}
