package com.airfrance.repind.service.adresse.internal;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.TerminalTypeEnum;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.telecom.internal.NormalizePhoneNumberDS;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
public class TelecomDSTest extends TelecomDS {

	private final String gin = "002053750936";	
	private final TerminalTypeEnum type = TerminalTypeEnum.MOBILE;
	private TelecomDS telecomDS;
	
	@Before
	public void setUp() {
		normalizePhoneNumberDS = EasyMock.createMock(NormalizePhoneNumberDS.class);		
		individuDS = EasyMock.createMock(IndividuDS.class);
		telecomsRepository = EasyMock.createMock(TelecomsRepository.class);
		telecomDS = (TelecomDS) EasyMock.createMock(this.getClass().getSuperclass());		
	}
	
	/* ######################################################################## */
	/* # normalizePhoneNumberTelecomsDTOList								  # */
	/* ######################################################################## */
	
//	@Test TODO
	public void testNormalizePhoneNumberTelecomsDTOListValid() throws JrafDomainException {
		
		TelecomsDTO telecomsDTO = new TelecomsDTO();
		telecomsDTO.setCountryCode("33");
		telecomsDTO.setSnumero("0141566118");
		
		List<TelecomsDTO> telecomsDTOList = new ArrayList<TelecomsDTO>();
		telecomsDTOList.add(telecomsDTO);
		telecomsDTOList.add(telecomsDTO);
		
		telecomsDTOList = normalizePhoneNumber(telecomsDTOList);
		
		for(TelecomsDTO telecomsDTOResult : telecomsDTOList) {
			Assert.assertNotNull(telecomsDTOResult.getSnorm_inter_country_code());
			Assert.assertNotNull(telecomsDTOResult.getSnorm_inter_phone_number());
			Assert.assertNotNull(telecomsDTOResult.getSnorm_nat_phone_number());
			Assert.assertNotNull(telecomsDTOResult.getSnorm_terminal_type_detail());
		}
		
    }
	
	/* ######################################################################## */
	/* # normalizePhoneNumberTelecomsDTO									  # */
	/* ######################################################################## */
	
//	@Test TODO
	public void testNormalizePhoneNumberTelecomsDTOValid() throws JrafDomainException {
		
		TelecomsDTO telecomsDTO = new TelecomsDTO();
		telecomsDTO.setCountryCode("33");
		telecomsDTO.setSnumero("0141566118");
		
		telecomsDTO = normalizePhoneNumber(telecomsDTO);
		
		Assert.assertNotNull(telecomsDTO.getSnorm_inter_country_code());
		Assert.assertNotNull(telecomsDTO.getSnorm_inter_phone_number());
		Assert.assertNotNull(telecomsDTO.getSnorm_nat_phone_number());
		Assert.assertNotNull(telecomsDTO.getSnorm_terminal_type_detail());
		
    }
	
	/* ######################################################################## */
	/* # checkMandatoryFields												  # */
	/* ######################################################################## */
	
	@Test
	public void testCheckMandatoryFields() throws JrafDomainException {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());
		telecomDTO.setCountryCode("33");
		telecomDTO.setSnumero("0123456789");
		telecomDTOList.add(telecomDTO);
		
		checkMandatoryFields(telecomDTOList);
	}
	
	@Test(expected=MissingParameterException.class)
	public void testCheckMandatoryFieldsFailed() throws JrafDomainException {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTOList.add(telecomDTO);
		
		checkMandatoryFields(telecomDTOList);
	}
	
	@Test
	public void testCheckMandatoryFieldsWithUsageCode() throws JrafDomainException {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());
		telecomDTO.setScode_medium(MediumCodeEnum.BUSINESS.toString());
		telecomDTO.setCountryCode("33");
		telecomDTO.setSnumero("0123456789");
		telecomDTOList.add(telecomDTO);
		
		checkMandatoryFieldsWithUsageCode(telecomDTOList);
	}
	
	@Test(expected=MissingParameterException.class)
	public void testCheckMandatoryFieldsWithUsageCodeFailed() throws JrafDomainException {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTOList.add(telecomDTO);
		
		checkMandatoryFieldsWithUsageCode(telecomDTOList);
	}
	
	/* ######################################################################## */
	/* # countFiltered														  # */
	/* ######################################################################## */
	
	@Test
	public void testCountFiltered() {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		
		int result = countFiltered(telecomDTOList, TerminalTypeEnum.FIX.toString());
	
		Assert.assertSame(3,result);
	}
	
	@Test
	public void testCountFilteredZero() {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		
		int result = countFiltered(telecomDTOList, TerminalTypeEnum.MOBILE.toString());
	
		Assert.assertSame(0,result);
	}
	
	/*
	 * NB: Medium codes can't be empty or can't be different form BUSINESS or HOME values (check done upper)
	 */
	@Test
	public void testCountFilteredByUsageCode() {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());		
		telecomDTO.setScode_medium(MediumCodeEnum.BUSINESS.toString());
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		
		int result = countFilteredByUsageCode(telecomDTOList, TerminalTypeEnum.FIX.toString(),MediumCodeEnum.BUSINESS.toString());	
		Assert.assertSame(3,result);
	}
	
	@Test
	public void testCountFilteredByUsageCodeZero() {
		
		List<TelecomsDTO> telecomDTOList = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSterminal(TerminalTypeEnum.FIX.toString());		
		telecomDTO.setScode_medium(MediumCodeEnum.BUSINESS.toString());
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		telecomDTOList.add(telecomDTO);
		
		int result = countFilteredByUsageCode(telecomDTOList, TerminalTypeEnum.FIX.toString(),MediumCodeEnum.HOME.toString());	
		Assert.assertSame(0,result);
	}
	
	/* ######################################################################## */
	/* # findLatest															  # */
	/* ######################################################################## */
	
	@Test
	public void testFindLatest() throws JrafDomainException{
		
		Telecoms telecoms = new Telecoms();
		TelecomsDTO telecomsDTO = new TelecomsDTO();
		
		EasyMock.expect(telecomsRepository.findLatest(gin, TerminalTypeEnum.FIX.toString())).andReturn(telecoms);
		EasyMock.expect(telecomsRepository.findLatest(gin, TerminalTypeEnum.MOBILE.toString())).andReturn(telecoms);
		EasyMock.replay(telecomsRepository);
		EasyMock.expect(telecomDS.findLatest(gin, type)).andReturn(telecomsDTO);		
		EasyMock.replay(telecomDS);
				
		Assert.assertEquals(2, findLatest(gin).size());
	}
	
	@Test
	public void testFindLatestIfNull() throws JrafDomainException{
		
		EasyMock.expect(telecomsRepository.findLatest(gin, TerminalTypeEnum.FIX.toString())).andReturn(null);
		EasyMock.expect(telecomsRepository.findLatest(gin, TerminalTypeEnum.MOBILE.toString())).andReturn(null);
		EasyMock.replay(telecomsRepository);
		EasyMock.expect(telecomDS.findLatest(gin, type)).andReturn(null);		
		EasyMock.replay(telecomDS);
		 
		// List should be empty if nothing returned 
		Assert.assertTrue(findLatest(gin).isEmpty());
	}	
}
