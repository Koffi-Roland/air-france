package com.afklm.batch.adrInvalidBarecode.service;

import com.afklm.batch.adrInvalidBarecode.model.OutputRecord;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.role.RoleContrats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
public class PostalAddressInvalidBarecodeDS {

    @Autowired
    private PostalAddressRepository postalAddressRepository;

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    @Autowired
    AdrInvalidBarecodeSummaryService summaryService;

    @Transactional
    public void updatePoastalAddressStatus(OutputRecord outputRecord) throws ParseException {
        PostalAddress postalAddress = postalAddressRepository.findBySain(outputRecord.getSain());
        if( postalAddress == null) {
            log.error("[-] postalAddress with sain " + outputRecord.getSain() + " is not found");
            summaryService.incrementRejectedCounter();
            summaryService.incrementInvalidedAinUnknownLinesCounter();
            outputRecord.setMessage("TECHNICAL ERROR UnknownAin");
        }else{
            if(postalAddress.getSstatut_medium() != null && MediumStatusEnum.HISTORIZED.toString().equals(postalAddress.getSstatut_medium())){
                log.error("[-] postalAddress with sain " + outputRecord.getSain() + " is Historized");
                summaryService.incrementRejectedCounter();
                summaryService.incrementInvalidHystoryLinesCounter();
                outputRecord.setMessage("TECHNICAL ERROR Address is status history");
            }else if(postalAddress.getSstatut_medium() != null && MediumStatusEnum.REMOVED.toString().equals(postalAddress.getSstatut_medium())){
                log.error("[-] postalAddress with sain " + outputRecord.getSain() + " is removed");
                summaryService.incrementRejectedCounter();
                summaryService.incrementInvalidStatusXLinesCounter();
                outputRecord.setMessage("TECHNICAL ERROR Address is removed");
            } else if (postalAddress.getSstatut_medium() != null && MediumStatusEnum.INVALID.toString().equals(postalAddress.getSstatut_medium())) {
                log.error("[-] postalAddress with sain " + outputRecord.getSain() + " is already invalid");
                summaryService.incrementRejectedCounter();
                summaryService.incrementAlreadyInvalidLinesCounter();
                outputRecord.setMessage("TECHNICAL ERROR Address is already invalid");
            }else{
                RoleContrats roleContrats = roleContratsRepository.findRoleContratsByNumContract(outputRecord.getNumeroContrat());
                if( roleContrats == null) {
                    log.error("[-] role contrat  "+ outputRecord.getNumeroContrat() + "is not found");
                    summaryService.incrementRejectedCounter();
                    summaryService.incrementInvalidRoleContratNotFoundLinesCounter();
                    outputRecord.setMessage("TECHNICAL ERROR role contrat not found");
                }else{
                    if(roleContrats.getGin() != null && postalAddress.getSgin() != null && roleContrats.getGin().equals(postalAddress.getSgin())){
                        log.info("[+] update postalAddress with sain " + outputRecord.getSain() + "to status INVALID");
                        postalAddress.setSstatut_medium(MediumStatusEnum.INVALID.toString());
                        postalAddress.setDdate_modification(new Date());
                        postalAddress.setSsignature_modification("INVALIDBARCODE");
                        postalAddressRepository.saveAndFlush(postalAddress);
                        summaryService.incrementUpdatededLinesCounter();
                        outputRecord.setMessage("[+] Address Updated");
                    }else{
                        log.error("[-] postalAddress with sain " + outputRecord.getSain() + "and gin "+postalAddress.getSgin()+" not match to role contrat "+outputRecord.getNumeroContrat()+" and gin "+roleContrats.getGin());
                        summaryService.incrementRejectedCounter();
                        summaryService.incrementInvalidRoleContratGinNotMatchLinesCounter();
                        outputRecord.setMessage("TECHNICAL ERROR Address gin and role contrat gin not match");
                    }
                }
            }
        }
        log.info("[+] update outputRecord : {}", outputRecord.toString());
    }

}
