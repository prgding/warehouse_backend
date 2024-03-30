package com.warehouse.mapper;

import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {

    //查询所有仓库的方法
    List<Store> findAllStore();

    //查询仓库总行数的方法
    int selectStoreCount(Store store);

    //分页查询仓库的方法
    List<Store> selectStorePage(@Param("page") Page page, @Param("store") Store store);

    //根据仓库编号查询仓库的方法
    Store selectStoreByNum(String storeNum);

    //添加仓库的方法
    int insertStore(Store store);

    //根据仓库id修改仓库的方法
    int updateStoreById(Store store);

    //根据仓库id删除仓库的方法
    int deleteStoreById(Integer storeId);
}
