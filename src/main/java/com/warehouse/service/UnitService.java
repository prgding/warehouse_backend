package com.warehouse.service;

import com.warehouse.entity.Unit;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitService {

    //查询所有单位的业务方法
    List<Unit> queryAllUnit();

    Page queryUnitPage(@Param("page") Page page, @Param("unit") Unit unit);

    void saveUnit(Unit unit);

    void updateUnit(Unit unit);

    void deleteUnit(Integer id);
}
