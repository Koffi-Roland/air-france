package com.afklm.repind.msv.provide.identification.data.service;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.soa.stubs.r000378.v1.model.Telecom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
/*
 * A service used to map the response of the telecom part of some other part the request of this MS
 */
public class TelecomService {

    private TelecomsRepository telecomsRepository;

    /**
     * Getter for list of telecom by Gin
     * @param gin 12 character String used to identify an individual.
     * @return A list of telecoms object corresponding to the provided Gin.
     */
    public List<Telecoms> getTelecomsByGin(String gin) {
        List<Telecoms> res = new ArrayList<>();
        List<Telecoms> tmp = this.telecomsRepository.getTelecomsByIndividuGinAndStatutMediumAndCodeMediumOrderByDateModificationDesc(gin, "V", "P");
        if(!tmp.isEmpty()){
            res.add(tmp.get(0));
        }
        tmp = this.telecomsRepository.getTelecomsByIndividuGinAndStatutMediumAndCodeMediumOrderByDateModificationDesc(gin, "V", "D");
        if(!tmp.isEmpty()){
            res.add(tmp.get(0));
        }
        return res;
    }

    /**
     * Converter for list of telecoms of the db to list of telecom of the stubs
     * @param telecoms the telecoms list of the db
     * @return The same list converted in telecom list of the stub of my MS
     */
    public List<Telecom> convertTelecomsListToTelecomList(List<Telecoms> telecoms){
        List<Telecom> res = new ArrayList<>();
        for(Telecoms telecom : telecoms){
            res.add(convertTelecomsToTelecom(telecom));
        }
        return res;
    }

    /**
     * Convert telcoms of the db to telecom of the stubs
     * @param telecoms the telecoms of the db
     * @return The same telecoms converted in telecom of the stub of my MS
     */
    public Telecom convertTelecomsToTelecom(Telecoms telecoms){
        Telecom res = new Telecom();

        res.setCountryCode(telecoms.getNormInterCountryCode());
        res.setVersion(telecoms.getVersion().toString());
        res.setMediumCode(telecoms.getCodeMedium());
        res.setPhoneNumber(telecoms.getNumero());
        res.setMediumStatus(telecoms.getStatutMedium());
        res.setTerminalType(telecoms.getTerminal());

        return res;
    }
}
