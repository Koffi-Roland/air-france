package com.afklm.rigui.services;

import com.afklm.rigui.dao.reference.RefPreferenceDataKeyRepository;
import com.afklm.rigui.dao.reference.RefPreferenceKeyTypeRepository;
import com.afklm.rigui.dao.reference.RefPreferenceTypeRepository;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeTransform;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;
import com.afklm.rigui.entity.reference.RefPreferenceDataKey;
import com.afklm.rigui.entity.reference.RefPreferenceKeyType;
import com.afklm.rigui.entity.reference.RefPreferenceType;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RefPreferenceServiceTest {

    @InjectMocks
    private RefPreferenceService refPreferenceService;

    @Mock
    RefPreferenceTypeRepository refPreferenceTypeRepository;

    @Mock
    RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;

    @Mock
    RefPreferenceDataKeyRepository refPreferenceDataKeyRepository;

    @Test
    public void findPreferenceTypes() throws JrafDomainException {
        RefPreferenceType ref = new RefPreferenceType();
        ref.setLibelleEN("EN");
        ref.setLibelleFR("FR");
        ref.setCode("CODE");
        when(refPreferenceTypeRepository.findAll()).thenReturn(List.of(ref));
        List<RefPreferenceTypeDTO> result = refPreferenceService.findPreferenceTypes();
        assertEquals(1, result.size());
        assertEquals("EN", result.get(0).getLibelleEN());
        assertEquals("FR", result.get(0).getLibelleFR());
        assertEquals("CODE", result.get(0).getCode());
    }

    @Test
    public void findDataKeys() throws JrafDomainException {
        RefPreferenceDataKey ref = new RefPreferenceDataKey();
        ref.setLibelleEn("EN");
        ref.setLibelleFr("FR");
        ref.setNormalizedKey("KEY");
        ref.setCode("CODE");
        when( refPreferenceDataKeyRepository.findAll()).thenReturn(List.of(ref));
        List<RefPreferenceDataKeyDTO> result = refPreferenceService.findDataKeys();
        assertEquals(1, result.size());
        assertEquals("EN", result.get(0).getLibelleEn());
        assertEquals("FR", result.get(0).getLibelleFr());
        assertEquals("CODE", result.get(0).getCode());
    }

    @Test
    public void findDataKeysByCode() throws JrafDomainException {
        RefPreferenceDataKey ref = new RefPreferenceDataKey();
        ref.setLibelleEn("EN");
        ref.setLibelleFr("FR");
        ref.setNormalizedKey("KEY");
        ref.setCode("CODE");
        when( refPreferenceDataKeyRepository.findByCode("code")).thenReturn(List.of(ref));
        List<RefPreferenceDataKeyDTO> result = refPreferenceService.findDataKeysByCode("code");
        assertEquals(1, result.size());
        assertEquals("EN", result.get(0).getLibelleEn());
        assertEquals("FR", result.get(0).getLibelleFr());
        assertEquals("CODE", result.get(0).getCode());
    }

    @Test
    public void findDataKeysByNormalizedKey() throws JrafDomainException {
        RefPreferenceDataKey ref = new RefPreferenceDataKey();
        ref.setLibelleEn("EN");
        ref.setLibelleFr("FR");
        ref.setNormalizedKey("KEY");
        ref.setCode("CODE");
        when( refPreferenceDataKeyRepository.findByNormalizedKey("key")).thenReturn(List.of(ref));
        List<RefPreferenceDataKeyDTO> result = refPreferenceService.findDataKeysByNormalizedKey("key");
        assertEquals(1, result.size());
        assertEquals("EN", result.get(0).getLibelleEn());
        assertEquals("FR", result.get(0).getLibelleFr());
        assertEquals("CODE", result.get(0).getCode());
    }

    @Test
    public void findKeys() throws JrafDomainException {
        RefPreferenceKeyType ref = new RefPreferenceKeyType();
        ref.setKey("KEY");
        ref.setCondition("CONDITION");
        when(refPreferenceKeyTypeRepository.findAll()).thenReturn(List.of(ref));
        List<RefPreferenceKeyTypeDTO> result = refPreferenceService.findKeys();
        assertEquals(1, result.size());
        assertEquals("KEY", result.get(0).getKey());
        assertEquals("CONDITION", result.get(0).getCondition());
    }

    @Test
    public void findPreferenceTypeByCode() throws JrafDomainException {
        RefPreferenceType ref = new RefPreferenceType();
        ref.setLibelleEN("EN");
        ref.setLibelleFR("FR");
        ref.setCode("CODE");
        when(refPreferenceTypeRepository.findByCode("code")).thenReturn(List.of(ref));
        List<RefPreferenceTypeDTO> result = refPreferenceService.findPreferenceTypeByCode("code");
        assertEquals(1, result.size());
        assertEquals("EN", result.get(0).getLibelleEN());
        assertEquals("FR", result.get(0).getLibelleFR());
        assertEquals("CODE", result.get(0).getCode());
    }

    @Test
    public void isCodeCorrect() {
        RefPreferenceDataKey ref = new RefPreferenceDataKey();
        ref.setLibelleEn("EN");
        ref.setLibelleFr("FR");
        ref.setNormalizedKey("KEY");
        ref.setCode("CODE");
        when( refPreferenceDataKeyRepository.findAll()).thenReturn(List.of(ref));
        boolean isOk = refPreferenceService.isCodeCorrect("CODE");
        assertTrue(isOk);
    }

    @Test
    public void isPreferenceTypeCorrect() {
        RefPreferenceType ref = new RefPreferenceType();
        ref.setLibelleEN("EN");
        ref.setLibelleFR("FR");
        ref.setCode("CODE");
        when(refPreferenceTypeRepository.findAll()).thenReturn(List.of(ref));
        boolean isOk = refPreferenceService.isPreferenceTypeCorrect("CODE");
        assertTrue(isOk);
    }

    @Test
    public void findKeysByCode() throws JrafDomainException {
        RefPreferenceKeyType ref = new RefPreferenceKeyType();
        ref.setKey("KEY");
        ref.setCondition("CONDITION");
        when(refPreferenceKeyTypeRepository.findByType("type")).thenReturn(List.of(ref));
        List<RefPreferenceKeyTypeDTO> result = refPreferenceService.findKeysByCode("type");
        assertEquals(1, result.size());
        assertEquals("KEY", result.get(0).getKey());
        assertEquals("CONDITION", result.get(0).getCondition());
    }

}
