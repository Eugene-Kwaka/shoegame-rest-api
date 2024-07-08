package com.eugene.shoegame.services;

import com.eugene.shoegame.dto.UserDTO;
import org.apache.catalina.User;

import java.util.List;

/*
* This interface provides methods for registering, logging in, retrieving, updating, and deleting users.
* It acts as an intermediary between the controller layer and the repository layer, encapsulating the business logic related to users.
* */
public interface UserService {

    // Creates a new UserEntity object using the UserRepository and returns a UserDTO.
    public UserDTO registerUser(UserDTO userDTO);

    // Retrieves a UserEntity object by username and password using the UserRepository and returns a UserDTO.
    public UserDTO loginUser(UserDTO userDTO);

    // Retrieve all users stored in the DB
    public List<UserDTO> getAllUsers();

    // Retrieves a UserEntity object by its userId using the UserRepository and returns a UserDTO.
    public UserDTO getUserById(Long userId);

    // Updates a UserEntity using the UserRepository and returns a UserDTO.
    public UserDTO updateUser(Long userId, UserDTO userDTO);

    // Deletes a UserEntity using the UserRepository.
    public void deleteUser(Long userId);
}
