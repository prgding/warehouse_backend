package com.warehouse.service;

import com.warehouse.entity.Brand;
import com.warehouse.page.Page;

import java.util.List;

public interface BrandService {

    //查询所有品牌的业务方法
    List<Brand> queryAllBrand();

    Page queryBrandPage(Page page, Brand brand);

    void saveBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);
}
