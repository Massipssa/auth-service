package com.anonymizer.auth.services.jpa.serviccesImp;

import com.anonymizer.auth.models.Permission;
import com.anonymizer.auth.repository.PermissionRepository;
import com.anonymizer.auth.services.jpa.PermissionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission privilege) {
        return permissionRepository.save(privilege);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> getPermissionByName(String name) {
        if (! Strings.isEmpty(name)) {
            return permissionRepository.findByName(name);
        }
        return null;
    }

    @Override
    public Permission updatePermission(Permission permission, int id) {

        return permissionRepository.findById(id)
                .map(p -> {
                    p.setName(permission.getName());
                    return permissionRepository.save(p);
                })
                .orElse(null);
    }

    @Override
    public void deletePermission(String name) {
        if (! Strings.isEmpty(name)) {
            Optional<Permission> privilege = permissionRepository.findByName(name);
            if (privilege.isPresent()) {
                permissionRepository.delete(privilege.get());
            }
        }
    }
}
