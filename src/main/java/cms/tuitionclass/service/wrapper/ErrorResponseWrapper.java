package cms.tuitionclass.service.wrapper;

import cms.tuitionclass.service.domain.response.ResponseDto;
import cms.tuitionclass.service.enums.ErrorResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseWrapper extends ResponseWrapper{
    public ErrorResponseWrapper(ErrorResponseStatus responseStatus, ResponseDto data) {
        super(responseStatus.getMessage(), responseStatus.getStatusCode(), data);
    }
}
