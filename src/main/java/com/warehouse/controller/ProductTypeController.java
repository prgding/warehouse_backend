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
@Api(tags = "05-商品分类管理")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
    @GetMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        //执行业务
        //响应
        return productTypeService.queryTypeByCode(typeCode);
    }

    /**
     * 添加商品分类的url接口/productCategory/type-add
     *
     * @param productType 将请求传递的json数据封装到参数ProductType对象;
     */
    @PostMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        return productTypeService.saveProductType(productType);
    }

    /**
     * 删除商品分类的url接口/productCategory/type-delete/{typeId}
     *
     * @param typeId 将路径占位符typeId的值赋值给参数变量typeId;
     */
    @DeleteMapping("/type-delete/{typeId}")
    public Result deleteType(@PathVariable Integer typeId) {
        return productTypeService.removeProductType(typeId);
    }

    /**
     * 修改商品分类的url接口/productCategory/type-update
     *
     * @param productType 将请求传递的json数据封装到参数ProductType对象;
     */
    @PutMapping("/type-update")
    public Result updateType(@RequestBody ProductType productType) {
        return productTypeService.updateProductType(productType);
    }
}
