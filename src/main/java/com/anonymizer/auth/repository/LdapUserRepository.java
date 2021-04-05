package com.anonymizer.auth.repository;

import com.anonymizer.auth.model.LdapUser;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapUserRepository  extends LdapRepository<LdapUser> {

}
