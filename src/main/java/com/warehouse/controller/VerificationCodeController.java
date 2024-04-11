package com.warehouse.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequestMapping("/captcha")
@RestController
@RequiredArgsConstructor
@Api(tags = "12-验证码管理")
@Slf4j
public class VerificationCodeController {

    //注入redis模板
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码图片的url接口/captcha/captchaImage
     */
    @GetMapping("/captchaImage")
    public void getCaptchaImage(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            // Generate CAPTCHA using Hutool
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 30);
            out = response.getOutputStream();
            lineCaptcha.write(out);
            Console.log(lineCaptcha.getCode());

            // Warehouse CAPTCHA code in Redis
            stringRedisTemplate.opsForValue().set(lineCaptcha.getCode().toLowerCase(), "", 5, TimeUnit.MINUTES);

            // Set response headers
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

        } catch (Exception e) {
            log.error("验证码生成失败");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("验证码生成失败");
                }
            }
        }
    }
}
