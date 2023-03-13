package cms.tuitionclass.service.domain.response;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TuitionClassListResponseDto extends ResponseDto{
    private final List<TuitionClassResponseDto> locations;

    public TuitionClassListResponseDto(Page<TuitionClass> tuitionClassPage) {
        this.locations = convertToResponseDto(tuitionClassPage);
    }

    private List<TuitionClassResponseDto> convertToResponseDto(Page<TuitionClass> tuitionClassPage) {
        return tuitionClassPage.stream().map(TuitionClassResponseDto::new).collect(Collectors.toList());
    }
}