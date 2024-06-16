package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.CorporateDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ProvideIdentifierDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import org.springframework.stereotype.Component;

/**
 * Builds RequestDTO instance for individual's associated firms
 * @author t950700
 *
 */
@Component
public class RequestSearchIndividualFirmDTOBuilder {

	public RequestDTO build(RequestDTO initialRequestDTO, CorporateDTO corporateDTO)
	{
		RequestDTO requestDTO = new RequestDTO();
		if((initialRequestDTO != null)
				&&	(corporateDTO != null)
				&&	(corporateDTO.getCorporateInformations() != null)
				&&	(corporateDTO.getCorporateInformations().getCorporateKey() != null))
		{
			/*
			 * Index
			 */
			requestDTO.setIndex(initialRequestDTO.getIndex());
			
			/*
			 * HQL request
			 */
			if(initialRequestDTO.getQueryMembers() != null)
			{
				requestDTO.setQueryMembers(initialRequestDTO.getQueryMembers());
			}
			
			/*
			 * Process type
			 */
			if(initialRequestDTO.getProcessType() != null)
			{
				requestDTO.setProcessType(initialRequestDTO.getProcessType());
			}
			
			/*
			 * Context
			 */
			if(initialRequestDTO.getContext() != null)
			{
				requestDTO.setContext(initialRequestDTO.getContext());
			}
			
			/*
			 * Preparing search by GIN
			 */
			ProvideIdentifierDTO provideIdentifierDTO = new ProvideIdentifierDTO();
			provideIdentifierDTO.setIdentifierType("GI");
			provideIdentifierDTO.setIdentifierValue(corporateDTO.getCorporateInformations().getCorporateKey());
			requestDTO.setProvideIdentifier(provideIdentifierDTO);
		}
		
		return requestDTO;
		
	}
}
