package com.warehouse.mapper;

import com.warehouse.entity.Brand;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {

    //查询所有品牌的方法
    List<Brand> findAllBrand();

    int selectBrandCount(Brand brand);

    List<Brand> selectBrandPage(@Param("page") Page page, @Param("brand") Brand brand);

    void saveBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);
}
