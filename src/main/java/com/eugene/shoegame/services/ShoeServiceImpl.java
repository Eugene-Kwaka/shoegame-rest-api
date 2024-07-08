package com.eugene.shoegame.services;

import com.eugene.shoegame.dto.ShoeDTO;
import com.eugene.shoegame.entities.ShoeEntity;
import com.eugene.shoegame.entities.UserEntity;
import com.eugene.shoegame.exceptions.shoeexceptions.InvalidShoeDetailsException;
import com.eugene.shoegame.exceptions.shoeexceptions.ShoeAlreadyExistsException;
import com.eugene.shoegame.exceptions.shoeexceptions.ShoeNotFoundException;
import com.eugene.shoegame.exceptions.userexceptions.UserNotFoundException;
import com.eugene.shoegame.repositories.ShoeRepository;
import com.eugene.shoegame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //  marks the class as a service component in Spring's component scanning.
public class ShoeServiceImpl implements ShoeService {

    // Added as a dependency using Constructor injection.
    private final ShoeRepository shoeRepository;

    // Introducing user association to include users handling shoe's CRUD implementation
    private final UserRepository userRepository;

    @Autowired
    /*
     * The ShoeRepository interface is injected as a dependency (@autowired) to this class using Constructor injection.
     */
    public ShoeServiceImpl(final ShoeRepository shoeRepository, UserRepository userRepository){
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShoeDTO createShoe(Long userId, ShoeDTO shoeDTO) {

        // Check if a registered user from the DB exists and if they do not exist, throw an exception.
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the shoe details are valid
        if (shoeDTO.getSize() < 0 || shoeDTO.getPrice() < 0) {
            throw new InvalidShoeDetailsException("Invalid shoe details");
        }

        // Check if a shoe with the same name and brand and user already exists, if so throw an exception.
        if (shoeRepository.findByNameAndBrandAndUserEntity_UserId(shoeDTO.getName(), shoeDTO.getBrand(), userId).isPresent()) {
            throw new ShoeAlreadyExistsException("Shoe already exists");
        }

        // Converting the shoeDTO based as the method parameter into a shoeEntity.
        // the convertShoeDTOToShoeEntity() uses the .builder() methods to convert it to a shoeEntity.
        ShoeEntity shoeEntity= convertShoeDTOToShoeEntity(shoeDTO);

        // setting the userEntity found in the DB as the one responsible for creating the shoe.
        shoeEntity.setUserEntity(userEntity);

        // save the shoeEntity into the DB.
        ShoeEntity savedShoe = shoeRepository.save(shoeEntity);

        // convert it back to a shoeDTO.
        return convertShoeEntityToShoeDTO(savedShoe);
    }

    @Override
    public List<ShoeDTO> getAllShoes(){

        // Retrieving all shoes stored in the DB
        List<ShoeEntity> shoeEntities = shoeRepository.findAll();

        // Create a new empty List of shoesDTO that the method will return.
        List<ShoeDTO> allShoesDTO = new ArrayList<>();

        // For each shoeEntity in the shoeEntities list, convert into a DTO and add it to the List of shoeDTOs.
        for(ShoeEntity shoeEntity : shoeEntities){
            // convert the single shoeEntity to a shoeDTO
            ShoeDTO shoeDTO = convertShoeEntityToShoeDTO(shoeEntity);
            // add it to the list of other shoes.
            allShoesDTO.add(shoeDTO);
        }

        // return the list
        return allShoesDTO;
    };

    @Override
    public ShoeDTO getShoeById(Long id) {

        // Retrieving the shoe from the DB and if it doesn't exist, we throw an exception.
        ShoeEntity shoeEntity = shoeRepository.findShoeById(id)
                .orElseThrow(() -> new ShoeNotFoundException("Shoe not found"));

        // convert the retrieved shoe into a shoeDTO
        return convertShoeEntityToShoeDTO(shoeEntity);
    }

    @Override
    public ShoeDTO getShoeByUser(Long userId, Long id) {

        // Retrieving the shoe from an existing user in the DB and if it doesn't exist, we throw an exception.
        ShoeEntity shoeEntity = shoeRepository.findByIdAndUserEntity_UserId(id, userId)
                .orElseThrow(() -> new ShoeNotFoundException("Shoe not found"));

        // convert the retrieved shoe into a shoeDTO
        return convertShoeEntityToShoeDTO(shoeEntity);
    }

    @Override
    public List<ShoeDTO> getAllShoesByUser(Long userId) {

        // Check if the user exists in the DB to get all their shows, if not, throw an exception.
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Retrieving all shoes stored in the DB by the userId that created them using the shoeRepository.findAllByUserEntity_UserId()
        List<ShoeEntity> shoeEntities = shoeRepository.findAllByUserEntity_UserId(userId);
        // Create a new List of shoesDTO that the method will return.
        List<ShoeDTO> shoeDTOs = new ArrayList<>();

        // For each shoeEntity in the shoeEntities list, convert into a DTO and add it to the List of shoeDTOs.
        for (ShoeEntity shoeEntity : shoeEntities) {
            ShoeDTO shoeDTO = convertShoeEntityToShoeDTO(shoeEntity);
            shoeDTOs.add(shoeDTO);
        }

        // return the list
        return shoeDTOs;

    }

    @Override
    public ShoeDTO updateShoe(Long userId, Long id, ShoeDTO shoeDTO) {
        // First, I need to get an existing shoe from an existing user using the shoeRepository.findByIdAndUserEntity_UserId() method .
        // If the shoe don't exist, throw an exception.
        ShoeEntity existingShoeEntity = shoeRepository.findByIdAndUserEntity_UserId(id, userId)
                .orElseThrow(() -> new ShoeNotFoundException("Shoe to be updated not found"));

        // Check if the shoe details are valid
        if (shoeDTO.getSize() < 0 || shoeDTO.getPrice() < 0) {
            throw new InvalidShoeDetailsException("Invalid shoe details");
        }

        // The updateShoeEntityFromShoeDTO will convert the shoeDTO to a shoeEntity and make the updates the user wants.
        updateShoeEntityFromShoeDTO(existingShoeEntity, shoeDTO);

        // Then save the updatedShoeEntity into the DB
        ShoeEntity updatedShoe = shoeRepository.save(existingShoeEntity);

        // Convert it back to a shoeDTo and return it.
        return convertShoeEntityToShoeDTO(updatedShoe);
    }

    @Override
    public void deleteShoe(Long userId, Long shoeId) {
        // Check for the shoe belonging to an existing user in the DB and if absent, throw an exception.
        ShoeEntity shoeEntity = shoeRepository.findByIdAndUserEntity_UserId(shoeId, userId)
                .orElseThrow(() -> new ShoeNotFoundException("Shoe to be deleted not found."));

        // Use the shoeRepository.delete() method to delete if from the DB.
        shoeRepository.delete(shoeEntity);
    }

    // Takes a shoeDTO and returns a shoeEntity
    private ShoeEntity convertShoeDTOToShoeEntity(ShoeDTO shoeDTO){
        return ShoeEntity.builder()
                .name(shoeDTO.getName())
                .brand(shoeDTO.getBrand())
                .size(shoeDTO.getSize())
                .color(shoeDTO.getColor())
                .price(shoeDTO.getPrice())
                .build();
    }

    // Takes a shoeEntity and returns a shoeDTO
    private ShoeDTO convertShoeEntityToShoeDTO(ShoeEntity shoeEntity){
        return ShoeDTO.builder()
                .id(shoeEntity.getId())
                .name(shoeEntity.getName())
                .brand(shoeEntity.getBrand())
                .size(shoeEntity.getSize())
                .color(shoeEntity.getColor())
                .price(shoeEntity.getPrice())
                .userId(shoeEntity.getUserEntity().getUserId())
                .build();
    }

    // Returns nothing rather updates a shoeEntity using shoeDTO details from the client.
    private void updateShoeEntityFromShoeDTO(ShoeEntity shoeEntity, ShoeDTO shoeDTO){
        shoeEntity = ShoeEntity.builder()
                .name(shoeDTO.getName())
                .brand(shoeDTO.getBrand())
                .size(shoeDTO.getSize())
                .color(shoeDTO.getColor())
                .price(shoeDTO.getPrice())
                .build();

//        // Update the existing shoe with new values using the setter methods.
//        shoeEntity.setName(shoeDTO.getName());
//        shoeEntity.setSize(shoeDTO.getSize());
//        shoeEntity.setBrand(shoeDTO.getBrand());
//        shoeEntity.setColor(shoeDTO.getColor());
//        shoeEntity.setPrice(shoeDTO.getPrice());
    }
}
