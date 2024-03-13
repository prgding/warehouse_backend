package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import com.warehouse.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/store")
@RestController
public class StoreController {

    //注入StoreService
    @Autowired
    private StoreService storeService;

    /**
     * 分页查询仓库的url接口/store/store-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Store对象用于接收请求参数仓库名称storeName、仓库地址storeAddress、
     * 联系人concat、联系电话phone;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/store-page-list")
    public Result storePageList(Page page, Store store){
        //执行业务
        page = storeService.queryStorePage(page, store);
        //响应
        return Result.ok(page);
    }

    /**
     * 校验仓库编号是否已存在的url接口/store/store-num-check
     */
    @RequestMapping("/store-num-check")
    public Result checkStoreNum(String storeNum){
        //执行业务
        Result result = storeService.checkStoreNum(storeNum);
        //响应
        return result;
    }

    /**
     * 添加仓库的url接口/store/store-add
     *
     * @RequestBody Store store将请求传递的json数据封装到参数Store对象;
     */
    @RequestMapping("/store-add")
    public Result addStore(@RequestBody Store store){
        //执行业务
        Result result = storeService.saveStore(store);
        //响应
        return result;
    }

    /**
     * 修改仓库的url接口/store/store-update
     *
     * @RequestBody Store store将请求传递的json数据封装到参数Store对象;
     */
    @RequestMapping("/store-update")
    public Result updateStore(@RequestBody Store store){
        //执行业务
        Result result = storeService.updateStore(store);
        //响应
        return  result;
    }

    /**
     * 删除仓库的url接口/store/store-delete/{storeId}
     *
     * @PathVariable Integer storeId将路径占位符storeId的值赋值给参数变量storeId;
     */
    @RequestMapping("/store-delete/{storeId}")
    public Result deleteStore(@PathVariable Integer storeId){
        //执行业务
        Result result = storeService.deleteStore(storeId);
        //响应
        return result;
    }

    /**
     * 导出数据的url接口/store/exportTable
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Store对象用于接收请求参数仓库名称storeName、仓库地址storeAddress、
     * 联系人concat、联系电话phone;
     *
     * 返回值Result对象向客户端响应组装了当前页数据的List;
     */
    @RequestMapping("/exportTable")
    public Result exportTable(Page page, Store store){
        //分页查询仓库
        page = storeService.queryStorePage(page, store);
        //拿到当前页数据
        List<?> resultList = page.getResultList();
        //响应
        return Result.ok(resultList);
    }
}
