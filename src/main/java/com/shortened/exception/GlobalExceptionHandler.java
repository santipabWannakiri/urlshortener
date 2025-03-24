package com.shortened.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shortened.constants.MessageConstants;
import com.shortened.model.generic.response.GenericResponse;
import com.shortened.exception.type.InternalErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        // Combine all validation messages into one string (optional)
        String validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        errorJsonResponse.setAppResponseCode(MessageConstants.INVALID_FORMAT_ERROR_CODE);
        errorJsonResponse.setAppMessageCode(MessageConstants.INVALID_FORMAT_MESSAGE_CODE);
        errorJsonResponse.setDescription(validationErrors);

        return new ResponseEntity<>(errorJsonResponse, httpStatus);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<GenericResponse> handleInvalidFormatException(InvalidFormatException ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        errorJsonResponse.setAppResponseCode(MessageConstants.INVALID_FORMAT_ERROR_CODE);
        errorJsonResponse.setAppMessageCode(MessageConstants.INVALID_FORMAT_MESSAGE_CODE);
        errorJsonResponse.setDescription(ex.getMessage());

        return new ResponseEntity<>(errorJsonResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<GenericResponse> handleInternalErrorException(InternalErrorException ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        errorJsonResponse.setAppResponseCode(MessageConstants.INTERNAL_SERVER_ERROR_CODE);
        errorJsonResponse.setAppMessageCode(MessageConstants.INTERNAL_SERVER_ERROR_MESSAGE_CODE);
        errorJsonResponse.setDescription(ex.getMessage());

        return new ResponseEntity<>(errorJsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleGeneralException(Exception ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        errorJsonResponse.setAppResponseCode(MessageConstants.INTERNAL_SERVER_ERROR_CODE);
        errorJsonResponse.setAppMessageCode(MessageConstants.INTERNAL_SERVER_ERROR_MESSAGE_CODE);
        errorJsonResponse.setDescription("Unexpected error: " + ex.getMessage());

        return new ResponseEntity<>(errorJsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}