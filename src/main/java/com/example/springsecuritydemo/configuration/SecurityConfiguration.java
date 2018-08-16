package com.example.springsecuritydemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.jdbcAuthentication().dataSource(securityDataSource);*/
        auth.inMemoryAuthentication()
                .withUser(User.withUsername("drago").password("{noop}123").roles("USER", "ADMIN"))
                .withUser(User.withUsername("gosho").password("{noop}pass2").roles("USER"))
                .withUser(User.withUsername("misho").password("{noop}pass3").roles("USER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                 .anyRequest().authenticated()
                /*.antMatchers("/").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")*/
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");

    }
}