package com.afklm.batch.adrInvalidBarecode.initData;


import com.afklm.batch.adrInvalidBarecode.model.InputRecord;
import com.afklm.batch.adrInvalidBarecode.model.OutputRecord;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.role.RoleContrats;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.mockito.Mockito.doReturn;

@Service
public class InitDataAdrInvalidBarecode {
    public static String NUMERO_CONTRAT ="001781704686";
    public static Boolean NUMERO_CONTRAT_NOT_FOUND_TRUE = true;
    public static Boolean NUMERO_CONTRAT_NOT_FOUND_FALSE = false;
    public static String SAIN ="0000000065866632";

    public static String GIN ="0000000065866632";

    public static String GIN_NOT_MATCH ="0000000065866633";
    public static String DATE_MODIFICATION ="20190602";

    public static String MESSAGE1 ="TECHNICAL ERROR UnknownAin";
    public static String MESSAGE2 ="TECHNICAL ERROR Address is status history";
    public static String MESSAGE3 ="TECHNICAL ERROR Address is removed";
    public static String MESSAGE4 ="TECHNICAL ERROR Address is already invalid";
    public static String MESSAGE5 ="TECHNICAL ERROR role contrat not found";
    public static String MESSAGE6 ="Address Updated";
    public static String MESSAGE7 ="TECHNICAL ERROR Address gin and role contrat gin not match";

    public static String MESSAGE8 ="TECHNICAL ERROR when processing ain 0000000065866632";
    @MockBean
    private PostalAddressRepository postalAddressRepository;

    @MockBean
    private RoleContratsRepository roleContratsRepository;

    public OutputRecord initDataAdrInvalidBarecodeOutputRecordUnknownAin()
    {
        return OutputRecord.builder()
                .numeroContrat(NUMERO_CONTRAT)
                .sain(SAIN)
                .dateModification(DATE_MODIFICATION)
                .build();
    }

    public InputRecord initDataAdrInvalidBarecodeInputRecords()
    {
        return InputRecord.builder()
                .numeroContrat(NUMERO_CONTRAT)
                .sain(SAIN)
                .dateModification(DATE_MODIFICATION)
                .build();
    }
    public void initMockPostalAddress(String status)
    {
        // Setup the mock repo
        PostalAddress a1ForMock = new PostalAddress();
        a1ForMock.setSain(SAIN);
        a1ForMock.setSgin(GIN);
        a1ForMock.setSstatut_medium(status);
        a1ForMock.setDdate_modification(new Date());
        doReturn(a1ForMock).when(postalAddressRepository).findBySain(SAIN);
    }

    public void initMockRoleContrats(String numeroContrat, boolean notFound, String notMatchGin)
    {
        RoleContrats roleContrats = new RoleContrats();
        roleContrats.setGin(GIN);
        if(notMatchGin != null) roleContrats.setGin(notMatchGin);
        roleContrats.setNumeroContrat(NUMERO_CONTRAT);
        if(notFound){
            roleContrats = null;
        }
        doReturn(roleContrats).when(roleContratsRepository).findRoleContratsByNumContract(numeroContrat);
    }
}
