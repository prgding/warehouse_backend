package com.warehouse.service.impl;

import com.warehouse.dto.AssignRoleDto;
import com.warehouse.entity.Result;
import com.warehouse.entity.Role;
import com.warehouse.mapper.AuthMapper;
import com.warehouse.mapper.RoleMapper;
import com.warehouse.page.Page;
import com.warehouse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    //注入RoleMapper
    @Autowired
    private RoleMapper roleMapper;

    //注入AuthMapper
    @Autowired
    private AuthMapper authMapper;

    //查询所有角色的业务方法
    @Override
    public List<Role> getAllRole() {
        //查询状态正常的所有角色
        return roleMapper.findAllRole();
    }

    //查询用户已分配的角色的业务方法
    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    //给用户分配角色的业务方法
    @Transactional//事务处理
    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {

        //拿到用户id
        Integer userId = assignRoleDto.getUserId();
        //拿到给用户分配的所有角色名
        List<String> roleNameList = assignRoleDto.getRoleCheckList();

        //根据用户id删除给用户已分配的所有角色
        roleMapper.delRoleByUserId(userId);

        //循环添加用户角色关系
        for (String roleName : roleNameList) {
            //根据当前角色名查询当前角色的id
            int roleId = roleMapper.getRoleIdByName(roleName);
            //添加用户角色关系
            roleMapper.insertUserRole(userId, roleId);
        }
    }

    //分页查询角色的业务方法
    @Override
    public Page queryRolePage(Page page, Role role) {

        //查询角色总行数
        int roleCount = roleMapper.selectRoleCount(role);

        //分页查询角色
        List<Role> roleList = roleMapper.selectRolePage(page, role);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(roleCount);
        page.setResultList(roleList);

        return page;
    }

    //添加角色的业务方法
    @Override
    public Result saveRole(Role role) {

        //根据角色名或角色代码查询角色
        Role oldRole = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if(oldRole!=null){//角色已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "该角色已存在！");
        }
        //角色不存在,添加角色
        roleMapper.insertRole(role);
        return Result.ok("添加角色成功！");
    }

    //修改角色状态的业务方法
    @Override
    public Result updateRoleState(Role role) {
        //根据角色id修改角色状态
        int i = roleMapper.updateRoleState(role);
        if(i>0){
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }

    //查询角色已分配的权限(菜单)的业务方法
    @Override
    public List<Integer> queryAuthIds(Integer roleId) {
        //根据角色id查询角色已分配的所有权限(菜单)的id
        return roleMapper.findAuthIds(roleId);
    }

    //删除角色的业务方法
    @Transactional//事务处理
    @Override
    public void deleteRole(Integer roleId) {
        //根据角色id删除角色
        int i = roleMapper.deleteRoleById(roleId);
        if(i>0){
            //根据角色id删除给角色已分配的所有权限(菜单)
            authMapper.delAuthByRoleId(roleId);
        }
    }

    //修改角色描述的业务方法
    @Override
    public Result updateRoleDesc(Role role) {

        //根据角色id修改角色描述
        int i = roleMapper.updateDescById(role);
        if(i>0){
            return Result.ok("角色修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色修改失败！");
    }
}
