package com.warehouse.mapper;

import com.warehouse.entity.Unit;
import com.warehouse.page.Page;

import java.util.List;

public interface UnitMapper {

    //查询所有单位的方法
    List<Unit> findAllUnit();

    int selectUnitCount(Unit unit);

    List<Unit> selectUnitPage(Page page, Unit unit);

    void saveUnit(Unit unit);

    void updateUnit(Unit unit);

    void deleteUnit(Integer id);
}
