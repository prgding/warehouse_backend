package com.warehouse.service;

import com.warehouse.entity.OutStore;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface OutStoreService {

    //添加出库单的业务方法
    Result saveOutStore(OutStore outStore);

    //分页查询出库单的业务方法
    Page outStorePage(Page page, OutStore outStore);

    //确定出库的业务方法
    Result confirmOutStore(OutStore outStore);
}
