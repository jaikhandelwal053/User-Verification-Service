package com.user.verification.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.user.verification.models.User;
import com.user.verification.responses.PageInfo;
import com.user.verification.responses.UserResponse;
import com.user.verification.responses.UserWithPageInfoResponse;
import com.user.verification.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private UserResponse userResponse;
    private UserWithPageInfoResponse finalResponse;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUserId(1L);
        user.setName("Genesis Fletcher");
        user.setAge(12);
        user.setGender("female");
        user.setDob("2008-01-01");
        user.setNationality("AU");
        user.setVerificationStatus("VERIFIED");
        user.setDateCreated(LocalDateTime.now());
        user.setDateModified(LocalDateTime.now());

        userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setAge(user.getAge());
        userResponse.setGender(user.getGender());
        userResponse.setDob(user.getDob());
        userResponse.setNationality(user.getNationality());
        userResponse.setVerificationStatus(user.getVerificationStatus());

        finalResponse = new UserWithPageInfoResponse(userResponse, new PageInfo(false, false, 1));
    }

    @Test
    public void testCreateUsersBadRequest() throws Exception {
        mockMvc.perform(post("/users")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUsersBadRequest() throws Exception {
        mockMvc.perform(get("/users")
                .param("sortType", "1234")
                .param("sortOrder", "EVEN")
                .param("limit", "5")
                .param("offset", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListAllUsers() throws Exception {
        List<UserWithPageInfoResponse> allUsers = Arrays.asList(finalResponse);
        Mockito.when(userService.getAllUsers()).thenReturn(allUsers);

        mockMvc.perform(get("/users/listUser")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].users.name").value(userResponse.getName()));
    }

  @Test
  public void testGetUsersSuccess() throws Exception {
      List<UserResponse> userList = Arrays.asList(userResponse);
      Mockito.when(userService.getUsers("age", "EVEN", 1, 0)).thenReturn(userList);

      mockMvc.perform(get("/users")
              .param("sortType", "age")
              .param("sortOrder", "EVEN")
              .param("limit", "1")
              .param("offset", "0")
              .contentType(MediaType.APPLICATION_JSON))
		      .andDo(print()) 
		      .andExpect(status().isOk())
		      .andExpect(jsonPath("$[0].name").value(userResponse.getName()));
  }
    @Test
    public void testCreateUsersSuccess() throws Exception {
        List<UserResponse> userList = Arrays.asList(userResponse);
        Mockito.when(userService.createUsers(1)).thenReturn(userList);

        mockMvc.perform(post("/users")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(userResponse.getName()));
    }

    @Test
    public void testCreateUsersInvalidParameter() throws Exception {
        mockMvc.perform(post("/users")
                .param("size", "abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

}
