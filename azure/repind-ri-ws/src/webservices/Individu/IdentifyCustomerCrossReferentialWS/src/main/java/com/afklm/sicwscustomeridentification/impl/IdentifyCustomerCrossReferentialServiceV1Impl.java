package com.afklm.sicwscustomeridentification.impl;

import com.afklm.sicwscustomeridentification.facades.IdentifyCustomerCrossReferentialFacade;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.IdentifyCustomerCrossReferentialServiceV1;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.IdentifyCustomerCrossReferentialRequest;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(endpointInterface = "com.afklm.soa.stubs.w001345.v1.IdentifyCustomerCrossReferentialServiceV1", targetNamespace = "http://www.af-klm.com/services/passenger/IdentifyCustomerCrossReferentialService-v1/wsdl", serviceName = "IdentifyCustomerCrossReferentialService-v1", portName = "IdentifyCustomerCrossReferentialService-v1-soap11http")
public class IdentifyCustomerCrossReferentialServiceV1Impl implements
		IdentifyCustomerCrossReferentialServiceV1 {

	@Resource
	private WebServiceContext context;		
	
	
	/* =============================================== */
	/* INJECTED BEANS */
	/* =============================================== */
	
	@Autowired
	private IdentifyCustomerCrossReferentialFacade customerIdFacade;

	/* =============================================== */
	/* WEB METHODS */
	/* =============================================== */
	
	@WebMethod
	public IdentifyCustomerCrossReferentialResponse search(
			IdentifyCustomerCrossReferentialRequest request)
			throws BusinessException, SystemException {
		
		IdentifyCustomerCrossReferentialResponse response = customerIdFacade.search(request, context);
		return response;
	}
}
