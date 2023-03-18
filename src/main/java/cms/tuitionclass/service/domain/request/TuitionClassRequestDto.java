package cms.tuitionclass.service.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TuitionClassRequestDto extends RequestDto{
    private String locationName;
    private String address;
    private String district;
    private String province;

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(locationName) && isNonEmpty(address) && isNonEmpty(district) && isNonEmpty(province);
    }
}
