package cms.tuitionclass.service.wrapper;

import cms.tuitionclass.service.domain.response.ResponseDto;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SuccessResponseWrapper extends ResponseWrapper{
    public SuccessResponseWrapper(SuccessResponseStatus responseStatus, ResponseDto data, HttpStatus httpStatus) {
        super(responseStatus.getMessage(), httpStatus.value(), data);
    }
}
