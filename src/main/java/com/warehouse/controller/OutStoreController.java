package com.warehouse.controller;

import com.warehouse.entity.OutStore;
import com.warehouse.entity.Result;
import com.warehouse.entity.Store;
import com.warehouse.page.Page;
import com.warehouse.service.OutStoreService;
import com.warehouse.service.StoreService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/outstore")
@RestController
@Api(tags = "04-出库管理")
@RequiredArgsConstructor
public class OutStoreController {

    //注入OutStoreService
    private final OutStoreService outStoreService;

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    //注入StoreService
    private final StoreService storeService;

    /**
     * 添加出库单的url接口/outstore/outstore-add
     *
     * @param outStore 将添加的出库单信息的json数据封装到参数OutStore对象;
     * @param token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        outStore.setCreateBy(createBy);

        return outStoreService.saveOutStore(outStore);
    }

    /**
     * 查询所有仓库的url接口/outstore/store-list
     */
    @GetMapping("/store-list")
    public Result storeList() {
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 分页查询出库单的url接口/outstore/outstore-page-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数OutStore对象用于接收请求参数仓库id storeId、商品名称productName、
     * 是否出库isOut、起止时间startTime和endTime;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/outstore-page-list")
    public Result outStorePageList(Page page, OutStore outStore) {
        //执行业务
        page = outStoreService.outStorePage(page, outStore);
        //响应
        return Result.ok(page);
    }

    /**
     * 确定出库的url接口/outstore/outstore-confirm
     */
    @PutMapping("/outstore-confirm")
    public Result confirmOutStore(@RequestBody OutStore outStore) {
        return outStoreService.confirmOutStore(outStore);
    }
}
