package com.eugene.shoegame.repositories;

import com.eugene.shoegame.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository /* Marks this interface as a DAO(Data Access Object).
  - DAOs abstract and encapsulate the data access and manipulation logic from the business logic of an application.
  - DAOs provide a layer of abstraction between the business logic and the data storage, making it easier to change or replace the data storage technology without affecting the business logic.
 */

/*
* We ShoeEntity and UserEntity types instead of DTOs (Data Transfer Objects) because these entities are the direct representations of the data stored in the database.
* There are several reasons why we don't return DTOs directly from the repositories:
        1. Separation of Concerns: Repositories are responsible for data access and retrieval. They should focus on retrieving the data from the database, not on transforming it into DTOs. This separation of concerns makes the code more modular and easier to maintain.
        2. Data Integrity: Entities are the direct representations of the data in the database. By returning entities, we ensure that the data is not modified or transformed in any way, maintaining its integrity.
* */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /* The Optional class helps avoid NullPointerExceptions in case a user is not found.
    * The findByUsername() method will be helpful during registration and login as I am using the user's username to check if they already exist in the Database.
    * If I try to register with a username that already exists, I will get a UserAlreadyExists exception(401 Not authorized ).
    * */
    Optional<UserEntity> findByUsername(String username);
    //boolean existsByUsername(String username);

    Optional<UserEntity> findById(Long userId);

    List<UserEntity> findAll();
}
