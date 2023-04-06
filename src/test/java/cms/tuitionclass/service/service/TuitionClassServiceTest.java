package cms.tuitionclass.service.service;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import cms.tuitionclass.service.exception.InvalidTuitionClassException;
import cms.tuitionclass.service.exception.TuitionClassAlreadyExistsException;
import cms.tuitionclass.service.exception.TuitionClassException;
import cms.tuitionclass.service.repository.TuitionClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TuitionClassServiceTest {

    private static final String TUITION_CLASS_ID = "tid-1256-9541-8523-7536";
    private static final String LOCATION_NAME = "Elegance";
    private static final String UPDATED_ADDRESS = "Queen's Road";
    private static final String DISTRICT = "Colombo";
    private static final String ADDRESS = "Queen's Road, Duplication Road";
    private static final String PROVINCE = "South";
    private static final int PAGE = 0;
    private static final int SIZE = 100;
    private static final String DEFAULT_SORT = "updated_at";

    @Mock
    private TuitionClassRepository tuitionClassRepository;
    private TuitionClassService tuitionClassService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        tuitionClassService = new TuitionClassService(tuitionClassRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Should_ReturnTuitionClass_When_CreateTuitionClassSuccessfully() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndIsDeletedFalse(LOCATION_NAME)).thenReturn(false);
        when(tuitionClassRepository.save(any(TuitionClass.class))).thenReturn(tuitionClass);
        assertEquals(tuitionClass, tuitionClassService.saveTuitionClass(tuitionClassRequestDto));
    }

    @Test
    void Should_ThrowTuitionClassAlreadyExistsException_When_TuitionClassLocationNameAlreadyExistsForCreateNewTuitionClass() {
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndIsDeletedFalse(LOCATION_NAME)).thenReturn(true);
        TuitionClassAlreadyExistsException exception = assertThrows(TuitionClassAlreadyExistsException.class, () ->
                tuitionClassService.saveTuitionClass(tuitionClassRequestDto));
        assertEquals("Tuition class name already exists in database", exception.getMessage());
    }

    @Test
    void Should_ThrowTuitionClassException_When_CheckDuplicateTuitionClassNameIsFailed() {
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndIsDeletedFalse(LOCATION_NAME))
                .thenThrow(new DataAccessException("ERROR") { });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () ->
                tuitionClassService.saveTuitionClass(tuitionClassRequestDto));
        assertEquals("Checking existing tuition class location name is failed", exception.getMessage());
    }

    @Test
    void Should_ThrowTuitionClassException_When_SaveTuitionCLassIsFailed() {
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndIsDeletedFalse(LOCATION_NAME)).thenReturn(false);
        when(tuitionClassRepository.save(any(TuitionClass.class))).thenThrow(new DataAccessException("ERROR") {
        });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () ->
                tuitionClassService.saveTuitionClass(tuitionClassRequestDto));
        assertEquals("Saving class location is failed", exception.getMessage());
    }

    @Test
    void Should_ReturnTuitionClass_When_UpdateTuitionClassSuccessfully() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        tuitionClass.setAddress(UPDATED_ADDRESS);
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndTuitionClassIdNotAndIsDeletedFalse(LOCATION_NAME, TUITION_CLASS_ID))
                .thenReturn(false);
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenReturn(Optional.of(tuitionClass));
        when(tuitionClassRepository.save(tuitionClass)).thenReturn(tuitionClass);
        assertEquals(tuitionClass, tuitionClassService.updateTuitionClass(updateTuitionClassRequestDto));
    }

    @Test
    void Should_ThrowTuitionClassAlreadyExistsException_When_TuitionClassLocationNameAlreadyExistsForUpdateTuitionClass() {
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndTuitionClassIdNotAndIsDeletedFalse(LOCATION_NAME, TUITION_CLASS_ID))
                .thenReturn(true);
        TuitionClassAlreadyExistsException exception = assertThrows(TuitionClassAlreadyExistsException.class, () ->
                tuitionClassService.updateTuitionClass(updateTuitionClassRequestDto));
        assertEquals("Tuition class name already exists in database", exception.getMessage());
    }

    @Test
    void Should_ThrowInvalidTuitionClassException_When_InvalidTuitionClassIdIsProvided() {
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        when(tuitionClassRepository.existsByLocationNameAndTuitionClassIdNotAndIsDeletedFalse(LOCATION_NAME, TUITION_CLASS_ID))
                .thenReturn(false);
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenReturn(Optional.empty());
        InvalidTuitionClassException exception = assertThrows(InvalidTuitionClassException.class, () ->
                tuitionClassService.updateTuitionClass(updateTuitionClassRequestDto));
        assertEquals("The given location id is not exists.", exception.getMessage());
    }

    @Test
    void Should_ThrowTuitionClassException_When_GetTuitionClassFromDatabaseIsFailed() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        tuitionClass.setAddress(UPDATED_ADDRESS);
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenThrow(new DataAccessException("ERROR") {
        });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () ->
                tuitionClassService.updateTuitionClass(updateTuitionClassRequestDto));
        assertEquals("Getting class location from database is failed", exception.getMessage());
    }

    @Test
    void Should_ThrowTuitionClassException_When_UpdateTuitionCLassIsFailed() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        tuitionClass.setAddress(UPDATED_ADDRESS);
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenReturn(Optional.of(tuitionClass));
        when(tuitionClassRepository.save(tuitionClass)).thenThrow(new DataAccessException("ERROR") {
        });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () ->
                tuitionClassService.updateTuitionClass(updateTuitionClassRequestDto));
        assertEquals("Updating location in database is failed. Id: " + TUITION_CLASS_ID, exception.getMessage());
    }

    @Test
    void Should_ReturnTuitionClassPage() {
        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(DEFAULT_SORT).ascending());
        Page<TuitionClass> tuitionClassPage = getSampleTuitionClassPage();
        when(tuitionClassRepository.findAll(pageable)).thenReturn(tuitionClassPage);
        assertEquals(tuitionClassPage, tuitionClassService.getTuitionClassPage());
    }

    @Test
    void Should_ThrowTuitionClassException_When_GetTuitionClassIsFailed() {
        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(DEFAULT_SORT).ascending());
        when(tuitionClassRepository.findAll(pageable)).thenThrow(new DataAccessException("ERROR") {
        });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () -> tuitionClassService.getTuitionClassPage());
        assertEquals("Retrieving location page from database is failed. ", exception.getMessage());
    }

    @Test
    void Should_DeletedTuitionClass_When_ValidTuitionClassIdIsProvided() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenReturn(Optional.of(tuitionClass));
        tuitionClassService.deleteLocation(TUITION_CLASS_ID);
        verify(tuitionClassRepository, times(1)).save(tuitionClass);
    }

    @Test
    void Should_ThrowTuitionClassException_When_DeleteTuitionClassIsFailed() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        when(tuitionClassRepository.findByTuitionClassId(TUITION_CLASS_ID)).thenReturn(Optional.of(tuitionClass));
        when(tuitionClassRepository.save(tuitionClass)).thenThrow(new DataAccessException("ERROR") { });
        TuitionClassException exception = assertThrows(TuitionClassException.class, () ->
                tuitionClassService.deleteLocation(TUITION_CLASS_ID));
        assertEquals("Deleting location from database is failed. Id: " + TUITION_CLASS_ID, exception.getMessage());
    }

    /**
     * This method creates sample tuition class
     *
     * @return TuitionClass
     */
    private TuitionClass getSampleTuitionClass() {
        TuitionClass tuitionClass = new TuitionClass();
        tuitionClass.setTuitionClassId(TUITION_CLASS_ID);
        tuitionClass.setAddress(ADDRESS);
        tuitionClass.setLocationName(LOCATION_NAME);
        tuitionClass.setDistrict(DISTRICT);
        tuitionClass.setProvince(PROVINCE);
        tuitionClass.setDeleted(false);
        return tuitionClass;
    }

    /**
     * This method creates sample tuition class request dto
     *
     * @return TuitionClassRequestDto
     */
    private TuitionClassRequestDto getSampleTuitionClassRequestDto() {
        TuitionClassRequestDto tuitionClassRequestDto = new TuitionClassRequestDto();
        tuitionClassRequestDto.setProvince(PROVINCE);
        tuitionClassRequestDto.setLocationName(LOCATION_NAME);
        tuitionClassRequestDto.setAddress(ADDRESS);
        tuitionClassRequestDto.setDistrict(DISTRICT);
        return tuitionClassRequestDto;
    }

    /**
     * This method creates sample update tuition class request dto
     *
     * @return UpdateTuitionClassRequestDto
     */
    private UpdateTuitionClassRequestDto getSampleUpdateTuitionClassRequestDto() {
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = new UpdateTuitionClassRequestDto();
        updateTuitionClassRequestDto.setProvince(PROVINCE);
        updateTuitionClassRequestDto.setLocationName(LOCATION_NAME);
        updateTuitionClassRequestDto.setTuitionClassId(TUITION_CLASS_ID);
        updateTuitionClassRequestDto.setAddress(UPDATED_ADDRESS);
        updateTuitionClassRequestDto.setDistrict(DISTRICT);
        return updateTuitionClassRequestDto;
    }

    /**
     * This method creates sample tuition class page
     *
     * @return TuitionClassPage
     */
    private Page<TuitionClass> getSampleTuitionClassPage() {
        TuitionClass tuitionClass = getSampleTuitionClass();
        List<TuitionClass> tuitionClasses = new ArrayList<>();
        tuitionClasses.add(tuitionClass);
        return new PageImpl<>(tuitionClasses);
    }
}