package com.warehouse.service.impl;

import com.warehouse.entity.ProductType;
import com.warehouse.entity.Result;
import com.warehouse.mapper.ProductTypeMapper;
import com.warehouse.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productTypeServiceImpl implements ProductTypeService {

    //注入ProductTypeMapper
    private final ProductTypeMapper productTypeMapper;

    /*
      查询所有商品分类树的业务方法
     */
    @Override
    public List<ProductType> allProductTypeTree() {
        //查询所有商品分类
        List<ProductType> allTypeList = productTypeMapper.findAllProductType();
        //将所有商品分类List<ProductType>转成商品分类树List<ProductType>
        List<ProductType> typeTreeList = allTypeToTypeTree(allTypeList, 0);
        //返回商品分类树List<ProductType>
        return typeTreeList;
    }

    //将查询到的所有商品分类List<ProductType>转成商品分类树List<ProductType>的算法
    private List<ProductType> allTypeToTypeTree(List<ProductType> allTypeList, Integer parentId) {

        List<ProductType> typeList = new ArrayList<>();
        for (ProductType productType : allTypeList) {
            if (productType.getParentId().equals(parentId)) {
                typeList.add(productType);
            }
        }

        for (ProductType productType : typeList) {
            List<ProductType> childTypeList = allTypeToTypeTree(allTypeList, productType.getTypeId());
            productType.setChildProductCategory(childTypeList);
        }

        return typeList;
    }

    //校验分类编码是否已存在的业务方法
    @Override
    public Result queryTypeByCode(String typeCode) {

        //根据分类编码查询商品分类
        ProductType productType = productTypeMapper.findTypeByCode(typeCode);

        return Result.ok(productType == null);
    }

    /*
      添加商品分类的业务方法
     */
    @Override
    public Result saveProductType(ProductType productType) {
        //添加商品分类
        int i = productTypeMapper.insertProductType(productType);
        if (i > 0) {
            return Result.ok("分类添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类添加失败！");
    }

    /*
      删除商品分类的业务方法

     */
    @Override
    public Result removeProductType(Integer typeId) {
        //根据分类id删除分类及其所有子级分类
        int i = productTypeMapper.deleteProductType(typeId);
        if (i > 0) {
            return Result.ok("分类删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类删除失败！");
    }

    /*
      修改商品分类的业务方法

     */
    @Override
    public Result updateProductType(ProductType productType) {
        //根据分类id修改分类
        int i = productTypeMapper.updateTypeById(productType);
        if (i > 0) {
            return Result.ok("分类修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类修改失败！");
    }
}
