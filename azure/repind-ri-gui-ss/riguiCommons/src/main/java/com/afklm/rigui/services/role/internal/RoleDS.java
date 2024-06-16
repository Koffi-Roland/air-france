package com.afklm.rigui.services.role.internal;

import com.afklm.rigui.exception.*;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.role.BusinessRoleRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.IndividuTransform;
import com.afklm.rigui.dto.role.BusinessRoleDTO;
import com.afklm.rigui.dto.role.BusinessRoleTransform;
import com.afklm.rigui.dto.role.RoleContratsDTO;
import com.afklm.rigui.dto.role.RoleContratsTransform;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;
import com.afklm.rigui.services.internal.unitservice.role.RoleUS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleDS {

    private static final Log log = LogFactory.getLog(RoleDS.class);

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
    public void remove(RoleContratsDTO dto) throws JrafDomainException {

        /*PROTECTED REGION ID(_in_sgPfNEd-uH5kd-A2OJg DS-CM remove) ENABLED START*/
        RoleContrats roleContrats = null;
        BusinessRole businessRole = null;

        // chargement du bo
        Optional<RoleContrats> roleContratsTemp = roleContratsRepository.findById(dto.getSrin());

        if(roleContratsTemp.isPresent()) roleContrats = roleContratsTemp.get();

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
        RoleContrats email = roleContratsRepository.findById(rc.getSrin()).get();
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

    public boolean isMyAccountByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.isFlyingBlueOrMyAccountByGin(gin, "MA");
    }

    public boolean isFlyingBlueByGin(String gin) throws JrafDomainException {
        return roleContratsRepository.isFlyingBlueOrMyAccountByGin(gin, "FP");
    }

    public String getFirstContractNumberOrTypeByGin(String gin, boolean number) throws JrafDomainException {
        List<RoleContrats> rc = roleContratsRepository.findRoleContrats(gin);
        if (number) {
            return rc.get(0).getNumeroContrat();
        } else {
            return rc.get(0).getTypeContrat();
        }
    }

}
