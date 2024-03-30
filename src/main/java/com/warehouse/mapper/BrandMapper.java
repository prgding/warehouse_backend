package com.warehouse.mapper;

import com.warehouse.entity.Brand;

import java.util.List;

public interface BrandMapper {

    //查询所有品牌的方法
    List<Brand> findAllBrand();
}
