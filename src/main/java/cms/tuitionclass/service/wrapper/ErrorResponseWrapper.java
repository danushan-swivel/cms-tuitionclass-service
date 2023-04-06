package cms.tuitionclass.service.wrapper;

import cms.tuitionclass.service.enums.ErrorResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorResponseWrapper extends ResponseWrapper {
    public ErrorResponseWrapper(ErrorResponseStatus responseStatus, HttpStatus httpStatus) {
        super(responseStatus.getMessage(), httpStatus.value(), null);
    }
}
