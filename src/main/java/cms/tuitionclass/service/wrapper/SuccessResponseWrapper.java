package cms.tuitionclass.service.wrapper;

import cms.tuitionclass.service.domain.response.ResponseDto;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseWrapper extends ResponseWrapper{
    public SuccessResponseWrapper(SuccessResponseStatus responseStatus, ResponseDto data) {
        super(responseStatus.getMessage(), responseStatus.getStatusCode(), data);
    }
}
