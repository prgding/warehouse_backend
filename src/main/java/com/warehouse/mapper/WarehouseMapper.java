package com.warehouse.mapper;

import com.warehouse.entity.Warehouse;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseMapper {

    //查询所有仓库的方法
    List<Warehouse> findAllWarehouse();

    //查询仓库总行数的方法
    int selectWarehouseCount(Warehouse warehouse);

    //分页查询仓库的方法
    List<Warehouse> selectWarehousePage(@Param("page") Page page, @Param("warehouse") Warehouse warehouse);

    //根据仓库编号查询仓库的方法
    Warehouse selectWarehouseByNum(String WarehouseNum);

    //添加仓库的方法
    int insertWarehouse(Warehouse warehouse);

    //根据仓库id修改仓库的方法
    int updateWarehouseById(Warehouse warehouse);

    //根据仓库id删除仓库的方法
    int deleteWarehouseById(Integer WarehouseId);
}
