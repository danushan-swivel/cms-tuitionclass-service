package cms.tuitionclass.service.enums;

import lombok.Getter;

@Getter
public enum SuccessResponseStatus {
    LOCATION_CREATED("The class location created successfully"),
    READ_LOCATION_LIST("All location details retrieved successfully"),
    READ_LOCATION("Location retrieved successfully"),
    LOCATION_UPDATED("Location updated successfully"),
    LOCATION_DELETED("Location deleted successfully");
    private final String message;

    SuccessResponseStatus(String message) {
        this.message = message;
    }
}
