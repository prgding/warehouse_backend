package com.warehouse.controller;

import com.warehouse.entity.Unit;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;
import com.warehouse.service.UnitService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author dings
 * @since 2024/4/10 17:59
 */
@RequestMapping("/unit")
@RestController
@CrossOrigin(origins = "*")
@Api(tags = "11-单位管理")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @GetMapping("/unit-page-list")
    public Result unitPageList(Page page, Unit unit) {
        //执行业务
        page = unitService.queryUnitPage(page, unit);
        //响应
        return Result.ok(page);
    }

    @PostMapping("/unit-add")
    public Result unitAdd(@RequestBody Unit unit) {
        //执行业务
        unitService.saveUnit(unit);
        //响应
        return Result.ok("添加成功");
    }

    @PutMapping("/unit-update")
    public Result unitUpdate(@RequestBody Unit unit) {
        //执行业务
        unitService.updateUnit(unit);
        //响应
        return Result.ok("修改成功");
    }

    @DeleteMapping("/unit-delete/{id}")
    public Result unitDelete(@PathVariable("id") Integer id) {
        //执行业务
        unitService.deleteUnit(id);
        //响应
        return Result.ok("删除成功");
    }
}
