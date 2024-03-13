package com.warehouse.service;

import com.warehouse.entity.Product;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface ProductService {

    //分页查询商品的业务方法
    Page queryProductPage(Page page, Product product);

    //添加商品的业务方法
    Result saveProduct(Product product);

    //修改商品上下架状态的业务方法
    Result updateProductState(Product product);

    //删除商品的业务方法
    Result deleteProduct(Integer productId);

    //修改商品的业务方法
    Result updateProduct(Product product);
}
