package com.warehouse.mapper;

import com.warehouse.entity.OutWarehouse;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutWarehouseMapper {

    //添加出库单的方法
    int insertOutWarehouse(OutWarehouse outWarehouse);

    //查询出库单总行数的方法
    int outWarehouseCount(OutWarehouse outWarehouse);

    //分页查询出库单的方法
    List<OutWarehouse> outWarehousePage(@Param("page") Page page, @Param("outWarehouse") OutWarehouse outWarehouse);

    //根据id将出库单状态改为已出库的方法
    int updateIsOutById(Integer outsId);

    int deleteOutWarehouse(Integer id);
}
