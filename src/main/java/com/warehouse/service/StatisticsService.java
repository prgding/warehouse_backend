package com.warehouse.service;

import com.warehouse.entity.Statistics;

import java.util.List;

public interface StatisticsService {

    //统计各个仓库商品库存数量的业务方法
    List<Statistics> statisticsWarehouseStock();
}
