package com.anonymizer.auth.service.jpa;

import com.anonymizer.auth.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Permission createPermission(Permission permission);
    List<Permission> getAllPermissions();
    Optional<Permission>  getPermissionByName(String name);
    Permission updatePermission(Permission permission, int id);
    void deletePermissionById(int id);
}
