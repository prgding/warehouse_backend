package com.warehouse.service;

import com.warehouse.entity.Result;
import com.warehouse.entity.Warehouse;
import com.warehouse.page.Page;

import java.util.List;

public interface WarehouseService {

    //查询所有仓库的业务方法
    List<Warehouse> queryAllWarehouse();

    //分页查询仓库的业务方法
    Page queryWarehousePage(Page page, Warehouse warehouse);

    //校验仓库编号是否已存在的业务方法
    Result checkWarehouseNum(String warehouseNum);

    //添加仓库的业务方法
    Result saveWarehouse(Warehouse warehouse);

    //修改仓库的业务方法
    Result updateWarehouse(Warehouse warehouse);

    //删除仓库的业务方法
    Result deleteWarehouse(Integer warehouseId);
}
