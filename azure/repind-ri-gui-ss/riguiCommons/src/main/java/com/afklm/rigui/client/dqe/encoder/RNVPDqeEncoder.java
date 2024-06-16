package com.afklm.rigui.client.dqe.encoder;

import com.afklm.rigui.client.dqe.model.RNVPDqeRequestModel;
import com.afklm.rigui.dao.reference.PaysRepository;
import com.afklm.rigui.entity.adresse.PostalAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RNVPDqeEncoder {

    @Autowired
    private PaysRepository paysRepository;

    public static String LICENCE = "D9YUNI1BHBkhx_ZSEPnh~vDq4j";
    public static String SEPARATOR = "|";

    public RNVPDqeRequestModel encode(PostalAddress iAddress){
        return new RNVPDqeRequestModel()
                .withLicence(LICENCE)
                .withPays(paysRepository.findById(iAddress.getScode_pays()).get().getIso3Code())
                .withModification("O")
                .withAdresse(buildAddress(iAddress));
    }

    /**
     * Build Address with following schema
     * complément|adresse|lieu-dit|code postal|ville
     * @param iAddress PostalAddress object
     * @return formatted string
     */
    private String buildAddress(PostalAddress iAddress){
        StringBuilder sb = new StringBuilder();
        //Complement adresse
        sb.append(SEPARATOR);
        sb.append(toStringValue(iAddress.getSno_et_rue())); //Address
        sb.append(SEPARATOR);
        //lieu-dit
        sb.append(SEPARATOR);
        sb.append(toStringValue(iAddress.getScode_postal())); //Code postale
        sb.append(SEPARATOR);
        sb.append(toStringValue(iAddress.getSville())); //Ville
        return sb.toString();
    }

    /**
     * Return Empty string or value
     * @param iValue
     * @return "" or iValue
     */
    private String toStringValue(String iValue){
        if(StringUtils.isNotEmpty(iValue)){
            return iValue;
        }
        return "";
    }
}
