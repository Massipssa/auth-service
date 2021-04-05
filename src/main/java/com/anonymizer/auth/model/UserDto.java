package com.anonymizer.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class UserDto {

    @JsonProperty
    private String userName;
    @JsonProperty
    private Set<String> roles;
    @JsonProperty
    private Set<String> privileges;

    public UserDto(String userName, Set<String> roles, Set<String> privileges) {
        this.userName = userName;
        this.roles = roles;
        this.privileges = privileges;
    }
}
