package com.warehouse.service.impl;

import com.warehouse.entity.Unit;
import com.warehouse.mapper.UnitMapper;
import com.warehouse.page.Page;
import com.warehouse.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    //注入UnitMapper
    private final UnitMapper unitMapper;

    /*
      查询所有单位的业务方法
     */
    @Override
    public List<Unit> queryAllUnit() {
        //查询所有单位
        return unitMapper.findAllUnit();
    }

    @Override
    public Page queryUnitPage(Page page, Unit unit) {
        //查询品牌总数
        int brandCount = unitMapper.selectUnitCount(unit);
        List<Unit> unitList = unitMapper.selectUnitPage(page, unit);

        page.setTotalNum(brandCount);
        page.setResultList(unitList);
        return page;
    }

    @Override
    public void saveUnit(Unit unit) {
        log.info("unit = " + unit);
        unitMapper.saveUnit(unit);
    }

    @Override
    public void updateUnit(Unit unit) {
        unitMapper.updateUnit(unit);

    }

    @Override
    public void deleteUnit(Integer id) {
        unitMapper.deleteUnit(id);

    }
}
