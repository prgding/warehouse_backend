package com.warehouse.controller;

import com.warehouse.entity.Result;
import com.warehouse.entity.User;
import com.warehouse.page.Page;
import com.warehouse.service.UserService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "03-用户管理")
@CrossOrigin(origins = "*")
public class UserController {

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    //注入UserService
    private final UserService userService;

    /**
     * 分页查询用户的url接口/user/user-list
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数User对象用于接收请求参数用户名userCode、用户类型userType、用户状态userState;
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @GetMapping("/user-list")
    public Result userListPage(Page page, User user) {
        log.info("user:{}", user);
        //执行业务
        page = userService.queryUserPage(page, user);
        //响应
        return Result.ok(page);
    }

    /**
     * 添加用户的url接口/user/addUser
     * User user将添加的用户信息的json串数据封装到参数User对象;
     * String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即创建新用户的用户id
        int createBy = currentUser.getUserId();
        user.setCreateBy(createBy);
        //执行业务
        return userService.saveUser(user);
    }

    /**
     * 修改用户状态的url接口/user/updateState
     * User user将客户端传递的json数据封装到参数User对象中;
     * String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PutMapping("/updateState")
    public Result updateUserState(@RequestBody User user,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改用户的用户id
        int updateBy = currentUser.getUserId();

        //设置修改用户的用户id和修改时间
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());

        //响应
        return userService.updateUserState(user);
    }

    /**
     * 删除用户的url接口/user/deleteUser/{userId}
     */
    @DeleteMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        //执行业务
        userService.deleteUserById(userId);
        //响应
        return Result.ok("用户删除成功！");
    }

    @DeleteMapping("/deleteUserList")
    public Result deleteUser(@RequestBody List<Integer> ids) {
        //执行业务
        for (Integer id : ids) {
            userService.deleteUserById(id);
        }
        //响应
        return Result.ok("用户删除成功！");
    }

    @PutMapping("/enableUserList")
    public Result enableUserList(@RequestBody List<Integer> ids) {
        //执行业务
        for (Integer id : ids) {
            User user = new User();
            user.setUserId(id);
            user.setIsEnabled(1);
            userService.updateUserState(user);
        }
        //响应
        return Result.ok("用户启用成功！");
    }

    @PutMapping("disableUserList")
    public Result disableUserList(@RequestBody List<Integer> ids) {
        //执行业务
        for (Integer id : ids) {
            User user = new User();
            user.setUserId(id);
            user.setIsEnabled(0);
            userService.updateUserState(user);
        }
        //响应
        return Result.ok("用户禁用成功！");
    }

    /**
     * 修改用户的url接口/user/updateUser
     * User user将请求传递的json数据封装到参数User对象;
     * String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 修改用户的用户id
        int updateBy = currentUser.getUserId();
        user.setUpdateBy(updateBy);
        return userService.updateUserName(user);
    }

    /**
     * 重置密码的url接口/user/updatePwd/{userId}
     */
    @PutMapping("/updatePwd/{userId}")
    public Result resetPassWord(@PathVariable Integer userId) {
        //响应
        return userService.resetPwd(userId);
    }
}
