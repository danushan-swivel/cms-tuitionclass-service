package cms.tuitionclass.service.controller;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import cms.tuitionclass.service.enums.ErrorResponseStatus;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import cms.tuitionclass.service.service.TuitionClassService;
import cms.tuitionclass.service.utils.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests {@link TuitionClassController} class.
 */
class TuitionClassControllerTest {

    private static final String TUITION_CLASS_BASE_URL = "/api/v1/tuition/";
    private static final String TUITION_CLASS_BY_ID_URL = "/api/v1/tuition/##TUITION-CLASS-ID##";
    private static final String REPLACE_TUITION_CLASS_ID = "##TUITION-CLASS-ID##";

    private static final String TUITION_CLASS_ID = "tid-1256-9541-8523-7536";
    private static final String LOCATION_NAME = "Elegance";
    private static final String UPDATED_ADDRESS = "Queen's Road";
    private static final String DISTRICT = "Colombo";
    private static final String ADDRESS = "Queen's Road, Duplication Road";
    private static final String PROVINCE = "South";
    private static final String ACCESS_TOKEN = "ey1365651-14156-51";

    @Mock
    private TuitionClassService tuitionClassService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        TuitionClassController tuitionClassController = new TuitionClassController(tuitionClassService);
        mockMvc = MockMvcBuilders.standaloneSetup(tuitionClassController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Should_ReturnOk_When_CreateTuitionClassSuccessfully() throws Exception {
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        TuitionClass tuitionClass = getSampleTuitionClass();
        when(tuitionClassService.saveTuitionClass(any(TuitionClassRequestDto.class))).thenReturn(tuitionClass);
        mockMvc.perform(MockMvcRequestBuilders.post(TUITION_CLASS_BASE_URL)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .content(tuitionClassRequestDto.toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatus.LOCATION_CREATED.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.tuitionClassId", startsWith("tid-")));
    }

    @Test
    void Should_ReturnBadRequest_When_RequiredFieldsAreNotAvailable() throws Exception {
        TuitionClassRequestDto tuitionClassRequestDto = getSampleTuitionClassRequestDto();
        tuitionClassRequestDto.setLocationName("");
        mockMvc.perform(MockMvcRequestBuilders.post(TUITION_CLASS_BASE_URL)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .content(tuitionClassRequestDto.toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatus.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data", nullValue()));
    }

    @Test
    void Should_ReturnOk_When_UpdateTuitionClassSuccessfully() throws Exception {
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        TuitionClass tuitionClass = getSampleTuitionClass();
        tuitionClass.setAddress(UPDATED_ADDRESS);
        when(tuitionClassService.updateTuitionClass(any(UpdateTuitionClassRequestDto.class))).thenReturn(tuitionClass);
        mockMvc.perform(MockMvcRequestBuilders.put(TUITION_CLASS_BASE_URL)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .content(updateTuitionClassRequestDto.toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatus.LOCATION_UPDATED.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.tuitionClassId", startsWith("tid-")));
    }

    @Test
    void Should_ReturnBadRequest_When_RequiredFieldsAreNotAvailableForUpdateTuitionClass() throws Exception {
        UpdateTuitionClassRequestDto updateTuitionClassRequestDto = getSampleUpdateTuitionClassRequestDto();
        updateTuitionClassRequestDto.setLocationName("");
        mockMvc.perform(MockMvcRequestBuilders.put(TUITION_CLASS_BASE_URL)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .content(updateTuitionClassRequestDto.toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatus.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data", nullValue()));
    }

    @Test
    void Should_ReturnOk_When_GetTuitionClassDetails() throws Exception {
        Page<TuitionClass> tuitionClassPage = getSampleTuitionClassPage();
        when(tuitionClassService.getTuitionClassPage()).thenReturn(tuitionClassPage);
        mockMvc.perform(MockMvcRequestBuilders.get(TUITION_CLASS_BASE_URL)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatus.READ_LOCATION_LIST.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.locations[0].tuitionClassId", startsWith("tid")));
    }

    @Test
    void Should_ReturnOk_When_GetTuitionClassSuccessfully() throws Exception {
        String url = TUITION_CLASS_BY_ID_URL.replace(REPLACE_TUITION_CLASS_ID, TUITION_CLASS_ID);
        TuitionClass tuitionClass = getSampleTuitionClass();
        when(tuitionClassService.getTuitionClassById(TUITION_CLASS_ID)).thenReturn(tuitionClass);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatus.READ_LOCATION.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.tuitionClassId", startsWith("tid-")));
    }

    @Test
    void Should_ReturnOk_When_DeleteTuitionClassSuccessfully() throws Exception {
        doNothing().when(tuitionClassService).deleteLocation(TUITION_CLASS_ID);
        String url = TUITION_CLASS_BY_ID_URL.replace(REPLACE_TUITION_CLASS_ID, TUITION_CLASS_ID);
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                        .header(Constants.TOKEN_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatus.LOCATION_DELETED.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data", nullValue()));
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