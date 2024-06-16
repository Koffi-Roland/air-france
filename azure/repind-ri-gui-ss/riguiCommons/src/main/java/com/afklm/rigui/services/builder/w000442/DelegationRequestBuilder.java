package com.afklm.rigui.services.builder.w000442;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.AccountDelegationDataRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.Delegate;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.DelegationData;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.Delegator;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.requests.ModelDelegationRequest;
import com.afklm.rigui.services.builder.RequestType;

@Component
public class DelegationRequestBuilder extends W000442RequestBuilder {

    @Override
    public CreateUpdateIndividualRequest buildCreateRequest(final String id, final Object model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CreateUpdateIndividualRequest buildUpdateRequest(final String id, final Object model) {
        CreateUpdateIndividualRequest request = null;
        request = mergeCommonRequestElements(id);
        AccountDelegationDataRequest delegationDataRequest = getAccountDelegationDataRequest(RequestType.UPDATE,
                        (ModelDelegationRequest) model);
        request.setAccountDelegationDataRequest(delegationDataRequest);
        return request;
    }

    @Override
    public CreateUpdateIndividualRequest buildDeleteRequest(final String id, final Object model) {
        throw new UnsupportedOperationException();
    }

    /**
     *  Create a AccountDelegationDataRequest accoding to the http verb and requestbody
     *
     * @param type RequestType
     * @param modelDelegationData ModelDelegationRequest (request body)
     * @return AccountDelegationDataRequest
     */
    private AccountDelegationDataRequest getAccountDelegationDataRequest(RequestType type,
                    ModelDelegationRequest modelDelegationData) {
        AccountDelegationDataRequest delegationDataRequest = new AccountDelegationDataRequest();

        String gin = modelDelegationData.getGin();
        String delegateId = modelDelegationData.getDelegate();
        String delegatorId = modelDelegationData.getDelegator();
        String action = modelDelegationData.getStatus();
        String typeDelegation = modelDelegationData.getType();

        Delegate delegate = new Delegate();
        Delegator delegator = new Delegator();

        if(type == RequestType.UPDATE) {
            // the action is done to the opposite individual : delegate to delegator OR delegator to delegate
            if(gin.equals(delegateId)) {
                delegator.setDelegationData(createDelegationData(delegatorId, action, typeDelegation));
                delegationDataRequest.getDelegator().add(delegator);
            } else if (gin.equals(delegatorId)) {
                delegate.setDelegationData(createDelegationData(delegateId, action, typeDelegation));
                delegationDataRequest.getDelegate().add(delegate);
            }
        }

        return delegationDataRequest;
    }

    /**
     * create a DelegationData Object according to GIN, ACTION and TYPE of a delegation
     *
     * @param gin of the individual doing the action
     * @param action value for the new status of a delegation
     * @param type of the delegation
     * @return DelegationData
     */
    private DelegationData createDelegationData(String gin, String action, String type) {
        DelegationData data = new DelegationData();
        data.setGin(gin);
        data.setDelegationAction(action);
        data.setDelegationType(type);
        return data;
    }

}
