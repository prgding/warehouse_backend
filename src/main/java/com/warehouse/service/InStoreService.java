package com.warehouse.service;

import com.warehouse.entity.InStore;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface InStoreService {

    //添加入库单的业务方法
    Result saveInStore(InStore inStore, Integer buyId);

    //分页查询入库单的业务方法
    Page queryInStorePage(Page page, InStore inStore);

    //确定入库的业务方法
    Result confirmInStore(InStore inStore);
}
