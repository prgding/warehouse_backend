package com.warehouse.controller;

import com.warehouse.entity.ProductType;
import com.warehouse.entity.Result;
import com.warehouse.service.ProductTypeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/productCategory")
@RestController
@Api(tags = "06-商品分类管理")
@RequiredArgsConstructor
public class ProductTypeController {

    //注入ProductTypeService
    private final ProductTypeService productTypeService;

    /**
     * 查询商品分类树的url接口/productCategory/product-category-tree
     */
    @GetMapping("/product-category-tree")
    public Result productCategoryTree() {
        //执行业务
        List<ProductType> productTypeList = productTypeService.allProductTypeTree();
        //响应
        return Result.ok(productTypeList);
    }

    /**
     * 校验分类编码是否已存在的url接口/productCategory/verify-type-code
     */
    @PostMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        //执行业务
        Result result = productTypeService.queryTypeByCode(typeCode);
        //响应
        return result;
    }

    /**
     * 添加商品分类的url接口/productCategory/type-add
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @PostMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        //执行业务
        Result result = productTypeService.saveProductType(productType);
        //响应
        return result;
    }

    /**
     * 删除商品分类的url接口/productCategory/type-delete/{typeId}
     *
     * @PathVariable Integer typeId将路径占位符typeId的值赋值给参数变量typeId;
     */
    @DeleteMapping("/type-delete/{typeId}")
    public Result deleteType(@PathVariable Integer typeId) {
        //执行业务
        Result result = productTypeService.removeProductType(typeId);
        //响应
        return result;
    }

    /**
     * 修改商品分类的url接口/productCategory/type-update
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @PostMapping("/type-update")
    public Result updateType(@RequestBody ProductType productType) {
        //执行业务
        Result result = productTypeService.updateProductType(productType);
        //响应
        return result;
    }
}
