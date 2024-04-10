package com.warehouse.service.impl;

import com.warehouse.entity.ProductBrand;
import com.warehouse.mapper.ProductBrandMapper;
import com.warehouse.page.Page;
import com.warehouse.service.ProductBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductBrandServiceImpl implements ProductBrandService {

    //注入BrandMapper
    private final ProductBrandMapper productBrandMapper;

    /*
      查询所有品牌的业务方法
     */
    @Override
    public List<ProductBrand> queryAllBrand() {
        //查询所有品牌
        return productBrandMapper.findAllBrand();
    }

    @Override
    public Page queryBrandPage(Page page, ProductBrand productBrand) {
        //查询品牌总数
        int brandCount = productBrandMapper.selectBrandCount(productBrand);
        List<ProductBrand> productBrandList = productBrandMapper.selectBrandPage(page, productBrand);

        page.setTotalNum(brandCount);
        page.setResultList(productBrandList);
        return page;
    }

    @Override
    public void saveBrand(ProductBrand productBrand) {
        productBrandMapper.saveBrand(productBrand);
    }

    @Override
    public void updateBrand(ProductBrand productBrand) {
        productBrandMapper.updateBrand(productBrand);
    }

    @Override
    public void deleteBrand(Integer id) {
        productBrandMapper.deleteBrand(id);
    }
}
