package com.warehouse.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson.JSON;
import com.warehouse.entity.Result;
import com.warehouse.utils.WarehouseConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录限制过滤器:
 */
@Slf4j
public class SecurityFilter implements Filter {
    /**
     * 过滤器拦截到请求执行的方法:
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //获取请求url接口
        String path = request.getServletPath();
        /*
          白名单请求都直接放行:
         */
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/logout");
        //对上传图片的url接口/product/img-upload的请求直接放行
        urlList.add("/product/img-upload");
        //对static下的/img/upload中的静态资源图片的访问直接放行
        if (urlList.contains(path) || path.contains("/img/upload") || path.contains("/webjars")
                || path.contains("/swagger") || path.contains("/v2/api-docs")) {
            log.info("path = {} 白名单请求", path);
            chain.doFilter(request, response);
            return;
        }
        /*
          其它请求都校验token:
         */
        //拿到前端归还的token
        String clientToken = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);

        try {
            JWTValidator.of(clientToken)
                    .validateDate(DateUtil.date())
                    .validateAlgorithm(JWTSignerUtil.hs256(JWT.of(clientToken).getPayload("userCode").toString().getBytes()));
        } catch (Exception e) {
            log.info("path = {} JWT校验失败", path);
            //校验失败,向前端响应失败的Result对象转成的json串
            Result result = Result.err(Result.CODE_ERR_UNLOGINED, "请登录！");
            String jsonStr = JSON.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.flush();
            out.close();
            return;
        }

        //校验token,校验通过请求放行
        log.info("path = {} JWT校验通过", path);
        chain.doFilter(request, response);
    }
}
