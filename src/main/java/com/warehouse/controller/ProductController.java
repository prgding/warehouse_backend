package com.warehouse.controller;

import com.warehouse.entity.*;
import com.warehouse.page.Page;
import com.warehouse.service.*;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/product")
@RestController
@Api(tags = "05-商品管理")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    //注入StoreService
    private final StoreService storeService;

    //注入BrandService
    private final ProductBrandService productBrandService;

    //注入ProductTypeService
    private final ProductTypeService productTypeService;

    //注入UnitService
    private final UnitService unitService;

    //注入ProductService
    private final ProductService productService;

    //注入TokenUtils
    private final TokenUtils tokenUtils;
    /**
     * 将配置文件的file.upload-path属性值注入给控制器的uploadPath属性,
     * 其为图片上传到项目的目录路径(类路径classes即resources下的static/img/upload);
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 查询所有仓库的url接口/product/store-list
     * 返回值Result对象给客户端响应查询到的List<Store>;
     */
    @GetMapping("/store-list")
    public Result storeList() {
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 查询所有商品分类树的url接口/product/category-tree
     * 返回值Result对象给客户端响应查询到的所有商品分类树List<ProductType>;
     */
    @GetMapping("/category-tree")
    public Result categoryTree() {
        //执行业务
        List<ProductType> typeTreeList = productTypeService.allProductTypeTree();
        //响应
        return Result.ok(typeTreeList);
    }

    /**
     * 查询所有单位的url接口/product/unit-list
     * 返回值Result对象给客户端响应查询到的List<Unit>;
     */
    @GetMapping("/unit-list")
    public Result unitList() {
        //执行业务
        List<Unit> unitList = unitService.queryAllUnit();
        //响应
        return Result.ok(unitList);
    }

    /**
     * 分页查询商品的url接口/product/product-page-list
     * @param page 对象用于接收请求参数页码pageNum、每页行数pageSize;
     * @param product 对象用于接收请求参数仓库id storeId、商品名称productName、
     * 品牌名称brandName、分类名称typeName
     * 上下架状态upDownState、是否过期isOverDate;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/product-page-list")
    public Result productPageList(Page page, Product product) {
        //执行业务
        page = productService.queryProductPage(page, product);
        //响应
        return Result.ok(page);
    }

    /**
     * 上传图片的url接口/product/img-upload
     * 参数MultipartFile file对象封装了上传的图片;
     * CrossOrigin 表示该url接口允许跨域请求;
     */
    @CrossOrigin
    @PostMapping("/img-upload")
    public Result uploadImg(MultipartFile file) {

        try {
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传到的目录的磁盘路径
            String uploadDirPath = uploadDirFile.getAbsolutePath();
            log.info("uploadDirPath: {}", uploadDirPath);
            //拿到图片保存到的磁盘路径
            String fileUploadPath = uploadDirPath + "/" + file.getOriginalFilename();
            log.info("fileUploadPath: {}", fileUploadPath);
            //保存图片
            file.transferTo(new File(fileUploadPath));
            //成功响应
            return Result.ok("图片上传成功！");
        } catch (IOException e) {
            //失败响应
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");
        }
    }

    /**
     * 添加商品的url接口/product/product-add
     *
     * @param product 将添加的商品信息的json串数据封装到参数Product对象;
     * @param token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加商品的用户id
        int createBy = currentUser.getUserId();
        product.setCreateBy(createBy);
        return productService.saveProduct(product);
    }

    /**
     * 修改商品上下架状态的url接口/product/state-change
     *
     * @param product 用于接收并封装请求json数据;
     */
    @PutMapping("/state-change")
    public Result changeProductState(@RequestBody Product product) {
        return productService.updateProductState(product);
    }

    /**
     * 删除商品的url接口/product/product-delete/{productId}
     */
    @DeleteMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

    @DeleteMapping("product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer> productIds) {
        for (Integer productId : productIds) {
            productService.deleteProduct(productId);
        }
        return Result.ok("商品删除成功！");
    }

    @PutMapping("product-list-up")
    public Result upProductList(@RequestBody List<Integer> productIds) {
        for (Integer productId : productIds) {
            Product product = new Product();
            product.setProductId(productId);
            product.setUpDownState("1");
            productService.updateProductState(product);
        }
        return Result.ok("商品上架成功！");
    }

    @PutMapping("product-list-down")
    public Result downProductList(@RequestBody List<Integer> productIds) {
        for (Integer productId : productIds) {
            Product product = new Product();
            product.setProductId(productId);
            product.setUpDownState("0");
            productService.updateProductState(product);
        }
        return Result.ok("商品下架成功！");
    }

    /**
     * 修改商品的url接口/product/product-update
     *
     * @param product 将请求传递的json数据封装到参数Product对象;
     * @param token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PutMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改商品的用户id
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);

        return productService.updateProduct(product);
    }
}
