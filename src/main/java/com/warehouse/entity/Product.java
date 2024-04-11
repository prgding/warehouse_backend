package com.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 商品表product表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private Integer productId;//商品id

    private Integer warehouseId;//商品所在仓库id
    private String warehouseName;//非表中字段 --商品所在仓库名称

    private Integer brandId;//商品所属品牌id
    private String brandName;//非表中字段 -- 商品所属品牌名称

    private String productName;//商品名称

    private String productCode;//商品编码

    private Integer productStock;//商品库存

    private Integer typeId;//商品所属分类id
    private String typeName;//非表中字段 -- 商品所属分类名称

    private Integer unitId;//商品单位id
    private String unitName;//非表中字段 -- 商品单位名称

    private String introduce;//商品介绍

    private Double inPrice;//商品进价

    private Double salePrice;//商品售价

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//商品的创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//商品的修改时间

    private Integer createBy;//创建商品的用户id

    private Integer updateBy;//修改商品的用户id

    private String imgs;//商品的图片地址

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;//商品的生产日期

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bestBeforeDate;//商品的保质期

    private String isOverDate;//非表中字段 -- 商品是否过期,0未过期,1已过期
}
