package com.example.microblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.DataOutput;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration", "css").
                permitAll().anyRequest()
                .authenticated().and().formLogin()
                .loginPage("/login").
                permitAll().and().logout().
                invalidateHttpSession(true).
                clearAuthentication(true).
                logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/login?logout").permitAll();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth =
                new DaoAuthenticationProvider();
        auth.setUserDetailsService(null);
        auth.setPasswordEncoder(encoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}

