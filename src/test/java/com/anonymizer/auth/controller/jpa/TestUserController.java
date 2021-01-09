package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.models.User;
import com.anonymizer.auth.repository.UserRepository;
import com.anonymizer.auth.services.jpa.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserController {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestUserController.class);
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository mockUserRepository;

    @MockBean
    UserService mockUserService;

    /*
    @Before
    public void init() {

    }
    */

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_CreateUser_When_RequestIsValid() throws Exception {
        User testUser = new User(1,"test_user", "test_password", "email");
        when(mockUserService.createUser(any(User.class))).thenReturn(testUser);

        String content = "{\"id\": \"" + testUser.getId() + "\",\"username\": \"" + testUser.getUserName() + "\"}";
        LOG.debug(content);

        mockMvc.perform(post("/api/jpa/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON ))
                //.andExpect(header().string("Location", "".toString()))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test_user"));
    }

    // GET_ALL
    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_ListUsers_When_RequestIsValid() throws Exception {

        User testUser = new User(1,"test_user", "test_password", "email");
        List<User> allUsers = Arrays.asList(testUser);

        given(mockUserService.getAllUsers()).willReturn(allUsers);

        mockMvc.perform(get("/api/jpa/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testUser.getUserName())));
    }

    // GET-USER
    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_GetUser_WhenRequestIsValid() throws Exception {

        User testUser = new User(1,"test_user", "test_password", "email");
        String content = "{\"id\": \"" + testUser.getId() + "\",\"username\": \"" + testUser.getUserName() + "\"}";
        LOG.debug(content);

        when(mockUserService.getUserByName(testUser.getUserName())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/jpa/{username}", "test_user")
                .content(content)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void whenDelete_theUserShouldBeDeleted() throws Exception {
        // Given
        final User testUser = new User(1,"test_user", "test_password", "email");
        // When
        when(mockUserService.getUserByName(testUser.getUserName())).thenReturn(Optional.of(testUser));
        mockUserService.deleteUser(testUser.getUserName());
        // Then
        verify(mockUserService, times(1)).deleteUser(testUser.getUserName());
        //assertNull(mockUserService.getUserByName(testUser.getUserName()));

        // mokmvc
        mockMvc.perform(delete("/api/jpa/{username}", "test_user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_UpdateUser_When_IsValid() throws Exception {

        final User testUser = new User(1,"test_name", "password", "email");
        String content = "{\"id\": \"" + testUser.getId() + "\",\"username\": \"" + testUser.getUserName() + "\"}";

        when(mockUserService.updateUser(testUser, 1)).thenReturn(testUser);

        mockMvc.perform(put("/api/jpa/{id}", "1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON ))
                //.andExpect(header().string("Location", "".toString()))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test_user"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_Return404_When_UserNotFound() throws Exception {
        when(mockUserService.getUserByName("test_user")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/jpa/test_user")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
