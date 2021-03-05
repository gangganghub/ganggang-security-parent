package com.ganggang.security.properties;

import com.ganggang.security.properties.LoginResponseType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="ganggang.security.authentication")
public class LoginTypeProperties {
    /**
     * 登录成功后响应JSON，还是重定向
     */
    LoginResponseType loginType;

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }
}
