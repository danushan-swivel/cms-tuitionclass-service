package cms.tuitionclass.service.controller;

import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import cms.tuitionclass.service.domain.response.TuitionClassListResponseDto;
import cms.tuitionclass.service.domain.response.TuitionClassResponseDto;
import cms.tuitionclass.service.enums.ErrorResponseStatus;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import cms.tuitionclass.service.service.TuitionClassService;
import cms.tuitionclass.service.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/v1/tuition")
@RestController
public class TuitionClassController extends BaseController {
    private final TuitionClassService tuitionClassService;

    public TuitionClassController(TuitionClassService tuitionClassService) {
        this.tuitionClassService = tuitionClassService;
    }

    /**
     * Create tuition class
     *
     * @param tuitionClassRequestDto tuition class request dto
     * @return success/ error response
     */
    @PostMapping("")
    public ResponseEntity<ResponseWrapper> createTuitionClass(@RequestBody TuitionClassRequestDto tuitionClassRequestDto) {
        if (!tuitionClassRequestDto.isRequiredAvailable()) {
            log.debug("The required fields {} are missing for create new tuition class", tuitionClassRequestDto.toJson());
            return getErrorResponse(ErrorResponseStatus.MISSING_REQUIRED_FIELDS);
        }
        var tuitionClass = tuitionClassService.saveTuitionClass(tuitionClassRequestDto);
        var responseDto = new TuitionClassResponseDto(tuitionClass);
        log.debug("The tuition class is created successfully");
        return getSuccessResponse(SuccessResponseStatus.LOCATION_CREATED, responseDto, HttpStatus.CREATED);
    }

    /**
     * Get all tuition class detail
     *
     * @return success/ error response
     */
    @GetMapping("")
    public ResponseEntity<ResponseWrapper> getAllTuitionClasses() {
        var tuitionClassPage = tuitionClassService.getTuitionClassPage();
        var responseDto = new TuitionClassListResponseDto(tuitionClassPage);
        log.debug("Retrieving all tuition class details successfully");
        return getSuccessResponse(SuccessResponseStatus.READ_LOCATION_LIST, responseDto, HttpStatus.OK);
    }

    /**
     * Get tuition class by tuition class id
     *
     * @param tuitionClassId tuition class id
     * @return success/ error response
     */
    @GetMapping("/{tuitionClassId}")
    public ResponseEntity<ResponseWrapper> getTuitionClassById(@PathVariable String tuitionClassId) {
        var tuitionClass = tuitionClassService.getTuitionClassById(tuitionClassId);
        var responseDto = new TuitionClassResponseDto(tuitionClass);
        log.debug("Tuition class retrieved successfully for tuition class id: {}", tuitionClassId);
        return getSuccessResponse(SuccessResponseStatus.READ_LOCATION, responseDto, HttpStatus.OK);
    }

    /**
     * Update existing tuition class
     *
     * @param updateLocationRequestDto update location request dto
     * @return success/ error response
     */
    @PutMapping("")
    public ResponseEntity<ResponseWrapper> updateTuitionClass(@RequestBody UpdateTuitionClassRequestDto updateLocationRequestDto) {
        if (!updateLocationRequestDto.isRequiredAvailable()) {
            log.debug("The required fields {} are missing for update tuition class id: {}",
                    updateLocationRequestDto.toJson(), updateLocationRequestDto.getTuitionClassId());
            return getErrorResponse(ErrorResponseStatus.MISSING_REQUIRED_FIELDS);
        }
        var tuitionClass = tuitionClassService.updateTuitionClass(updateLocationRequestDto);
        var responseDto = new TuitionClassResponseDto(tuitionClass);
        log.debug("The tuition class is updated successfully for tuition class id: {}", updateLocationRequestDto.getTuitionClassId());
        return getSuccessResponse(SuccessResponseStatus.LOCATION_UPDATED, responseDto, HttpStatus.OK);
    }

    /**
     * Delete tuition class by tuition class id
     *
     * @param tuitionClassId tuition class id
     * @return success/ error response
     */
    @DeleteMapping("/{tuitionClassId}")
    public ResponseEntity<ResponseWrapper> deleteTuitionClass(@PathVariable String tuitionClassId) {
        tuitionClassService.deleteLocation(tuitionClassId);
        log.debug("Tuition class is deleted successfully for tuition class id: {}", tuitionClassId);
        return getSuccessResponse(SuccessResponseStatus.LOCATION_DELETED, null, HttpStatus.OK);
    }
}
