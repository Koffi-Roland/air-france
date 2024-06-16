package com.afklm.repindmsv.tribe.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.criteria.member.AddMemberCriteria;
import com.afklm.repindmsv.tribe.criteria.member.DeleteMemberCriteria;
import com.afklm.repindmsv.tribe.criteria.member.UpdateMemberCriteria;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberChecker {

    private final CheckerService checkerService;

    public AddMemberCriteria checkAddMemberCriteria(String tribeId,  String gin, String application) throws BusinessException {

        return new AddMemberCriteria()
                .withTribeId(checkerService.tribeIdChecker(tribeId))
                .withGin(checkerService.ginChecker(gin))
                .withApplication(checkerService.applicationChecker(application));

    }

    public UpdateMemberCriteria checkUpdateMemberCriteria(String tribeId, String gin, String status, String application) throws BusinessException {

        return new UpdateMemberCriteria()
                .withTribeId(checkerService.tribeIdChecker(tribeId))
                .withGin(checkerService.ginChecker(gin))
                .withStatus(checkerService.statusChecker(status))
                .withApplication(checkerService.applicationChecker(application));

    }
}
