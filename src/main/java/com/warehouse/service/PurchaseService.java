package com.warehouse.service;

import com.warehouse.entity.Purchase;
import com.warehouse.entity.Result;
import com.warehouse.page.Page;

public interface PurchaseService {

    //添加采购单的业务方法
    Result savePurchase(Purchase purchase);

    //分页查询采购单的业务方法
    Page queryPurchasePage(Page page, Purchase purchase);

    //修改采购单的业务方法
    Result updatePurchase(Purchase purchase);

    //删除采购单的业务方法
    Result deletePurchase(Integer buyId);
}
