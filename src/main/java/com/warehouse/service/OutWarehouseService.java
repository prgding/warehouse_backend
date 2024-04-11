package com.warehouse.service;

import com.warehouse.entity.OutWarehouse;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface OutWarehouseService {

    //添加出库单的业务方法
    Result saveOutWarehouse(OutWarehouse outWarehouse);

    //分页查询出库单的业务方法
    Page outWarehousePage(Page page, OutWarehouse outWarehouse);

    //确定出库的业务方法
    Result confirmOutWarehouse(OutWarehouse outWarehouse);

    Result deleteOutWarehouse(Integer id);
}
