package com.eugene.shoegame.controllers;


import com.eugene.shoegame.dto.UserDTO;
import com.eugene.shoegame.exceptions.userexceptions.*;
import com.eugene.shoegame.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shoegame/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){

        try{
            UserDTO registeredUser = userService.registerUser(userDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e){
            throw e;
        } catch(Exception e){
            throw new UserCreationFailedException("Failed to create a new user.");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO){

        try{
            UserDTO loggedUser = userService.loginUser(userDTO);
            return ResponseEntity.ok(loggedUser);
        } catch (UserNotFoundException | InvalidUserCredentialsException e) {
            throw e; // These will be handled by a global exception handler
        }catch (Exception e) {
            throw new UserLoginAttemptFailedException("Failed to log in.");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        // Call the userService.getAllUsers() method that returns a list of users and save it in the allUsers list
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    // get user by specifying their id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        try {
            UserDTO user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO>updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){

        try{
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException | UsernameAlreadyExistsException e) {
            throw e;
        } catch(Exception e){
            throw new UserUpdateFailedException("Failed to update new user");
        }


    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        try{
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            throw e; // This will be handled by a global exception handler
        } catch (Exception e){
            throw new UserDeletionFailedException("Failed to delete user");
        }
    }






}
