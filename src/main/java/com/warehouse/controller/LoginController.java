package com.warehouse.controller;

import cn.hutool.jwt.JWT;
import com.warehouse.entity.LoginUser;
import com.warehouse.entity.Result;
import com.warehouse.entity.User;
import com.warehouse.service.UserService;
import com.warehouse.utils.CurrentUser;
import com.warehouse.utils.DigestUtil;
import com.warehouse.utils.TokenUtils;
import com.warehouse.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@Api(tags = "03-登录管理")
@RequiredArgsConstructor
public class LoginController {

    //注入UserService
    private final UserService userService;

    //注入redis模板
    private final StringRedisTemplate stringRedisTemplate;

    //注入TokenUtils
    private final TokenUtils tokenUtils;

    @Value("${warehouse.expire-time}")
    private int expireTime;

    /**
     * 登录的url接口/login
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
		// 校验验证码
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(loginUser.getVerificationCode().toLowerCase()))) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码不正确！");
        }
		// 校验用户名密码
        User user = userService.findUserByCode(loginUser.getUserCode());
        //查到了用户
        if (user != null) {
            if (Objects.equals(user.getIsEnabled(), WarehouseConstants.USER_ENABLED)) {
                //将用户录入的密码进行加密
                String password = DigestUtil.hmacSign(loginUser.getUserPwd());
                //查到的用户的密码和用户录入的密码相同
                if (password.equals(user.getUserPwd())) {
                    //生成token并响应给前端
                    String token = JWT.create()
                            .setPayload("userId", user.getUserId())
                            .setPayload("userCode", user.getUserCode())
                            .setPayload("userName", user.getUserName())
                            .setPayload("isAdmin", user.getIsAdmin())
                            .setIssuedAt(new Date())
                            .setExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                            .setKey(user.getUserCode().getBytes()).sign();
                    return Result.ok("登录成功！", token);
                } else {
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码不正确！");
                }
            } else {
                return Result.err(Result.CODE_ERR_BUSINESS, "该用户被禁用！");
            }
        } else {
            return Result.err(Result.CODE_ERR_BUSINESS, "该用户不存在！");
        }
    }

    /**
     * 获取当前登录用户信息的url接口/curr-user
     *
     * @param clientToken
     * 将请求头Token的值即前端归还的token,赋值给请求处理方法的参数String clientToken
     */
    @GetMapping("/curr-user")
    public Result currUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
        //从前端归还的token中解析出当前登录用户的信息
        CurrentUser currentUser = tokenUtils.getCurrentUser(clientToken);
        return Result.ok(currentUser);
    }

    /**
     * 登出的url接口/logout
     *
     * @param clientToken
     * 将请求头Token的值即前端归还的token,赋值给请求处理方法的参数String clientToken
     */
    @DeleteMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
        //从redis移除token
        stringRedisTemplate.delete(clientToken);
        return Result.ok();
    }

}
