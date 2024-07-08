package com.eugene.shoegame.repositories;

import com.eugene.shoegame.entities.ShoeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ShoeRepository extends JpaRepository<ShoeEntity, Long> {

    // Find a shoe by its id. Can be accessed by a guest user
    Optional<ShoeEntity> findShoeById(Long id);

    //@Query("SELECT s FROM ShoeEntity s")
    // Find all shoes that have been created. Can be accessed by a guest user.
    List<ShoeEntity> findAll();

    // Find a shoe by its id and the id of the user it belongs to.
    // The Optional return type handles cases where no shoe is found with the specified shoe ID and User ID.
    Optional<ShoeEntity> findByIdAndUserEntity_UserId(Long id, Long userId);

    // Finds all shoes that belong to a specific user. It returns a list of shoe objects.
    List<ShoeEntity> findAllByUserEntity_UserId(Long userId);

    // Find shoe by its name, brand and user it belongs to. Helps to avoid a user creating a shoe that already exists.
    Optional<ShoeEntity> findByNameAndBrandAndUserEntity_UserId(String name, String brand, Long userId);





}
