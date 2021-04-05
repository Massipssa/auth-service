package com.anonymizer.auth.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(
	base = "ou=users",
	objectClasses = { "person", "inetOrgPerson", "organizationalPerson", "top" })
public class LdapUser {
	
	@Id
    private Name id;

    @JsonProperty("userName")
    private @Attribute(name = "uid") String uid;

    @JsonProperty("firstName")
    private @Attribute(name = "cn") String firstName;

    //@JsonIgnore
    // private @Attribute(name = "displayname") String displayName;

    @JsonProperty("lastName")
    private  @Attribute(name = "sn") String lastName;
 
}
