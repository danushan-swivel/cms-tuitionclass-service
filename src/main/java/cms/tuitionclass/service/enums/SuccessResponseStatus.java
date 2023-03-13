package cms.tuitionclass.service.enums;

import lombok.Getter;

@Getter
public enum SuccessResponseStatus {
    STUDENT_CREATED(2000, "The student created successfully"),
    READ_STUDENT_LIST(2001, "Students details retrieved successfully"),
    LOCATION_CREATED(2030, "The class location created successfully"),
    READ_LOCATION_LIST(2031, "All location details retrieved successfully"),
    READ_LOCATION(2032, "Location retrieved successfully"),
    LOCATION_UPDATED(2033, "Location updated successfully"),
    LOCATION_DELETED(2034, "Location deleted successfully"),
    PAID_SUCCESSFUL(2050, "The payment made successfully");
    private final String message;
    private final int statusCode;

    SuccessResponseStatus(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
