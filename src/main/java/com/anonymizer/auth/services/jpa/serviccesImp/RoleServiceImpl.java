package com.anonymizer.auth.services.jpa.serviccesImp;

import com.anonymizer.auth.models.Role;
import com.anonymizer.auth.repository.RoleRepository;
import com.anonymizer.auth.services.jpa.RoleService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {


    private static Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class.getName());

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        Optional<Role> r = roleRepository.findByName(role.getName());

        LOG.debug("Role name: %s", role.getName());
        if (!r.isPresent()) {
            return roleRepository.save(role);
        }
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> geRoleByName(String name) {
        if (! Strings.isEmpty(name)) {
            return roleRepository.findByName(name);
        }
        return null;
    }

    @Override
    public Role updateRole(Role role, int id) {
        return roleRepository.findById(id)
                .map(r -> {
                     r.setName(role.getName());
                    return  roleRepository.save(r);
                })
                .orElse(null);
    }

    // TODO --> fonction generique pour la suppression
    @Override
    public void deleteRole(String name) {
        if (! Strings.isEmpty(name)) {
            Optional<Role> role = roleRepository.findByName(name);
            if (role.isPresent()) {
                roleRepository.delete(role.get());
            }
        }
    }
}
