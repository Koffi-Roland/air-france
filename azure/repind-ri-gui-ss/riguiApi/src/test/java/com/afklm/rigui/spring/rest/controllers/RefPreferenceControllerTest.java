package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.services.RefPreferenceService;
import com.afklm.rigui.spring.configuration.SecurityConfiguration;
import com.afklm.rigui.spring.rest.resources.RefPreferenceDataKey;
import com.afklm.rigui.spring.rest.resources.RefPreferenceKeyTypeResource;
import com.afklm.rigui.spring.rest.resources.RefPreferenceTypeResource;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RefPreferenceControllerTest {

    @Mock
    private RefPreferenceService refPreferenceService;

    @Mock
    private DozerBeanMapper dozerBeanMapper;

    @InjectMocks
    private RefPreferenceController refPreferenceController;

    @Test
    public void getPreferenceDataKeys() throws JrafDomainException {
        RefPreferenceDataKeyDTO data = new RefPreferenceDataKeyDTO();
        data.setCode("CODE");
        data.setLibelleEn("EN");
        when(refPreferenceService.findDataKeys()).thenReturn(List.of(data));

        RefPreferenceDataKey dataMapped = new RefPreferenceDataKey();
        dataMapped.setCode("CODE");
        dataMapped.setLibelleEN("EN");
        when(dozerBeanMapper.map(data, RefPreferenceDataKey.class)).thenReturn(dataMapped);
        List<RefPreferenceDataKey> result = refPreferenceController.getPreferenceDataKeys();
        assertEquals("CODE", result.get(0).getCode());
        assertEquals("EN", result.get(0).getLibelleEN());

    }

    @Test
    public void getPreferenceKeys() throws JrafDomainException {
        RefPreferenceKeyTypeDTO data = new RefPreferenceKeyTypeDTO();
        data.setKey("KEY");
        data.setCondition("CONDITION");
        when(refPreferenceService.findKeys()).thenReturn(List.of(data));

        RefPreferenceKeyTypeResource dataMapped = new RefPreferenceKeyTypeResource();
        dataMapped.setKey("KEY");
        dataMapped.setCondition("CONDITION");
        when(dozerBeanMapper.map(data, RefPreferenceKeyTypeResource.class)).thenReturn(dataMapped);
        List<RefPreferenceKeyTypeResource> result = refPreferenceController.getPreferenceKeys();
        assertEquals("KEY", result.get(0).getKey());
        assertEquals("CONDITION", result.get(0).getCondition());
    }
    @Test
    public void getPreferenceTypes() throws JrafDomainException {
        RefPreferenceTypeDTO data = new RefPreferenceTypeDTO();
        data.setCode("CODE");
        data.setLibelleEN("EN");
        when(refPreferenceService.findPreferenceTypes()).thenReturn(List.of(data));


        RefPreferenceTypeResource dataMapped = new RefPreferenceTypeResource();
        dataMapped.setCode("CODE");
        dataMapped.setLibelleEN("EN");
        when(dozerBeanMapper.map(data, RefPreferenceTypeResource.class)).thenReturn(dataMapped);
        List<RefPreferenceTypeResource> result = refPreferenceController.getPreferenceTypes();
        assertEquals("CODE", result.get(0).getCode());
        assertEquals("EN", result.get(0).getLibelleEN());
    }
    @Test
    public void getPreferenceDataKeysByCode() throws JrafDomainException {
        RefPreferenceDataKeyDTO data = new RefPreferenceDataKeyDTO();
        data.setLibelleEn("EN");
        data.setCode("CODE");
        when(refPreferenceService.isCodeCorrect("CODE")).thenReturn(true);
        when(refPreferenceService.findDataKeysByCode("CODE")).thenReturn(List.of(data));
        RefPreferenceDataKey dataMapped = new RefPreferenceDataKey();
        dataMapped.setLibelleEN("EN");
        dataMapped.setCode("CODE");
        when(dozerBeanMapper.map(data, RefPreferenceDataKey.class)).thenReturn(dataMapped);
        List<RefPreferenceDataKey> result = refPreferenceController.getPreferenceDataKeysByCode("code");
        assertEquals("CODE", result.get(0).getCode());
        assertEquals("EN", result.get(0).getLibelleEN());
    }
    @Test
    public void getPreferenceKeysByType() throws JrafDomainException {

        RefPreferenceKeyTypeDTO data = new RefPreferenceKeyTypeDTO();
        data.setCondition("CONDITION");
        data.setKey("KEY");
        when(refPreferenceService.isPreferenceTypeCorrect("TYPE")).thenReturn(true);
        when(refPreferenceService.findKeysByCode("TYPE")).thenReturn(List.of(data));
        RefPreferenceKeyTypeResource dataMapped = new RefPreferenceKeyTypeResource();
        dataMapped.setCondition("CONDITION");
        dataMapped.setKey("KEY");
        when(dozerBeanMapper.map(data, RefPreferenceKeyTypeResource.class)).thenReturn(dataMapped);
        List<RefPreferenceKeyTypeResource> result =  refPreferenceController.getPreferenceKeysByType("type");
        assertEquals("CONDITION", result.get(0).getCondition());
        assertEquals("KEY", result.get(0).getKey());
    }
    @Test
    public void getPreferenceDataKeysByNormalizedKey() throws JrafDomainException {

        RefPreferenceDataKeyDTO data = new RefPreferenceDataKeyDTO();
        data.setCode("CODE");
        data.setLibelleEn("EN");
        when(refPreferenceService.findDataKeysByNormalizedKey("key")).thenReturn(List.of(data));

        RefPreferenceDataKey dataMapped = new RefPreferenceDataKey();
        dataMapped.setCode("CODE");
        dataMapped.setLibelleEN("EN");
        when(dozerBeanMapper.map(data, RefPreferenceDataKey.class)).thenReturn(dataMapped);
        List<RefPreferenceDataKey> result = refPreferenceController.getPreferenceDataKeysByNormalizedKey("key");
        assertEquals("CODE", result.get(0).getCode());
        assertEquals("EN", result.get(0).getLibelleEN());
    }

    @Test
    public void getPreferenceTypeByCode() throws JrafDomainException {
        RefPreferenceTypeDTO data = new RefPreferenceTypeDTO();
        data.setCode("CODE");
        data.setLibelleEN("EN");
        when(refPreferenceService.isPreferenceTypeCorrect("CODE")).thenReturn(true);
        when(refPreferenceService.findPreferenceTypeByCode("CODE")).thenReturn(List.of(data));

        RefPreferenceTypeResource dataMapped = new RefPreferenceTypeResource();
        dataMapped.setCode("CODE");
        dataMapped.setLibelleEN("EN");
        when(dozerBeanMapper.map(data, RefPreferenceTypeResource.class)).thenReturn(dataMapped);
        List<RefPreferenceTypeResource> result = refPreferenceController.getPreferenceTypeByCode("code");
        assertEquals("CODE", result.get(0).getCode());
        assertEquals("EN", result.get(0).getLibelleEN());
    }
}
