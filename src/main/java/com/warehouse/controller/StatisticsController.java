package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.Statistics;
import com.warehouse.service.StatisticsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/statistics")
@RestController
@RequiredArgsConstructor
@Api(tags = "10-统计管理")
@Slf4j
@CrossOrigin(origins = "*")
public class StatisticsController {

    //注入StatisticsService
    private final StatisticsService statisticsService;

    /**
     * 统计各个仓库商品库存数量的url接口/statistics/store-invent
     */
    @GetMapping("/warehouse-stock")
    public Result statisticsWarehouseStock() {
        //执行业务
        List<Statistics> statisticsList = statisticsService.statisticsWarehouseStock();
        //响应
        return Result.ok(statisticsList);
    }

    @GetMapping("occupancy-rate")
    public Result rate() {
        Double rate = statisticsService.allOccupancyRate();
        return Result.ok(rate);
    }

    @GetMapping("warehouse-trend")
    public Result trend() {
        // 获取今年以来，各月份最后一天的各仓库的库存量
        Map<String, Object> result = statisticsService.trend();
        return Result.ok(result);
    }

    @GetMapping("today-in-out")
    public Result todayInOut() {
        // 获取今天的出入库量
        List<Integer> integers = statisticsService.todayInOut();
        return Result.ok(integers);
    }
}
