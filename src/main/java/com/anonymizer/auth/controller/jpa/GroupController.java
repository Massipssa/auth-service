package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.models.Group;
import com.anonymizer.auth.services.jpa.GroupService;
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
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {

    private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    GroupService groupService;

    @PostMapping(path = "/api/v1/group/")
    @ResponseStatus(HttpStatus.CREATED)
    public Group addGroup(@Valid @NotNull @RequestBody Group group) {
        LOG.debug(String.format("group: {} created", group.getGroupName()));
        return groupService.createGroup(group);
    }

    @GetMapping(path = "/api/v1/groups")
    public List<Group> getAllGroups() {
        return groupService.getAllGroup();
    }

    @GetMapping(path = "/api/v1/group/{groupName}")
    public Optional<Group> getUserByGroupName(@PathVariable("groupName") String groupName) {
        return groupService.getGroupByName(groupName);
    }


    // TODO (delete by name)
    @DeleteMapping(path = "/api/v1/group/{groupName}")
    public void deleteGroupByName(@PathVariable("groupName") String groupName) {
        groupService.deleteGroup(groupName);
    }

    @PutMapping(path = "/api/v1/group/{id}")
    public Group updateGroup(@Valid @NotNull @RequestBody Group group, @PathVariable("id") int id) {
        return groupService.updateGroup(group, id);
    }

}
