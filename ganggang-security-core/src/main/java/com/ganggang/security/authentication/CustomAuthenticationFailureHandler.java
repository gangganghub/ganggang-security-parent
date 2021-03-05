package com.ganggang.security.authentication;


import com.ganggang.result.GangGangResult;
import com.ganggang.security.properties.LoginTypeProperties;
import com.ganggang.security.properties.SecurityProperties;
import com.ganggang.security.properties.LoginResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    public LoginTypeProperties loginTypeProperties;

    @Autowired
    public SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("认证失败之后");
        if(LoginResponseType.JSON.equals(loginTypeProperties.getLoginType())) {
            System.out.println(LoginResponseType.JSON);
            GangGangResult result = GangGangResult.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            System.out.println("result:" + result.toJsonString());
            httpServletResponse.getWriter().write(result.toJsonString());
        }else {
            System.out.println(LoginResponseType.REDIRECT);
            System.out.println(e.getMessage());
            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }
    }
}
