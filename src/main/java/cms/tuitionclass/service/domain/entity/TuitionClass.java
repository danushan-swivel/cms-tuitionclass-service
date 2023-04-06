package cms.tuitionclass.service.domain.entity;

import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    @Column(length = 50)
    private String tuitionClassId;
    @Column(length = 20)
    private String locationName;
    @Column(length = 100)
    private String address;
    @Column(length = 20)
    private String district;
    @Column(length = 20)
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
        this.locationName = updateLocationRequestDto.getLocationName();
        this.province = updateLocationRequestDto.getProvince();
        this.updatedAt = new Date(System.currentTimeMillis());
        this.isDeleted = false;
    }
}
