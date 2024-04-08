package com.warehouse.service.impl;

import com.warehouse.entity.Brand;
import com.warehouse.mapper.BrandMapper;
import com.warehouse.page.Page;
import com.warehouse.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.pn.service.impl.BrandServiceImpl")
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    //注入BrandMapper
    private final BrandMapper brandMapper;

    /*
      查询所有品牌的业务方法
     */
    //对查询到的所有品牌进行缓存,缓存到redis的键为all:brand
    @Cacheable(key = "'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {
        //查询所有品牌
        return brandMapper.findAllBrand();
    }

    @Override
    public Page queryBrandPage(Page page, Brand brand) {
        //查询品牌总数
        int brandCount = brandMapper.selectBrandCount(brand);
        List<Brand> brandList = brandMapper.selectBrandPage(page, brand);

        page.setTotalNum(brandCount);
        page.setResultList(brandList);
        return page;
    }

    @Override
    public void saveBrand(Brand brand) {
        log.info("brand = " + brand);
        brandMapper.saveBrand(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        brandMapper.deleteBrand(id);
    }
}
