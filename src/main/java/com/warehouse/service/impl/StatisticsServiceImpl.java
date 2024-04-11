package com.warehouse.service.impl;

import com.warehouse.entity.Statistics;
import com.warehouse.mapper.StatisticsMapper;
import com.warehouse.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    //注入StatisticsMapper
    @Autowired
    private StatisticsMapper statisticsMapper;

    //统计各个仓库商品库存数量的业务方法
    @Override
    public List<Statistics> statisticsWarehouseStock() {
        return statisticsMapper.statisticsWarehouseStock();
    }
}
