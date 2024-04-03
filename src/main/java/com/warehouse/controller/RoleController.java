package com.warehouse.controller;

import com.warehouse.dto.AssignAuthDto;
import com.warehouse.entity.Result;
import com.warehouse.entity.Role;
import com.warehouse.page.Page;
import com.warehouse.service.AuthService;
import com.warehouse.service.RoleService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/role")
@RestController
@RequiredArgsConstructor
@Api(tags = "08-角色管理")
public class RoleController {

    //注入RoleService
    private final RoleService roleService;

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    //注入AuthService
    private final AuthService authService;

    /**
     * 查询所有角色的url接口role/role-list
     */
    @GetMapping("/role-list")
    public Result queryAllRole() {
        //执行业务
        List<Role> roleList = roleService.getAllRole();
        //响应
        return Result.ok(roleList);
    }

    /**
     * 分页查询角色的url接口/role/role-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Role对象用于接收请求参数角色名roleName、角色代码roleCode、角色状态roleState;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/role-page-list")
    public Result roleListPage(Page page, Role role) {

        //执行业务
        page = roleService.queryRolePage(page, role);

        //响应
        return Result.ok(page);
    }

    /**
     * 添加角色的url接口/role/role-add
     *
     * @RequestBody Role role将添加的角色信息的json串数据封装到参数Role对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即创建新角色的用户id
        int createBy = currentUser.getUserId();
        role.setCreateBy(createBy);

        //执行业务
        Result result = roleService.saveRole(role);
        return result;
    }

    /**
     * 修改角色状态的url接口/role/role-state-update
     *
     * @RequestBody Role role将客户端传递的json数据封装到参数Role对象中;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改角色的用户id
        int updateBy = currentUser.getUserId();

        //设置修改角色的用户id和修改时间
        role.setUpdateBy(updateBy);
        role.setUpdateTime(new Date());

        //执行业务
        Result result = roleService.updateRoleState(role);

        //响应
        return result;
    }

    /**
     * 查询角色已分配的权限(菜单)的url接口/role/role-auth
     * Integer roleId将请求参数roleId赋值给请求处理方法参数roleId;
     * 返回值Result对象向客户端响应组装了给角色分配的所有权限(菜单)id的List<Integer>;
     */
    @GetMapping("/role-auth")
    public Result queryRoleAuth(Integer roleId) {
        //执行业务
        List<Integer> authIdList = roleService.queryAuthIds(roleId);
        //响应
        return Result.ok(authIdList);
    }

    /**
     * 给角色分配权限(菜单)的url接口/role/auth-grant
     *
     * @RequestBody AssignAuthDto assignAuthDto将请求传递的json数据
     * 封装到参数AssignAuthDto对象中;
     */
    @PutMapping("/auth-grant")
    public Result assignAuth(@RequestBody AssignAuthDto assignAuthDto) {
        //执行业务
        authService.assignAuth(assignAuthDto);
        //响应
        return Result.ok("分配权限成功！");
    }

    /**
     * 删除角色的url接口/role/role-delete/{roleId}
     */
    @DeleteMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId) {
        //执行业务
        roleService.deleteRole(roleId);
        //响应
        return Result.ok("角色删除成功！");
    }

    /**
     * 修改角色的url接口/role/role-update
     *
     * @RequestBody Role roler将请求传递的json数据封装到参数Role对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/role-update")
    public Result updateRole(@RequestBody Role role,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 修改角色的用户id
        int updateBy = currentUser.getUserId();

        role.setUpdateBy(updateBy);

        //执行业务
        Result result = roleService.updateRoleDesc(role);

        //响应
        return result;
    }
}
