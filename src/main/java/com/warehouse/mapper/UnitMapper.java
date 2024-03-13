package com.warehouse.mapper;

import com.warehouse.entity.Unit;

import java.util.List;

public interface UnitMapper {

    //查询所有单位的方法
    public List<Unit> findAllUnit();
}
