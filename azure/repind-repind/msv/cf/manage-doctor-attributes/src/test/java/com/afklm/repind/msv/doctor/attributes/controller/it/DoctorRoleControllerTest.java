package com.afklm.repind.msv.doctor.attributes.controller.it;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesUpdateDto;
import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.entity.Role;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesDto;
import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.repository.IRoleRepository;
import com.afklm.repind.msv.doctor.attributes.utils.DoctorStatus;
import com.afklm.repind.msv.doctor.attributes.utils.ELanguage;
import com.afklm.repind.msv.doctor.attributes.utils.ESpeciality;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@Slf4j
class DoctorRoleControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    IRoleRepository roleRepository;

    private static final String DEFAULT_ROLE_ID = "10000";
    private static final String STATUS = "V";
    private final String speciality = ESpeciality.GENERAL_PRACTITIONER.getValue();
    private static final String AIRLINECODE = "AF";
    private static final String DOCTOR_ID = "0000000000017000";
    private static final String SIGNATURE_SOURCE = "CBS";
    private static final String SITE_CREATION = "CBS";
    private static final String ROLE_ID = "050000000000000";
    private final List<String> languages = Arrays.asList(ELanguage.FRENCH.getValue());

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void retrieveDoctorRoleNotExist() throws Exception {
        MvcResult result = mockMvc.perform(get("/role/" + DEFAULT_ROLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roleId", DEFAULT_ROLE_ID))
                .andExpect(status().isNotFound()).andReturn();
        Assertions.assertEquals(result.getResolvedException().getMessage(), BusinessError.ROLE_NOT_FOUND.getRestError().getDescription());
    }


    @Test
    void createDoctorRole() throws Exception {
        CreateDoctorRoleCriteria criteria = createDoctorRoleCriteria();
        DoctorAttributesDto doctorAttributesDto = getDoctorAttributesDto(criteria);

        mockMvc.perform(post("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorAttributesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(STATUS)))
                .andExpect(jsonPath("$.doctorId", is(DOCTOR_ID)))
                .andExpect(jsonPath("$.airLineCode.value", is(AIRLINECODE)))
                .andExpect(jsonPath("$.signatureSourceCreation", is(SIGNATURE_SOURCE)))
                .andExpect(jsonPath("$.siteCreation", is(SITE_CREATION)))
                .andExpect(jsonPath("$.speciality.value", is(speciality)))
                .andExpect(jsonPath("$.roleId", is(ROLE_ID)));

        Optional<Role> roleOpt = roleRepository.findByRoleId(criteria.getRoleId());
        Role actualRole;
        if (roleOpt.isPresent()) {
            actualRole = roleOpt.get();
            List<String> actual = actualRole.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());

            assertEquals(DOCTOR_ID, criteria.getDoctorId());
            assertEquals(ROLE_ID, criteria.getRoleId());
            assertEquals(AIRLINECODE, criteria.getAirLineCode().getValue());
            assertEquals(speciality, criteria.getSpeciality().getValue());
            assertEquals(SIGNATURE_SOURCE, criteria.getSignatureSourceCreation());
            assertEquals(SITE_CREATION, criteria.getSiteCreation());
            assertThat(actual).hasSameElementsAs(languages);

            removeTestData(ROLE_ID);
        }
    }


    @Test
    void retrieveDoctorRole() throws Exception {
        Role role = createRole("0101011R", "0101011R", "V", "CAPI", "CAPI", "AF", ESpeciality.NEUROLOGIST.getValue());

        mockMvc.perform(get("/role/" + role.getRoleId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roleId", role.getRoleId()))
                .andExpect(status().isOk());

        Optional<Role> roleOpt = roleRepository.findByRoleId(role.getRoleId());
        Role expectedRole;
        if (roleOpt.isPresent()) {
            expectedRole = roleOpt.get();
            List<String> actual = role.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());
            List<String> expected = expectedRole.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());

            assertEquals(role.getDoctorStatus(), expectedRole.getDoctorStatus());
            assertEquals(role.getDoctorId(), expectedRole.getDoctorId());
            assertEquals(role.getRoleId(), expectedRole.getRoleId());
            assertEquals(role.getAirLineCode().getValue(), expectedRole.getAirLineCode().getValue());
            assertEquals(role.getSpeciality().getValue(), expectedRole.getSpeciality().getValue());
            assertEquals(role.getSignatureSourceCreation(), expectedRole.getSignatureSourceCreation());
            assertEquals(role.getSiteCreation(), expectedRole.getSiteCreation());
            assertThat(actual).hasSameElementsAs(expected);

            removeTestData(role.getRoleId());
        }
    }

    @Test
    void updateDoctorRole() throws Exception {
        Role roleUpdate = createRole("0101011U", "0101011U", "V", "CBS", "CBS", "AF", ESpeciality.NEUROLOGIST.getValue());

        Set<Language> languages = new HashSet<>();
        languages.add(Language.builder().acronyme(ELanguage.FRENCH.getAcronyme()).value(ELanguage.FRENCH.getValue()).build());
        DoctorAttributesUpdateDto doctorAttributesUpdateDto = new DoctorAttributesUpdateDto();
        doctorAttributesUpdateDto.setEndDateRole("2031-01-02T00:00:00Z");
        doctorAttributesUpdateDto.setDoctorId(roleUpdate.getDoctorId());
        doctorAttributesUpdateDto.setDoctorStatus(roleUpdate.getDoctorStatus());
        doctorAttributesUpdateDto.setSiteModification("CAPI");
        doctorAttributesUpdateDto.setSignatureSourceModification("CAPI");
        doctorAttributesUpdateDto.setAirLineCode(AirLineCode.builder().value("KL").build());
        doctorAttributesUpdateDto.setSpeciality(Speciality.builder().value(ESpeciality.UROLOGIST.getValue()).build());
        doctorAttributesUpdateDto.setLanguages(languages);
        doctorAttributesUpdateDto.setDoctorId("00012");

        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withDoctorId("00012")
                .withRoleId(roleUpdate.getRoleId())
                .withDoctorStatus("V")
                .withAirLineCode(AirLineCode.builder().value("KL").build())
                .withSpeciality(Speciality.builder().value(ESpeciality.UROLOGIST.getValue()).build())
                .withSignatureSourceModification("CAPI")
                .withSiteModification("CAPI");

        mockMvc.perform(put("/role/" + roleUpdate.getRoleId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorAttributesUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(updateDoctorRoleCriteria.getDoctorStatus())))
                .andExpect(jsonPath("$.doctorId", is(updateDoctorRoleCriteria.getDoctorId())))
                .andExpect(jsonPath("$.airLineCode", is(updateDoctorRoleCriteria.getAirLineCode().getValue())))
                .andExpect(jsonPath("$.signatureSourceModification", is(updateDoctorRoleCriteria.getSignatureSourceModification())))
                .andExpect(jsonPath("$.siteModification", is(updateDoctorRoleCriteria.getSiteModification())))
                .andExpect(jsonPath("$.speciality", is(updateDoctorRoleCriteria.getSpeciality().getValue())))
                .andExpect(jsonPath("$.roleId", is(updateDoctorRoleCriteria.getRoleId())));

        Optional<Role> roleOpt = roleRepository.findByRoleId(roleUpdate.getRoleId());
        Role roleExpected;
        List<String> expected;
        List<String> actual = roleUpdate.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());
        if (roleOpt.isPresent()) {
            roleExpected = roleOpt.get();
            expected = roleExpected.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());

            assertEquals(updateDoctorRoleCriteria.getDoctorId(), roleExpected.getDoctorId());
            assertEquals(updateDoctorRoleCriteria.getRoleId(), roleExpected.getRoleId());
            assertEquals(updateDoctorRoleCriteria.getAirLineCode(), roleExpected.getAirLineCode());
            assertEquals(updateDoctorRoleCriteria.getSpeciality(), roleExpected.getSpeciality());
            assertEquals(updateDoctorRoleCriteria.getSignatureSourceModification(), roleExpected.getSignatureSourceModification());
            assertEquals(updateDoctorRoleCriteria.getSiteModification(), roleExpected.getSiteModification());
            assertThat(actual).hasSameElementsAs(expected);

            removeTestData(roleUpdate.getRoleId());
        }
    }

    @Test
    void deleteDoctorAttributes() throws Exception {

        Role role = createRole("0101011", "0101011", "V", "CAPI", "2021-04-27T00:00:00Z", "AF", ESpeciality.NEUROLOGIST.getValue());
        mockMvc.perform(delete("/role/" + role.getRoleId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roleId", role.getRoleId()))
                .andExpect(status().isOk());
        Optional<Role> expectedRoleOpt = roleRepository.findByRoleId(role.getRoleId());
        Role expectedRole;
        if (expectedRoleOpt.isPresent()) {
            expectedRole = expectedRoleOpt.get();
            assertEquals(expectedRole.getDoctorStatus(), DoctorStatus.X.getAcronyme());
        }

        removeTestData(role.getRoleId());
    }


    public CreateDoctorRoleCriteria createDoctorRoleCriteria() throws ParseException {
        //criteria
        //RelationModel relation = createRelationModel();
        Set<Language> languages = new HashSet<>();
        languages.add(Language.builder().acronyme(ELanguage.FRENCH.getAcronyme()).value(ELanguage.FRENCH.getValue()).build());
        return new CreateDoctorRoleCriteria()
                .withGin("000000123456")
                .withDoctorId("0000000000017000")
                .withEndDateRole(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2031-01-02T00:00:00Z"))
                .withDoctorStatus("V")
                .withAirLineCode(AirLineCode.builder().value("AF").build())
                .withRoleId("050000000000000")
                .withSpeciality(Speciality.builder().value("GENP").build())
                .withSignatureSourceCreation("CBS")
                .withSiteCreation("CBS")
                .withLanguages(languages);
    }

    public Role createRole(String roleId, String doctorId, String status, String signatureSource, String siteCreation, String airLineCode, String speciality) throws ParseException {

        Role role = new Role();
        role.setRoleId(roleId);
        role.setDoctorId(doctorId);
        role.setDoctorStatus(status);
        role.setSignatureSourceCreation(signatureSource);
        role.setSignatureDateCreation(new Date());
        role.setSiteCreation(siteCreation);
        role.setSpeciality(Speciality.builder().value(speciality).build());
        role.setAirLineCode(AirLineCode.builder().value(airLineCode).build());
        Set<Language> languages = new HashSet<>();
        languages.add(Language.builder().acronyme(ELanguage.FRENCH.getAcronyme()).value(ELanguage.FRENCH.getValue()).build());
        role.setLanguages(languages);

        roleRepository.save(role);

        return role;
    }

    private void removeTestData(String roleId) {
        Optional<Role> roleOpt = roleRepository.findByRoleId(roleId);
        Role role;
        if (roleOpt.isPresent()) {
            role = roleOpt.get();
            roleRepository.delete(role);
        } else {
            log.debug("nothing to delete here");
        }
    }

    public List<String> listLangs() {
        Language lg1 = new Language();
        lg1.setValue(ELanguage.ENGLISH.getValue());
        lg1.setAcronyme(ELanguage.ENGLISH.getAcronyme());
        Language lg2 = new Language();
        lg2.setValue(ELanguage.FRENCH.getValue());
        lg2.setAcronyme(ELanguage.FRENCH.getAcronyme());
        List<String> listLangs = new ArrayList<>();
        listLangs.add(lg1.getValue());
        listLangs.add(lg2.getValue());
        return listLangs;
    }

    public List<String> listLangs2() {
        Language lg1 = new Language();
        lg1.setValue(ELanguage.NETHERLANDS.getValue());
        lg1.setAcronyme(ELanguage.NETHERLANDS.getAcronyme());
        Language lg2 = new Language();
        lg2.setValue(ELanguage.OTHER.getValue());
        lg2.setAcronyme(ELanguage.OTHER.getAcronyme());
        List<String> listLangs = new ArrayList<>();
        listLangs.add(lg1.getValue());
        listLangs.add(lg2.getValue());
        return listLangs;
    }

    public RelationModel createRelationModel() {
        List<String> listLangs = listLangs();
        RelationModel relation = new RelationModel();
        relation.setType("SPEAK");
        relation.setValues(listLangs);
        return relation;

    }

    public RelationModel updateRelationModel() {
        List<String> listLangs = listLangs2();
        RelationModel relation = new RelationModel();
        relation.setType("SPEAK");
        relation.setValues(listLangs);
        return relation;

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DoctorAttributesDto getDoctorAttributesDto(CreateDoctorRoleCriteria criteria) {

        DoctorAttributesDto doctorAttributesDto = new DoctorAttributesDto();
        doctorAttributesDto.setGin(criteria.getGin());
        doctorAttributesDto.setRoleId(criteria.getRoleId());
        doctorAttributesDto.setEndDateRole("2031-01-02T00:00:00Z");
        doctorAttributesDto.setDoctorStatus(criteria.getDoctorStatus());
        doctorAttributesDto.setDoctorId(criteria.getDoctorId());
        doctorAttributesDto.setSignatureSourceCreation(criteria.getSignatureSourceCreation());
        doctorAttributesDto.setSiteCreation(criteria.getSiteCreation());
        doctorAttributesDto.setAirLineCode(criteria.getAirLineCode());
        doctorAttributesDto.setSpeciality(criteria.getSpeciality());
        doctorAttributesDto.setLanguages(criteria.getLanguages());
        return doctorAttributesDto;
    }

}
