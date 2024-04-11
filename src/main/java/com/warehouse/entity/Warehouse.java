package com.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 仓库表warehouse表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Warehouse implements Serializable {

    private Integer warehouseId;//仓库id

    private String warehouseName;//仓库名称

    private String warehouseCode;//仓库编码

    private String warehouseAddress;//仓库地址

    private String capacity;//仓库容量

    private String contact;//仓库联系人

    private String phone;//仓库联系电话
}
