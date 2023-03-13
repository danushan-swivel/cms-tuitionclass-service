package cms.tuitionclass.service.domain.response;

import cms.tuitionclass.service.exception.TuitionClassException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ResponseDto {
    public String toLogJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new TuitionClassException("Convert object to string is failed", e);
        }
    }
}
