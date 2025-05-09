package com.shortened.model.generic.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Response")
public class GenericResponse {
    private String appResponseCode;
    private String appMessageCode;
    private String description;

    public String getAppResponseCode() {
        return appResponseCode;
    }

    public void setAppResponseCode(String appResponseCode) {
        this.appResponseCode = appResponseCode;
    }

    public String getAppMessageCode() {
        return appMessageCode;
    }

    public void setAppMessageCode(String appMessageCode) {
        this.appMessageCode = appMessageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GenericResponse() {
    }
    public GenericResponse(String appResponseCode, String appMessageCode, String description) {
        this.appResponseCode = appResponseCode;
        this.appMessageCode = appMessageCode;
        this.description = description;
    }

    public GenericResponse(GenericResponse msgResponse) {
        this.appResponseCode = msgResponse.appResponseCode;
        this.appMessageCode = msgResponse.appMessageCode;
        this.description = msgResponse.description;
    }
}
