package com.ganggang.security.authentication.code;

import com.ganggang.security.conroller.LoginController;
import com.ganggang.security.authentication.CustomAuthenticationFailureHandler;
import com.ganggang.security.properties.SecurityProperties;
import com.ganggang.security.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter:所有请求之前被调用一次
 */
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(ImageCodeValidateFilter.class);

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //如果是post请求，校验输入验证码是否正确
        if (securityProperties.getAuthentication().getLoginProcessingUrl().equals(httpServletRequest.getRequestURI())
                && httpServletRequest.getMethod().equalsIgnoreCase("post")) {
            try {
                validate(httpServletRequest);
            } catch (AuthenticationException e) {
                //交给失败处理器处理
                customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        //放行请求
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(HttpServletRequest httpServletRequest) {
        //获取session验证码
        String sessionCode = (String) httpServletRequest.getSession().getAttribute(LoginController.SESSION_KEY);
        //获取用户输入验证码
        String inputCode = httpServletRequest.getParameter("code");
        logger.info("session验证码：" + sessionCode + "\t用户输入验证码：" + inputCode);
        //判断是否正确
        if (inputCode == null || inputCode.length() == 0) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误");
        }

    }
}
