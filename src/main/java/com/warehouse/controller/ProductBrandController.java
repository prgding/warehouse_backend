package com.warehouse.controller;

import com.warehouse.entity.ProductBrand;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;
import com.warehouse.service.ProductBrandService;
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
@Api(tags = "06-商品品牌管理")
@CrossOrigin(origins = "*")
public class ProductBrandController {
    private final ProductBrandService productBrandService;

    @GetMapping("/brand-list")
    @ApiOperation("查询所有品牌")
    public Result brandList() {
        //执行业务
        List<ProductBrand> productBrandList = productBrandService.queryAllBrand();
        //响应
        return Result.ok(productBrandList);
    }

    @GetMapping("/brand-page-list")
    public Result brandPageList(Page page, ProductBrand productBrand) {
        //执行业务
        page = productBrandService.queryBrandPage(page, productBrand);
        //响应
        return Result.ok(page);
    }

    @PostMapping("/brand-add")
    public Result brandAdd(@RequestBody ProductBrand productBrand) {
        //执行业务
        productBrandService.saveBrand(productBrand);
        //响应
        return Result.ok("添加成功");
    }

    @PutMapping("/brand-update")
    public Result brandUpdate(@RequestBody ProductBrand productBrand) {
        //执行业务
        productBrandService.updateBrand(productBrand);
        //响应
        return Result.ok("修改成功");
    }

    @DeleteMapping("/brand-delete/{id}")
    public Result brandDelete(@PathVariable("id") Integer id) {
        //执行业务
        productBrandService.deleteBrand(id);
        //响应
        return Result.ok("删除成功");
    }


}
