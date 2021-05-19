package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.AuthApplication;
import com.anonymizer.auth.model.Permission;
import com.anonymizer.auth.service.jpa.PermissionService;
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
@SpringBootTest(classes = {AuthApplication.class, PermissionController.class})
@WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
public class PermissionTest {

    private static final ObjectMapper om = new ObjectMapper();
    private Permission testPermission;
    private String permissionPayload;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionService mockPermissionService;

    @Before
    public void setUp() throws JsonProcessingException {
        LocalDateTime currentTime =  LocalDateTime.now();
        testPermission = new Permission(1, "test-permission",
                null,
                null,
                Collections.emptySet()
        );
        permissionPayload = om.writeValueAsString(testPermission);
    }

    @Test
    public void should_CreatePermission_When_RequestIsValid() throws Exception {

        when(mockPermissionService.createPermission(any(Permission.class)))
                .thenReturn(testPermission);

        mockMvc.perform(post("/api/v1/auth/permission")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(permissionPayload)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testPermission.getId()))
                .andExpect(jsonPath("$.name").value(testPermission.getName()));
    }

    @Test
    public void should_ListPermissions_When_RequestIsValid() throws Exception {

        List<Permission> allPermissions = Arrays.asList(testPermission);
        given(mockPermissionService.getAllPermissions()).willReturn(allPermissions);

        mockMvc.perform(get("/api/v1/auth/permission/permissions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testPermission.getName())));
    }

    @Test
    public void should_GetPermission_WhenRequestIsValid() throws Exception {

        when(mockPermissionService.getPermissionByName(testPermission.getName()))
                .thenReturn(Optional.of(testPermission));

        mockMvc.perform(get("/api/v1/auth/permission/{name}", testPermission.getName())
                .content(permissionPayload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_thePermissionShouldBeDeleted() throws Exception {

        when(mockPermissionService.getPermissionByName(testPermission.getName()))
                .thenReturn(Optional.of(testPermission));
        mockPermissionService.deletePermissionById(testPermission.getId());

        verify(mockPermissionService, times(1))
                .deletePermissionById(testPermission.getId());

        mockMvc.perform(delete("/api/v1/auth/permission/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_UpdatePermission_When_IsValid() throws Exception {

        when(mockPermissionService.updatePermission(testPermission, 1))
                .thenReturn(testPermission);

        mockMvc.perform(put("/api/v1/auth/permission/{id}", 1)
                .content(permissionPayload)
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
