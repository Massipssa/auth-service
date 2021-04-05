package com.anonymizer.auth.security;

import com.anonymizer.auth.configuration.Datasource;
import com.anonymizer.auth.service.jpa.JpaUserServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class JpaSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private Datasource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserServiceDetails jpaUserServiceDetails;

    /**
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/jwt")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Finds user in database and encodes its password with the encoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaUserServiceDetails);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


