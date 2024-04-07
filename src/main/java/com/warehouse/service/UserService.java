package com.warehouse.service;

import com.warehouse.entity.Result;
import com.warehouse.entity.User;
import com.warehouse.page.Page;

public interface UserService {

    //根据用户名查找用户的业务方法
    User findUserByCode(String userCode);

    //分页查询用户的业务方法
    Page queryUserPage(Page page, User user);

    //添加用户的业务方法
    Result saveUser(User user);

    //修改用户状态的业务方法
    Result updateUserState(User user);

    //根据用户id删除用户的业务方法
    void deleteUserById(Integer userId);

    //修改用户昵称的业务方法
    Result updateUserName(User user);

    //重置密码的业务方法
    Result resetPwd(Integer userId);
}
