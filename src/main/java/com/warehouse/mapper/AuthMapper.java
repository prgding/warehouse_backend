package com.warehouse.mapper;

import com.warehouse.entity.Auth;

import java.util.List;

public interface AuthMapper {

    //根据用户id查询用户所有权限(菜单)的方法
    List<Auth> findAllAuth(int userId);

    //查询所有状态正常的权限(菜单)的方法
    List<Auth> getAllAuth();

    //根据角色id删除给角色已分配的所有权限(菜单)
    int delAuthByRoleId(Integer roleId);

    //添加角色权限(菜单)关系的方法
    void insertRoleAuth(Integer roleId, Integer authId);

}
