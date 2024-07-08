package com.eugene.shoegame.exceptions.shoeexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShoeNotFoundException  extends RuntimeException {
        public ShoeNotFoundException(String message) {
            super(message);
        }

}
