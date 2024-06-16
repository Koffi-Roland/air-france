package com.afklm.rigui.spring.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.services.RefPreferenceService;
import com.afklm.rigui.spring.rest.resources.RefPreferenceDataKey;
import com.afklm.rigui.spring.rest.resources.RefPreferenceKeyTypeResource;
import com.afklm.rigui.spring.rest.resources.RefPreferenceTypeResource;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;

@RestController
@RequestMapping("/ref/preference")
public class RefPreferenceController {
	
	@Autowired
	private Mapper dozerBeanMapper;
	
	@Autowired
	private RefPreferenceService refPreferenceService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/types", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceTypeResource> getPreferenceTypes() throws JrafDomainException {
		List<RefPreferenceTypeResource> result = new ArrayList<>();
		List<RefPreferenceTypeDTO> preferenceTypeDTOs = refPreferenceService.findPreferenceTypes();
		for (RefPreferenceTypeDTO dto : preferenceTypeDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceTypeResource.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/types/{code}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceTypeResource> getPreferenceTypeByCode(@PathVariable("code") String code) throws JrafDomainException {
		List<RefPreferenceTypeResource> result = new ArrayList<>();
		boolean isPreferenceTypeCorrect = refPreferenceService.isPreferenceTypeCorrect(code.toUpperCase());
		if (!isPreferenceTypeCorrect) return result;
		List<RefPreferenceTypeDTO> preferenceTypeDTOs = refPreferenceService.findPreferenceTypeByCode(code.toUpperCase());
		for (RefPreferenceTypeDTO dto : preferenceTypeDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceTypeResource.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keys", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceKeyTypeResource> getPreferenceKeys() throws JrafDomainException {
		List<RefPreferenceKeyTypeResource> result = new ArrayList<>();
		List<RefPreferenceKeyTypeDTO> preferenceKeyTypeDTOs = refPreferenceService.findKeys();
		for (RefPreferenceKeyTypeDTO dto : preferenceKeyTypeDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceKeyTypeResource.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keys/{type}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceKeyTypeResource> getPreferenceKeysByType(@PathVariable("type") String type) throws JrafDomainException {
		List<RefPreferenceKeyTypeResource> result = new ArrayList<>();
		boolean isPreferenceTypeCorrect = refPreferenceService.isPreferenceTypeCorrect(type.toUpperCase());
		if (!isPreferenceTypeCorrect) return result;
		List<RefPreferenceKeyTypeDTO> preferenceKeyTypeDTOs = refPreferenceService.findKeysByCode(type.toUpperCase());
		for (RefPreferenceKeyTypeDTO dto : preferenceKeyTypeDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceKeyTypeResource.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keys/labels", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceDataKey> getPreferenceDataKeys() throws JrafDomainException {
		List<RefPreferenceDataKey> result = new ArrayList<>();
		List<RefPreferenceDataKeyDTO> preferenceDataKeyDTOs = refPreferenceService.findDataKeys();
		for (RefPreferenceDataKeyDTO dto : preferenceDataKeyDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceDataKey.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keys/labels/{code}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceDataKey> getPreferenceDataKeysByCode(@PathVariable("code") String code) throws JrafDomainException {
		List<RefPreferenceDataKey> result = new ArrayList<>();
		boolean isCodeCorrect = refPreferenceService.isCodeCorrect(code.toUpperCase());
		if (!isCodeCorrect) return result;
		List<RefPreferenceDataKeyDTO> preferenceDataKeyDTOs = refPreferenceService.findDataKeysByCode(code.toUpperCase());
		for (RefPreferenceDataKeyDTO dto : preferenceDataKeyDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceDataKey.class));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keys/{normalizedKey}/labels", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
	public List<RefPreferenceDataKey> getPreferenceDataKeysByNormalizedKey(@PathVariable("normalizedKey") String key) throws JrafDomainException {
		List<RefPreferenceDataKey> result = new ArrayList<>();
		List<RefPreferenceDataKeyDTO> preferenceDataKeyDTOs = refPreferenceService.findDataKeysByNormalizedKey(key);
		for (RefPreferenceDataKeyDTO dto : preferenceDataKeyDTOs) {
			result.add(dozerBeanMapper.map(dto, RefPreferenceDataKey.class));
		}
		return result;
	}
}
