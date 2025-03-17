package com.shortened.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shortened.constants.MessageConstants;
import com.shortened.model.generic.response.GenericResponse;
import com.shortened.exception.type.InternalErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<GenericResponse> handleException(Exception ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof InvalidFormatException) {
            errorJsonResponse.setAppResponseCode(MessageConstants.INVALID_FORMAT_ERROR_CODE);
            errorJsonResponse.setAppMessageCode(MessageConstants.INVALID_FORMAT_MESSAGE_CODE);
            errorJsonResponse.setDescription(ex.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof InternalErrorException) {
            errorJsonResponse.setAppResponseCode(MessageConstants.INTERNAL_SERVER_ERROR_CODE);
            errorJsonResponse.setAppMessageCode(MessageConstants.INTERNAL_SERVER_ERROR_MESSAGE_CODE);
            errorJsonResponse.setDescription(ex.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errorJsonResponse, httpStatus);
    }

}
