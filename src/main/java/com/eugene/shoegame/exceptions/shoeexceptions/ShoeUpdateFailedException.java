package com.eugene.shoegame.exceptions.shoeexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ShoeUpdateFailedException extends RuntimeException {
    public ShoeUpdateFailedException(String message) {
        super(message);
    }
}
