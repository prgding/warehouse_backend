package com.warehouse.controller;

import com.warehouse.entity.InStore;
import com.warehouse.entity.Purchase;
import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import com.warehouse.service.InStoreService;
import com.warehouse.service.PurchaseService;
import com.warehouse.service.StoreService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/purchase")
@RestController
@RequiredArgsConstructor
@Api(tags = "07-采购管理")
public class PurchaseController {

    //注入PurchaseService
    private final PurchaseService purchaseService;

    //注入StoreService
    private final StoreService storeService;

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    //注入InStoreService
    private final InStoreService inStoreService;

    /**
     * 添加采购单的url接口/purchase/purchase-add
     */
    @PostMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {
        //执行业务
        Result result = purchaseService.savePurchase(purchase);
        //响应
        return result;
    }

    /**
     * 查询所有仓库的url接口/purchase/store-list
     */
    @GetMapping("/store-list")
    public Result storeList() {
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 分页查询采购单的url接口/purchase/purchase-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Purchase对象用于接收请求参数仓库id storeId、商品名称productName、
     * 采购人buyUser、是否生成入库单isIn、起止时间startTime和endTime;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/purchase-page-list")
    public Result purchasePageList(Page page, Purchase purchase) {
        //执行业务
        page = purchaseService.queryPurchasePage(page, purchase);
        //响应
        return Result.ok(page);
    }

    /**
     * 修改采购单的url接口/purchase/purchase-update
     *
     * @RequestBody Purchase purchase将请求传递的json数据封装到参数Purchase对象;
     */
    @PostMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        //执行业务
        Result result = purchaseService.updatePurchase(purchase);
        //响应
        return result;
    }

    /**
     * 删除采购单的url接口/purchase/purchase-delete/{buyId}
     *
     * @PathVariable Integer buyId将路径占位符buyId的值赋值给参数变量buyId;
     */
    @DeleteMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId) {
        //执行业务
        Result result = purchaseService.deletePurchase(buyId);
        //响应
        return result;
    }

    /**
     * 添加入库单的url接口/purchase/in-warehouse-record-add
     *
     * @RequestBody Purchase purchase将请求传递的json数据封装到参数Purchase对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 创建入库单的用户id
        int createBy = currentUser.getUserId();

        //创建InStore对象封装添加的入库单的信息
        InStore inStore = new InStore();
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());
        inStore.setCreateBy(createBy);

        //执行业务
        Result result = inStoreService.saveInStore(inStore, purchase.getBuyId());

        //响应
        return result;
    }
}
