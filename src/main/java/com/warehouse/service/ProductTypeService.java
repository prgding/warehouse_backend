package com.warehouse.service;

import com.warehouse.entity.ProductType;
import com.warehouse.entity.Result;

import java.util.List;

public interface ProductTypeService {

    //查询所有商品分类树的业务方法
    List<ProductType> allProductTypeTree();

    //校验分类编码是否已存在的业务方法
    Result queryTypeByCode(String typeCode);

    //添加商品分类的业务方法
    Result saveProductType(ProductType productType);

    //删除商品分类的业务方法
    Result removeProductType(Integer typeId);

    //修改商品分类的业务方法
    Result updateProductType(ProductType productType);
}
