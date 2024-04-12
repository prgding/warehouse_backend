package com.warehouse.service.impl;

import com.warehouse.entity.Statistics;
import com.warehouse.mapper.StatisticsMapper;
import com.warehouse.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsMapper statisticsMapper;

    public static ArrayList<String> monthList(String pattern) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ArrayList<String> date = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(currentYear, month);
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

            // 如果是当前月份，则输出今天的日期，否则输出月份的最后一天
            if (month == today.getMonthValue()) {
                date.add(formatter.format(today));
                break; // 如果到达当前月份，则不再继续循环后面的月份
            } else {
                date.add(formatter.format(lastDayOfMonth));
            }
        }
        return date;
    }

    private static ArrayList<Map<String, Object>> getSeries(ArrayList<List<Statistics>> lists) {
        ArrayList<Map<String, Object>> series = new ArrayList<>();
        for (int i = 0; i < lists.get(0).size(); i++) {
            Map<String, Object> seriesMap = new HashMap<>();
            ArrayList<Integer> data = new ArrayList<>();
            for (List<Statistics> list : lists) {
                data.add(list.get(i).getTotalStock());
            }
            seriesMap.put("name", lists.get(0).get(i).getWarehouseName());
            seriesMap.put("type", "line");
            seriesMap.put("data", data);
            series.add(seriesMap);
        }
        return series;
    }

    //统计各个仓库商品库存数量的业务方法
    @Override
    public List<Statistics> statisticsWarehouseStock() {
        return statisticsMapper.statisticsWarehouseStock(null);
    }

    @Override
    public Double allOccupancyRate() {
        List<Statistics> statistics = statisticsMapper.statisticsWarehouseStock(null);
        double stock = statistics.stream().mapToInt(Statistics::getTotalStock).sum();
        double capacitySum = statisticsMapper.findCapacitySum();
        return (double) Math.round(stock / capacitySum * 100 * 100) / 100;
    }

    @Override
    public Map<String, Object> trend() {
        ArrayList<List<Statistics>> lists = new ArrayList<>();
        for (int i = 0; i < monthList("yyyy-MM-dd").size(); i++) {
            List<Statistics> statistics = statisticsMapper.statisticsWarehouseStock(monthList("yyyy-MM-dd").get(i));
            lists.add(statistics);
        }
        System.out.println("lists = " + lists);
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> monthList = monthList("MM月");
        map.put("month", monthList);
        map.put("lists", lists);
        map.put("series", getSeries(lists));
        return map;
    }

    @Override
    public List<Integer> inAndOut() {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < monthList("yyyy-MM-dd").size(); i++) {
            Integer inNum = statisticsMapper.inNum(monthList("yyyy-MM-dd").get(i));
            Integer outNum = statisticsMapper.outNum(monthList("yyyy-MM-dd").get(i));
            nums.add(inNum - outNum);
        }
        return nums;
    }

    @Override
    public List<Integer> todayInOut() {
        ArrayList<Integer> nums = new ArrayList<>();
        Integer todayIn = statisticsMapper.todayIn();
        Integer todayOut = statisticsMapper.todayOut();
        nums.add(todayIn);
        nums.add(todayOut);
        return nums;
    }


}
