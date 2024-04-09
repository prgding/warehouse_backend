package com.warehouse.service;

import com.warehouse.entity.ProductBrand;
import com.warehouse.page.Page;

import java.util.List;

public interface ProductBrandService {

    //查询所有品牌的业务方法
    List<ProductBrand> queryAllBrand();

    Page queryBrandPage(Page page, ProductBrand productBrand);

    void saveBrand(ProductBrand productBrand);

    void updateBrand(ProductBrand productBrand);

    void deleteBrand(Integer id);
}
