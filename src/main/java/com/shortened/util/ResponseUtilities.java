package com.shortened.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortened.constants.MessageConstants;
import com.shortened.model.generic.response.GenericResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public final class ResponseUtilities {
    private static final Logger logger = LogManager.getLogger(ResponseUtilities.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void sendCustomErrorResponse(HttpServletResponse response, int status, String appResponseCode, String appMessageCode, String description) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        GenericResponse errorResponse = new GenericResponse(appResponseCode, appMessageCode, description);
        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("Failed to send custom error response", e);
            throw e;
        }
    }

    public static ResponseEntity<GenericResponse> createSuccessResponse(String description) {
        GenericResponse response = new GenericResponse(MessageConstants.CODE_SUCCESS, MessageConstants.MESSAGE_SUCCESS, description);
        return ResponseEntity.ok(response);
    }

}
