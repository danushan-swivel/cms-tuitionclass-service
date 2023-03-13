package cms.tuitionclass.service.domain.entity;

import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tuition_class")
@Entity
public class TuitionClass {
    private static final String PREFIX ="tid-";
    @Id
    private String tuitionClassId;
    private String locationName;
    private String address;
    private String district;
    private String province;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;

    public TuitionClass(TuitionClassRequestDto tuitionClassRequestDto) {
        this.tuitionClassId = PREFIX + UUID.randomUUID();
        this.locationName = tuitionClassRequestDto.getLocationName();
        this.address = tuitionClassRequestDto.getAddress();
        this.district = tuitionClassRequestDto.getDistrict();
        this.province = tuitionClassRequestDto.getProvince();
        this.createdAt = this.updatedAt = new Date(System.currentTimeMillis());
        this.isDeleted = false;
    }

    public void update(UpdateTuitionClassRequestDto updateLocationRequestDto) {
        this.address = updateLocationRequestDto.getAddress();
        this.district = updateLocationRequestDto.getDistrict();
        this.province = updateLocationRequestDto.getProvince();
        this.updatedAt = new Date(System.currentTimeMillis());
        this.isDeleted = false;
    }
}
