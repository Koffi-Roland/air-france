package com.airfrance.batch.propagate.email.processor;

import com.airfrance.batch.propagate.email.validation.ProcessorValidation;
import com.airfrance.repind.entity.individu.AccountData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@PropertySource("classpath:/app/propagate-email.properties")
@Slf4j
public class AccountEmailProcessor implements ItemProcessor<AccountData, AccountData> {

    @Value("${app.propagate.email.site}")
    private String site;

    @Value("${app.propagate.email.signature}")
    private String signature;

    @Autowired
    ProcessorValidation processorValidation;

    @Transactional
    @Override
    public AccountData process(AccountData account) throws Exception {
        //Rule 1 and 1.1 : Define all individuals in RI (status Valid) who have FB or MYA contract (status Confirmed)
        // who do not have Email Identifier, but who have one Email (Status=Valid, Code=Direct)
        if (processorValidation.isIndividuValid.apply(account) && processorValidation.isEmailDirectValid.apply(account) && (processorValidation.isIndividuFB.apply(account) || processorValidation.isIndividuMyAccount.apply(account))) {
            //Rule 2 : Check if Email is unique in RI by checking in both Email Direct and Email identifier (account data)
            if(processorValidation.isEmailUnique.apply(account)){

                //update account with email
                account.setEmailIdentifier(processorValidation.getDirectEmailFunction.apply(account.getSgin()));

                //Modify technical data
                account.setDateModification(new Date());
                account.setSignatureModification(signature);
                account.setSiteModification(site);
                log.info("GIN " + account.getSgin() + " is updated with Email " + account.getEmailIdentifier());
            }
        }
        return account;
    }


}
