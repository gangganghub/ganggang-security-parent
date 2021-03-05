package com.ganggang.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PasswordEncoder PasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("请求认证的用户名：" + s);

        if(!"ganggang".equals(s)){
            logger.info("请求认证的用户名--1：" + s);
            throw new UsernameNotFoundException("找不到用户");
        }

        String password = PasswordEncoder.encode("111111");

        return new User(s,password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
