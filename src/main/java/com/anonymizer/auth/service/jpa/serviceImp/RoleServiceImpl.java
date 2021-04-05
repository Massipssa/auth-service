package com.anonymizer.auth.service.jpa.serviceImp;

import com.anonymizer.auth.model.Role;
import com.anonymizer.auth.repository.RoleRepository;
import com.anonymizer.auth.service.jpa.RoleService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            role.setCreateTime(LocalDateTime.now());
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

    @Override
    public void deleteRoleById(int id) {
        roleRepository.deleteById(id);
    }
}
