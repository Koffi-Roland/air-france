//package com.afklm.cati.cati.spring.rest.assemblers;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//
//import org.dozer.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
//import org.springframework.stereotype.Component;
//
//import com.afklm.cati.cati.entity.User;
//import com.afklm.cati.cati.spring.rest.controllers.UserController;
//import com.afklm.cati.cati.spring.rest.resources.UserResource;
//
//@Component
//public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {
//
//	public UserResourceAssembler() {
//		super(UserController.class, UserResource.class);
//	}
//
//	@Autowired
//	private Mapper dozerBeanMapper;
//	
//	@Override
//	public UserResource toResource(User entity) {
//	
//		UserResource resource = createResourceWithId(entity.getIdUser(), entity);
//		resource.add(linkTo(UserController.class).slash(resource.getTechId()).slash("picture").withRel("picture"));
//		return resource;
//		
//	}
//
//	@Override
//    protected UserResource instantiateResource(User entity) {
//    	UserResource user = dozerBeanMapper.map(entity, UserResource.class);
//    	return user;
//    }
//	
//}
