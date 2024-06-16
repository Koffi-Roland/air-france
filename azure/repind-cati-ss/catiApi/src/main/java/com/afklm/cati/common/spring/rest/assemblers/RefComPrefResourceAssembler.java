package com.afklm.cati.common.spring.rest.assemblers;

import com.afklm.cati.common.spring.rest.controllers.RefComPrefController;
import com.afklm.cati.common.spring.rest.resources.RefComPrefResource;
import com.afklm.cati.common.entity.RefComPref;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RefComPrefResourceAssembler extends RepresentationModelAssemblerSupport<RefComPref, RefComPrefResource> {

	public RefComPrefResourceAssembler() {
		super(RefComPrefController.class, RefComPrefResource.class);
	}

	@Override
	public RefComPrefResource toModel(RefComPref entity) {
		return createModelWithId(entity.getRefComprefId(), entity);
	}
}
