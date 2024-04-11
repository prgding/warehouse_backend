package com.warehouse.service.impl;

import com.warehouse.entity.Result;
import com.warehouse.entity.Warehouse;
import com.warehouse.mapper.WarehouseMapper;
import com.warehouse.page.Page;
import com.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    //注入WarehouseMapper
    private final WarehouseMapper warehouseMapper;

    /*
      查询所有仓库的业务方法
     */
    @Override
    public List<Warehouse> queryAllWarehouse() {
        //查询所有仓库
        return warehouseMapper.findAllWarehouse();
    }

    //分页查询仓库的业务方法
    @Override
    public Page queryWarehousePage(Page page, Warehouse warehouse) {

        //查询仓库总行数
        int warehouseCount = warehouseMapper.selectWarehouseCount(warehouse);

        //分页查询仓库
        List<Warehouse> warehouseList = warehouseMapper.selectWarehousePage(page, warehouse);

        //将查到的总行数和当前页数据封装到Page对象
        page.setTotalNum(warehouseCount);
        page.setResultList(warehouseList);

        return page;
    }

    //校验仓库编号是否已存在的业务方法
    @Override
    public Result checkWarehouseNum(String warehouseNum) {
        //根据仓库编号查询仓库
        Warehouse warehouse = warehouseMapper.selectWarehouseByNum(warehouseNum);
        return Result.ok(warehouse == null);
    }

    //添加仓库的业务方法
    @Override
    public Result saveWarehouse(Warehouse warehouse) {
        //添加仓库
        int i = warehouseMapper.insertWarehouse(warehouse);
        if (i > 0) {
            return Result.ok("仓库添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库添加失败！");
    }

    //修改仓库的业务方法
    @Override
    public Result updateWarehouse(Warehouse warehouse) {
        //根据仓库id修改仓库
        int i = warehouseMapper.updateWarehouseById(warehouse);
        if (i > 0) {
            return Result.ok("仓库修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库修改失败！");
    }

    //删除仓库的业务方法
    @Override
    public Result deleteWarehouse(Integer warehouseId) {
        //根据仓库id删除仓库
        int i = warehouseMapper.deleteWarehouseById(warehouseId);
        if (i > 0) {
            return Result.ok("仓库删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库删除失败！");
    }
}
