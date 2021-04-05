package com.anonymizer.auth.service.jpa.serviceImp;

import com.anonymizer.auth.model.Group;
import com.anonymizer.auth.repository.GroupRepository;
import com.anonymizer.auth.service.jpa.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Group createGroup(Group group) {
        group.setCreateTime(LocalDateTime.now());
        return groupRepository.save(group);
    }

    @Override
    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> getGroupByName(final String groupName) {

        return groupRepository.findByGroupName(groupName);
    }

    @Override
    public Group updateGroup(Group group, int id) {

        return groupRepository.findById(id)
                .map(r -> {
                    r.setGroupName(group.getGroupName());
                    r.setUpdateTime(LocalDateTime.now());
                    return groupRepository.save(r);
                })
                .orElse(null);
    }
    @Override
    public void deleteGroupById(final int id) {
        groupRepository.deleteById(id);
    }
}
