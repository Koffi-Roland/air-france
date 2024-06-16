package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.AccountDataStatusEnum;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleContratsTransform;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.internal.unitservice.role.RoleUS;
import com.airfrance.repind.util.EncryptionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleDS {

    private static final Log log = LogFactory.getLog(RoleDS.class);

    @PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;

    @Autowired
    private RoleUS roleUS;

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    @Autowired
    private BusinessRoleRepository businessRoleRepository;

    @Autowired
    private AccountDataRepository accountDataRepository;

    @Autowired
    private IndividuRepository individuRepository;


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(RoleContratsDTO roleContratsDTO) throws JrafDomainException {
        RoleContrats roleContrats = null;

        roleContrats = RoleContratsTransform.dto2BoLight(roleContratsDTO);

        Integer cleRole = null;
        if (roleContratsDTO.getBusinessroledto() != null
                && roleContratsDTO.getBusinessroledto().getCleRole() != null) {
            cleRole = roleContratsDTO.getBusinessroledto().getCleRole();
        } else if (roleContratsDTO.getCleRole() != null) {
            cleRole = roleContratsDTO.getCleRole();
        }
        if (cleRole == null) {
            throw new MissingParameterException(
                    "Business Role is mandatory for a role contrat");
        } else {
            Optional<BusinessRole> businessRole = businessRoleRepository.findById(cleRole);
            if (!businessRole.isPresent()) {
                throw new NotFoundException("Business Role not found for a role contrat");
            } else {
                roleContrats.setBusinessrole(businessRole.get());
            }
        }

        String gin = null;
        if (roleContratsDTO.getIndividudto() != null
                && roleContratsDTO.getIndividudto().getSgin() != null) {
            gin = roleContratsDTO.getIndividudto().getSgin();
        } else if (roleContratsDTO.getGin() != null) {
            gin = roleContratsDTO.getGin();
        }
        if (gin == null) {
            throw new MissingParameterException(
                    "Individual is mandatory for a role contrat");
        } else {
            Optional<Individu> individu = individuRepository.findById(gin);
            if (!individu.isPresent()) {
                throw new IndivudualNotFoundException("Individual not found for a role contrat");
            } else {
                roleContrats.setIndividu(individu.get());
            }
        }

        roleContrats.setSrin(roleContratsRepository.getSequence().toString());
        entityManager.persist(roleContrats);

        // Version update and Id update if needed
        RoleContratsTransform.bo2DtoLight(roleContrats, roleContratsDTO);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(RoleContratsDTO dto) throws JrafDomainException {

        /*PROTECTED REGION ID(_in_sgPfNEd-uH5kd-A2OJg DS-CM remove) ENABLED START*/
        RoleContrats roleContrats = null;
        BusinessRole businessRole = null;

        // chargement du bo
        roleContrats = roleContratsRepository.getOne(dto.getSrin());

        // Checking the optimistic strategy
        if (!(roleContrats.getVersion().equals(dto.getVersion()))) {
            throw new SimultaneousUpdateException("Simultaneous update on following roleContrats: " + roleContrats.getSrin());
        }

        // transformation light dto -> bo
        RoleContratsTransform.dto2BoLight(dto, roleContrats);

        // suppression en base
        roleContratsRepository.delete(roleContrats);

        // Suppresion des données business
        businessRole = roleContrats.getBusinessrole();
        if (businessRole != null) {
            businessRoleRepository.delete(businessRole);
        }

        /*PROTECTED REGION END*/
    }

    public void update(RoleContratsDTO rc) throws InvalidParameterException {
        RoleContrats email = roleContratsRepository.getOne(rc.getSrin());
        RoleContratsTransform.dto2BoLight(rc, email);
        roleContratsRepository.saveAndFlush(email);
    }

    public List<RoleContratsDTO> findAll(String contractNumber, boolean isFBRecognitionActivate) throws JrafDomainException {
        List<RoleContratsDTO> result = new ArrayList<>();
        for (RoleContrats found : roleContratsRepository.findListRoleContratsByNumContract(contractNumber)) {
            result.add(RoleContratsTransform.bo2Dto(found, isFBRecognitionActivate));
        }
        return result;
    }

    public List<RoleContratsDTO> findAll(String contractNumber) throws JrafDomainException {
        List<RoleContratsDTO> result = new ArrayList<>();
        for (RoleContrats found : roleContratsRepository.findListRoleContratsByNumContract(contractNumber)) {
            result.add(RoleContratsTransform.bo2Dto(found));
        }
        return result;
    }


    public List<RoleContratsDTO> findAll(RoleContratsDTO roleContrats) throws JrafDomainException {
        RoleContrats rc = RoleContratsTransform.dto2BoLight(roleContrats);
        List<RoleContratsDTO> result = new ArrayList<>();
        for (RoleContrats found : roleContratsRepository.findAll(Example.of(rc))) {
            result.add(RoleContratsTransform.bo2DtoLight(found));
        }
        return result;
    }


    @Transactional(readOnly = true)
    public Integer count() throws JrafDomainException {
        return (int) roleContratsRepository.count();
    }

    public Integer countAll(RoleContratsDTO currentRoleContrat) throws JrafDomainException {
        RoleContrats rc = RoleContratsTransform.dto2BoLight(currentRoleContrat);
        return (int) roleContratsRepository.count(Example.of(rc));
    }

    @Transactional(readOnly = true)
    public RoleContratsDTO get(RoleContratsDTO dto) throws JrafDomainException {

        Optional<RoleContrats> roleContrats = roleContratsRepository.findById(dto.getSrin());
        RoleContratsDTO roleContratsDTO = null;

        if (roleContrats.isPresent()) {
            if (roleContrats != null) {
                roleContratsDTO = RoleContratsTransform.bo2Dto(roleContrats.get());

                if (roleContrats.get().getBusinessrole() != null) {
                    BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(roleContrats.get().getBusinessrole());
                    roleContratsDTO.setBusinessroledto(businessRoleDTO);
                }

                if (roleContrats.get().getIndividu() != null) {
                    IndividuDTO individuDTO = IndividuTransform.bo2DtoLight(roleContrats.get().getIndividu());
                    roleContratsDTO.setIndividudto(individuDTO);
                }
            }
            return roleContratsDTO;
        }

        return null;
    }

    public RoleContratsDTO get(String id) throws JrafDomainException {
        Optional<RoleContrats> rc = roleContratsRepository.findById(id);
        if (!rc.isPresent()) {
            return null;
        }
        return RoleContratsTransform.bo2DtoLight(rc.get());
    }

    /**
     * Getter
     *
     * @return RoleUS
     */
    public RoleUS getRoleUS() {
        return roleUS;
    }

    /**
     * Setter
     *
     * @param roleUS the RoleUS
     */
    public void setRoleUS(RoleUS roleUS) {
        this.roleUS = roleUS;
    }

    /**
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_in_sgPfNEd-uH5kd-A2OJggem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     * @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * createMyAccountContract
     *
     * @param sgin           in String
     * @param myAccountIdent in String
     * @param codeCompagnie  in String
     * @param signature      in SignatureDTO
     * @return The createMyAccountContract as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public String createMyAccountContract(String sgin, String myAccountIdent, String codeCompagnie, SignatureDTO signature) throws JrafDomainException {
        /*PROTECTED REGION ID(_i01wYFO8EeCtVMMOrStJ8A) ENABLED START*/
        // Creation mother class of business role
        BusinessRoleDTO businessRole = new BusinessRoleDTO();
        businessRole.setNumeroContrat(myAccountIdent);
        businessRole.setDateCreation(signature.getDate());
        businessRole.setSignatureCreation(signature.getSignature());
        businessRole.setSiteCreation(signature.getSite());
        businessRole.setDateModification(signature.getDate());
        businessRole.setSignatureModification(signature.getSignature());
        businessRole.setSiteModification(signature.getSite());
        businessRole.setGinInd(sgin);
        businessRole.setType("C");
        BusinessRole brBO = BusinessRoleTransform.dto2BoLight(businessRole);
        businessRoleRepository.saveAndFlush(brBO);

        // Creation of Role Contrat
        RoleContratsDTO roleContrats = new RoleContratsDTO();
        roleContrats.setCleRole(brBO.getCleRole());
        roleContrats.setCodeCompagnie(codeCompagnie);
        roleContrats.setDateCreation(signature.getDate());
        roleContrats.setDateDebutValidite(signature.getDate());
        roleContrats.setEtat("C");
        roleContrats.setGin(sgin);
        roleContrats.setNumeroContrat(myAccountIdent);
        roleContrats.setSignatureCreation(signature.getSignature());
        roleContrats.setSiteCreation(signature.getSite());
        roleContrats.setSignatureModification(signature.getSignature());
        roleContrats.setSiteModification(signature.getSite());
        roleContrats.setDateModification(signature.getDate());
        roleContrats.setTypeContrat("MA");
        create(roleContrats);

        return roleContrats.getSrin();
        /*PROTECTED REGION END*/
    }

    /**
     * createRoleContract
     *
     * @param sgin           in String
     * @param contractNumber in String
     * @param contractType   in String
     * @param codeCompagnie  in String
     * @param signature      in SignatureDTO
     * @return The roleContract as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public String createRoleContract(String sgin, String contractNumber, String contractType, String codeCompagnie, SignatureDTO signature) throws JrafDomainException {
        /*PROTECTED REGION ID(_i01wYFO8EeCtVMMOrStJ8A CRC) ENABLED START*/
        // Creation mother class of business role
        BusinessRoleDTO businessRole = new BusinessRoleDTO();
        businessRole.setNumeroContrat(contractNumber);
        businessRole.setDateCreation(signature.getDate());
        businessRole.setSignatureCreation(signature.getSignature());
        businessRole.setSiteCreation(signature.getSite());
        businessRole.setDateModification(signature.getDate());
        businessRole.setSignatureModification(signature.getSignature());
        businessRole.setSiteModification(signature.getSite());
        businessRole.setGinInd(sgin);
        businessRole.setType("C");
        BusinessRole brBO = BusinessRoleTransform.dto2BoLight(businessRole);
        businessRoleRepository.saveAndFlush(brBO);

        // Creation of Role Contrat
        RoleContratsDTO roleContrats = new RoleContratsDTO();
        roleContrats.setCleRole(brBO.getCleRole());
        roleContrats.setCodeCompagnie(codeCompagnie);
        roleContrats.setDateCreation(signature.getDate());
        roleContrats.setDateDebutValidite(signature.getDate());
        roleContrats.setEtat("C");
        roleContrats.setGin(sgin);
        roleContrats.setNumeroContrat(contractNumber);
        roleContrats.setSignatureCreation(signature.getSignature());
        roleContrats.setSiteCreation(signature.getSite());
        roleContrats.setSignatureModification(signature.getSignature());
        roleContrats.setSiteModification(signature.getSite());
        roleContrats.setDateModification(signature.getDate());
        roleContrats.setTypeContrat(contractType);
        create(roleContrats);

        return roleContrats.getSrin().toString();
        /*PROTECTED REGION END*/
    }


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void removeByGin(String gin) {
        roleContratsRepository.deleteByGin(gin);
    }

    @Transactional(readOnly = true)
    public List<RoleContratsDTO> findRoleContrats(String gin) throws JrafDomainException {
        return findRoleContrats(gin, true);
    }

    @Transactional(readOnly = true)
    public List<RoleContratsDTO> findRoleContrats(String gin, boolean isFBRecognitionActivate) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable to find role contrats without GIN");
        }

        List<RoleContrats> roleContratsList = roleContratsRepository.findRoleContrats(gin);

        if (roleContratsList == null) {
            return null;
        }

        return RoleContratsTransform.bo2Dto(roleContratsList, isFBRecognitionActivate);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public RoleContratsDTO createRoleContractFP(String gin, RoleContratsDTO roleContract, BusinessRoleDTO businessRole,
                                                SignatureDTO signature) throws JrafDomainException {
        BusinessRole brBO = BusinessRoleTransform.dto2BoLight(businessRole);
        getEntityManager().persist(brBO);
        roleContract.setCleRole(brBO.getCleRole());
        create(roleContract);

        AccountData accountdata = accountDataRepository.findBySgin(gin);

        if (accountdata == null) {
            Individu individu = individuRepository.getOne(gin);
            accountdata = createAccountData(roleContract, signature, individu);
            accountDataRepository.saveAndFlush(accountdata);
        }
        // REPIND-870 : MyAccount not linked to FB
        else {
            if (StringUtils.isEmpty(accountdata.getFbIdentifier())) {
                accountdata.setFbIdentifier(roleContract.getNumeroContrat());
                accountdata.setStatus("U");
                accountdata.setAccountUpgradeDate(new Date());
                accountDataRepository.saveAndFlush(accountdata);
            }
        }

        return roleContract;
    }

    @Transactional(readOnly = true)
    public RoleContratsDTO findRoleContratsFP(String numeroContract) throws JrafDomainException {

        if (StringUtils.isEmpty(numeroContract)) {
            throw new IllegalArgumentException("Unable to find role contrats without contract number");
        }

        RoleContrats roleContratsList = roleContratsRepository.findRoleContratsFP(numeroContract);

        if (roleContratsList == null) {
            return null;
        }

        return RoleContratsTransform.bo2Dto(roleContratsList);
    }

    @Transactional(readOnly = true)
    public List<RoleContratsDTO> findRoleContrats(String gin, String type) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable to find role contrats without gin");
        }

        List<RoleContrats> roleContratsList = roleContratsRepository.findRoleContrats(gin, type);

        if (roleContratsList == null) {
            return null;
        }

        return RoleContratsTransform.bo2Dto(roleContratsList);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updateARoleContract(RoleContratsDTO roleContractsDTO) throws JrafDomainException {

        RoleContrats roleContract = roleContratsRepository.getOne(roleContractsDTO.getSrin());

        roleContract.setCleRole(roleContractsDTO.getCleRole());
        roleContract.setNumeroContrat(roleContractsDTO.getNumeroContrat());
        roleContract.setGin(roleContractsDTO.getGin());

        roleContract.setSegmentsQualif(roleContractsDTO.getSegmentsQualif());
        roleContract.setMilesQualif(roleContractsDTO.getMilesQualif());
        roleContract.setSoldeMiles(roleContractsDTO.getSoldeMiles());
        roleContract.setMemberType(roleContractsDTO.getMemberType());
        roleContract.setTier(roleContractsDTO.getTier());

        roleContract.setFamilleTraitement(roleContractsDTO.getFamilleTraitement());
        roleContract.setTypeContrat(roleContractsDTO.getTypeContrat());
        roleContract.setEtat(roleContractsDTO.getEtat());

        roleContract.setDateDebutValidite(roleContractsDTO.getDateDebutValidite());
        roleContract.setDateFinValidite(roleContractsDTO.getDateFinValidite());

        roleContract.setDateModification(roleContractsDTO.getDateModification());
        roleContract.setSignatureModification(roleContractsDTO.getSignatureModification());
        roleContract.setSiteModification(roleContractsDTO.getSiteModification());

        roleContratsRepository.saveAndFlush(roleContract);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public Integer deleteRoleContract(RoleContratsDTO roleContractsDTO) throws JrafDomainException {
        roleContratsRepository.deleteById(roleContractsDTO.getSrin());
        return roleContractsDTO.getCleRole();
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void removeByGinAndType(String gin, String type) throws JrafDomainException {

        roleContratsRepository.deleteByGinAndTypeContrat(gin, type);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public String createRoleContractForSubscribers(RoleContratsDTO rcDTO) throws JrafDomainException {

        RoleContrats rc = RoleContratsTransform.dto2BoLight(rcDTO);
        Optional<Individu> ind = individuRepository.findById(rc.getGin());
        if (!ind.isPresent()) {
            throw new NotFoundException("Contract cannot be created, individual not found");
        }
        getEntityManager().persist(rc.getBusinessrole());
        rc.setCleRole(rc.getBusinessrole().getCleRole());
        rc.setIndividu(ind.get());

        roleContratsRepository.saveAndFlush(rc);

        return rc.getNumeroContrat();
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void deleteRoleContractForSubscribers(RoleContratsDTO existing) throws JrafDomainException {
        roleContratsRepository.deleteById(existing.getSrin());
        if (existing.getBusinessroledto() != null) {
            businessRoleRepository.deleteById(existing.getBusinessroledto().getCleRole());
        }
    }

    @Transactional(readOnly = true)
    public RoleContratsDTO findRoleContractByNumContract(String contractNumber) throws JrafDomainException {

        RoleContrats rc = roleContratsRepository.findRoleContratsByNumContract(contractNumber);
        if (rc == null) {
            return null;
        }
        return RoleContratsTransform.bo2Dto(rc);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class)
    public boolean haveActiveFlyingBlueContract(String gin) throws JrafDomainException {
        if (StringUtils.isBlank(gin)) {
            throw new InvalidParameterException("Gin cannot be null");
        }
        List<RoleContratsDTO> contracts = this.findRoleContrats(gin);
        if (contracts == null || contracts.size() == 0) {
            return false;
        }
        for (RoleContratsDTO contract : contracts) {
            if (isContractActive(contract) && isFlyingBlue(contract)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContractActive(RoleContratsDTO roleContrat) throws InvalidParameterException {
        if (roleContrat == null) {
            throw new InvalidParameterException("role contract must not be null");
        }
        Date now = new Date();
        if (roleContrat.getDateDebutValidite().before(now) &&
                (roleContrat.getDateFinValidite() == null || roleContrat.getDateFinValidite().after(now))) {
            return true;
        }
        return false;
    }

    public boolean isFlyingBlue(RoleContratsDTO roleContrat) throws InvalidParameterException {
        if (roleContrat == null) {
            throw new InvalidParameterException("role contract must not be null");
        }
        return StringUtils.equals(roleContrat.getTypeContrat(), "FP");
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public String updateRoleContractForSubscribers(RoleContratsDTO rcDTO) throws JrafDomainException {
        RoleContrats rc = roleContratsRepository.getOne(rcDTO.getSrin());
        RoleContratsTransform.dto2BoLight(rcDTO, rc);
        RoleContratsTransform.dto2BoLink(rcDTO, rc);

        getEntityManager().merge(rc.getBusinessrole());
        roleContratsRepository.saveAndFlush(rc);

        return rc.getNumeroContrat();
    }

    public List<RoleContratsDTO> findValidRoleContractNotMyA(String gin) throws JrafDomainException {
        if (gin == null) {
            throw new IllegalArgumentException("Unable to find role contrats with empty gin");
        }

        List<RoleContrats> roleContratsList = roleContratsRepository.findValidRoleContractNotMyA(gin);

        if (roleContratsList == null) {
            return null;
        } else {
            List<RoleContratsDTO> resultList = new ArrayList<RoleContratsDTO>();
            for (RoleContrats rc : roleContratsList) {
                resultList.add(RoleContratsTransform.bo2Dto(rc));
            }
            return resultList;
        }
    }

    public int getNumberFPContractsByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.getNumberFPContractsOrOthersByGin(gin, "FP");
    }

    public boolean isMyAccountByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.isFlyingBlueOrMyAccountByGin(gin, "MA");
    }

    public boolean isFlyingBlueByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.isFlyingBlueOrMyAccountByGin(gin, "FP");
    }

    public List<String> getFPNumberByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.getFPNumberByGin(gin);
    }

    public int getNumberContractsByGinExceptFP(String gin) throws JrafDomainException {
        return roleContratsRepository.getNumberFPContractsOrOthersByGin(gin, "Others");
    }

    public String getFirstContractNumberOrTypeByGin(String gin, boolean number) throws JrafDomainException {
        List<RoleContrats> rc = roleContratsRepository.findRoleContrats(gin);
        if (number) {
            return rc.get(0).getNumeroContrat();
        } else {
            return rc.get(0).getTypeContrat();
        }
    }

    @Transactional
    public RoleContratsDTO findContractByCinAndType(String contractNumber, String contractType) throws JrafDomainException {

        RoleContrats roleContrats = roleContratsRepository.findByNumeroContratAndTypeContrat(contractNumber, contractType);

        if (roleContrats == null) {
            return null;
        }
        return RoleContratsTransform.bo2Dto(roleContrats);
    }

	/**
	 * 
	 * @param contractType
	 * @return The list of individual with valid and active FB contract and don't
	 *         have an account data in ACCOUNT_DATA table
	 */
	public List<RoleContratsDTO> findRoleContratsByTypeWithoutAccountData(String contractType) {
		List<RoleContratsDTO> roleContratsDTOList = null;

		List<Tuple> tupleList = roleContratsRepository.findRoleContratsByTypeWithoutAccountData(contractType);
		if (tupleList != null) {
			roleContratsDTOList = tupleList.stream().map(RoleContratsTransform::boToDto).collect(Collectors.toList());
		}

		return roleContratsDTOList;
	}

	public boolean existFbContractWithGindAndNumeroContract(String gin, String numeroContract){

        if (roleContratsRepository.countFPContractsByGinAndNumContract(gin, numeroContract) == 0){
            return false;
        };
        return true;
    }

    /**
     *
     * @param gin
     * @return Company Code linked to an existing FB contract of GIN parameter
     */
    @Transactional(readOnly = true)
    public String getCompanyCodeFromFBContract(String gin) throws JrafDomainException {
	    String companyCode = null;
        if(StringUtils.isNotBlank(gin)) {
            // Get number Flying-blue
            List<String> contractNumberList = getFPNumberByGin(gin);

            if (!CollectionUtils.isEmpty(contractNumberList)) {
                String contractNumber = contractNumberList.get(0); // Result is sorted from newest to oldest contract
                // Get contract Flying-blue
                if(StringUtils.isNotEmpty(contractNumber)){
                    RoleContratsDTO roleContratsDTO = findRoleContratsFP(contractNumber);
                    // Get companyCode
                    if(roleContratsDTO != null && roleContratsDTO.getCodeCompagnie() != null)
                        companyCode = roleContratsDTO.getCodeCompagnie();
                }
            }
        }

        return companyCode;
    }

    /**
     * @param roleContract
     * @param signature
     * @param individu
     * @return accountData
     * Create an Account Data for a customer
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public AccountData createAccountData(RoleContratsDTO roleContract, SignatureDTO signature, Individu individu) {
        String gin = roleContract.getGin();
        AccountData accountData = new AccountData();
        accountData.setFbIdentifier(roleContract.getNumeroContrat());
        accountData.setVersion(0);
        accountData.setSgin(gin);
        accountData.setStatus(AccountDataStatusEnum.ACCOUNT_VALID.code());
        try {
            String calculPinCode = EncryptionUtils.calculPinCodeV2(gin);
            String pswHashed = EncryptionUtils.hashPBKDF2WithHmacSHA1(calculPinCode, gin);
            if (StringUtils.isNotEmpty(pswHashed)) {
                accountData.setPassword(pswHashed.toUpperCase());
            }
        } catch (JrafApplicativeException e) {
            log.error(e.getMessage());
        }

        accountData.setPasswordToChange(0);
        accountData.setNbFailureAuthentification(0);
        accountData.setNbFailureSecretQuestionAns(0);
        accountData.setIndividu(individu);
        //Signature data
        accountData.setSignatureCreation(signature.getSignature());
        accountData.setSiteCreation(signature.getSite());
        accountData.setSignatureModification(signature.getSignature());
        accountData.setSiteModification(signature.getSite());
        accountData.setDateCreation(signature.getDate());
        accountData.setDateModification(signature.getDate());

        return accountData;
    }

    @Transactional(readOnly = true)
    public List<RoleContratsDTO> findValidRoleContractMya(@NotNull String gin) {
        List<RoleContrats> rcList = roleContratsRepository.findValidRoleContractMya(gin);
        List<RoleContratsDTO> rcDTOList = null;
        if (!CollectionUtils.isEmpty(rcList)) {
            rcDTOList = rcList.stream().map(rc -> {
                try {
                    return RoleContratsTransform.bo2DtoLight(rc);
                } catch (JrafDomainException e) {
                    log.error("Unable to map entity");
                    return null;
                }
            }).collect(Collectors.toList());
        }
        return null;
    }

    public boolean cinBelongsToGin(String cin, String gin) {
        int defaultLength = 10;

        if (StringUtils.isBlank(cin) || StringUtils.isBlank(gin)) {
            return false;
        }

        if (cin.length() == defaultLength) {
            cin = "00" + cin;
        }

        return roleContratsRepository.isGinAndCinFbMaExist(gin, cin);
    }
}
