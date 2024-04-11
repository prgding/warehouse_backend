package com.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * user_info表的实体类:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;//用户id

    private String userCode;//账号

    private String userName;//用户名

    private String userPwd;//用户密码

    private String isAdmin;//是否是管理员

    private Integer isEnabled;//是否启用

    private String isDeleted;//删除状态

    private int createBy;//创建人id

    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private int updateBy;//修改人id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//修改时间

}
