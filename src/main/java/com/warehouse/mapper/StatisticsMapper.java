package com.warehouse.mapper;

import com.warehouse.entity.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsMapper {

    //统计各个仓库商品库存数量的方法
    List<Statistics> statisticsWarehouseStock(@Param("date") String date);

    double findCapacitySum();

    List<Integer> inOut(@Param("date") String date);

    Integer inNum(@Param("date") String date);

    Integer outNum(@Param("date") String date);

    Integer todayIn();

    Integer todayOut();
}
