package com.eugene.shoegame.exceptions;


import com.eugene.shoegame.exceptions.shoeexceptions.*;
import com.eugene.shoegame.exceptions.userexceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShoeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ShoeNotFoundException ex) {
        ApiErrorResponse error = new ApiErrorResponse("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidShoeDetailsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidShoeDetailsException(InvalidShoeDetailsException ex) {
        ApiErrorResponse error = new ApiErrorResponse("INVALID_SHOE_DETAILS", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShoeAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleShoeAlreadyExistsException(ShoeAlreadyExistsException ex) {
        ApiErrorResponse error = new ApiErrorResponse("SHOE_ALREADY_EXISTS", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShoeCreationFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleShoeCreationFailedException(ShoeCreationFailedException ex) {
        ApiErrorResponse error = new ApiErrorResponse("SHOE_CREATION_FAILED", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShoeUpdateFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleShoeUpdateFailedException(ShoeUpdateFailedException ex) {
        ApiErrorResponse error = new ApiErrorResponse("SHOE_UPDATE_FAILED", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShoeDeletionFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleShoeDeletionFailedException(ShoeDeletionFailedException ex) {
        ApiErrorResponse error = new ApiErrorResponse("SHOE_DELETION_FAILED", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidUserCredentialsException(InvalidUserCredentialsException ex) {
        ApiErrorResponse error = new ApiErrorResponse("INVALID_USER_CREDENTIALS", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        ApiErrorResponse error = new ApiErrorResponse("USER_NOT_AUTHORIZED", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExists(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("CONFLICT", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserCreationFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserCreationFailedException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserUpdateFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserUpdateFailedException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserDeletionFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDeletionFailedException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserLoginAttemptFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserLoginAttemptFailedException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
