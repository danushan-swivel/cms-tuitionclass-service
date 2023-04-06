package cms.tuitionclass.service.domain.response;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class TuitionClassResponseDto extends ResponseDto {
    private String tuitionClassId;
    private String locationName;
    private String address;
    private String district;
    private String province;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;

    public TuitionClassResponseDto(TuitionClass tuitionClass) {
        this.tuitionClassId = tuitionClass.getTuitionClassId();
        this.locationName = tuitionClass.getLocationName();
        this.address = tuitionClass.getAddress();
        this.district = tuitionClass.getDistrict();
        this.province = tuitionClass.getProvince();
        this.createdAt = tuitionClass.getCreatedAt();
        this.updatedAt = tuitionClass.getUpdatedAt();
        this.isDeleted = tuitionClass.isDeleted();
    }
}
