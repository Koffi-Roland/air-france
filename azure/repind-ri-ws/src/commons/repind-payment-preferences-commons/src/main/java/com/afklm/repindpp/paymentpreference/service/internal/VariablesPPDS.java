package com.afklm.repindpp.paymentpreference.service.internal;

import com.afklm.repindpp.paymentpreference.dao.VariablesPPRepository;
import com.afklm.repindpp.paymentpreference.dto.VariablesPPDTO;
import com.afklm.repindpp.paymentpreference.dto.VariablesPPTransform;
import com.afklm.repindpp.paymentpreference.entity.EnvVarPp;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class VariablesPPDS {
	
    private static final Log log = LogFactory.getLog(VariablesPPDS.class);

    @Autowired
    private VariablesPPRepository variablesPPRepository;
   
    public VariablesPPDTO getByEnvKey(String envKeyPP) throws JrafDomainException {
    	Optional<EnvVarPp> variablesPP = variablesPPRepository.findByIdEnvKeyPp(envKeyPP);
    	if(variablesPP.isPresent()) {
    		return VariablesPPTransform.bo2DtoLight(variablesPP.get());
    	}
    	return null;
	}
    public String isPCIDSSon() throws JrafDomainException {
	    VariablesPPDTO variablesPPDTO = new VariablesPPDTO();
		variablesPPDTO.setEnvKeyPP("IS_PCIDSS_ON");
		String isPCIDSS = "N";
		try{
			VariablesPPDTO test = getByEnvKey("IS_PCI_DSS_ON");
			isPCIDSS=String.valueOf(test.getEnvValuePP());
		}catch (JrafDomainException e) {
			log.fatal(e);
		}
		return isPCIDSS;
    }
    
	public String isFullPCIDSS() throws JrafDomainException {
	    VariablesPPDTO variablesPPDTO = new VariablesPPDTO();
		variablesPPDTO.setEnvKeyPP("IS_FULL_PCI_DSS");
		String isFullPCIDSS = "N";
		try{
			VariablesPPDTO test = getByEnvKey("IS_FULL_PCI_DSS");
			isFullPCIDSS=String.valueOf(test.getEnvValuePP());
		}catch (JrafDomainException e) {
			log.fatal(e);
		}
		return isFullPCIDSS;
	}
	
    public String batchPCIDSSOn() throws JrafDomainException {
	    VariablesPPDTO variablesPPDTO = new VariablesPPDTO();
		variablesPPDTO.setEnvKeyPP("BATCH_PCIDSS_ON");
		String isPCIDSS = "N";
		try{
			VariablesPPDTO test = getByEnvKey("BATCH_PCIDSS_ON");
			isPCIDSS=String.valueOf(test.getEnvValuePP());
		}catch (JrafDomainException e) {
			log.fatal(e);
		}
		return isPCIDSS;
    }

	public String isPhaseTest() throws JrafDomainException {
	    VariablesPPDTO variablesPPDTO = new VariablesPPDTO();
		variablesPPDTO.setEnvKeyPP("PHASE_TEST_W000470");
		String isPhaseTest = "N";
		try{
			VariablesPPDTO test = getByEnvKey("PHASE_TEST_W000470");
			isPhaseTest=String.valueOf(test.getEnvValuePP());
		}catch (JrafDomainException e) {
			log.fatal(e);
		}
		return isPhaseTest;
	}
}
