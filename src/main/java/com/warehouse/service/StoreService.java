package com.warehouse.service;

import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;

import java.util.List;

public interface StoreService {

    //查询所有仓库的业务方法
    List<Store> queryAllStore();

    //分页查询仓库的业务方法
    Page queryStorePage(Page page, Store store);

    //校验仓库编号是否已存在的业务方法
    Result checkStoreNum(String storeNum);

    //添加仓库的业务方法
    Result saveStore(Store store);

    //修改仓库的业务方法
    Result updateStore(Store store);

    //删除仓库的业务方法
    Result deleteStore(Integer storeId);
}
