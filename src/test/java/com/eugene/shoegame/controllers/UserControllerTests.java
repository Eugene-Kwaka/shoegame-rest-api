//package com.eugene.shoegame.controllers;
//
//import com.eugene.app.services.UserService;
//import com.eugene.shoegame.dto.UserDTO;
//import com.eugene.shoegame.exceptions.userexceptions.UserNotFoundException;
//import com.eugene.shoegame.exceptions.userexceptions.UsernameAlreadyExistsException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///* This annotation tests the UserController class.
//*  It sets up a Spring application context with only the web layer components, such as controllers, filters, and related configurations.
//*  It does not load the entire application context, making the tests faster and more focused on the web layer.*/
//@WebMvcTest(UserController.class)
//// Activates the 'test' profile which uses the application-test.properties file for config.
//@ActiveProfiles("test")
//public class UserControllerTests {
//
//    /* MockMvc object performs HTTP requests and asserts the results.
//    *  I do not have to test the web layer by starting a full HTTP server.
//    */
//    @Autowired
//    private MockMvc mockMvc;
//
//    /* @MockBean creates mock instance of the UserService class and injects it into the Spring Context.
//    * This allows mocking the behavior/methods provided by the UserService interface.
//    */
//    @MockBean
//    private UserService userService;
//
//    /* The ObjectMapper converts objects into JSON and vice versa.
//    *  It serializes the request body and deserializes the response body.
//    */
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    void registerUser_Success() throws Exception {
//
//        /*
//        * Mock the user details in the request body (inputUserDTo) passed in the POST request.
//        * Mock the response body (registeredDTO) containing user details saved in the DB.
//        * Mock that the userService.registerUser() method is successfully called and the registeredUserDTO is returned.
//        * Mock the performance of the POST request(the correct HTTP verb and api endpoint is passed).
//        * Assert that the request body was in JSON, the inputUserDTO details were serialized and the HTTP response has the 201 status code.
//        * Assert that the response body has a userId of 1L and username is 'newUser' as passed in the request body.  */
//        // Arrange
//
//        // Mocking the creation of a userDTO object that is entered in the request body.
//        UserDTO inputUserDTO = new UserDTO(null, "newUser", "password");
//
//        // Mock the creation of a successfully registered user provided in the response body.
//        UserDTO registeredUserDTO = new UserDTO(1L, "newUser", "password");
//
//        // Mocking that when calling the userService.registerUser(userDTO) method,it then returns a registeredUserDTO object.
//        when(userService.registerUser(any(UserDTO.class))).thenReturn(registeredUserDTO);
//
//        // Act & Assert
//        // Mocking performing a POST request and asserting the response status and content returned.
//        // The mockMvc.perform(post, "/shoegame/users/register") performs a POST request to the specified URL.
//        mockMvc.perform(post("/shoegame/users/register")
//                // The contentType(MediaType.APPLICATION_JSON) sets the type of the request to JSON.
//                        .contentType(MediaType.APPLICATION_JSON)
//                // Serializes the inputUserDTO as the request body.
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Asserts that the Response status code should be 201 Created.
//                .andExpect(status().isCreated())
//                // Asserts that the content type of the response body is JSON.
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                // Expect that the userId in the response body has a value of 1.
//                .andExpect(jsonPath("$.userId").value(1L))
//                // Expect that the new user's username is 'newUser'
//                .andExpect(jsonPath("$.username").value("newUser"));
//    }
//
//    @Test
//    void registerUser_UsernameAlreadyExists() throws Exception {
//        // Arrange
//
//        // Mocking the creation of a userDTO object that is entered in the request body.
//        UserDTO inputUserDTO = new UserDTO(null, "existingUser", "password");
//
//        // mock that if the username entered when the registerUser(userDTO) method is called matches an existing name then throw the UsernameAlreadyExistsException.
//        when(userService.registerUser(any(UserDTO.class))).thenThrow(new UsernameAlreadyExistsException("Username already exists"));
//
//        // Act & Assert
//
//        // Mock that the HTTP request method entered is POST and api endpoint entered are correct.
//        mockMvc.perform(post("/shoegame/users/register")
//                        // The contentType(MediaType.APPLICATION_JSON) sets the type of the request to JSON.
//                        .contentType(MediaType.APPLICATION_JSON)
//                        // Serializes the inputUserDTO as the request body.
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Assert that the expected status code when the exception is raised is Conflict.
//                .andExpect(status().isConflict())
//                // Assert that the expected exception expression is a message and its value is -> 'Username already exists'
//                .andExpect(jsonPath("$.message").value("Username already exists"));
//    }
//
//    @Test
//    void loginUser_Success() throws Exception {
//        // Arrange
//        // Mocking the creation of a userDTO object that is entered in the input request body.
//        UserDTO inputUserDTO = new UserDTO(null, "testUser", "password");
//        // Mock the creation of a successfully logged in user in the response body.
//        UserDTO loggedUserDTO = new UserDTO(1L, "testUser", "password");
//
//        // Mock the userService.loginUser(userDTO) method to return the expected response.
//        when(userService.loginUser(any(UserDTO.class))).thenReturn(loggedUserDTO);
//
//        // Act & Assert
//        // Mock the performance of a POST request to '/shoegame/users/login' and assert the response status and content.
//        mockMvc.perform(post("/shoegame/users/login")
//                        // Assert that the request body is of JSON format.
//                        .contentType(MediaType.APPLICATION_JSON)
//                        // Assert that inputDTO was serialized to JSON/
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Assert that the response status is 200 OK.
//                .andExpect(status().isOk())
//                // Assert that the response body is of JSON type.
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                // Assert that the userId field in the response is 1.
//                .andExpect(jsonPath("$.userId").value(1L))
//                // Assert that the username field in the response is "testUser".
//                .andExpect(jsonPath("$.username").value("testUser"));
//    }
//
//    @Test
//    void loginUser_UserNotFound() throws Exception {
//        // Arrange
//        // Create a UserDTO object for the input request body.
//        UserDTO inputUserDTO = new UserDTO(null, "nonExistentUser", "password");
//
//        // Mock the userService.loginUser(userDTO) method to throw a UserNotFoundException.
//        when(userService.loginUser(any(UserDTO.class))).thenThrow(new UserNotFoundException("User not found"));
//
//        // Act & Assert
//        // Perform a POST request to /shoegame/users/login and assert the response status and content.
//        mockMvc.perform(post("/shoegame/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Assert that the response status is 404 Not Found.
//                .andExpect(status().isNotFound())
//                // Assert that the message field in the response is "User not found".
//                .andExpect(jsonPath("$.message").value("User not found"));
//    }
//
//    @Test
//    void getAllUsers_Success() throws Exception {
//        // Arrange
//
//        // mock the Creation of a list of userDTO objects that represent users in the DB.
//        List<UserDTO> users = Arrays.asList(
//                new UserDTO(1L, "user1", "password1"),
//                new UserDTO(2L, "user2", "password2")
//        );
//
//        // Mock that the userService.getAllUsers() returns the list of users.
//        when(userService.getAllUsers()).thenReturn(users);
//
//        // Act & Assert
//        // Mock the performance of a GET request to /shoegame/users endpoint and assert the response status and content.
//        mockMvc.perform(get("/shoegame/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response is 200 OK
//                .andExpect(status().isOk())
//                // Assert that the response body is in JSON.
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response contains 2 users.
//                .andExpect(jsonPath("$.length()").value(2))
//                // Assert that the first user has a userId of 1.
//                .andExpect(jsonPath("$[0].userId").value(1L))
//                // Assert that the first user's name is user1.
//                .andExpect(jsonPath("$[0].username").value("user1"))
//                // Assert that the second user has a userId of 2.
//                .andExpect(jsonPath("$[1].userId").value(2L))
//                // Assert that the second user's name is user2.
//                .andExpect(jsonPath("$[1].username").value("user2"));
//    }
//
//    @Test
//    void getUserById_Success() throws Exception {
//        // Arrange
//
//        // Create a UserDTO object for the expected response.
//        UserDTO userDTO = new UserDTO(1L, "testUser", "password");
//
//        // Mock the calling of the userService.getUserById(userId) method to return the user.
//        when(userService.getUserById(1L)).thenReturn(userDTO);
//
//        // Act & Assert
//
//        // Mock the Performance a GET request to /shoegame/users/{userId} and assert the response status and content.
//        mockMvc.perform(get("/shoegame/users/{userId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response status code is 200 OK
//                .andExpect(status().isOk())
//                // Assert that the response body is of JSON format.
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                // Assert that the user's Id is 1L.
//                .andExpect(jsonPath("$.userId").value(1L))
//                // Assert that the user's name is testUser.
//                .andExpect(jsonPath("$.username").value("testUser"));
//    }
//
//    @Test
//    void getUserById_UserNotFound() throws Exception {
//        // Arrange
//
//        // Mock the calling of the userService.getUserById(userId) method to return the UserNotFoundException
//        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));
//
//        // Act & Assert
//        // Mock the Performance a GET request to /shoegame/users/{userId} and assert the response status and content.
//        mockMvc.perform(get("/shoegame/users/{userId}", 1L)
//                        // Assert that the response body is in JSON format.
//                        .contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response status code is 404 Not Found.
//                .andExpect(status().isNotFound())
//                // Assert that the response body has the message "User not found".
//                .andExpect(jsonPath("$.message").value("User not found"));
//    }
//
//    @Test
//    void updateUser_Success() throws Exception {
//        // Arrange
//        // Mock the creation of the UserDTO object for the input request body.
//        UserDTO inputUserDTO = new UserDTO(null, "newUsername", "newPassword");
//        // Create a UserDTO object for the expected response.
//        UserDTO updatedUserDTO = new UserDTO(1L, "newUsername", "newPassword");
//
//        // Mock the userService.updateUser method to return the expected response.
//        when(userService.updateUser(any(Long.class), any(UserDTO.class))).thenReturn(updatedUserDTO);
//
//        // Act & Assert
//        // Mock the performance of the PUT request to /shoegame/users/{userId} and assert the response status and content.
//        mockMvc.perform(put("/shoegame/users/{userId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Assert that the response status is 200 OK.
//                .andExpect(status().isOk())
//                // Assert that the response body is in JSON format.
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                // Assert that the userId field in the response is 1.
//                .andExpect(jsonPath("$.userId").value(1L))
//                // Assert that the updated username field in the response is "newUsername".
//                .andExpect(jsonPath("$.username").value("newUsername"));
//    }
//
//    @Test
//    void updateUser_UserNotFound() throws Exception {
//        // Arrange
//        // Create a UserDTO object for the input request body.
//        UserDTO inputUserDTO = new UserDTO(null, "newUsername", "newPassword");
//
//        // Mock the userService.updateUser method to throw a UserNotFoundException.
//        when(userService.updateUser(any(Long.class), any(UserDTO.class))).thenThrow(new UserNotFoundException("User not found"));
//
//        // Act & Assert
//        // Mock the performance of a PUT request to /shoegame/users/{userId} and assert the response status and content.
//        mockMvc.perform(put("/shoegame/users/{userId}", 1L)
//                        // Assert that the request body is JSON
//                        .contentType(MediaType.APPLICATION_JSON)
//                        // Assert that the inputUserDTO request body is serialized to JSON
//                        .content(objectMapper.writeValueAsString(inputUserDTO)))
//                // Assert that the response status is 404 Not Found.
//                .andExpect(status().isNotFound())
//                // Assert that the message field in the response is "User not found".
//                .andExpect(jsonPath("$.message").value("User not found"));
//    }
//
//    @Test
//    void deleteUser_Success() throws Exception {
//        // Arrange
//        // Mock the userService.deleteUser() method to do nothing.
//        doNothing().when(userService).deleteUser(1L);
//
//        // Act & Assert
//        // Mock the performance of a DELETE request to /shoegame/users/{userId} and assert the response status.
//        mockMvc.perform(delete("/shoegame/users/{userId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response content is 204 No Content.
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void deleteUser_UserNotFound() throws Exception {
//        // Mock the userService.deleteUser() method to throw a UserNotException found.
//        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(1L);
//
//        // Act & Assert
//        // Mock the performance of a DELETE request to /shoegame/users/{userId} and assert the response status.
//        mockMvc.perform(delete("/shoegame/users/{userId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                // Assert that the response content is 404 Not Found.
//                .andExpect(status().isNotFound())
//                // Assert that the response body has a message 'User not found'
//                .andExpect(jsonPath("$.message").value("User not found"));
//    }
//}
