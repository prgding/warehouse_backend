package com.warehouse.service.impl;

import com.warehouse.entity.InStore;
import com.warehouse.entity.Result;
import com.warehouse.mapper.InStoreMapper;
import com.warehouse.mapper.ProductMapper;
import com.warehouse.mapper.PurchaseMapper;
import com.warehouse.page.Page;
import com.warehouse.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {

    //注入InStoreMapper
    @Autowired
    private InStoreMapper inStoreMapper;

    //注入PurchaseMapper
    @Autowired
    private PurchaseMapper purchaseMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //添加入库单的业务方法
    @Transactional//事务处理
    @Override
    public Result saveInStore(InStore inStore, Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if (i > 0) {
            //根据id将入库单状态改为已生成
            int j = purchaseMapper.updateIsInById(buyId);
            if (j > 0) {
                return Result.ok("入库单添加成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
    }

    //分页查询入库单的业务方法
    @Override
    public Page queryInStorePage(Page page, InStore inStore) {

        //查询入库单总行数
        int inStoreCount = inStoreMapper.selectInStoreCount(inStore);

        //分页查询入库单
        List<InStore> inStoreList = inStoreMapper.selectInStorePage(page, inStore);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(inStoreCount);
        page.setResultList(inStoreList);

        return page;
    }

    //确定入库的业务方法
    @Transactional//事务处理
    @Override
    public Result confirmInStore(InStore inStore) {

        //根据id将入库单状态改为已入库
        int i = inStoreMapper.updateIsInById(inStore.getInsId());
        if (i > 0) {
            //根据商品id增加商品库存
            int j = productMapper.addInventById(inStore.getProductId(), inStore.getInNum());
            if (j > 0) {
                return Result.ok("入库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
    }
}
