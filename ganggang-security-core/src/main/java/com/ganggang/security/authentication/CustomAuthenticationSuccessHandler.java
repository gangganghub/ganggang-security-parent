package com.ganggang.security.authentication;

import com.alibaba.fastjson.JSON;
import com.ganggang.result.GangGangResult;
import com.ganggang.security.properties.LoginTypeProperties;
import com.ganggang.security.properties.LoginResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 */
@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    public LoginTypeProperties loginTypeProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("认证成功之后");
        if(LoginResponseType.JSON.equals(loginTypeProperties.getLoginType())){
            System.out.println(LoginResponseType.JSON);
            GangGangResult result = GangGangResult.ok("认证成功");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            System.out.println("result:" + result.toJsonString());
            httpServletResponse.getWriter().write(result.toJsonString());
        }else {
            System.out.println(LoginResponseType.REDIRECT);
            //重定向到上次请求的地址上，引发跳转到认证页面的地址
            logger.info("authentication: " + JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }

    }
}
