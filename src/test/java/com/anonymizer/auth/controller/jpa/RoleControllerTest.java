package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.AuthApplication;
import com.anonymizer.auth.model.Role;
import com.anonymizer.auth.service.jpa.RoleService;
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
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes={AuthApplication.class, RoleController.class})
@WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
public class RoleControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    private Role testRole;
    private String rolePayload;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService mockRoleService;

    @Before
    public void setUp() throws JsonProcessingException {
        LocalDateTime currentTime =  LocalDateTime.now();
        testRole = new Role(1, "test-role",
                null,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );
        rolePayload = om.writeValueAsString(testRole);
    }

    @Test
    public void should_CreateRole_When_RequestIsValid() throws Exception {

        when(mockRoleService.createRole(any(Role.class))).thenReturn(testRole);

        mockMvc.perform(post("/api/v1/auth/role")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(rolePayload)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testRole.getId()))
                .andExpect(jsonPath("$.name").value(testRole.getName()));
    }

    @Test
    public void should_ListRoles_When_RequestIsValid() throws Exception {

        List<Role> allRoles = Arrays.asList(testRole);
        given(mockRoleService.getAllRoles()).willReturn(allRoles);

        mockMvc.perform(get("/api/v1/auth/role/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testRole.getName())));
    }


    @Test
    public void should_GetRole_WhenRequestIsValid() throws Exception {

        when(mockRoleService.getRoleByName(testRole.getName()))
                .thenReturn(Optional.of(testRole));

        mockMvc.perform(get("/api/v1/auth/role/{name}", testRole.getName())
                .content(rolePayload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_theRoleShouldBeDeleted() throws Exception {

        when(mockRoleService.getRoleByName(testRole.getName()))
                .thenReturn(Optional.of(testRole));
        mockRoleService.deleteRoleById(testRole.getId());

        verify(mockRoleService, times(1)).deleteRoleById(testRole.getId());

        mockMvc.perform(delete("/api/v1/auth/role/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_UpdateRole_When_IsValid() throws Exception {

        when(mockRoleService.updateRole(testRole, 1)).thenReturn(testRole);

        mockMvc.perform(put("/api/v1/auth/role/{id}", 1)
                .content(rolePayload)
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
