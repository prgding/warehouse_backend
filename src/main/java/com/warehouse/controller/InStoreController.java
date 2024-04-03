package com.warehouse.controller;

import com.warehouse.entity.InStore;
import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import com.warehouse.service.InStoreService;
import com.warehouse.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/instore")
@RestController
@RequiredArgsConstructor
@Api(tags = "02-入库管理")
public class InStoreController {

    //注入StoreService
    private final StoreService StoreService;

    //注入InStoreService
    private final InStoreService inStoreService;

    /**
     * 查询所有仓库的url接口/instore/store-list
     */
    @GetMapping("/store-list")
    @ApiOperation("查询所有仓库")
    public Result storeList() {
        //执行业务
        List<Store> storeList = StoreService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 分页查询入库单的url接口/instore/instore-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数InStore对象用于接收请求参数仓库id storeId、商品名称productName、
     * 起止时间startTime和endTime;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/instore-page-list")
    @ApiOperation("分页查询入库单")
    public Result inStorePageList(Page page, InStore inStore) {
        //执行业务
        page = inStoreService.queryInStorePage(page, inStore);
        //响应
        return Result.ok(page);
    }

    /**
     * 确定入库的url接口/instore/instore-confirm
     *
     * @RequestBody InStore inStore将请求传递的json数据封装到参数InStore对象;
     */
    @PostMapping("/instore-confirm")
    @ApiOperation("确定入库")
    public Result confirmInStore(@RequestBody InStore inStore) {
        //执行业务
        Result result = inStoreService.confirmInStore(inStore);
        //响应
        return result;
    }
}
