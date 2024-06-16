package com.afklm.rigui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefPreferenceDataKeyRepository;
import com.afklm.rigui.dao.reference.RefPreferenceKeyTypeRepository;
import com.afklm.rigui.dao.reference.RefPreferenceTypeRepository;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyTransform;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeTransform;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceTypeTransform;
import com.afklm.rigui.entity.reference.RefPreferenceDataKey;
import com.afklm.rigui.entity.reference.RefPreferenceKeyType;
import com.afklm.rigui.entity.reference.RefPreferenceType;

@Service
public class RefPreferenceService {
	
	@Autowired
	RefPreferenceTypeRepository refPreferenceTypeRepository;
	
	@Autowired
	RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;
	
	@Autowired
	RefPreferenceDataKeyRepository refPreferenceDataKeyRepository;
	
	public List<RefPreferenceTypeDTO> findPreferenceTypes() throws JrafDomainException {
		List<RefPreferenceTypeDTO> result = new ArrayList<>();
		List<RefPreferenceType> entities = refPreferenceTypeRepository.findAll();
		for (RefPreferenceType entity : entities) {
			result.add(RefPreferenceTypeTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceTypeDTO> findPreferenceTypeByCode(String code) throws JrafDomainException {
		List<RefPreferenceTypeDTO> result = new ArrayList<>();
		List<RefPreferenceType> entities = refPreferenceTypeRepository.findByCode(code);
		for (RefPreferenceType entity : entities) {
			result.add(RefPreferenceTypeTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceKeyTypeDTO> findKeys() throws JrafDomainException {
		List<RefPreferenceKeyTypeDTO> result = new ArrayList<>();
		List<RefPreferenceKeyType> entities = refPreferenceKeyTypeRepository.findAll();
		for (RefPreferenceKeyType entity : entities) {
			result.add(RefPreferenceKeyTypeTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceKeyTypeDTO> findKeysByCode(String type) throws JrafDomainException {
		List<RefPreferenceKeyTypeDTO> result = new ArrayList<>();
		List<RefPreferenceKeyType> entities = refPreferenceKeyTypeRepository.findByType(type);
		for (RefPreferenceKeyType entity : entities) {
			result.add(RefPreferenceKeyTypeTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceDataKeyDTO> findDataKeys() throws JrafDomainException {
		List<RefPreferenceDataKeyDTO> result = new ArrayList<>();
		List<RefPreferenceDataKey> entities = refPreferenceDataKeyRepository.findAll();
		for (RefPreferenceDataKey entity : entities) {
			result.add(RefPreferenceDataKeyTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceDataKeyDTO> findDataKeysByCode(String code) throws JrafDomainException {
		List<RefPreferenceDataKeyDTO> result = new ArrayList<>();
		List<RefPreferenceDataKey> entities = refPreferenceDataKeyRepository.findByCode(code);
		for (RefPreferenceDataKey entity : entities) {
			result.add(RefPreferenceDataKeyTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public List<RefPreferenceDataKeyDTO> findDataKeysByNormalizedKey(String key) throws JrafDomainException {
		List<RefPreferenceDataKeyDTO> result = new ArrayList<>();
		List<RefPreferenceDataKey> entities = refPreferenceDataKeyRepository.findByNormalizedKey(key);
		for (RefPreferenceDataKey entity : entities) {
			result.add(RefPreferenceDataKeyTransform.bo2DtoLight(entity));
		}
		return result;
	}
	
	public boolean isPreferenceTypeCorrect(String type) {
		boolean result = false;
		List<RefPreferenceType> entities = refPreferenceTypeRepository.findAll();
		for (RefPreferenceType entity : entities) {
			if (entity.getCode().equals(type)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isCodeCorrect(String type) {
		boolean result = false;
		List<RefPreferenceDataKey> entities = refPreferenceDataKeyRepository.findAll();
		for (RefPreferenceDataKey entity : entities) {
			if (entity.getCode().equals(type)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
