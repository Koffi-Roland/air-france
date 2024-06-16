package com.afklm.repind.msv.provide.contract.data.service;

import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleTravelers;
import com.afklm.repind.common.entity.role.RoleUCCR;
import com.afklm.repind.common.enums.ContractType;
import com.afklm.repind.common.enums.IdentifierTypeEnum;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.common.repository.role.RoleTravelersRepository;
import com.afklm.repind.common.repository.role.RoleUCCRRepository;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.transform.ContractTransform;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
@Slf4j
/*
 * This service is responsible for the call to the repository and the logic between the mapping of the response
 */
public class ContractService {
    private BusinessRoleRepository businessRoleRepository;
    private RoleContractRepository roleContractRepository;
    private RoleUCCRRepository roleUCCRRepository;
    private RoleTravelersRepository roleTravelersRepository;
    private ContractTransform contractTransform;

    /**
     * Return a list of business role using the cin of a contract
     * This method use the cin to get the gin of an individual and then return the list of business role of that individual
     *
     * @param cin The contract number of a contract
     * @return null if we don't find the contract related to the cin or if this contract doesn't have the gin. Else it'll return the list of business Role
     */
    public List<BusinessRole> findAllBusinessRoleByCin(String cin) {
        BusinessRole businessRole = businessRoleRepository.findByNumeroContrat(cin);
        if (businessRole == null || businessRole.getGinInd() == null) {
            return new ArrayList<>();
        }
        return findAllBusinessRoleByGin(businessRole.getGinInd());
    }

    /**
     * Return a list of business role using the gin of a contract
     *
     * @param gin The identifier of an individual
     * @return The list of business Role
     */
    public List<BusinessRole> findAllBusinessRoleByGin(String gin) {
        return businessRoleRepository.findBusinessRolesByGinInd(gin);
    }

    /**
     * Return the RoleContract Object from db using the cleRole variable
     *
     * @param cleRole the identifier of a RoleContract
     * @return The RoleContract
     */
    public RoleContract findRoleContractByCleRole(int cleRole) {
        return roleContractRepository.findByCleRole(cleRole);
    }

    /**
     * Return the RoleUCCR Object from db using the cleRole variable
     *
     * @param cleRole the identifier of a RoleUCCR
     * @return The RoleUCCR
     */
    public RoleUCCR findRoleUCCRByCleRole(int cleRole) {
        return roleUCCRRepository.findByCleRole(cleRole);
    }

    /**
     * Return the RoleTravelers Object from db using the cleRole variable
     *
     * @param cleRole the identifier of a RoleTravelers
     * @return The RoleTravelers
     */
    public RoleTravelers findRoleTravelerByCleRole(int cleRole) {
        return roleTravelersRepository.findByCleRole(cleRole);
    }

    /**
     * This method transform a business Role into a contract
     * if the type is not 'U', 'C', 'T', 'A', or 'D' it'll return null
     *
     * @param businessRole The businessRole that give us all the required data to create a contract
     * @return null if an error occured, else the mapped contract
     */
    public Contract mapBusinessRoleToContract(BusinessRole businessRole) {
        Contract res = new Contract();
        if (businessRole.getType() == null || businessRole.getType().isEmpty()) {
            return null;
        }
        if (businessRole.getNumeroContrat() != null && !businessRole.getNumeroContrat().isEmpty()) {
            res.setContractNumber(businessRole.getNumeroContrat());
        }
        ContractType type = ContractType.fromLabel(businessRole.getType());
        if(type == null){
            return null;
        }

        res.setContractType(businessRole.getType());
        switch (type) {
            case ROLE_CONTRACT -> {
                return contractTransform.mapRoleContractToContract(res, findRoleContractByCleRole(businessRole.getCleRole()));
            }
            case ROLE_UCCR -> {
                return contractTransform.mapRoleUCCRToContract(res, findRoleUCCRByCleRole(businessRole.getCleRole()));
            }
            case ROLE_TRAVELERS -> {
                return contractTransform.mapRoleTravelerToContract(res, findRoleTravelerByCleRole(businessRole.getCleRole()));
            }
            case CONTRACT_DOCTOR -> res.setContractType(ContractType.CONTRACT_DOCTOR.toString());
            default -> {
                return null;
            }
        }
        return res;
    }

    /**
     * Create a list of contract based on the individual linked to the given information
     * All contract with non managed type are deleted from the list
     *
     * @param type       The type of the given identifier
     * @param identifier the identifier used to find the individual
     * @return null if no contract was found, else it'll be the list containing all the contract of the individual
     */
    public List<Contract> getContractList(String type, String identifier) {
        List<Contract> res = null;

        if (IdentifierTypeEnum.valueOf(type.toUpperCase()).equals(IdentifierTypeEnum.CIN)) {
            res = this.findAllBusinessRoleByCin(identifier)
                    .stream().map(this::mapBusinessRoleToContract)
                    .filter(Objects::nonNull)
                    .toList();
        } else if (IdentifierTypeEnum.valueOf(type.toUpperCase()).equals(IdentifierTypeEnum.GIN)) {
            res = this.findAllBusinessRoleByGin(identifier)
                    .stream().map(this::mapBusinessRoleToContract)
                    .filter(Objects::nonNull)
                    .toList();
        }

        return res;
    }
}
