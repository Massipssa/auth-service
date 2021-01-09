package com.anonymizer.auth.security;

import com.anonymizer.auth.configuration.LdapConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

//@Configuration
//@EnableWebSecurity(debug = true)
public class LdapSecurityConfigAdapter { //extends WebSecurityConfigurerAdapter {

    //@Autowired
    //LdapConfiguration ldapConfig;

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {

        /*
        //http.authorizeRequests().antMatchers("/api/jpa/user/*")
                .permitAll().and()
                .csrf().disable();
        */

        /*
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/jpa/users/**").permitAll();
                //.antMatchers("/authenticate").permitAll()
                //.antMatchers("/login**", "/").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/jpa/users/**", "/api/jpa/roles/**").hasAnyRole("ADMIN", "USER")
                //.antMatchers(HttpMethod.POST, "/api/jpa/user/**", "/api/jpa/role/**").hasRole("ADMIN")
                //.antMatchers(HttpMethod.DELETE, "/api/jpa/user/**", "/api/jpa/role/**").hasRole("ADMIN")
                //.antMatchers(HttpMethod.PUT, "/api/jpa/user/**", "/api/jpa/role/**").hasRole("ADMIN");
        */

        /*
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic();
                //.formLogin();

         */
    //}

    /**
     * Connect to ldap and authenticate the user
     * @param auth
     * @throws Exception
     */
    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=users")
                .groupSearchBase("ou=groups")
                .contextSource(ldapConfig.contextSource())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
        */

        /*
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .userSearchBase("ou=people")
                .userSearchFilter("uid={0}")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("uniqueMember={0}")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");

    }

         */
}
