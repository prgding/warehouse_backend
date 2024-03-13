package com.warehouse.service.impl;

import com.warehouse.entity.Brand;
import com.warehouse.mapper.BrandMapper;
import com.warehouse.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.pn.service.impl.BrandServiceImpl")
@Service
public class BrandServiceImpl implements BrandService {

    //注入BrandMapper
    @Autowired
    private BrandMapper brandMapper;

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
}
