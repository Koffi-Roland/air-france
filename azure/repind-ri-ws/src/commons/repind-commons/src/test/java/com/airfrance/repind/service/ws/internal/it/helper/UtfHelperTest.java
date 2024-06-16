package com.airfrance.repind.service.ws.internal.it.helper;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.UtfDatasDTO;
import com.airfrance.repind.dto.ws.UtfRequestDTO;
import com.airfrance.repind.dto.ws.utfDataDTO;
import com.airfrance.repind.service.ws.internal.helpers.UtfHelper;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.service.utf.internal.UtfDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.lang.StringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class UtfHelperTest {

	@Autowired
	private UtfHelper utfHelper;

	@Test
	public void generateDTOupdateOrDeleteInvalidParameter1() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_generateDTOupdateOrDelete", com.airfrance.repind.dto.ws.UtfDTO.class);
		method.setAccessible(true);
		com.airfrance.repind.dto.ws.UtfDTO utf = null;
		try {
			method.invoke(utfHelper, utf);
			Assert.fail();

		} catch (InvocationTargetException e) {
			if (!(e.getTargetException() instanceof InvalidParameterException)) {
				Assert.fail();
			}
		}

	}

	@Test
	public void generateDTOupdateOrDeleteInvalidParameter2() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_generateDTOupdateOrDelete", com.airfrance.repind.dto.ws.UtfDTO.class);
		method.setAccessible(true);
		com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		try {
			method.invoke(utfHelper, utf);
			Assert.fail();

		} catch (InvocationTargetException e) {
			if (!(e.getTargetException() instanceof InvalidParameterException)) {
				Assert.fail();
			}
		}

	}

	@Test
	public void generateDTOupdateOrDeleteInvalidParameter3() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, JrafDomainException, UtfException{
		final Method method = utfHelper.getClass().getDeclaredMethod("_generateDTOupdateOrDelete", com.airfrance.repind.dto.ws.UtfDTO.class);
		method.setAccessible(true);
		com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setId(Long.getLong("toto"));
		try{
			method.invoke(utfHelper, utf);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(e.getTargetException() instanceof InvalidParameterException)){
				Assert.fail();
			}
		}
	}

	@Test
	public void generateDTOupdateOrDeleteNormalCase() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException{
		final Method method = utfHelper.getClass().getDeclaredMethod("_generateDTOupdateOrDelete", com.airfrance.repind.dto.ws.UtfDTO.class);
		method.setAccessible(true);
		com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setId(Long.parseLong("1"));
		UtfDTO utfDto = (UtfDTO)method.invoke(utfHelper, utf);
		Assert.assertEquals(new Long(1), utfDto.getUtfId());

	}

	@Test
	public void fillUtfDataDtoInvalidParameter1() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfDataDto", UtfDatasDTO.class, boolean.class);
		method.setAccessible(true);
		final UtfDatasDTO utfDatas = null;
		final boolean isCreate = false;
		Object o = method.invoke(utfHelper, utfDatas, isCreate);
		Assert.assertNull(o);
	}

	@Test
	public void fillUtfINDbySCMissingIndividualInformations() throws NoSuchMethodException, SecurityException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfINDbySC", UtfRequestDTO.class,
				String.class, String.class, String.class);
		method.setAccessible(true);
		UtfRequestDTO utfRequest = null;
		String gin = null;
		UtfRequestDTO result = (UtfRequestDTO) method.invoke(utfHelper, utfRequest, "", "", gin);
		Assert.assertEquals(utfRequest, result);
	}

	@Test
	public void fillUtfINDbySCEmptyIndividualInformation() throws NoSuchMethodException, SecurityException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfINDbySC", UtfRequestDTO.class,
				String.class, String.class, String.class);
		method.setAccessible(true);
		UtfRequestDTO utfRequest = null;
		String gin = null;
		UtfRequestDTO result = (UtfRequestDTO) method.invoke(utfHelper, utfRequest, "", "", gin);
		Assert.assertEquals(utfRequest, result);
	}

	@Test
	public void fillUtfINDbySCUtfRequestNotEmpty() throws NoSuchMethodException, SecurityException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfINDbySC", UtfRequestDTO.class,
				String.class, String.class, String.class);
		method.setAccessible(true);
		UtfRequestDTO utfRequest = new UtfRequestDTO();
		List<com.airfrance.repind.dto.ws.UtfDTO> utfDTO = new ArrayList<com.airfrance.repind.dto.ws.UtfDTO>();
		utfRequest.setUtfDTO(utfDTO);
		com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setType("IND");
		utfRequest.getUtfDTO().add(utf);
		String gin = null;
		UtfRequestDTO result = (UtfRequestDTO) method.invoke(utfHelper, utfRequest, "toto", "tata", gin);
		Assert.assertEquals(utfRequest, result);
	}

	@Test
	public void fillUtfINDbySCUtfRequestNormalCreate() throws NoSuchMethodException, SecurityException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, JrafDomainException {

		UtfDS utfDSMock = EasyMock.createNiceMock(UtfDS.class);
		EasyMock.expect(utfDSMock.findByExample(EasyMock.anyObject(UtfDTO.class))).andReturn(new ArrayList<UtfDTO>());
		EasyMock.replay(utfDSMock);
		ReflectionTestUtils.setField(utfHelper, "utfDS", utfDSMock);

		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfINDbySC", UtfRequestDTO.class,
				String.class, String.class, String.class);
		method.setAccessible(true);

		UtfRequestDTO utfRequest = null;
		String gin = null;
		UtfRequestDTO result = (UtfRequestDTO) method.invoke(utfHelper, utfRequest, "tata", "tata", gin);
		Assert.assertEquals(1, result.getUtfDTO().size());
		com.airfrance.repind.dto.ws.UtfDTO utf = result.getUtfDTO().get(0);
		Assert.assertEquals("IND", utf.getType());
		Assert.assertEquals(null, utf.getId());
		utfDataDTO utfData = utf.getUtfDatasDTO().getUtfDataDTO().get(0);
		Assert.assertTrue(utfData.getKey().equals("FIRST_NAME") || utfData.getKey().equals("LAST_NAME"));
		Assert.assertEquals("tata", utfData.getValue());

	}

	@Test
	public void processMissingParameters() throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, JrafDomainException, UtfException {
		UtfDS utfDSMock = EasyMock.createNiceMock(UtfDS.class);
		List<UtfDTO> mockresult = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setUtfId(new Long(42));
		mockresult.add(utfDto);
		EasyMock.expect(utfDSMock.findByExample(EasyMock.anyObject(UtfDTO.class))).andReturn(mockresult);
		EasyMock.replay(utfDSMock);
		ReflectionTestUtils.setField(utfHelper, "utfDS", utfDSMock);
		try {
			utfHelper.processIndividualOnUTF8(null, null, null, "toto", null);
			Assert.fail();
		} catch (InvalidParameterException e) {
		}
	}

	@Test
	public void fillUtfDataDtoNormalUpdate() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfDataDto", UtfDatasDTO.class, boolean.class);
		method.setAccessible(true);
		final UtfDatasDTO utfDatas = new UtfDatasDTO();
		List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
		utfDatas.setUtfDataDTO(utfDataDTO);
		utfDataDTO utfData = new utfDataDTO();
		utfData.setKey("FIRST_NAME");
		utfData.setValue("TOTO");
		utfDatas.getUtfDataDTO().add(utfData);
		final boolean isCreate = false;
		Set<UtfDataDTO> setUtfDataDto = (HashSet<UtfDataDTO>) method.invoke(utfHelper, utfDatas, isCreate);
		Assert.assertEquals(1, setUtfDataDto.size());
		for(UtfDataDTO utfDataDto : setUtfDataDto) {
			Assert.assertEquals("FIRST_NAME", utfDataDto.getKey());
			Assert.assertEquals("TOTO", utfDataDto.getValue());
		}
	}

	@Test
	public void fillUtfDataDtoNormalCreate() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("_fillUtfDataDto", UtfDatasDTO.class, boolean.class);
		method.setAccessible(true);
		final UtfDatasDTO utfDatas = new UtfDatasDTO();
		List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
		utfDatas.setUtfDataDTO(utfDataDTO);
		utfDataDTO utfData = new utfDataDTO();
		utfData.setKey("FIRST_NAME");
		utfData.setValue("TOTO");
		utfDatas.getUtfDataDTO().add(utfData);
		final boolean isCreate = true;
		Set<UtfDataDTO> setUtfDataDto = (HashSet<UtfDataDTO>) method.invoke(utfHelper, utfDatas, isCreate);
		Assert.assertEquals(1, setUtfDataDto.size());
		for (UtfDataDTO utfDataDto : setUtfDataDto) {
			Assert.assertEquals("FIRST_NAME", utfDataDto.getKey());
			Assert.assertEquals("TOTO", utfDataDto.getValue());
		}
	}

	@Test
	public void normalizeSCNamesToLatin1EmptyParamter() {
		IndividualInformationsDTO individual = new IndividualInformationsDTO();
		UtfHelper.NormalizeSCNames(individual);
		Assert.assertTrue(StringUtils.isBlank(individual.getFirstNameSC()));
		Assert.assertTrue(StringUtils.isBlank(individual.getLastNameSC()));
		Assert.assertTrue(StringUtils.isBlank(individual.getMiddleNameSC()));
	}

	@Test
	public void normalizeSCNamesToLatin1ParameterSet() {
		IndividualInformationsDTO individual = new IndividualInformationsDTO();
		String name = "Pîérrê-Etïennë L'Hômme!!$^";
		individual.setFirstNameSC(name);
		UtfHelper.NormalizeSCNames(individual);
		Assert.assertNotEquals(name, StringUtils.isBlank(individual.getFirstNameSC()));
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void normalizeSCNamesToLatin1MissingParameter() {
		UtfHelper.NormalizeSCNames(null);
	}

	@Test
	public void findIndividuByGin() throws IllegalAccessException, IllegalArgumentException,
	NoSuchMethodException, SecurityException, JrafDomainException, UtfException, InvocationTargetException {
		
		final Method method = utfHelper.getClass().getDeclaredMethod("findIndividuByGin", String.class);
		method.setAccessible(true);
		final String gin = "400243677233";

		List<UtfDTO> setUtfDataDto = (ArrayList<UtfDTO>) method.invoke(utfHelper, gin);
		Assert.assertEquals(1, setUtfDataDto.size());
		for (UtfDTO utfDTO : setUtfDataDto) {
			Assert.assertEquals("IND", utfDTO.getType());
			Assert.assertEquals("400243677233", utfDTO.getGin());
		}
	}

}
