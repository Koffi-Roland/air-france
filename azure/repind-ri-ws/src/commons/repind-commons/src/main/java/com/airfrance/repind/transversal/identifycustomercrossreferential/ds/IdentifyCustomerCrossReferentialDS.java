package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades.IdentifyCustomerCrossReferentialSearchFacade;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IdentifyCustomerCrossReferential Domain Service
 * @author t950700
 *
 */
@Service
public class IdentifyCustomerCrossReferentialDS {

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/
	
	@Autowired
	private IdentifyCustomerCrossReferentialSearchFacade identifyCustomerCrossReferentialSearchFacade = null;
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * IdentifyCustomerCrossReferential - Search operation
	 * @throws SystemException 
	 */
	public ResponseDTO search(RequestDTO request) throws BusinessExceptionDTO, SystemException
	{
		ResponseDTO response = identifyCustomerCrossReferentialSearchFacade.search(request);
		return response;
	}

}
