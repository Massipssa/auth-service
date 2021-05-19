package com.anonymizer.auth.service.jpa.serviceImp;

import com.anonymizer.auth.model.Permission;
import com.anonymizer.auth.repository.PermissionRepository;
import com.anonymizer.auth.service.jpa.PermissionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        permission.setCreateTime(LocalDateTime.now());
        return permissionRepository.save(permission);
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
    public void deletePermissionById(int id) {
      permissionRepository.deleteById(id);
    }
}
