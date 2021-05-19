package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.model.Permission;
import com.anonymizer.auth.service.jpa.PermissionService;
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
@RequestMapping("/api/v1/auth/permission")
@CrossOrigin(origins = "http://localhost:4200")
public class PermissionController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    PermissionService permissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Permission addPermission(@Valid @NotNull @RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @GetMapping(path = "/permissions")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping(path = "/{name}")
    public Optional<Permission> getPermissionByName(@PathVariable("name") String name) {
        return permissionService.getPermissionByName(name);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePermissionById(@PathVariable("id") int id) {
        permissionService.deletePermissionById(id);
    }

    @PutMapping(path = "/{id}")
    public Permission updatePermission(@Valid @NotNull @RequestBody Permission permission, @PathVariable("id") int id) {
        return permissionService.updatePermission(permission, id);
    }
}
