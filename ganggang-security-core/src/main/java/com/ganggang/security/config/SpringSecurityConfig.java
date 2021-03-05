package com.ganggang.security.config;

import com.ganggang.security.authentication.code.ImageCodeValidateFilter;
import com.ganggang.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public SecurityProperties securityProperties;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public ImageCodeValidateFilter imageCodeValidateFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //身份认证管理
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("ganggang").password(passwordEncoder().encode("111111")).authorities("ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    //资源过滤
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /*http.formLogin()//httpBasic()
               .loginPage("/login/page")
               .loginProcessingUrl("/login/form")
               .usernameParameter("name")
               .passwordParameter("pwd")
               .and()
               .authorizeRequests()
               .antMatchers("/login/page").permitAll()
               .anyRequest().authenticated()
               .and().csrf().disable();*/
        http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()//httpBasic()
                .loginPage(securityProperties.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl())
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter())
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter())
                .successHandler(authenticationSuccessHandler)//认证成功处理器
                .failureHandler(authenticationFailureHandler)//认证失败处理器
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getAuthentication().getLoginPage(),"/code/image").permitAll()
                .anyRequest().authenticated();
                //.and().csrf().disable();
    }

    //静态资源

    @Override
    public void configure(WebSecurity web) {
        //web.ignoring().antMatchers("/dist/**","/modules/**","/plugins/**");
        System.out.println(securityProperties.getAuthentication().getStaticPaths());
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
