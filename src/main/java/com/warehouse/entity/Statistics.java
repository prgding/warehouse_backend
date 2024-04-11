package com.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private Integer warehouseId;//仓库id

    private String warehouseName;//仓库名称

    private Integer totalStock;//仓库商品库存数
}
