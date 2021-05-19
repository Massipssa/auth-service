package com.anonymizer.auth.service.jpa;

import com.anonymizer.auth.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAllRoles();
    Optional<Role> getRoleByName(String name);
    Role updateRole(Role role, int id);
    void deleteRoleById(int id);
}
