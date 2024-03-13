package com.warehouse.service.impl;

import com.warehouse.entity.Product;
import com.warehouse.entity.Result;
import com.warehouse.mapper.ProductMapper;
import com.warehouse.page.Page;
import com.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //分页查询商品的业务方法
    @Override
    public Page queryProductPage(Page page, Product product) {

        //查询商品总行数
        int productCount = productMapper.selectProductCount(product);

        //分页查询商品
        List<Product> productList = productMapper.selectProductPage(page, product);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(productCount);
        page.setResultList(productList);

        return page;
    }

    /*
      将配置文件的file.access-path属性值注入给service的accessPath属性,
     * 其为上传的图片保存到数据库中的访问地址的目录路径/img/upload/;
     */
    @Value("${file.access-path}")
    private String accessPath;

    //添加商品的业务方法
    @Override
    public Result saveProduct(Product product) {

        //处理上传的图片的访问地址 -- /img/upload/图片名称
        product.setImgs(accessPath+product.getImgs());

        //添加商品
        int i = productMapper.insertProduct(product);

        if(i>0){
            return Result.ok("添加商品成功！");
        }

        return Result.err(Result.CODE_ERR_BUSINESS, "添加商品失败！");
    }

    //修改商品上下架状态的业务方法
    @Override
    public Result updateProductState(Product product) {
        //根据商品id修改商品上下架状态
        int i = productMapper.updateStateById(product);
        if(i>0){
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }

    //删除商品的业务方法
    @Override
    public Result deleteProduct(Integer productId) {
        //根据商品id删除商品
        int i = productMapper.deleteProductById(productId);
        if(i>0){
            return Result.ok("商品删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败！");
    }

    //修改商品的业务方法
    @Override
    public Result updateProduct(Product product) {

        /*
          处理商品上传的图片的访问地址:
          如果product对象的imgs属性值没有以/img/upload/开始,说明商品的图片
          被修改了即上传了新的图片,那么product对象的imgs属性值只是图片的名称,
          则给图片名称前拼接/img/upload构成商品新上传的图片的访问地址;
         */
        if(!product.getImgs().startsWith(accessPath)){
            product.setImgs(accessPath+product.getImgs());
        }
        //根据商品id修改商品信息
        int i = productMapper.updateProductById(product);
        if(i>0){
            return Result.ok("商品修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品修改失败！");
    }
}
