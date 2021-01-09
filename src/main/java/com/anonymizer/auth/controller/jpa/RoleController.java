package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.models.Role;
import com.anonymizer.auth.services.jpa.RoleService;
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
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @PostMapping(path = "/api/v1/role/")
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@Valid @NotNull @RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping(path = "/api/v1/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(path = "/api/v1/role/{name}")
    public Optional<Role> getRoleByName(@PathVariable("name") String name) {
        return roleService.geRoleByName(name);
    }

    @DeleteMapping(path = "/api/v1/role/{name}")
    public void deleteRoleById(@PathVariable("name") String name) {
        roleService.deleteRole(name);
    }

    @PutMapping(path = "/api/v1/role/{id}")
    public Role updateRole(@Valid @NotNull @RequestBody Role role, @PathVariable("id") int id) {
        return roleService.updateRole(role, id);
    }
}
