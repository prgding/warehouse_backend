package com.warehouse.controller;

import com.warehouse.entity.OutWarehouse;
import com.warehouse.entity.Result;
import com.warehouse.entity.Warehouse;
import com.warehouse.page.Page;
import com.warehouse.service.OutWarehouseService;
import com.warehouse.service.WarehouseService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/out-warehouse")
@RestController
@Api(tags = "04-出库管理")
@RequiredArgsConstructor
public class OutWarehouseController {

    //注入OutWarehouseService
    private final OutWarehouseService outWarehouseService;

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    //注入WarehouseService
    private final WarehouseService warehouseService;

    /**
     * 添加出库单的url接口/out-warehouse/out-warehouse-add
     *
     * @param outWarehouse 将添加的出库单信息的json数据封装到参数OutWarehouse对象;
     * @param token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/add")
    public Result addOutWarehouse(@RequestBody OutWarehouse outWarehouse,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        outWarehouse.setCreateBy(createBy);

        return outWarehouseService.saveOutWarehouse(outWarehouse);
    }

    /**
     * 查询所有仓库的url接口/out-warehouse/warehouse-list
     */
    @GetMapping("/list")
    public Result warehouseList() {
        //执行业务
        List<Warehouse> warehouseList = warehouseService.queryAllWarehouse();
        //响应
        return Result.ok(warehouseList);
    }

    /**
     * 分页查询出库单的url接口/out-warehouse/out-warehouse-page-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数OutWarehouse对象用于接收请求参数仓库id warehouseId、商品名称productName、
     * 是否出库isOut、起止时间startTime和endTime;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/page-list")
    public Result outWarehousePageList(Page page, OutWarehouse outWarehouse) {
        //执行业务
        page = outWarehouseService.outWarehousePage(page, outWarehouse);
        //响应
        return Result.ok(page);
    }

    /**
     * 确定出库的url接口/out-warehouse/out-warehouse-confirm
     */
    @PutMapping("/confirm")
    public Result confirmOutWarehouse(@RequestBody OutWarehouse outWarehouse) {
        return outWarehouseService.confirmOutWarehouse(outWarehouse);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteOutWarehouse(@PathVariable Integer id) {
        return outWarehouseService.deleteOutWarehouse(id);
    }
}
