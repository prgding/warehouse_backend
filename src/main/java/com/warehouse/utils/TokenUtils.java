package com.warehouse.utils;

import cn.hutool.jwt.JWT;
import com.warehouse.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * token工具类
 */
@Component
public class TokenUtils {
    /* *
     * 从客户端归还的token中获取用户信息的方法
     */
    public CurrentUser getCurrentUser(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException("令牌为空，请登录！");
        }

        JWT jwt = JWT.of(token);
        int userId = Integer.parseInt(jwt.getPayload("userId").toString());
        String userCode = jwt.getPayload("userCode").toString();
        String userName = jwt.getPayload("userName").toString();

        return new CurrentUser(userId, userCode, userName);
    }

}
