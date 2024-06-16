package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InferredHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/*
	 * public WrapperIndividualInferred sortInferreds(WrapperIndividualInferred
	 * wrapperIndividualInferred, HashSet<Inferred> inferreds) {
	 * List<ModelInferred> modelInferreds = new ArrayList<>(); if (inferreds !=
	 * null) { for (Inferred inferred : inferreds) { ModelInferred modelInferred
	 * = new ModelInferred(); modelInferred = dozerBeanMapper.map(inferred,
	 * ModelInferred.class); modelInferreds.add(modelInferred); } }
	 * wrapperIndividualInferred.items = modelInferreds; return
	 * wrapperIndividualInferred; }
	 */

}
