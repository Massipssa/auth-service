package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.AuthApplication;
import com.anonymizer.auth.model.User;
import com.anonymizer.auth.repository.UserRepository;
import com.anonymizer.auth.service.jpa.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes={AuthApplication.class, UserController.class})
public class UserControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    private User testUser;
    private String userPayload;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository mockUserRepository;

    @MockBean
    UserService mockUserService;

    @Before
    public void setUp() throws JsonProcessingException {
        testUser = new User(1,"test_user", "test_password", "email");
        userPayload = om.writeValueAsString(testUser);
    }

    @Test
    public void should_CreateUser_When_RequestIsValid() throws Exception {

        when(mockUserService.createUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/v1/auth/user/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userPayload)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test_user"));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_ListUsers_When_RequestIsValid() throws Exception {

        List<User> allUsers = Arrays.asList(testUser);
        given(mockUserService.getAllUsers()).willReturn(allUsers);

        mockMvc.perform(get("/api/v1/auth/user/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testUser.getUserName())));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_GetUser_WhenRequestIsValid() throws Exception {

        when(mockUserService.getUserByName(testUser.getUserName())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/v1/auth/user/username/{username}", "test_user")
                .content(userPayload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void whenDelete_theUserShouldBeDeleted() throws Exception {

        when(mockUserService.getUserByName(testUser.getUserName())).thenReturn(Optional.of(testUser));
        mockUserService.deleteUserById(testUser.getId());

        verify(mockUserService, times(1)).deleteUserById(testUser.getId());

        mockMvc.perform(delete("/api/v1/auth/user/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_UpdateUser_When_IsValid() throws Exception {

        when(mockUserService.updateUser(testUser, 1)).thenReturn(testUser);

        mockMvc.perform(put("/api/v1/auth/user/{id}", 1)
                .content(userPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andDo(print())
                //.andExpect(jsonPath("$.id").value(1))
                //.andExpect(jsonPath("$.username").value("test_user"))
                //.andReturn()
                //.getResponse()
                //.getContentAsString();
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
    public void should_Return404_When_UserNotFound() throws Exception {
        when(mockUserService.getUserByName("test_user")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/auth/user/username/test_user")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
