package cms.tuitionclass.service.enums;

import lombok.Getter;

@Getter
public enum ErrorResponseStatus {
    INTERNAL_SERVER_ERROR("Internal server error"),
    MISSING_REQUIRED_FIELDS("The required fields are missing"),
    INVALID_LOCATION("The selected location not found"),
    LOCATION_EXISTS("The tuition class location already exists");
    private final String message;

    ErrorResponseStatus(String message) {
        this.message = message;
    }
}
