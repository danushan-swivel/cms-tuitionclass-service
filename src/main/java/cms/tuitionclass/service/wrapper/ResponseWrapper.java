package cms.tuitionclass.service.wrapper;

import cms.tuitionclass.service.domain.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseWrapper {
    private String message;
    private int statusCode;
    private ResponseDto data;
}
