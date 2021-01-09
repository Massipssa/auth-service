package com.anonymizer.auth.services.ldpa;

import com.anonymizer.auth.models.LdapUser;

public interface LdapService {
    void addUser(LdapUser ldapUser);
}
