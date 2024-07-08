package com.eugene.shoegame.services;

import com.eugene.shoegame.dto.ShoeDTO;

import java.util.List;
/*
* The ShoeService interface provides methods for creating, retrieving, updating, and deleting shoes.
* It acts as an intermediary between the controller layer and the repository layer, encapsulating the business logic related to shoes.
*/
public interface ShoeService {
    // Creates a new ShoeEntity using the ShoeRepository and returns a ShoeDTO.
    public ShoeDTO createShoe(Long userId, ShoeDTO shoeDTO);

    // Retrieves all ShoeEntity objects using the ShoeRepository and returns ShoeDTO objects.
    public List<ShoeDTO> getAllShoes();

    // Retrieves a ShoeEntity by ID using the ShoeRepository and returns a ShoeDTO.
    public ShoeDTO getShoeById(Long id);

    // Retrieves a ShoeEntity by ID and the user's ID using the ShoeRepository and returns a ShoeDTO.
    public ShoeDTO getShoeByUser(Long userId, Long id);

    // Retrieves a ShoeEntity object by ID from a specific user (userId) using the ShoeRepository and returns a ShoeDTO.
    public List<ShoeDTO> getAllShoesByUser(Long userId);

    // Updates a ShoeEntity using the ShoeRepository and returns a ShoeDTO.
    public ShoeDTO updateShoe(Long userId, Long id, ShoeDTO shoeDTO);

    // Deletes a ShoeEntity using the ShoeRepository
    public void deleteShoe(Long userId, Long id);
}
