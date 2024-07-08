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
@Entity //-> Entity classes represents a table in the database, with each property corresponding to a column in the table.
@Table(name="shoes") //-> Specifies the name of the database table the entity is mapped to.
public class ShoeEntity {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // specifies the primary key generation strategy.
    // In this case, it uses the database's auto-increment feature to generate a unique identifier for each shoe item.
    private Long id;

    private String name;
    private String brand;
    private Double size;
    private String color;
    private Double price;

    /*This annotation specifies a many-to-one relationship between ShoeEntity and UserEntity.
    It means that a shoe belongs to one user, and a user can have many shoes.
    The fetch = FetchType.LAZY part specifies that the related UserEntity should be loaded lazily,
    meaning it will only be loaded when it is actually needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    /*This annotation specifies the foreign key column in the database that links a shoe to its user.
     The nullable = false part means that a shoe must always be associated with a user.*/
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

}
