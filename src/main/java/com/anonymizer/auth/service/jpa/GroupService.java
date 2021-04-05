package com.anonymizer.auth.service.jpa;

import com.anonymizer.auth.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(Group group);
    List<Group> getAllGroup();
    Optional<Group> getGroupByName(String groupName);
    Group updateGroup(Group group , int id);
    void deleteGroupById(int id);

}
