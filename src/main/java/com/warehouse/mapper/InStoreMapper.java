package com.warehouse.mapper;

import com.warehouse.entity.InStore;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InStoreMapper {

    //添加入库单的方法
    int insertInStore(InStore inStore);

    //查询入库单总行数的方法
    int selectInStoreCount(InStore inStore);

    //分页查询入库单的方法
    List<InStore> selectInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);

    //根据id将入库单状态改为已入库的方法
    int updateIsInById(Integer insId);
}
