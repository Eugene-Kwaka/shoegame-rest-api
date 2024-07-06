package com.eugene.shoegame.dto;


import com.eugene.shoegame.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoeDTO {

    private Long id;
    private String name;
    private String brand;
    private Double size;
    private String color;
    private Double price;
    private Long userId;
}
