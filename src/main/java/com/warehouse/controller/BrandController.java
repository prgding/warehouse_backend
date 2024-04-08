package com.warehouse.controller;

import com.warehouse.entity.Brand;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;
import com.warehouse.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dings
 * @since 2024/4/8 14:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
@Api(tags = "01-商品品牌管理")
public class BrandController {
    private final BrandService brandService;

    @GetMapping("/brand-list")
    @ApiOperation("查询所有品牌")
    public Result brandList() {
        //执行业务
        List<Brand> brandList = brandService.queryAllBrand();
        //响应
        return Result.ok(brandList);
    }

    @GetMapping("/brand-page-list")
    public Result brandPageList(Page page, Brand brand) {
        //执行业务
        page = brandService.queryBrandPage(page, brand);
        //响应
        return Result.ok(page);
    }

    @PostMapping("/brand-add")
    public Result brandAdd(@RequestBody Brand brand) {
        //执行业务
        brandService.saveBrand(brand);
        //响应
        return Result.ok("添加成功");
    }

    @PutMapping("/brand-update")
    public Result brandUpdate(@RequestBody Brand brand) {
        //执行业务
        brandService.updateBrand(brand);
        //响应
        return Result.ok("修改成功");
    }

    @DeleteMapping("/brand-delete/{id}")
    public Result brandDelete(@PathVariable("id") Integer id) {
        //执行业务
        brandService.deleteBrand(id);
        //响应
        return Result.ok("删除成功");
    }


}
