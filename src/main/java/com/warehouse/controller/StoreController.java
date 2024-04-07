package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import com.warehouse.service.StoreService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/store")
@RestController
@RequiredArgsConstructor
@Api(tags = "10-仓库管理")
public class StoreController {

    //注入StoreService
    private final StoreService storeService;

    /**
     * 分页查询仓库的url接口/store/store-page-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Store对象用于接收请求参数仓库名称storeName、仓库地址storeAddress、
     * 联系人concat、联系电话phone;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/store-page-list")
    public Result storePageList(Page page, Store store) {
        //执行业务
        page = storeService.queryStorePage(page, store);
        //响应
        return Result.ok(page);
    }

    /**
     * 校验仓库编号是否已存在的url接口/store/store-num-check
     */
    @GetMapping("/store-num-check")
    public Result checkStoreNum(String storeNum) {
        //执行业务
        //响应
        return storeService.checkStoreNum(storeNum);
    }

    /**
     * 添加仓库的url接口/store/store-add
     *
     * @param store 将请求传递的json数据封装到参数Store对象;
     */
    @PostMapping("/store-add")
    public Result addStore(@RequestBody Store store) {
        //执行业务
        //响应
        return storeService.saveStore(store);
    }

    /**
     * 修改仓库的url接口/store/store-update
     *
     * @param store 将请求传递的json数据封装到参数Store对象;
     */
    @PutMapping("/store-update")
    public Result updateStore(@RequestBody Store store) {
        //执行业务
        //响应
        return storeService.updateStore(store);
    }

    /**
     * 删除仓库的url接口/store/store-delete/{storeId}
     *
     * @param storeId 将路径占位符storeId的值赋值给参数变量storeId;
     */
    @DeleteMapping("/store-delete/{storeId}")
    public Result deleteStore(@PathVariable Integer storeId) {
        //执行业务
        //响应
        return storeService.deleteStore(storeId);
    }

    /**
     * 导出数据的url接口/store/exportTable
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Store对象用于接收请求参数仓库名称storeName、仓库地址storeAddress、
     * 联系人concat、联系电话phone;
     * 返回值Result对象向客户端响应组装了当前页数据的List;
     */
    @PostMapping("/exportTable")
    public Result exportTable(Page page, Store store) {
        //分页查询仓库
        page = storeService.queryStorePage(page, store);
        //拿到当前页数据
        List<?> resultList = page.getResultList();
        //响应
        return Result.ok(resultList);
    }
}
