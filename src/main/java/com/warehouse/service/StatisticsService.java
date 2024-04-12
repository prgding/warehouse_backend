package com.warehouse.service;

import com.warehouse.entity.Statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    //统计各个仓库商品库存数量的业务方法
    List<Statistics> statisticsWarehouseStock();

    Double allOccupancyRate();

    Map<String, Object> trend();

    List<Integer> inAndOut();

    List<Integer> todayInOut();
}
