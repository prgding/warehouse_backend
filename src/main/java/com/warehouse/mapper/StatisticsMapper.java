package com.warehouse.mapper;

import com.warehouse.entity.Statistics;

import java.util.List;

public interface StatisticsMapper {

    //统计各个仓库商品库存数量的方法
    public List<Statistics> statisticsStoreInvent();
}
