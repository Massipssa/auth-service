package com.anonymizer.auth.controller.ldap;

import com.anonymizer.auth.models.LdapUser;
import com.anonymizer.auth.repository.LdapUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LdapController {

    @Autowired
    LdapUserRepository ldapUserRepository;

    @ResponseBody
    @GetMapping("/ldap_users")
    public Iterable<LdapUser> getAllUsers() {
        return ldapUserRepository.findAll();
    }
}
