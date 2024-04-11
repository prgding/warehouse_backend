package com.warehouse.service.impl;

import com.warehouse.entity.InWarehouse;
import com.warehouse.entity.Result;
import com.warehouse.mapper.InWarehouseMapper;
import com.warehouse.mapper.ProductMapper;
import com.warehouse.page.Page;
import com.warehouse.service.InWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InWarehouseServiceImpl implements InWarehouseService {

    //注入InWarehouseMapper
    private final InWarehouseMapper inWarehouseMapper;

    //注入ProductMapper
    private final ProductMapper productMapper;

    @Override
    public Result saveInWarehouse(InWarehouse inWarehouse) {
        //添加出库单
        int i = inWarehouseMapper.insertInWarehouse(inWarehouse);
        if (i > 0) {
            return Result.ok("添加入库单成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加入库单失败！");
    }

    //分页查询入库单的业务方法
    @Override
    public Page queryInWarehousePage(Page page, InWarehouse inWarehouse) {

        //查询入库单总行数
        int inWarehouseCount = inWarehouseMapper.selectInWarehouseCount(inWarehouse);

        //分页查询入库单
        List<InWarehouse> inWarehouseList = inWarehouseMapper.selectInWarehousePage(page, inWarehouse);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(inWarehouseCount);
        page.setResultList(inWarehouseList);

        return page;
    }

    //确定入库的业务方法
    @Transactional//事务处理
    @Override
    public Result confirmInWarehouse(InWarehouse inWarehouse) {

        //根据id将入库单状态改为已入库
        int i = inWarehouseMapper.updateIsInById(inWarehouse.getInsId());
        if (i > 0) {
            //根据商品id增加商品库存
            int j = productMapper.addInventById(inWarehouse.getProductId(), inWarehouse.getInNum());
            if (j > 0) {
                return Result.ok("入库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
    }
}
