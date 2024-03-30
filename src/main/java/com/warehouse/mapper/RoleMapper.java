package com.warehouse.mapper;

import com.warehouse.entity.Role;
import com.warehouse.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    //查询状态正常的所有角色的方法
    List<Role> findAllRole();

    //根据用户id查询用户已分配的角色
    List<Role> findRolesByUserId(Integer userId);

    //根据用户id删除给用户已分配的所有角色
    int delRoleByUserId(Integer userId);

    //根据角色名称查询角色id
    int getRoleIdByName(String roleName);

    //添加用户角色关系的方法
    void insertUserRole(Integer userId, Integer roleId);

    //查询角色总行数的方法
    int selectRoleCount(Role role);

    //分页查询角色的方法
    List<Role> selectRolePage(@Param("page") Page page, @Param("role") Role role);

    //根据角色名称或者角色代码查询角色的方法
    Role findRoleByNameOrCode(String roleName, String roleCode);

    //添加角色的方法
    int insertRole(Role role);

    //根据角色id修改角色状态的方法
    int updateRoleState(Role role);

    //根据角色id查询角色已分配的所有权限(菜单)的id
    List<Integer> findAuthIds(Integer roleId);

    //根据角色id删除角色的方法
    int deleteRoleById(Integer roleId);

    //根据角色id修改角色描述的方法
    int updateDescById(Role role);
}
