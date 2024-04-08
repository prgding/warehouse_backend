package com.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 品牌表brand表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand implements Serializable {

    private final static long serialVersionUID = 1L;
    private Integer brandId;//品牌id

    private String brandName;//品牌名称

    private String brandInitial;//品牌首字母

    private String brandDesc;//品牌描述
}
