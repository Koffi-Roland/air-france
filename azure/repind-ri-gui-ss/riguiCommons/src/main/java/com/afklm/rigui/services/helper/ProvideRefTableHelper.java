package com.afklm.rigui.services.helper;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.wrapper.ref.WrapperRefTable;
import com.afklm.soa.stubs.w001588.v1_0_1.commontype.Requestor;
import com.afklm.soa.stubs.w001588.v1_0_1.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v1_0_1.data.ProvideIndividualReferenceTableResponse;

@Component
public class ProvideRefTableHelper {

	@Autowired
	public DozerBeanMapper dozerBeanMapper;

	public ProvideIndividualReferenceTableRequest createProvideRequestForTable(String table) {

		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName(table);
		request.setRequestor(getRequestorForRequest());
		return request;
	}

	public Requestor getRequestorForRequest() {
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("REPIND");
		return requestor;
	}

	public WrapperRefTable transformDataFromWsToWrapper(ProvideIndividualReferenceTableResponse responseFromWs) {
		WrapperRefTable wrapper = new WrapperRefTable();
		wrapper = dozerBeanMapper.map(responseFromWs, WrapperRefTable.class);
		return wrapper;
	}

}
