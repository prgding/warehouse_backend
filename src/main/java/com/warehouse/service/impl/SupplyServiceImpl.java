package com.warehouse.service.impl;

import com.warehouse.entity.Supply;
import com.warehouse.mapper.SupplyMapper;
import com.warehouse.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.pn.service.impl.SupplyServiceImpl")
@Service
public class SupplyServiceImpl implements SupplyService {

    //注入SupplyMapper
    @Autowired
    private SupplyMapper supplyMapper;

    /*
      查询所有供应商的业务方法
     */
    //对查询到的所有供应商进行缓存,缓存到redis的键为all:supply
    @Cacheable(key = "'all:supply'")
    @Override
    public List<Supply> queryAllSupply() {
        //查询所有供应商
        return supplyMapper.findAllSupply();
    }
}
