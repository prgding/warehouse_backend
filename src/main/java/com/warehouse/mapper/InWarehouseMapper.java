package com.warehouse.mapper;

import com.warehouse.entity.InWarehouse;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InWarehouseMapper {

    //添加入库单的方法
    int insertInWarehouse(InWarehouse inWarehouse);

    //查询入库单总行数的方法
    int selectInWarehouseCount(InWarehouse inWarehouse);

    //根据id将入库单状态改为已入库的方法
    int updateIsInById(Integer insId);

    List<InWarehouse> selectInWarehousePage(@Param("page") Page page, @Param("inWarehouse") InWarehouse inWarehouse);

    int updateIsCreatedById(Integer buyId);
}
