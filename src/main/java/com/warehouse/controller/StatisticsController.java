package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.Statistics;
import com.warehouse.service.StatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistics")
@RestController
@Api(tags = "09-统计管理")
public class StatisticsController {

    //注入StatisticsService
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 统计各个仓库商品库存数量的url接口/statistics/store-invent
     */
    @GetMapping("/store-invent")
    public Result statisticsStoreInvent() {
        //执行业务
        List<Statistics> statisticsList = statisticsService.statisticsStoreInvent();
        //响应
        return Result.ok(statisticsList);
    }
}
