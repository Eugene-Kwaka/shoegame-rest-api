package com.eugene.shoegame.exceptions.shoeexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ShoeAlreadyExistsException extends RuntimeException{
    public ShoeAlreadyExistsException(String message) {
        super(message);
    }
}
