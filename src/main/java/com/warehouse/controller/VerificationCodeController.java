package com.warehouse.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/captcha")
@RestController
public class VerificationCodeController {

    //注入id引用名为captchaProducer的Producer接口的实现类DefaultKaptcha的bean对象
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码图片的url接口/captcha/captchaImage
     */
    @GetMapping("/captchaImage")
    public void getCaptchaImage(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            // Generate CAPTCHA using Hutool
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
            out = response.getOutputStream();
            lineCaptcha.write(out);
            Console.log(lineCaptcha.getCode());
            String code = lineCaptcha.getCode();

            // Store CAPTCHA code in Redis
            stringRedisTemplate.opsForValue().set(code, code);

            // Set response headers
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
