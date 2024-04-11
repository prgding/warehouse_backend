package com.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 存储用户登录信息的User类：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {

    private String userCode;//用户编码

    private String userPwd;//密码

    private Integer isEnabled;//是否启用

    private String verificationCode;//验证码
}
