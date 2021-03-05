package com.ganggang.security.conroller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 认证登录页面
     *
     * @return
     */
    @RequestMapping("/login/page")
    public String toLogin() {
        System.out.println("------toLogin-------");
        return "login";
    }

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 获取图形验证码
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取验证码字符串
        String code = defaultKaptcha.createText();
        logger.info("生成的图片验证码是：" + code);
        //2.字符串把它放到session
        request.getSession().setAttribute(SESSION_KEY,code);
        //3.获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4.将验证码图片把它写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
    }
}
