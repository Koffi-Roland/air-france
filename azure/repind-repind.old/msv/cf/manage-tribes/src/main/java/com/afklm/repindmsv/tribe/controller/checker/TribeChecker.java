package com.afklm.repindmsv.tribe.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.criteria.tribe.CreateTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.DeleteTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByGinCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByIdCriteria;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TribeChecker {

    private final CheckerService checkerService;

    public CreateTribeCriteria checkCreateTribeCriteria(String name, String type, String manager, String application) throws BusinessException {

        return new CreateTribeCriteria()
                .withName(checkerService.nameChecker(name))
                .withType(checkerService.typeChecker(type))
                .withManager(checkerService.ginChecker(manager))
                .withApplication(checkerService.applicationChecker(application));

    }

    public RetrieveTribeByIdCriteria checkRetrieveByIdTribeCriteria(String tribeId, String application) throws BusinessException {

        return new RetrieveTribeByIdCriteria()
                .withTribeId(checkerService.tribeIdChecker(tribeId))
                .withApplication(checkerService.applicationChecker(application));

    }

    public RetrieveTribeByGinCriteria checkRetrieveByGinTribeCriteria(String gin, String application) throws BusinessException {
        return new RetrieveTribeByGinCriteria()
                .withGin(checkerService.ginChecker(gin))
                .withApplication(checkerService.applicationChecker(application));

    }


    public DeleteTribeCriteria checkDeleteTribeCriteria(String tribeId, String application) throws BusinessException {
        return new DeleteTribeCriteria()
                .withTribeId(checkerService.tribeIdChecker(tribeId))
                .withApplication(checkerService.applicationChecker(application));
    }

}
