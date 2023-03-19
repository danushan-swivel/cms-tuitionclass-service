package cms.tuitionclass.service.service;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import cms.tuitionclass.service.exception.InvalidTuitionClassException;
import cms.tuitionclass.service.exception.TuitionClassException;
import cms.tuitionclass.service.repository.TuitionClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * Tuition class service
 */
@Service
public class TuitionClassService {
    private static final int PAGE = 0;
    private static final int SIZE = 100;
    private static final String DEFAULT_SORT = "updated_at";
    private final TuitionClassRepository tuitionClassRepository;

    @Autowired
    public TuitionClassService(TuitionClassRepository tuitionClassRepository) {
        this.tuitionClassRepository = tuitionClassRepository;
    }

    /**
     * This method get tuition class by id
     *
     * @param tuitionClassId tuition class id
     * @return TuitionClass
     */
    public TuitionClass getTuitionClassById(String tuitionClassId) {
        try {
            var optionalTuitionClass = tuitionClassRepository.findByTuitionClassId(tuitionClassId);
            if (optionalTuitionClass.isEmpty()) {
                throw new InvalidTuitionClassException("The given location id is not exists.");
            }
            return optionalTuitionClass.get();
        } catch (DataAccessException e) {
            throw new TuitionClassException("Getting class location from database is failed", e);
        }
    }

    /**
     * This method save new tuition class
     *
     * @param tuitionClassRequestDto tuition class request dto
     * @return TuitionClass
     */
    public TuitionClass saveTuitionClass(TuitionClassRequestDto tuitionClassRequestDto) {
        try {
            TuitionClass tuitionClass = new TuitionClass(tuitionClassRequestDto);
            return tuitionClassRepository.save(tuitionClass);
        } catch (DataAccessException e) {
            throw new TuitionClassException("Saving class location is failed", e);
        }
    }

    /**
     * This method get tuition class page
     *
     * @return TuitionClassPage
     */
    public Page<TuitionClass> getTuitionClassPage() {
        try {
            Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(DEFAULT_SORT).ascending());
            return tuitionClassRepository.findAll(pageable);
        } catch (DataAccessException e) {
            throw new TuitionClassException("Retrieving location page from database is failed. ");
        }
    }

    /**
     * This method update tuition class request dto
     *
     * @param updateTuitionClassRequestDto update tuition class request dto
     * @return TuitionClass
     */
    public TuitionClass updateTuitionClass(UpdateTuitionClassRequestDto updateTuitionClassRequestDto) {
        try {
            TuitionClass tuitionClass = getTuitionClassById(updateTuitionClassRequestDto.getTuitionClassId());
            tuitionClass.update(updateTuitionClassRequestDto);
            tuitionClassRepository.save(tuitionClass);
            return tuitionClass;
        } catch (DataAccessException e) {
            throw new TuitionClassException("Updating location in database is failed. Id: "
                    + updateTuitionClassRequestDto.getTuitionClassId(), e);
        }
    }

    /**
     * This method delete tuition class by id
     *
     * @param locationId tuition class id
     */
    public void deleteLocation(String locationId) {
        try {
            TuitionClass tuitionClassFromDB = getTuitionClassById(locationId);
            tuitionClassFromDB.setDeleted(true);
            tuitionClassFromDB.setUpdatedAt(new Date(System.currentTimeMillis()));
            tuitionClassRepository.save(tuitionClassFromDB);
        } catch (DataAccessException e) {
            throw new TuitionClassException("Deleting location from database is failed. Id: " + locationId, e);
        }
    }
}
