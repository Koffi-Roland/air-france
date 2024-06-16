package com.afklm.repind.msv.automatic.merge.service;


import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.automatic.merge.model.error.BusinessError;
import com.afklm.repind.msv.automatic.merge.model.individual.ModelIndividual;
import com.afklm.repind.msv.automatic.merge.helper.MergeHelper;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.List;

@Service
public class IndividualService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndividualService.class);

    @Autowired
    IndividuRepository individuRepository;

    @Autowired
    RoleContractRepository roleContractRepository;

    @Autowired
    MergeHelper mergeHelper;

    @Autowired
    BeanMapper beanMapper;

    /**
     * Find individual by an identifier (CIN or GIN)
     * @param identifiant CIN or GIN
     * @return Individual DTO
     * @throws BusinessException Generic Error
     */
    @Transactional
    public ModelIndividual getIndividualByIdentifiant(String identifiant) throws BusinessException {
        ModelIndividual ind = getIndividualByGin(identifiant);

        if (ind == null) {
            ind = getIndividualByCin(identifiant);
        }

        if (ind == null) {
            LOGGER.error(BusinessError.API_CUSTOMER_NOT_FOUND.getRestError().getCode());
            throw new BusinessException(BusinessError.API_CUSTOMER_NOT_FOUND);
        }

        return ind;
    }

    /**
     * Find individual by GIN
     * @param identifiant GIN
     * @return Individual DTO
     */
    private ModelIndividual getIndividualByGin(String identifiant) {
        Individu ind = null;

        try {
            ind = individuRepository.getIndividualOrProspectByGinExceptForgotten(identifiant);
        } catch (NoResultException e) {
            String msg = "Individual with id " + identifiant + " not found";
            LOGGER.error(msg);
        }

        return beanMapper.individuToModelIndividual(ind);
    }

    /**
     * Find individual by CIN
     * @param cin CIN
     * @return Individual DTO
     */
    private ModelIndividual getIndividualByCin(String cin) {
        /*
         * Call DS after JRAF Migration
         */
        List<RoleContract> rc = roleContractRepository.findRoleContractsByNumeroContrat(cin);

        if (!CollectionUtils.isEmpty(rc)) {
            // CIN must be unique in DB --> linked to only one GIN
            return getIndividualByGin(rc.get(0).getIndividu().getGin());
        } else {
            return null;
        }
    }
}
