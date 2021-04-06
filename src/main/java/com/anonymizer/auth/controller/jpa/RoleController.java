package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.model.Role;
import com.anonymizer.auth.service.jpa.RoleService;
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
@RequestMapping("/api/v1/auth/role")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@Valid @NotNull @RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping(path = "/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(path = "/{name}")
    public Optional<Role> getRoleByName(@PathVariable("name") String name) {
        return roleService.geRoleByName(name);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoleById(@PathVariable("id") int id) {
        roleService.deleteRoleById(id);
    }

    @PutMapping(path = "/{id}")
    public Role updateRole(@Valid @NotNull @RequestBody Role role, @PathVariable("id") int id) {
        return roleService.updateRole(role, id);
    }
}
