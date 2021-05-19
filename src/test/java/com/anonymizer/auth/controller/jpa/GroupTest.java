package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.AuthApplication;
import com.anonymizer.auth.model.Group;
import com.anonymizer.auth.service.jpa.GroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AuthApplication.class, GroupController.class})
@WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
public class GroupTest {


    private static final ObjectMapper om = new ObjectMapper();
    private Group testGroup;
    private String groupPayload;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService mockGroupService;

    @Before
    public void setUp() throws JsonProcessingException {
        LocalDateTime currentTime =  LocalDateTime.now();
        testGroup = new Group(1, "test-group",
                null,
                null,
                Collections.emptySet(),
                Collections.emptySet()
        );
        groupPayload = om.writeValueAsString(testGroup);
    }

    @Test
    public void should_CreateGroup_When_RequestIsValid() throws Exception {

        when(mockGroupService.createGroup(any(Group.class))).thenReturn(testGroup);

        mockMvc.perform(post("/api/v1/auth/group")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(groupPayload)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testGroup.getId()))
                .andExpect(jsonPath("$.name").value(testGroup.getGroupName()));
    }

    @Test
    public void should_ListGroups_When_RequestIsValid() throws Exception {

        List<Group> allGroups = Arrays.asList(testGroup);
        given(mockGroupService.getAllGroup()).willReturn(allGroups);

        mockMvc.perform(get("/api/v1/auth/group/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testGroup.getGroupName())));
    }

    @Test
    public void should_GetRole_WhenRequestIsValid() throws Exception {

        when(mockGroupService.getGroupByName(testGroup.getGroupName()))
                .thenReturn(Optional.of(testGroup));

        mockMvc.perform(get("/api/v1/auth/group/{name}", testGroup.getGroupName())
                .content(groupPayload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_theGroupShouldBeDeleted() throws Exception {

        when(mockGroupService.getGroupByName(testGroup.getGroupName()))
                .thenReturn(Optional.of(testGroup));
        mockGroupService.deleteGroupById(testGroup.getId());

        verify(mockGroupService, times(1)).deleteGroupById(testGroup.getId());

        mockMvc.perform(delete("/api/v1/auth/group/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_UpdateGroup_When_IsValid() throws Exception {

        when(mockGroupService.updateGroup(testGroup, 1)).thenReturn(testGroup);

        mockMvc.perform(put("/api/v1/auth/group/{id}", 1)
                .content(groupPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //.andDo(print())
        //.andExpect(jsonPath("$.id").value(1))
        //.andExpect(jsonPath("$.name").value("test-role"));
        //.andReturn()
        //.getResponse()
        //.getContentAsString();
    }

}
