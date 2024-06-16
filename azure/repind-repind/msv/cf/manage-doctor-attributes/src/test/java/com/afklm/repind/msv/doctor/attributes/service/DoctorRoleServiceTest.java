package com.afklm.repind.msv.doctor.attributes.service;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.RetrieveDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.Role;
import com.afklm.repind.msv.doctor.attributes.model.error.ErrorMessage;
import com.afklm.repind.msv.doctor.attributes.repository.IRoleRepository;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.CreateRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.DeleteRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.RetrieveRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.UpdateRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.utils.DoctorStatus;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoctorRoleServiceTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private RetrieveRoleEncoder retrieveRoleEncoder;

    @Mock
    private CreateRoleEncoder createRoleEncoder;

    @Mock
    private UpdateRoleEncoder updateRoleEncoder;

    @Mock
    private DeleteRoleEncoder deleteRoleEncoder;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DoctorRoleService doctorRoleService;

    @Before
    public void setUp() {
        doctorRoleService = new DoctorRoleService(roleRepository, retrieveRoleEncoder, createRoleEncoder, updateRoleEncoder, deleteRoleEncoder);
    }


    @Test
    public void retrieveDoctorRole() throws BusinessException {
        //prepare
        RetrieveDoctorRoleCriteria retrieve =
                new RetrieveDoctorRoleCriteria()
                        .withRoleId("01");

        Role role = new Role();
        role.setDoctorStatus("V");
        role.setRoleId("01");

        WrapperRetrieveDoctorRoleResponse response =
                new WrapperRetrieveDoctorRoleResponse();
        response.setRoleId(role.getRoleId());

        Optional<Role> roleOpt = Optional.of(role);


        //rule
        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);
        when(retrieveRoleEncoder.decode(role)).thenReturn(response);

        doctorRoleService.retrieveDoctorRole(retrieve);

        //verify
        assertEquals(response.getRoleId(), role.getRoleId());
    }

    @Test
    public void retrieveDoctorRoleIdNotExist() throws BusinessException {
        //prepare
        RetrieveDoctorRoleCriteria retrieve =
                new RetrieveDoctorRoleCriteria()
                        .withRoleId("01");
        //rule
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_404_003.getDescription());

        doctorRoleService.retrieveDoctorRole(retrieve);

    }

    @Test
    public void retrieveBadDoctorStatus() throws BusinessException {
        //prepare
        RetrieveDoctorRoleCriteria retrieve =
                new RetrieveDoctorRoleCriteria()
                        .withRoleId("01");
        Role role = new Role();
        role.setDoctorStatus("X");
        role.setRoleId("01");
        Optional<Role> roleOpt = Optional.of(role);

        //rule
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_403_003.getDescription());

        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);

        doctorRoleService.retrieveDoctorRole(retrieve);

    }

    @Test
    public void createDoctorRole() throws BusinessException {
        //prepare
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");
        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.empty();

        //rule
        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);
        when(createRoleEncoder.encode(createDoctorRoleCriteria)).thenReturn(role);

        doctorRoleService.createDoctorRole(createDoctorRoleCriteria);

        //verify
        verify(roleRepository).save(role);
    }


    @Test
    public void createExistingDoctorRole() throws BusinessException {
        //prepare
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria()
                .withGin("123456789000")
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");
        Role role = new Role();
        role.setGin("123456789000");
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.of(role);

        //rule
        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_409_001.getDescription());

        doctorRoleService.createDoctorRole(createDoctorRoleCriteria);

    }

    @Test
    public void createWithExistingDoctorIdRole() throws BusinessException {
        //prepare
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");
        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.empty();

        //rule
        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);
        when(roleRepository.findByDoctorId("0101")).thenReturn(role);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_409_002.getDescription());

        doctorRoleService.createDoctorRole(createDoctorRoleCriteria);

    }

    @Test
    public void updateDoctorRole() throws BusinessException {
        //prepare
        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");
        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.of(role);

        //rule
        when(updateRoleEncoder.encode(role, updateDoctorRoleCriteria)).thenReturn(role);
        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);

        doctorRoleService.updateDoctorRole(updateDoctorRoleCriteria);

        //verify
        verify(roleRepository).save(role);
    }

    @Test
    public void updateDoctorRoleNotFound() throws BusinessException {
        //prepare
        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");

        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_404_003.getDescription());

        doctorRoleService.updateDoctorRole(updateDoctorRoleCriteria);

    }

    @Test
    public void updateDoctorRoleIdAllReadyExist() throws BusinessException {
        //prepare
        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("V");
        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.of(role);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_409_002.getDescription());


        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);
        when(roleRepository.findByDoctorIdAndRoleIdNot(updateDoctorRoleCriteria.getDoctorId(), role.getRoleId())).thenReturn(role);

        doctorRoleService.updateDoctorRole(updateDoctorRoleCriteria);

    }

    @Test
    public void updateDoctorRoleWithBadStatus() throws BusinessException {
        //prepare
        UpdateDoctorRoleCriteria updateDoctorRoleCriteria = new UpdateDoctorRoleCriteria()
                .withRoleId("01")
                .withDoctorId("0101")
                .withDoctorStatus("X");
        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("X");
        Optional<Role> roleOpt = Optional.of(role);

        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorMessage.ERROR_MESSAGE_403_003.getDescription());

        doctorRoleService.updateDoctorRole(updateDoctorRoleCriteria);

    }

    @Test
    public void deleteDoctorRole() throws BusinessException {
        //prepare
        DeleteDoctorRoleCriteria deleteDoctorRoleCriteria = new DeleteDoctorRoleCriteria()
                .withRoleId("01");

        Role role = new Role();
        role.setRoleId("01");
        role.setDoctorStatus("V");
        Optional<Role> roleOpt = Optional.of(role);

        when(roleRepository.findByRoleId("01")).thenReturn(roleOpt);

        doctorRoleService.deleteDoctorRole(deleteDoctorRoleCriteria);

        assertEquals(role.getDoctorStatus(), DoctorStatus.X.getAcronyme());
        //verify
        verify(roleRepository).save(role);

    }


}
