package com.afklm.repind.msv.provide.contact.data.transform;


import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.soa.stubs.r000347.v1.model.Email;
import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.soa.stubs.r000347.v1.model.EmailResponse;
import com.afklm.soa.stubs.r000347.v1.model.Signature;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailsTransform {

    public List<EmailResponse> boToResponse(List<EmailEntity> emailsBo) {
        List<EmailResponse> emailResponse = new ArrayList<>();

        if(!emailsBo.isEmpty()) {
            for(EmailEntity email : emailsBo) {
                EmailResponse response = new EmailResponse();
                setEmailsResponseEmail(email, response);
                setEmailsResponseSignature(email, response);
                emailResponse.add(response);
            }
        }
        
        return emailResponse;
    }

    public void setEmailsResponseEmail(EmailEntity emailBo, EmailResponse emailResponse) {
        Email email = new Email();

        email.setEmail(emailBo.getEmail());
        email.setEmailOptin(emailBo.getAutorisationMailing());
        email.setMediumCode(emailBo.getCodeMedium());
        email.setMediumStatus(emailBo.getStatutMedium());
        email.setVersion(emailBo.getVersion());

        emailResponse.setEmail(email);
    }

    public void setEmailsResponseSignature(EmailEntity emailBO, EmailResponse emailResponse) {
        List<Signature> signatures = new ArrayList<>();

        Signature signatureCreation = new Signature();
        signatureCreation.setDate(emailBO.getDateCreation().toInstant().atOffset(ZoneOffset.UTC));
        signatureCreation.setSignature(emailBO.getSignatureCreation());
        signatureCreation.setSignatureSite(emailBO.getSiteCreation());
        signatureCreation.setSignatureType(SignatureEnum.CREATION.toString());
        signatures.add(signatureCreation);

        Signature signatureModification = new Signature();
        signatureModification.setDate(emailBO.getDateModification().toInstant().atOffset(ZoneOffset.UTC));
        signatureModification.setSignature(emailBO.getSignatureModification());
        signatureModification.setSignatureSite(emailBO.getSiteModification());
        signatureModification.setSignatureType(SignatureEnum.MODIFICATION.toString());
        signatures.add(signatureModification);

        emailResponse.setSignature(signatures);
    }
}
