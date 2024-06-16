package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dao.IdentifyCustomerCrossReferentialDAO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Return individual members - Members are used to get individual firms and agencies
 * @author t950700
 *
 */
@Service
public class ReadIndividualMembersDS extends AbstractDS{

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/
	
	@Autowired
	private IdentifyCustomerCrossReferentialDAO identifyCustomerCrossReferentialDAO = null;
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Read individual corporate
	 * @param requestDTO
	 * @return Set<CorporateDTO> individualCorporates
	 * @throws BusinessExceptionDTO 
	 */
	public Set<CorporateDTO> readIndividualCorporates(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO
	{
		Set<CorporateDTO> individualCorporates = identifyCustomerCrossReferentialDAO.readIndividualCorporates(requestDTO, responseDTO);
		return individualCorporates;
	}

	public Set<AgencyDTO> readIndividualAgencies(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO
	{
		Set<AgencyDTO> individualAgencies = identifyCustomerCrossReferentialDAO.readIndividualAgencies(requestDTO, responseDTO);
		return individualAgencies;
	}

}
