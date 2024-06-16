package com.afklm.rigui.services;

import com.afklm.rigui.dao.external.ExternalIdentifierRepository;
import com.afklm.rigui.dao.profil.ProfilsRepository;
import com.afklm.rigui.dao.role.BusinessRoleRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.dao.role.RoleGPRepository;
import com.afklm.rigui.dao.role.RoleUCCRRepository;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.role.RoleContratsDTO;
import com.afklm.rigui.entity.profil.Profils;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;
import com.afklm.rigui.entity.role.RoleGP;
import com.afklm.rigui.enums.IndividualRestrictedTypesEnum;
import com.afklm.rigui.enums.ProductTypeEnum;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.model.individual.ModelIndividual;
import com.afklm.rigui.model.individual.ModelIndividualResult;
import com.afklm.rigui.model.individual.ModelIndividualResume;
import com.afklm.rigui.model.individual.ModelProfile;
import com.afklm.rigui.model.individual.requests.ModelIndividualIdentificationRequest;
import com.afklm.rigui.model.individual.requests.ModelIndividualRequest;
import com.afklm.rigui.services.adresse.internal.EmailDS;
import com.afklm.rigui.services.adresse.internal.PostalAddressDS;
import com.afklm.rigui.services.adresse.internal.TelecomDS;
import com.afklm.rigui.services.builder.w000442.IndividualRequestBuilder;
import com.afklm.rigui.services.delegation.internal.DelegationDataDS;
import com.afklm.rigui.services.handler.W000442BusinessErrorHandler;
import com.afklm.rigui.services.helper.IndividualHelper;
import com.afklm.rigui.services.individu.internal.AccountDataDS;
import com.afklm.rigui.services.individu.internal.AlertDS;
import com.afklm.rigui.services.individu.internal.CommunicationPreferencesDS;
import com.afklm.rigui.services.individu.internal.IndividuDS;
import com.afklm.rigui.services.preference.internal.PreferenceDS;
import com.afklm.rigui.services.resources.LastActivityService;
import com.afklm.rigui.services.role.internal.BusinessRoleDS;
import com.afklm.rigui.services.role.internal.RoleDS;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.rigui.wrapper.individual.WrapperIndividual;
import com.afklm.rigui.wrapper.individual.WrapperIndividualResult;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualResponse;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class IndividualServiceTest {

    @InjectMocks
    private IndividualService individualService;

    @Mock
    private IndividuDS individuDS;

    @Mock
    private AccountDataDS accountDataDS;

    @Mock
    private RoleGPRepository roleGPRepository;

    @Mock
    private RoleDS roleDS;

    @Mock
    private EmailDS emailDS;

    @Mock
    private PostalAddressDS adressesDS;

    @Mock
    private AlertDS alertesDS;

    @Mock
    private ProfilsRepository profilsRepository;

    @Mock
    private TelecomDS telecomsDS;

    @Mock
    private CommunicationPreferencesDS commPrefDS;

    @Mock
    private DelegationDataDS delegationDataDS;

    @Mock
    private PreferenceDS preferenceDS;

    @Mock
    private BusinessRoleDS businessRoleDS;

    @Mock
    private RoleContratsRepository roleContratsRepository;

    @Mock
    private ExternalIdentifierRepository externalIdentifierRepository;

    @Mock
    private IndividualHelper individualHelper;

    @Mock
    private RoleUCCRRepository roleUCCRRepository;

    @Mock
    public DozerBeanMapper dozerBeanMapper;

    @Mock
    private IndividualRequestBuilder individualRequestBuilder;

    @Mock
    private BusinessRoleRepository businessRoleRepository;

    @Mock
    private CreateUpdateIndividualServiceV8 createOrUpdateService;

    @Mock
    private LastActivityService lastActivityService;

    private final static String GIN = "123456789";

    private final static String CIN = "123445";

    @Test
    public void updateIndividual() throws SystemException, ServiceException, BusinessErrorBlocBusinessException {
        ModelIndividualRequest request = new ModelIndividualRequest();
        ModelIndividualIdentificationRequest id = new ModelIndividualIdentificationRequest();
        id.setGin(GIN);
        request.setIdentification(id);
        when(individualRequestBuilder.buildUpdateRequest(GIN, request)).thenReturn(new CreateUpdateIndividualRequest());
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        response.setSuccess(true);
        when(createOrUpdateService.createIndividual(any())).thenReturn(response);
        Boolean success = individualService.updateIndividual(request);
        assertEquals(true, success);
    }

    @Test
    public void isContractAndIndividualHaveSameNumber() throws ServiceException {
        when(individuDS.isContractAndIndividualHaveSameNumber(GIN)).thenReturn(1);
        int result = individualService.isContractAndIndividualHaveSameNumber(GIN);
        assertEquals(1, result);
    }

    @Test
    public void getListIndividualResult() throws ServiceException, JrafDomainException {
        IndividuDTO ind = new IndividuDTO();
        ind.setAliasNom1("ALIAS1");
        when(individuDS.getIndividualOrProspectByGinExceptForgotten(GIN)).thenReturn(ind);
        when(businessRoleDS.getSginIndByContractNumber(CIN)).thenReturn(List.of());
        ModelIndividualResult mapped = new ModelIndividualResult();
        mapped.setGin(GIN);
        when(dozerBeanMapper.map(ind, ModelIndividualResult.class)).thenReturn(mapped);
        when(adressesDS.getAddressByGin(GIN)).thenReturn(List.of());
        when(individualHelper.getAdresseFormatResultByGin(List.of())).thenReturn("FORMAT");
        WrapperIndividualResult result = individualService.getListIndividualResult(GIN);
    }

    @Test
    public void getGinByIdentifiant() throws JrafDomainException, ServiceException {
        IndividuDTO individuDTO = new IndividuDTO();
        individuDTO.setSgin(GIN);
        when(individuDS.getIndividualOrProspectByGinExceptForgotten(CIN)).thenReturn(individuDTO);
        String result = individualService.getGinByIdentifiant(CIN);
        assertEquals(GIN, result);
    }


    @Test
    public void getIndividualByIdentifiant() throws JrafDomainException, ServiceException {
        IndividuDTO individuDTO = new IndividuDTO();
        individuDTO.setSgin(GIN);
        when(individuDS.getIndividualOrProspectByGinExceptForgotten(CIN)).thenReturn(individuDTO);
        IndividuDTO result = individualService.getIndividualByIdentifiant(CIN);
        assertEquals(GIN, result.getSgin());
    }

    @Test
    public void getIndividualDetailsByIdentifiant() throws JrafDomainException, ServiceException {

        IndividuDTO ind = new IndividuDTO();
        ind.setAliasNom1("ALIAS1");
        ind.setSgin(GIN);
        ind.setType("T");
        when(individuDS.getIndividualOrProspectByGinExceptForgotten(GIN)).thenReturn(ind);
        when(profilsRepository.findBySgin(GIN)).thenReturn(null);
        ModelIndividual modelIndividual = new ModelIndividual();
        modelIndividual.setGin(GIN);
        when(dozerBeanMapper.map(ind, ModelIndividual.class)).thenReturn(modelIndividual);
        // RESUME MOCK PART
        when(roleDS.findRoleContrats(GIN, ProductTypeEnum.FLYING_BLUE.toString())).thenReturn(List.of());
        when(businessRoleRepository.findByGinInd(GIN)).thenReturn(List.of());
        when(roleDS.isFlyingBlueByGin(GIN)).thenReturn(true);
        when(roleDS.isMyAccountByGin(GIN)).thenReturn(true);
        when(telecomsDS.getNumberTelecomsPerso(GIN)).thenReturn(1);
        when(telecomsDS.getNumberTelecomsPro(GIN)).thenReturn(2);
        when(commPrefDS.getNumberOptinComPrefByGin(GIN)).thenReturn(2);
        when(commPrefDS.getNumberOptoutComPrefByGin(GIN)).thenReturn(2);
        when( emailDS.getNumberPersoEmailByGin(GIN)).thenReturn(0);
        when(emailDS.getNumberProEmailByGin(GIN)).thenReturn(0);
        when(adressesDS.getNumberPersoAddressesByGin(GIN)).thenReturn(0);
        when(adressesDS.getNumberProAddressesByGin(GIN)).thenReturn(0);
        when(externalIdentifierRepository.findByGin(GIN)).thenReturn(List.of());
        when(alertesDS.getNumberOptinAlertsByGin(GIN)).thenReturn(0);
        when(alertesDS.getNumberOptoutAlertsByGin(GIN)).thenReturn(0);
        when(preferenceDS.getPreferencesNumberByGin(GIN)).thenReturn(0);
        when(delegationDataDS.getDelegateNumberByGin(ind)).thenReturn(0);
        when(delegationDataDS.getDelegatorNumberByGin(ind)).thenReturn(0);
        when(roleGPRepository.getGPRolesCount(GIN)).thenReturn(0);
        when(roleUCCRRepository.findByGin(GIN)).thenReturn(List.of());
        when(roleGPRepository.getRoleGPByGin(GIN)).thenReturn(List.of());
        when(businessRoleRepository.findByGinInd(GIN)).thenReturn(List.of());
        when(accountDataDS.isAccountDeleteByGin(GIN)).thenReturn(false);
        // RESULT
        WrapperIndividual result = individualService.getIndividualDetailsByIdentifiant(GIN);
        assertEquals(GIN, result.individu.getGin());
    }
}
