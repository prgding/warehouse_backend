package com.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库单表in_warehouse表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InWarehouse {

    private Integer insId;//入库单id

    private Integer warehouseId;//仓库id

    private Integer productId;//商品id

    private Integer inNum;//入库数量

    private Integer createBy;//创建入库单的用户id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private Integer isIn;//是否入库,1.是,0.否

    // 以下是扩展字段

    private String productName;//商品名称

    private String warehouseName;//仓库名称

    private String buyUser;//创建入库单的用户的名称

    private BigDecimal phone;//商品入库价格

    private String startTime;//起始时间
    
    private String endTime;//结束时间

    private String userCode;//创建入库单的用户的编码
}
