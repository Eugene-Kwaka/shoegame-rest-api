package com.eugene.shoegame.controllers;

import com.eugene.shoegame.services.ShoeService;
import com.eugene.shoegame.dto.ShoeDTO;
import com.eugene.shoegame.exceptions.shoeexceptions.*;
import com.eugene.shoegame.exceptions.userexceptions.UserNotAuthorizedException;
import com.eugene.shoegame.exceptions.userexceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // marks the class as a RESTful web service controller.
//  specifies the base URL for all methods in this controller.
@RequestMapping("/api/v2/shoegame")
public class ShoeController {

    // Injecting the ShoeService interface a dependency for the ShoeController class using Constructor injection.
    // We will be implementing the ShoeService interface methods in this class.
    private final ShoeService shoeService; 

    @Autowired // marks the ShoeService interface as a dependency for our controller class.
    public ShoeController(ShoeService shoeService){
        this.shoeService = shoeService;
    }

    @PostMapping("/users/{userId}/shoes")
    /* maps POST request to the controller method.
    - Adding the @PathVariable {userId} injects the userId path variable from the URL.
    - We are adding it to our method because only a registered user can create a shoe item.
    - The @RequestBody defines that a JSON body to be included in the request body. The body defines the shoeDTO object's details.
    */
    public ResponseEntity<ShoeDTO> createShoe(@PathVariable Long userId, @RequestBody ShoeDTO shoeDTO){

        try{
            // the shoeService.createShoe() method is called and returns a shoeDTO object.
            // The object is then saved in a createdShoeDTO object which will be returned as a res
            ShoeDTO createdShoe = shoeService.createShoe(userId, shoeDTO);

            // The createdShoeDTO object is returned as a ResponseEntity object.
            // The new object contains a response body and the HTTP Status -> CREATED.
            return new ResponseEntity<>(createdShoe, HttpStatus.CREATED);

        // If the code above returns exceptions, catch them in the code below.
        } catch (UserNotFoundException e) {
            throw new UserNotAuthorizedException("User not found or not authorized");
        } catch (ShoeAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new ShoeCreationFailedException("Failed to create shoe item.");
        }
    }

    @GetMapping("/shoes")
    public ResponseEntity<List<ShoeDTO>> getAllShoes(){

        // Call the shoeService.getAllShoes() method that returns a list of shoes and save it in the allShoes list
        List<ShoeDTO> allShoes = shoeService.getAllShoes();

        // From the list, return it as a ResponseEntity object.
        return ResponseEntity.ok(allShoes);
    }

    @GetMapping("/shoes/{id}")
    public ResponseEntity<ShoeDTO> getShoeById(@PathVariable Long id){

        // Call the shoeService.getShoeById() method to return the individual shoe item.
        ShoeDTO shoeDTO = shoeService.getShoeById(id);

        // return it as a ResponseEntity object.
        return ResponseEntity.ok(shoeDTO);
    }


    @GetMapping("/users/{userId}/shoes/{id}")
    public ResponseEntity<ShoeDTO> getShoeByUser(@PathVariable Long userId, @PathVariable Long id){

        // call the shoeService.getShoesByUser() method that returns a shoe item belonging to an existing user.
        ShoeDTO shoeDTO = shoeService.getShoeByUser(userId, id);
        return ResponseEntity.ok(shoeDTO);
    }

    @GetMapping("/users/{userId}/shoes")
    public ResponseEntity<List<ShoeDTO>> getAllShoesByUser(@PathVariable Long userId){


        // Return a list of shoes created by a user using the getAllShoesByUser() method.
        List<ShoeDTO> allShoes = shoeService.getAllShoesByUser(userId);
        return ResponseEntity.ok(allShoes);
    }

    @PutMapping("/users/{userId}/shoes/{id}")
    // the @PathVariable defines the shoe id passed in the endpoint URL.
    // The @RequestBody defines the updated set of data needed to be passed as JSON to update an existing shoe's data.
    public ResponseEntity<ShoeDTO> updateShoe(@PathVariable Long userId, @PathVariable Long id, @RequestBody ShoeDTO shoeDTO){
        try{
            // calling the updateShoe() method from the service impl class provides the updatedShoeDTO item.
            ShoeDTO updatedShoe = shoeService.updateShoe(userId, id, shoeDTO);
            return ResponseEntity.ok(updatedShoe);

        // if the updateShoe() method fails, then throw the shoeUpdateFailedException.
        } catch (Exception e){
            throw new ShoeUpdateFailedException("Failed to update shoe item.");
        }

    }

    @DeleteMapping("/users/{userId}/shoes/{id}")
    public ResponseEntity<ShoeDTO> deleteShoe(@PathVariable Long userId, @PathVariable Long id){
        try{
            shoeService.deleteShoe(userId, id);
            return ResponseEntity.noContent().build();

        } catch (Exception e){
            throw new ShoeDeletionFailedException("Failed to delete shoe item.");
        }
    }

}

