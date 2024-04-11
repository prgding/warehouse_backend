package com.warehouse.service.impl;

import com.warehouse.entity.OutWarehouse;
import com.warehouse.entity.Product;
import com.warehouse.entity.Result;
import com.warehouse.mapper.OutWarehouseMapper;
import com.warehouse.mapper.ProductMapper;
import com.warehouse.page.Page;
import com.warehouse.service.OutWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutWarehouseServiceImpl implements OutWarehouseService {

    //注入OutWarehouseMapper
    private final OutWarehouseMapper outWarehouseMapper;

    //注入ProductMapper
    private final ProductMapper productMapper;

    //添加出库单的业务方法
    @Override
    public Result saveOutWarehouse(OutWarehouse outWarehouse) {
        //添加出库单
        int i = outWarehouseMapper.insertOutWarehouse(outWarehouse);
        if (i > 0) {
            return Result.ok("添加出库单成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加出库单失败！");
    }

    //分页查询出库单的业务方法
    @Override
    public Page outWarehousePage(Page page, OutWarehouse outWarehouse) {

        //查询出库单总行数
        int outWarehouseCount = outWarehouseMapper.outWarehouseCount(outWarehouse);

        //分页查询出库单
        List<OutWarehouse> outWarehouseList = outWarehouseMapper.outWarehousePage(page, outWarehouse);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(outWarehouseCount);
        page.setResultList(outWarehouseList);

        return page;
    }

    //确定出库的业务方法
    @Transactional//事务处理
    @Override
    public Result confirmOutWarehouse(OutWarehouse outWarehouse) {

        //根据商品id查询商品
        Product product = productMapper.selectProductById(outWarehouse.getProductId());
        if (outWarehouse.getOutNum() > product.getProductStock()) {
            return Result.err(Result.CODE_ERR_BUSINESS, "商品库存不足");
        }

        //根据id将出库单状态改为已出库
        int i = outWarehouseMapper.updateIsOutById(outWarehouse.getOutsId());
        if (i > 0) {
            //根据商品id减商品库存
            int j = productMapper.addInventById(outWarehouse.getProductId(), -outWarehouse.getOutNum());
            if (j > 0) {
                return Result.ok("出库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
    }

    @Override
    public Result deleteOutWarehouse(Integer id) {
        int i = outWarehouseMapper.deleteOutWarehouse(id);
        if (i > 0) {
            return Result.ok("删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败！");
    }
}
