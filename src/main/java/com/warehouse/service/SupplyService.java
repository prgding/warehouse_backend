package com.warehouse.service;

import com.warehouse.entity.Supply;

import java.util.List;

public interface SupplyService {

    //查询所有供应商的业务方法
    List<Supply> queryAllSupply();
}
