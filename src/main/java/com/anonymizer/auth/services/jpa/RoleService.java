package com.anonymizer.auth.services.jpa;

import com.anonymizer.auth.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAllRoles();
    Optional<Role> geRoleByName(String name);
    Role updateRole(Role role, int id);
    void deleteRole(String name);
}
