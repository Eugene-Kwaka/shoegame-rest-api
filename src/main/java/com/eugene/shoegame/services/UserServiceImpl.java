package com.eugene.shoegame.services;

import com.eugene.shoegame.dto.UserDTO;
import com.eugene.shoegame.entities.UserEntity;
import com.eugene.shoegame.exceptions.userexceptions.InvalidUserCredentialsException;
import com.eugene.shoegame.exceptions.userexceptions.UserNotFoundException;
import com.eugene.shoegame.exceptions.userexceptions.UsernameAlreadyExistsException;
import com.eugene.shoegame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service // This class is marked as a Spring
public class UserServiceImpl implements UserService{

    // initiate UserRepository
    private final UserRepository userRepository;

    @Autowired
    /*
    * The UserRepository interface is injected as a dependency (@autowired) to this class using Constructor injection.*/
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    // Takes a userDTO object as its parameter(the userDTO object contains getUsername() and get.Password() methods)
    // and returns a userDTO object. Which will be handled in the controller methods.
    public UserDTO registerUser(UserDTO userDTO) {

        /* Uses the userRepo's findByUsername() method to see if a user/userDTO object already exists by comparing usernames.
        * findByUsername() method returns an Optional<UserEntity> object, which may or may not contain a UserEntity instance.
        * The isPresent() method provided by the userRepo interface checks if the Optional object contains a value.
        * I use the userDTO.getUsername() to get the username.
        * If an existing user is found, throw a usernameAlreadyExistsException.
        */
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        /* This creates a new userEntity object from the convertUserDTOToUserEntity(userDTO) method using the userDTO object parameter.
        * The .builder() methods creates a builder object for constructing the userEntity object.
        * I get the username and the password from the userDTO.
        * The build() method is called to create the UserEntity instance.
        */
        UserEntity userEntity = convertUserDTOToUserEntity(userDTO);

        // This line saves the new UserEntity instance to the database using the UserRepository.save() method.
        // The save method returns the saved UserEntity instance, which is stored in the savedUser variable.
        UserEntity savedUser = userRepository.save(userEntity);

        /* Using the convertUserEntityToUserDTO() method, we convert the savedUser entity object to a userDTO object
         - Entity object is saved in the DB while its corresponding DTO will be the return value.
         - The converted UserDTO object is returned as the result of the registerUser method.
        */
        return convertUserEntityToUserDTO(savedUser);
    }

    @Override
    // This is the method signature, which takes a UserDTO object as a parameter and returns a UserDTO object.
    public UserDTO loginUser(UserDTO userDTO) {

        // Retrieve a user by their username from the database using the userRepository.findByUsername() method.
        // check if user exists, if they aren't in the DB, return an exception.
        UserEntity userEntity = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // if the userEntity object's password in the database DOES NOT MATCH the password provided by the user trying to log in, return an exception.
        if (!userEntity.getPassword().equals(userDTO.getPassword())) {
            throw new InvalidUserCredentialsException("Invalid login details used! Try again.");
        }

        // Otherwise, if they exist, convert and return the retrieved userEntity as a userDTO object.
        return convertUserEntityToUserDTO(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers(){

        // Getting all users from the DB using the userRepo.findAll() method and putting them in a list.
        List<UserEntity> allUsersEntity = userRepository.findAll();

        List<UserDTO> allUsers = new ArrayList<>();

        for(UserEntity userEntity : allUsersEntity){
            UserDTO userDTO = convertUserEntityToUserDTO(userEntity);
            allUsers.add(userDTO);
        }

        return allUsers;
    }
    // This method retrieves a user by their ID. Helpful for User management.
    @Override
    public UserDTO getUserById(Long userId){
        // Retrieve a user from the DB using the UserRepository.findById() method or else return null
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // converting the retrieved user to a userDTO object.
        return convertUserEntityToUserDTO(userEntity);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO){
        // Retrieve the user from the DB using their Id and if they dont exist throw an exception.
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // This code checks if the new username is different from the existing username and if a user with the new username already exists.
        // If true, throw an error.
        // This code ensures a user does not update their username to a username that already exists with another user.
        if (!userEntity.getUsername().equals(userDTO.getUsername()) &&
                userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        // If the user exists, I can set their new username.
        userEntity.setUsername(userDTO.getUsername());

        // checks if the password is not null and the user's password exists in the DB.
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            // user can update the password.
            userEntity.setPassword(userDTO.getPassword());
        }

        // save the updated user in the DB using userRepository.save() method.
        UserEntity updatedUser = userRepository.save(userEntity);

        // convert the updatedUser into a userDTO object.
        return convertUserEntityToUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        // Check if user exists in the DB using the userRepository.existsById(userId) method or else throw an exception.
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        // otherwise delete the user using the userRepository.deleteById() method.
        userRepository.deleteById(userId);
    }


    private UserEntity convertUserDTOToUserEntity(UserDTO userDTO){
        return UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }

    private UserDTO convertUserEntityToUserDTO(UserEntity userEntity) {
        if (userEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();


        /* Alternatively, I could also consider returning an Optional<UserDTO> instead of throwing an exception.
         - This would allow the caller to handle the absence of a user in a more flexible way:

         private Optional<UserDTO> convertUserEntityToUserDTO(UserEntity userEntity) {
            if (userEntity == null) {
                return Optional.empty();
            }
            return Optional.of(UserDTO.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build());
         }
         */

    }

}
