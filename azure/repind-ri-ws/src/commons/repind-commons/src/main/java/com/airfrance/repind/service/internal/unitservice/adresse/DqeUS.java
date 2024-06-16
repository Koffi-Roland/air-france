package com.airfrance.repind.service.internal.unitservice.adresse;

import com.airfrance.ref.exception.AddressNormalizationException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.repind.client.dqe.RNVP;
import com.airfrance.repind.client.dqe.RNVP1;
import com.airfrance.repind.client.dqe.RNVPDqeClient;
import com.airfrance.repind.dao.reference.RefProvinceRepository;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.refTable.RefTableCAT_MED;
import com.airfrance.repind.entity.refTable.RefTablePAYS;
import com.airfrance.repind.entity.refTable.RefTableREF_PROVINCE;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.repind.entity.reference.RefProvince;
import com.airfrance.repind.mapper.address.PostalAddressMapper;
import com.airfrance.repind.service.reference.internal.RefPaysDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DqeUS {

    @Autowired(required = false)
    private RNVPDqeClient rnvpDqeClient;

    @Autowired
    private RefPaysDS refPaysDS;

    @Autowired
    private RefProvinceRepository refProvinceRepository;

    public PostalAddressResponseDTO normalizeAddress(PostalAddress ioAddress, boolean hasToBeChecked, boolean addressForFunction) throws JrafDomainRollbackException {
        // Forcage
        boolean forcage = OuiNonFlagEnum.OUI.toString().equals(ioAddress.getSforcage());

        if (hasToBeChecked) {
            // Check mandatory fields and their validity
            checkMandatoryAndValidity(ioAddress, forcage, addressForFunction);
        }

        if (!forcage) {
            // Address Normalization
            // Most of the countries are supported, very few are set to N
            if (!refPaysDS.codePaysIsValide(ioAddress.getScode_pays())) {
                throw new JrafDomainRollbackException("Country not in scope");
            }

            if(!refPaysDS.codePaysIsNormalizable(ioAddress.getScode_pays())){
                log.info("Country not normalisable");
                // Force to not normalize the address
                ioAddress.setIcod_err(0);
                ioAddress.setIcod_warning(null);
            }
            else {
                RNVP1 dqeResponse =  callDqeService(ioAddress);
                if (dqeResponse != null) {
                    EDqeErrorDetails eDqeErrorDetails = EDqeErrorDetails.valueFromLevel(dqeResponse.getDqECodeDetail());
                    setIErrorCode(ioAddress , eDqeErrorDetails.getLevel());
                    if(eDqeErrorDetails.getLevel() > 1){
                        log.error("NormalizeDQE - Call returns error with number "+eDqeErrorDetails.getCode()+" (not able to normalize the address)");
                        PostalAddressResponseDTO addressResponse = PostalAddressMapper.INSTANCE.mapRNVP1ToPostalAddressResponseDTO(dqeResponse);
                        return addressResponse;
                    }
                    log.debug("Dqe Verify Level {}",eDqeErrorDetails.getCode());
                    feedAddress(ioAddress , dqeResponse);
                }
            }
        } else {
            // Force to not normalize the address
            ioAddress.setIcod_err(0);
            ioAddress.setIcod_warning(null);
        }
        return null;
     }

    private RNVP1 callDqeService(PostalAddress ioAddress) throws AddressNormalizationException {
        RNVP response = rnvpDqeClient.execute(ioAddress);
        if(response.get1() == null){
            log.warn("NormalizeDQE - Call returns RNVP1 as null");
            return null;
        }
        return response.get1();
    }

    private void setIErrorCode(PostalAddress address , Integer iLevel) {
        address.setIcod_err(iLevel);
        address.setScod_err_simple(iLevel.toString());
        address.setScod_err_detaille(""); // not used anymore
    }

    private void feedAddress(PostalAddress ioAddress, RNVP1 rnvp1){
        ioAddress.setSno_et_rue(rnvp1.getAdresse());
        ioAddress.setSlocalite(""); //TODO: find localite inside RNVP response
        ioAddress.setScode_postal(rnvp1.getCodePostal());
        ioAddress.setSville(rnvp1.getLocalite());
        ioAddress.setScode_province(refProvinceRepository.findByCodeAndCodePays(rnvp1.getProvince(), ioAddress.getScode_pays()).map(RefProvince::getCode).orElse(null));
    }

    private void checkMandatoryAndValidity(PostalAddress pa, boolean forcage, boolean addressForFonction)
            throws JrafDomainRollbackException {

        if (org.apache.commons.lang.StringUtils.isEmpty(pa.getScode_medium())) {
            throw new JrafDomainRollbackException("117"); // REF_ERREUR : MEDIUM CODE MANDATORY
        }
        if (addressForFonction) {
            if (!RefTableCAT_MED._REF_L.equals(pa.getScode_medium())) {
                throw new JrafDomainRollbackException("116"); // REF_ERREUR : INVALID MEDIUM CODE
            }
        } else if (!RefTableCAT_MED._REF_L.equals(pa.getScode_medium())
                && !RefTableCAT_MED._REF_M.equals(pa.getScode_medium())
                && !RefTableCAT_MED._REF_F.equals(pa.getScode_medium())) {
            throw new JrafDomainRollbackException("116"); // REF_ERREUR : INVALID MEDIUM CODE
        }

        if (org.apache.commons.lang.StringUtils.isEmpty(pa.getSstatut_medium())) {
            throw new JrafDomainRollbackException("137"); // REF_ERREUR : MEDIUM STATUS MANDATORY
        }

        // Modification mode
        if (!org.apache.commons.lang.StringUtils.isEmpty(pa.getSain()) && !RefTableREF_STA_MED._REF_I.equals(pa.getSstatut_medium())
                && !RefTableREF_STA_MED._REF_V.equals(pa.getSstatut_medium())) {
            throw new JrafDomainRollbackException("136"); // REF_ERREUR : INVALID MEDIUM STATUS
        }

        // Creation mode
        if (org.apache.commons.lang.StringUtils.isEmpty(pa.getSain()) && !RefTableREF_STA_MED._REF_V.equals(pa.getSstatut_medium())) {
            throw new JrafDomainRollbackException("136"); // REF_ERREUR : INVALID MEDIUM STATUS
        }

        // If forcage = Yes => Num rue code postal ville not mandatory
        if (!forcage) {
            if (org.apache.commons.lang.StringUtils.isEmpty(pa.getSno_et_rue())) {
                throw new JrafDomainRollbackException("279"); // REF_ERREUR : STREET AND NUMBER MANDATORY
            }

            // Some countries don't have zip code
            /*
            if (org.apache.commons.lang.StringUtils.isEmpty(pa.getScode_postal())) {
                throw new JrafDomainRollbackException("281"); // REF_ERREUR : INVALID ZIP CODE
            }
            */

            if (org.apache.commons.lang.StringUtils.isEmpty(pa.getSville())) {
                throw new JrafDomainRollbackException("278"); // REF_ERREUR : INVALID CITY
            }
        }

        if (!org.apache.commons.lang.StringUtils.isEmpty(pa.getScode_province())
                && !RefTableREF_PROVINCE.instance().estValide(pa.getScode_province(), "")) {
            throw new JrafDomainRollbackException("280"); // REF_ERREUR : INVALID PROVINCE/DISTRICT CODE
        }

        if (StringUtils.isEmpty(pa.getScode_pays())) {
            throw new JrafDomainRollbackException("192"); // REF_ERREUR : COUNTRY CODE MANDATORY
        }

        if ((!RefTablePAYS.instance().estValide(pa.getScode_pays(), ""))) {
            throw new JrafDomainRollbackException("131"); // REF_ERREUR : INVALID COUNTRY
        }
    }

}
