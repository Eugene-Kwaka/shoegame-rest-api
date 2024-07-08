package com.eugene.shoegame.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // entity represents a table in a relational database, and each entity instance corresponds to a single row in that table.
@Table(name="users") // This annotation specifies the name of the database table that this entity is mapped to.
public class UserEntity {

    @Id // This annotation specifies the primary key of the entity.

    /* specifies the primary key generation strategy.
    In this case, it uses the database's auto-increment feature to generate a unique identifier for each user. */
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    /* This annotation specifies the column name and constraints for the userId field.
    The nullable = false part means that the userId cannot be null.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false) // Unique username which cannot be null.
    private String username;

    @Column(nullable = false) // Password cannot be null.
    private String password;

}
