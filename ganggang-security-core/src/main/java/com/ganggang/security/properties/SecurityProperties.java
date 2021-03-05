package com.ganggang.security.properties;

import com.ganggang.security.properties.AuthenticationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="ganggang.security")
public class SecurityProperties {
    private AuthenticationProperties authentication = null;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
