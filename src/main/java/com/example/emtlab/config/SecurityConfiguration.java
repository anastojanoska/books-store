package com.example.emtlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("emt-user").password(this.passwordEncoder.encode("emt-user")).authorities("ROLE_USER")
//                .and()
//                .withUser("admin").password(this.passwordEncoder.encode("admin")).authorities("ROLE_ADMIN");
        auth.authenticationProvider(this.customUsernamePasswordAuthenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/home","/api/categories", "/assets/**","/signup/**","/api/authors/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                    .failureUrl("/login?error=BadCredentials")
                    .defaultSuccessUrl("/books", true)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
//                .accessDeniedPage("/");
                .accessDeniedPage("/books?error=You are not authorized!");

    }
}
