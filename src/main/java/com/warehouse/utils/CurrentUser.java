package com.warehouse.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 此User类只封装了用户的用户id、用户名和真实姓名
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentUser {
    //用户id
    private int userId;
    //用户编号
    private String userCode;
    //用户姓名
    private String userName;
    // 是否是管理员
    private String isAdmin;
}
