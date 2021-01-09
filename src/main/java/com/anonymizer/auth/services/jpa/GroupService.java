package com.anonymizer.auth.services.jpa;

import com.anonymizer.auth.models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(Group group);
    List<Group> getAllGroup();
    Optional<Group> getGroupByName(String groupName);
    Group updateGroup(Group group , int id);
    void deleteGroup(String groupName);

}
