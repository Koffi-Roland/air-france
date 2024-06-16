package com.afklm.repind.msv.doctor.attributes.controller.it;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.node.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.entity.node.Language;
import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import com.afklm.repind.msv.doctor.attributes.entity.node.Speciality;
import com.afklm.repind.msv.doctor.attributes.model.DoctorAttributesModel;
import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.repository.IAirLineCodeRepository;
import com.afklm.repind.msv.doctor.attributes.repository.ILanguageRepository;
import com.afklm.repind.msv.doctor.attributes.repository.IRoleRepository;
import com.afklm.repind.msv.doctor.attributes.repository.ISpecialityRepository;
import com.afklm.repind.msv.doctor.attributes.utils.DoctorStatus;
import com.afklm.repind.msv.doctor.attributes.utils.ELanguage;
import com.afklm.repind.msv.doctor.attributes.utils.ESpeciality;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
class DoctorRoleControllerTest {

    MockMvc mockMvc;


    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IAirLineCodeRepository airLineCodeRepository;

    @Autowired
    ISpecialityRepository specialityRepository;

    @Autowired
    ILanguageRepository languageRepository;

    public static final String DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private String defaultRoleId = "10000";
    private String status = "V";
    private String speciality = ESpeciality.GENERAL_PRACTITIONER.getValue();
    private String airLineCode = "AF";
    private String doctorId = "0000000000017000";
    private String signatureSource = "CBS";
    private String siteCreation = "CBS";
    private String roleId = "050000000000000";
    private List<String> languages = Arrays.asList(ELanguage.FRENCH.getValue(),ELanguage.ENGLISH.getValue());
    private List<String> languages2 = Arrays.asList(ELanguage.NETHERLANDS.getValue(),ELanguage.OTHER.getValue());



    private static ServerControls   embeddedDataBaseServer;

    @BeforeEach
     void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @BeforeAll
    static void initializeNeo4j() {
        embeddedDataBaseServer = TestServerBuilders.newInProcessBuilder().newServer();
    }

    @AfterAll
    static void stopNeo4j() {
        embeddedDataBaseServer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", embeddedDataBaseServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
    }

    @Test
    void retrieveDoctorRoleNotExist() throws Exception {
        MvcResult result = mockMvc.perform(get("/role/" + defaultRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("roleId", defaultRoleId))
                .andExpect(status().isNotFound()).andReturn();
        Assertions.assertEquals(result.getResolvedException().getMessage(), BusinessError.ROLE_NOT_FOUND.getRestError().getDescription());
    }


    @Test
    void createDoctorRole() throws Exception {
        CreateDoctorRoleCriteria criteria = createDoctorRoleCriteria();
        DoctorAttributesModel doctorAttributesModel = new DoctorAttributesModel();
        List<RelationModel> listRelations = new ArrayList<>();
        listRelations.add(createRelationModel());
        doctorAttributesModel.setRelationsList(listRelations);

         mockMvc.perform(post("/role/" + criteria.getRoleId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(doctorAttributesModel))
                 .param("gin", criteria.getGin())
                .param("endDateRole", "2031-01-02T00:00:00Z")
                .param("doctorStatus", criteria.getDoctorStatus())
                .param("doctorId", criteria.getDoctorId())
                .param("signatureSource", criteria.getSignatureCreation())
                .param("siteCreation", criteria.getSiteCreation())
                .param("airLineCode", criteria.getAirLineCode())
                .param("speciality", criteria.getSpeciality()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(status)))
                 .andExpect(jsonPath("$.doctorId", is(doctorId)))
                .andExpect(jsonPath("$.airLineCode", is(airLineCode)))
                .andExpect(jsonPath("$.signatureSourceCreation", is(signatureSource)))
                 .andExpect(jsonPath("$.siteCreation", is(siteCreation)))
                .andExpect(jsonPath("$.speciality", is(speciality)))
                .andExpect(jsonPath("$.roleId", is(roleId)));

        Optional<Role> opt = roleRepository.findById(criteria.getRoleId());
        Role actualRole = opt.get();
        List<String> actual = actualRole.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());

        assertEquals(doctorId, criteria.getDoctorId());
        assertEquals(roleId, criteria.getRoleId());
        assertEquals(airLineCode, criteria.getAirLineCode());
        assertEquals(speciality, criteria.getSpeciality());
        assertEquals(signatureSource, criteria.getSignatureCreation());
        assertEquals(siteCreation, criteria.getSiteCreation());
        assertThat(actual).hasSameElementsAs(languages);

        removeTestData(roleId);
    }

    @Test
    void retrieveDoctorRole() throws Exception {
        Role role = createRole("0101011R", "0101011R", "V", "CAPI", "CAPI", "AF", ESpeciality.NEUROLOGIST.getValue());

        mockMvc.perform(get("/role/" + role.getRoleId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("roleId", role.getRoleId()))
                .andExpect(status().isOk());

        Optional<Role> roleOpt = roleRepository.findById(role.getRoleId());
        Role expectedRole = roleOpt.get();

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

    @Test
    void updateDoctorRole() throws Exception {
        Role roleUpdate = createRole("0101011U", "0101011U", "V", "CBS", "CBS", "AF", ESpeciality.NEUROLOGIST.getValue());;
        Set<RelationModel> relationsList = new HashSet<>();
        relationsList.add(updateRelationModel());
        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withDoctorId("00012")
                .withRoleId(roleUpdate.getRoleId())
                .withDoctorStatus("V")
                .withAirLineCode("KL")
                .withSpeciality(ESpeciality.UROLOGIST.getValue())
                .withSignatureSourceModification("CAPI")
                .withSiteModification("CAPI")
                .withRelationsList(relationsList);

        DoctorAttributesModel doctorAttributesModel = new DoctorAttributesModel();
        List<RelationModel> listRelations = new ArrayList<>();
        listRelations.add(updateRelationModel());
        doctorAttributesModel.setRelationsList(listRelations);

        mockMvc.perform(put("/role/" + roleUpdate.getRoleId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(doctorAttributesModel))
                .param("endDateRole", "2031-01-02T00:00:00Z")
                .param("doctorStatus", updateDoctorRoleCriteria.getDoctorStatus())
                .param("doctorId", updateDoctorRoleCriteria.getDoctorId())
                .param("signatureSource", updateDoctorRoleCriteria.getSignatureSourceModification())
                .param("siteModification", updateDoctorRoleCriteria.getSiteModification())
                .param("airLineCode", updateDoctorRoleCriteria.getAirLineCode())
                .param("speciality", updateDoctorRoleCriteria.getSpeciality()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(updateDoctorRoleCriteria.getDoctorStatus())))
                .andExpect(jsonPath("$.doctorId", is(updateDoctorRoleCriteria.getDoctorId())))
                .andExpect(jsonPath("$.airLineCode", is(updateDoctorRoleCriteria.getAirLineCode())))
                .andExpect(jsonPath("$.signatureSourceModification", is(updateDoctorRoleCriteria.getSignatureSourceModification())))
                .andExpect(jsonPath("$.siteModification", is(updateDoctorRoleCriteria.getSiteModification())))
                .andExpect(jsonPath("$.speciality", is(updateDoctorRoleCriteria.getSpeciality())))
                .andExpect(jsonPath("$.roleId", is(updateDoctorRoleCriteria.getRoleId())));

        Optional<Role> roleOpt = roleRepository.findById(roleUpdate.getRoleId());
        Role roleExpected = roleOpt.get();

        List<String> expected = roleExpected.getLanguages().stream().map(Language::getValue).collect(Collectors.toList());

        assertEquals(updateDoctorRoleCriteria.getDoctorId(), roleExpected.getDoctorId());
        assertEquals(updateDoctorRoleCriteria.getRoleId(), roleExpected.getRoleId());
        assertEquals(updateDoctorRoleCriteria.getAirLineCode(), roleExpected.getAirLineCode().getValue());
        assertEquals(updateDoctorRoleCriteria.getSpeciality(), roleExpected.getSpeciality().getValue());
        assertEquals(updateDoctorRoleCriteria.getSignatureSourceModification(), roleExpected.getSignatureSourceModification());
        assertEquals(updateDoctorRoleCriteria.getSiteModification(), roleExpected.getSiteModification());
        assertThat(languages2).hasSameElementsAs(expected);

        removeTestData(roleUpdate.getRoleId());

    }

    @Test
    void deleteDoctorAttributes() throws Exception {

        Role role = createRole("0101011", "0101011", "V", "CAPI", "2021-04-27T00:00:00Z", "AF", ESpeciality.NEUROLOGIST.getValue());
        mockMvc.perform(delete("/role/" + role.getRoleId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("roleId", role.getRoleId()))
                .andExpect(status().isOk());
        Optional<Role> expected = roleRepository.findById(role.getRoleId());

        assertEquals(expected.get().getDoctorStatus(), DoctorStatus.X.getAcronyme());

        removeTestData(role.getRoleId());
    }



    public CreateDoctorRoleCriteria createDoctorRoleCriteria() throws ParseException {
        //criteria
        RelationModel relation = createRelationModel();
        Set<RelationModel> relationsList  = new HashSet<>();
        relationsList.add(relation);
        return new CreateDoctorRoleCriteria()
                .withGin("000000123456")
                .withDoctorId("0000000000017000")
                .withEndDateRole(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2031-01-02T00:00:00Z"))
                .withDoctorStatus("V")
                .withAirLineCode("AF")
                .withRoleId("050000000000000")
                .withSpeciality("GENP")
                .withSignatureCreation("CBS")
                .withSiteCreation("CBS")
                .withRelationsList(relationsList);
    }

    public Role createRole(String roleId, String doctorId, String status, String signatureSource, String siteCreation, String airLineCode, String speciality) throws ParseException {

        final String DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        Role role = new Role();
        role.setRoleId(roleId);
        role.setDoctorId(doctorId);
        role.setDoctorStatus(status);
        role.setSignatureSourceCreation(signatureSource);
        role.setSignatureDateCreation(new Date());
        role.setSiteCreation(siteCreation);

        Speciality speciality1 = new Speciality();
        speciality1.setValue(speciality);
        specialityRepository.save(speciality1);
        role.setSpeciality(speciality1);

        AirLineCode airLineCode1 = new AirLineCode();
        airLineCode1.setValue(airLineCode);
        airLineCodeRepository.save(airLineCode1);

        role.setAirLineCode(airLineCode1);

        Language lg = new Language();
        lg.setAcronyme(ELanguage.FRENCH.getAcronyme());
        lg.setValue(ELanguage.FRENCH.getValue());
        languageRepository.save(lg);
        role.addLanguage(lg);

        roleRepository.save(role);

        return role;
    }

    private void removeTestData(String roleId) {
        Optional<Role> opt = roleRepository.findById(roleId);
        Role r = opt.get();
        specialityRepository.deleteAll();
        airLineCodeRepository.deleteAll();
        languageRepository.deleteAll();
        roleRepository.delete(r);
    }



    public List<String> listLangs(){
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
    public List<String> listLangs2(){
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

}
