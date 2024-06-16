package com.afklm.repind.v7.provideindividualdata.it.helpers;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v7.provideindividualdata.helpers.UtfHelper;
import com.afklm.soa.stubs.w000418.v7.response.UtfResponse;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.Utf;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.UtfData;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.UtfDatas;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.service.utf.internal.UtfDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.lang3.StringUtils;
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
	public void getByGinListEmpty() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, JrafDomainException, UtfException {

		final List<UtfDTO> mockReturnValue = new ArrayList<>();
		final UtfDS _mockUtfDS = EasyMock.createNiceMock(UtfDS.class);
		EasyMock.expect(_mockUtfDS.findByGin("1")).andReturn(mockReturnValue);
		EasyMock.replay(_mockUtfDS);
		ReflectionTestUtils.setField(utfHelper, "utfDS", _mockUtfDS);
		final UtfResponse response = utfHelper.getByGin("1");
		Assert.assertNull(response);
	}

	/*@Test
	public void getByGinNormalCase() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, JrafDomainException, UtfException {

		final List<UtfDTO> mockReturnValue = new ArrayList<>();
		for (int i = 0; i < IUtfDS.MAX_NUMBER_UTF; i++) {
			final UtfDTO utfDto = new UtfDTO();
			utfDto.setUtfId(i);
			utfDto.setType("IND");
			final Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();
			final UtfDataDTO utfDataDTO = new UtfDataDTO();
			utfDataDTO.setKey("tata");
			utfDataDTO.setValue("titi");
			setUtfDataDTO.add(utfDataDTO);
			utfDto.setUtfDataDTO(setUtfDataDTO);
			mockReturnValue.add(utfDto);
		}

		final IUtfDS _mockUtfDS = EasyMock.createNiceMock(IUtfDS.class);
		EasyMock.expect(_mockUtfDS.findByGin("1")).andReturn(mockReturnValue);
		EasyMock.replay(_mockUtfDS);

		ReflectionTestUtils.setField(utfHelper, "utfDS", _mockUtfDS);
		final UtfResponse response = utfHelper.getByGin("1");
		Assert.assertEquals(IUtfDS.MAX_NUMBER_UTF, response.getUtf().size());

		for (final Utf utf : response.getUtf()) {
			Assert.assertEquals("IND", utf.getType());
			Assert.assertTrue(StringUtils.isNotBlank(utf.getId()));
			Assert.assertEquals(1, utf.getUtfDatas().getUtfData().size());
			for (final UtfData utfData : utf.getUtfDatas().getUtfData()) {
				Assert.assertEquals("tata", utfData.getKey());
				Assert.assertEquals("titi", utfData.getValue());
			}
		}
	}*/
	
	@Test
	public void getByGinNormalCase() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, JrafDomainException, UtfException {

		/*final List<UtfDTO> mockReturnValue = new ArrayList<>();
		for (int i = 0; i < IUtfDS.MAX_NUMBER_UTF; i++) {
			final UtfDTO utfDto = new UtfDTO();
			utfDto.setUtfId(i);
			utfDto.setType("IND");
			final Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();
			final UtfDataDTO utfDataDTO = new UtfDataDTO();
			utfDataDTO.setKey("tata");
			utfDataDTO.setValue("titi");
			setUtfDataDTO.add(utfDataDTO);
			utfDto.setUtfDataDTO(setUtfDataDTO);
			mockReturnValue.add(utfDto);
		}

		final IUtfDS _mockUtfDS = EasyMock.createNiceMock(IUtfDS.class);
		EasyMock.expect(_mockUtfDS.findByGin("1")).andReturn(mockReturnValue);
		EasyMock.replay(_mockUtfDS);

		ReflectionTestUtils.setField(utfHelper, "utfDS", _mockUtfDS);
		final UtfResponse response = utfHelper.getByGin("1");
		Assert.assertEquals(IUtfDS.MAX_NUMBER_UTF, response.getUtf().size());*/
		
		final UtfResponse response = utfHelper.getByGin("910000059171");

		for (final Utf utf : response.getUtf()) {
			Assert.assertEquals("IND", utf.getType());
			Assert.assertTrue(StringUtils.isNotBlank(utf.getId()));
			Assert.assertEquals(2, utf.getUtfDatas().getUtfData().size());
			for (final UtfData utfData : utf.getUtfDatas().getUtfData()) {
				if (utfData.getKey().equalsIgnoreCase("LAST_NAME")) {
					Assert.assertEquals("YDSHJQDHBM", utfData.getValue());
				}
				if (utfData.getKey().equalsIgnoreCase("FIRST_NAME")) {
					Assert.assertEquals("GIRVGRQGBM", utfData.getValue());
				}
			}
		}
	}

	@Test
	public void getSignatureInputNull() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException,
			SecurityException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("getSignature", UtfDTO.class);
		method.setAccessible(true);
		final UtfDTO utfDto = null;
		final UtfDatas utfDatas = (UtfDatas) method.invoke(utfHelper, utfDto);
		Assert.assertNull(utfDatas);
	}

	@Test
	public void getUtfDatasEmpty() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException,
			SecurityException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("getUtfDatas", UtfDTO.class);
		method.setAccessible(true);
		final UtfDTO utfDto = new UtfDTO();
		final Set<UtfDataDTO> setUtfDataDto = new HashSet<>();
		utfDto.setUtfDataDTO(setUtfDataDto);
		final UtfDatas utfDatas = (UtfDatas) method.invoke(utfHelper, utfDto);
		Assert.assertNull(utfDatas);
	}

	@Test
	public void getUtfDatasInputNull1() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException,
			SecurityException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("getUtfDatas", UtfDTO.class);
		method.setAccessible(true);
		final UtfDTO utfDto = new UtfDTO();
		final UtfDatas utfDatas = (UtfDatas) method.invoke(utfHelper, utfDto);
		Assert.assertNull(utfDatas);
	}

	@Test
	public void getUtfDatasInputNull2() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException,
			SecurityException, InvocationTargetException {
		final Method method = utfHelper.getClass().getDeclaredMethod("getUtfDatas", UtfDTO.class);
		method.setAccessible(true);
		final UtfDTO utfDto = null;
		final UtfDatas utfDatas = (UtfDatas) method.invoke(utfHelper, utfDto);
		Assert.assertNull(utfDatas);
	}

	@Test
	public void getUtfDatasNormalCase() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		final Method method = utfHelper.getClass().getDeclaredMethod("getUtfDatas", UtfDTO.class);
		method.setAccessible(true);
		final UtfDTO utfDto = new UtfDTO();

		final Set<UtfDataDTO> setUtfDataDto = new HashSet<>();
		for (int i = 0; i < UtfDS.MAX_NUMBER_UTFDATA; i++) {
			final UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("tata" + i);
			utfDataDto.setValue("toto" + i);
			setUtfDataDto.add(utfDataDto);
		}
		utfDto.setUtfDataDTO(setUtfDataDto);
		final UtfDatas utfDatas = (UtfDatas) method.invoke(utfHelper, utfDto);

		final List<UtfData> listUtfData = utfDatas.getUtfData();
		Assert.assertEquals(UtfDS.MAX_NUMBER_UTFDATA, listUtfData.size());
		for (final UtfData utfData : listUtfData) {
			Assert.assertTrue(utfData.getKey().contains("tata"));
		}
	}
}
