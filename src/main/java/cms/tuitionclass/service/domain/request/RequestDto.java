package cms.tuitionclass.service.domain.request;

import cms.tuitionclass.service.exception.TuitionClassException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class RequestDto {
    public boolean isRequiredAvailable() {
        return true;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new TuitionClassException("Convert object to string is failed", e);
        }
    }

    public boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
