package com.warehouse.mapper;

import com.warehouse.entity.ProductBrand;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductBrandMapper {

    //查询所有品牌的方法
    List<ProductBrand> findAllBrand();

    int selectBrandCount(ProductBrand productBrand);

    List<ProductBrand> selectBrandPage(@Param("page") Page page, @Param("productBrand") ProductBrand productBrand);

    void saveBrand(ProductBrand productBrand);

    void updateBrand(ProductBrand productBrand);

    void deleteBrand(Integer id);
}
