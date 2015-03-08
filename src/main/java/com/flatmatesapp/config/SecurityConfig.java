package com.flatmatesapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 *
 * @author iulian.dafinoiu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .anonymous()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/javax.faces.resources/**", "/javax.faces.resource/**", "/pages/unsecure/**", "/images/**", "/pages/login.faces").permitAll()
                .antMatchers("/pages/secure/admin/**").hasRole("ROOT")
                .antMatchers("/pages/secure/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/pages/login.faces")
                .defaultSuccessUrl("/pages/secure/dashboard.faces")
                .failureUrl("/pages/login.faces?error")
                .and()
                .rememberMe().rememberMeServices(rememberMeServices())
                .key("madFoxDev756438gjhfdjrksf47326bdfshgidshj")
                //                .rememberMeServices(rememberMeServices("madFoxDev756438gjhfdjrksf47326bdfshgidshj"))
                .and()
                .logout()
                .logoutUrl("/j_spring_security_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                .logoutSuccessUrl("/pages/login.faces");
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("madFoxDev756438gjhfdjrksf47326bdfshgidshj", userDetailsService);
        rememberMeServices.setCookieName("SPRING_SECURITY_REMEMBER_ME_COOKIE");
        rememberMeServices.setParameter("_spring_security_remember_me");
        return rememberMeServices;

    }
}
