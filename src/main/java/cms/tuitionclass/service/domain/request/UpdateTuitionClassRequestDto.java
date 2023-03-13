package cms.tuitionclass.service.domain.request;

import lombok.Getter;

@Getter
public class UpdateTuitionClassRequestDto extends RequestDto{
    private String tuitionClassId;
    private String address;
    private String district;
    private String province;

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(tuitionClassId) && isNonEmpty(address) && isNonEmpty(district) && isNonEmpty(province);
    }
}
