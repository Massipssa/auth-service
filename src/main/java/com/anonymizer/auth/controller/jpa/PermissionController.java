package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.models.Permission;
import com.anonymizer.auth.services.jpa.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public class PermissionController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    PermissionService permissionService;

    @PostMapping(path = "/api/v1/permission/")
    @ResponseStatus(HttpStatus.CREATED)
    public Permission addPermission(@Valid @NotNull @RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @GetMapping(path = "/api/v1/permissions")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping(path = "/api/v1/permission/{name}")
    public Optional<Permission> getPermissionByName(@PathVariable("name") String name) {
        return permissionService.getPermissionByName(name);
    }

    @DeleteMapping(path = "/api/v1/permission/{name}")
    public void deletePermissionById(@PathVariable("name") String name) {
        permissionService.deletePermission(name);
    }

    @PutMapping(path = "/api/v1/permission/{id}")
    public Permission updatePermission(@Valid @NotNull @RequestBody Permission permission, @PathVariable("id") int id) {
        return permissionService.updatePermission(permission, id);
    }
}
