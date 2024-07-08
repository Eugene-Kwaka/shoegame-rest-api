package com.eugene.shoegame.dto;


import com.eugene.shoegame.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //-> Lombok annotation that generates setter and getter methods for the fields
@AllArgsConstructor // -> Creates a parameterized constructor.
@NoArgsConstructor // -> Creates a default constructor without parameters.
@Builder // -> Generates a builder class that creates instances of classes in a readable way.
public class ShoeDTO {
    private Long id;
    private String name;
    private String brand;
    private Double size;
    private String color;
    private Double price;
    // private field to store the userId associated with a shoe item.
    private Long userId;
}
