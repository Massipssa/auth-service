package com.anonymizer.auth.service.ldpa;

import com.anonymizer.auth.model.LdapUser;

public interface LdapService {
    void addUser(LdapUser ldapUser);
}
