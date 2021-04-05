package com.anonymizer.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties("ldap")
public class LdapConfiguration {

    @Autowired
    Environment env;

 /*   @Bean
    public LdapContextSource contextSource () {
        LdapContextSource contextSource= new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("url"));
        contextSource.setBase(env.getRequiredProperty("base"));
        contextSource.setUserDn(env.getRequiredProperty("userDn"));
        contextSource.setPassword(env.getRequiredProperty("password"));
        return contextSource;
    }*/

    /*@Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }*/

}
