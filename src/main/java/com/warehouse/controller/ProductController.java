package com.warehouse.controller;

import com.warehouse.entity.*;
import com.warehouse.page.Page;
import com.warehouse.service.*;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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
public class ProductController {

    //注入StoreService
    private final StoreService storeService;

    //注入BrandService
    private final BrandService brandService;

    //注入ProductTypeService
    private final ProductTypeService productTypeService;

    //注入SupplyService
    private final SupplyService supplyService;

    //注入PlaceService
    private final PlaceService placeService;

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
     *
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
     * 查询所有品牌的url接口/product/brand-list
     *
     * 返回值Result对象给客户端响应查询到的List<Brand>;
     */
    @GetMapping("/brand-list")
    public Result brandList() {
        //执行业务
        List<Brand> brandList = brandService.queryAllBrand();
        //响应
        return Result.ok(brandList);
    }

    /**
     * 查询所有商品分类树的url接口/product/category-tree
     *
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
     * 查询所有供应商的url接口/product/supply-list
     *
     * 返回值Result对象给客户端响应查询到的List<Supply>;
     */
    @GetMapping("/supply-list")
    public Result supplyList() {
        //执行业务
        List<Supply> supplyList = supplyService.queryAllSupply();
        //响应
        return Result.ok(supplyList);
    }

    /**
     * 查询所有产地的url接口/product/place-list
     *
     * 返回值Result对象给客户端响应查询到的List<Place>;
     */
    @GetMapping("/place-list")
    public Result placeList() {
        //执行业务
        List<Place> placeList = placeService.queryAllPlace();
        //响应
        return Result.ok(placeList);
    }

    /**
     * 查询所有单位的url接口/product/unit-list
     *
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
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Product对象用于接收请求参数仓库id storeId、商品名称productName、
     * 品牌名称brandName、分类名称typeName、供应商名称supplyName、产地名称
     * placeName、上下架状态upDownState、是否过期isOverDate;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @PostMapping("/product-page-list")
    public Result productPageList(Page page, Product product) {
        //执行业务
        page = productService.queryProductPage(page, product);
        //响应
        return Result.ok(page);
    }

    /**
     * 上传图片的url接口/product/img-upload
     *
     * 参数MultipartFile file对象封装了上传的图片;
     *
     * @CrossOrigin表示该url接口允许跨域请求;
     */
    @CrossOrigin
    @PostMapping("/img-upload")
    public Result uploadImg(MultipartFile file) {

        try {
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传到的目录的磁盘路径
            String uploadDirPath = uploadDirFile.getAbsolutePath();
            //拿到图片保存到的磁盘路径
            String fileUploadPath = uploadDirPath + "\\" + file.getOriginalFilename();
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
     * @RequestBody Product product将添加的商品信息的json串数据封装到参数Product对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
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

        //执行业务
        Result result = productService.saveProduct(product);

        //响应
        return result;
    }

    /**
     * 修改商品上下架状态的url接口/product/state-change
     *
     * @RequestBody Product product用于接收并封装请求json数据;
     */
    @PutMapping("/state-change")
    public Result changeProductState(@RequestBody Product product) {
        //执行业务
        Result result = productService.updateProductState(product);
        //响应
        return result;
    }

    /**
     * 删除商品的url接口/product/product-delete/{productId}
     */
    @DeleteMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId) {
        //执行业务
        Result result = productService.deleteProduct(productId);
        //响应
        return result;
    }

    /**
     * 修改商品的url接口/product/product-update
     *
     * @RequestBody Product product将请求传递的json数据封装到参数Product对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改商品的用户id
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);

        //执行业务
        Result result = productService.updateProduct(product);

        //响应
        return result;
    }
}
