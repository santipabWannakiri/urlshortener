package com.shortened.constants;

public final class MessageConstants {

    //====== SUCCESS ======
    public static final String CODE_SUCCESS = "0000";
    public static final String MESSAGE_SUCCESS = "SUCCESS";
    public static final String MESSAGE_REGISTER_SUCCESS = "Registered successfully.";
    public static final String MESSAGE_LOGOUT_SUCCESS = "User logged out successfully.";

    // ====== INVALID_FORMAT ======
    public static final String INVALID_FORMAT_ERROR_CODE = "6400";
    public static final String INVALID_FORMAT_MESSAGE_CODE = "INVALID_FORMAT";
    public static final String AUTHORIZATION_HEADER_MISSING = "Authorization header is missing from the request.";

    // ====== INTERNAL_SERVER_ERROR ======
    public static final String INTERNAL_SERVER_ERROR_CODE = "6601";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE_CODE = "FAILURE";
    public static final String REGISTRATION_FAILURE = "Registration failed. Please check your connection and try again.";
    public static final String REQUEST_PROCESSING_FAILURE = "An error occurred while processing your request. Please try again later.";


}
