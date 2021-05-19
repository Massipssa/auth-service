package com.anonymizer.auth.controller.jwt;

import com.anonymizer.auth.AuthApplication;
import com.anonymizer.auth.model.Group;
import com.anonymizer.auth.model.jwt.JwtRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AuthApplication.class, JwtControllerTest.class})
@WithMockUser(username = "user", password = "pwd", roles = "ADMIN")
public class JwtControllerTest {


    private static final ObjectMapper om = new ObjectMapper();
    private JwtRequest testJwtRequest;
    private String jwtRequestPayload;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() throws JsonProcessingException {
        LocalDateTime currentTime =  LocalDateTime.now();
        testJwtRequest = new JwtRequest("user", "pwd");
        jwtRequestPayload = om.writeValueAsString(testJwtRequest);
    }

    @Test
    public  void should_GenerateToken() throws Exception {
        mockMvc.perform(post("/api/v1/auth/jwt/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jwtRequestPayload)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
