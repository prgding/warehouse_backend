package com.warehouse.mapper;

import com.warehouse.entity.Unit;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitMapper {

    //查询所有单位的方法
    List<Unit> findAllUnit();

    int selectUnitCount(Unit unit);

    List<Unit> selectUnitPage(@Param("page") Page page, @Param("unit") Unit unit);

    void saveUnit(Unit unit);

    void updateUnit(Unit unit);

    void deleteUnit(Integer id);
}
