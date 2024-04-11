package com.warehouse.service;

import com.warehouse.entity.InWarehouse;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface InWarehouseService {

    //添加入库单的业务方法
    Result saveInWarehouse(InWarehouse inWarehouse);

    //分页查询入库单的业务方法
    Page queryInWarehousePage(Page page, InWarehouse inWarehouse);

    //确定入库的业务方法
    Result confirmInWarehouse(InWarehouse inWarehouse);
}
