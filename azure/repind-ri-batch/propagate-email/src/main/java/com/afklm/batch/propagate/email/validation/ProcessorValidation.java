package com.afklm.batch.propagate.email.validation;

import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class ProcessorValidation {

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private AccountDataRepository accountDataRepository;

    //Rule 1 : Define individual with status Valid
    public Function<AccountData, Boolean> isIndividuValid = account -> {
        Individu individu = individuRepository.findBySgin(account.getSgin());
        return individu != null && "V".equals(individu.getStatutIndividu());
    };

    //Rule 1 : Check if individual have Confirmed Flying Blue contract
    public Function<AccountData, Boolean> isIndividuFB = account -> {
        try{
            if (individuRepository.isFlyingBlue(account.getSgin()) > 0) {
                RoleContrats roleContrat = roleContratsRepository.findByNumeroContrat(account.getFbIdentifier());
                return roleContrat.getEtat().equals("C");
            }
        }catch (Exception e) {
            log.error("Error while checking the validity of Flying Blue contract of this gin: " + account.getSgin());
        }
        return false;
    };

    //Rule 1 : Check if individual have Confirmed MyAccount contract
    public Function<AccountData, Boolean> isIndividuMyAccount = account -> {
        try {
            if (individuRepository.isMyAccount(account.getSgin()) > 0) {
                RoleContrats roleContrat = roleContratsRepository.findByNumeroContrat(account.getAccountIdentifier());
                return roleContrat.getEtat().equals("C");
            }
        } catch (Exception e) {
            log.error("Error while checking the validity of MyAccount contract of this gin: " + account.getSgin());
        }
        return false;
    };

    //Rule 1.1 : Check if individual have only one Valid/Driect email
    public Function<AccountData, Boolean> isEmailDirectValid = account -> {
        try {
            if (emailRepository.findDirectEmail(account.getSgin()).size() == 1) {
                return true;
            }
        } catch (Exception e) {
            log.error("Error while checking the validity of email of this gin: " + account.getSgin());
        }
        return false;
    };

    //Rule 2 : Check if Email is unique in RI by checking in both Email Direct and Email identifier (account data)
    public Function<AccountData, Boolean> isEmailUnique = account -> {
        try {
            String email = emailRepository.findDirectEmail(account.getSgin()).get(0).getEmail();
            Optional<AccountData> emailIdentifier = Optional.ofNullable(accountDataRepository.findByEmailIdentifier(email));
            return (!emailIdentifier.isPresent() && isEmailDirectUnique(email, account.getSgin()));
        } catch (Exception e) {
            log.error("Error while checking the uniqueness of email of this gin: " + account.getSgin());
        }
        return false;
    };

    //Checks if direct email is only associated to the input gin
    public boolean isEmailDirectUnique(String email, String gin) {
        List<Email> directEmails = emailRepository.findByEmail(email);
        return directEmails.stream().allMatch(e -> e.getSgin().equals(gin));
    }

    //Get directEmail
    public Function<String, String> getDirectEmailFunction = gin -> {
        try {
            return emailRepository.findDirectEmail(gin).get(0).getEmail();
        } catch (Exception e) {
            log.error("Error while retrieving direct email for this gin: " + gin);
            return null;
        }
    };
}
