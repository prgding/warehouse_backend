package com.warehouse.service;

import com.warehouse.dto.AssignAuthDto;
import com.warehouse.entity.Auth;

import java.util.List;

public interface AuthService {

    //根据用户id查询用户权限(菜单)树的业务方法
    List<Auth> findAuthTree(int userId);

    //查询整个权限(菜单)树的业务方法
    List<Auth> allAuthTree();

    //给角色分配权限(菜单)的业务方法
    void assignAuth(AssignAuthDto assignAuthDto);

}
