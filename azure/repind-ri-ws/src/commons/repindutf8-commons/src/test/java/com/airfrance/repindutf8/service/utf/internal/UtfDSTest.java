package com.airfrance.repindutf8.service.utf.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.config.WebTestConfig;
import com.airfrance.repindutf8.dao.RefUtfKeyTypeRepository;
import com.airfrance.repindutf8.dao.RefUtfTypeRepository;
import com.airfrance.repindutf8.dao.UtfRepository;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.entity.UtfData;
import com.airfrance.repindutf8.dao.UtfDataRepository;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.entity.RefUtfType;
import com.airfrance.repindutf8.entity.Utf;
import org.apache.commons.lang.StringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepindUtf8")
public class UtfDSTest {
	
	@Autowired
	public UtfDS utfDS;

	@PersistenceContext(unitName="entityManagerFactoryRepindUtf8")
	private EntityManager entityManagerSicUtf8;
	
	@Autowired
	private RefUtfTypeRepository refUtfTypeRepository;
	
	@Test
	public void checkListValidityMissingParameter() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		UtfDS utfDS = new UtfDS();
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		List<UtfDTO> listUtfDto = null;
		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		method.invoke(utfDS, listUtfDto, gin, false);
		//If not crashing
	}


	@Test
	public void checkListValidityUtfListUtfDataNull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);

		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);

		List<UtfDTO> listUtfDto = new ArrayList<>();
		for(int i = 0; i < UtfDS.MAX_NUMBER_UTF; i++){
			UtfDTO utfDto = new UtfDTO();
			utfDto.setType("IND");
			listUtfDto.add(utfDto);
		}
		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTFDATA_LIST_CANNOT_BE_NULL.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void checkListValidityTypeNotValid() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(false);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(false);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		List<UtfDTO> listUtfDto = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> listUtfData = new HashSet<>();
		for(int i = 0; i < UtfDS.MAX_NUMBER_UTFDATA; i++){
			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("TOTO");
			utfDataDto.setValue("TATA");
			listUtfData.add(utfDataDto);
			utfDto.setUtfDataDTO(listUtfData);
		}
		listUtfDto.add(utfDto);

		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTF_TYPE_DOESNT_EXIST.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void checkListValidityKeyNotValid() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(false);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);
		
		List<UtfDTO> listUtfDto = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		Set<UtfDataDTO> listUtfData = new HashSet<>();
		for(int i = 0; i < UtfDS.MAX_NUMBER_UTFDATA; i++){
			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("TOTO");
			utfDataDto.setValue("TATA");
			listUtfData.add(utfDataDto);
			utfDto.setUtfDataDTO(listUtfData);
		}
		listUtfDto.add(utfDto);

		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTFDATA_KEY_DOESNT_EXIST_FOR_TYPE.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void checkListValidityTooManyINDTypeBestEffort() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);

		List<UtfDTO> listUtfDto = new ArrayList<>();

		for(int i = 0; i < 3; i++){
			UtfDTO utfDto = new UtfDTO();
			utfDto.setType("IND");
			Set<UtfDataDTO> listUtfData = new HashSet<>();

			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("FIRST_NAME");
			utfDataDto.setValue("TATA");
			listUtfData.add(utfDataDto);

			UtfDataDTO utfDataDto2 = new UtfDataDTO();
			utfDataDto2.setKey("LAST_NAME");
			utfDataDto2.setValue("TATA");
			listUtfData.add(utfDataDto2);
			utfDto.setUtfDataDTO(listUtfData);

			listUtfDto.add(utfDto);
		}


		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		List<UtfDTO> dto = (List<UtfDTO>) method.invoke(utfDS, listUtfDto, gin, true);
		Assert.assertEquals(1, dto.size());
	}



	@Test
	public void checkListValidityTooManyINDType() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true).anyTimes();
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true).anyTimes();
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);

		
		List<UtfDTO> listUtfDto = new ArrayList<>();

		for(int i = 0; i < 3; i++){
			UtfDTO utfDto = new UtfDTO();
			utfDto.setType("IND");
			Set<UtfDataDTO> listUtfData = new HashSet<>();

			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("FIRST_NAME");
			utfDataDto.setValue("TATA");
			listUtfData.add(utfDataDto);

			UtfDataDTO utfDataDto2 = new UtfDataDTO();
			utfDataDto2.setKey("LAST_NAME");
			utfDataDto2.setValue("TATA");
			listUtfData.add(utfDataDto2);
			utfDto.setUtfDataDTO(listUtfData);

			listUtfDto.add(utfDto);
		}


		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTF_TOO_MANY_UTF_WITH_THIS_TYPE.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}


	@Test
	public void findByExampleTest() throws JrafDomainException, UtfException{
		List<UtfDTO> listUtfDto = utfDS.findByGin("400518823573");
		Assert.assertEquals(1, listUtfDto.size());
	}

	@Test
	public void checkListValidityKeyAlreadyExist() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);
		List<UtfDTO> listUtfDto = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		Set<UtfDataDTO> listUtfData = new HashSet<>();
		for(int i = 0; i < UtfDS.MAX_NUMBER_UTFDATA; i++){
			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDataDto.setKey("TOTO");
			utfDataDto.setValue("TATA");

			listUtfData.add(utfDataDto);
			utfDto.setUtfDataDTO(listUtfData);
		}
		listUtfDto.add(utfDto);

		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTFDATA_KEY_ALREADY_EXIST.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}





	@Test
	public void checkListValidityValueNull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);

		List<UtfDTO> listUtfDto = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> listUtfData = new HashSet<>();
		for(int i = 0; i < UtfDS.MAX_NUMBER_UTFDATA; i++){
			UtfDataDTO utfDataDto = new UtfDataDTO();
			utfDto.setType("IND");
			listUtfData.add(utfDataDto);
			utfDto.setUtfDataDTO(listUtfData);
		}
		listUtfDto.add(utfDto);

		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTFDATA_VALUE_CANNOT_BE_NULL.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}


	@Test
	public void checkListValidityUtfDataListTooBig() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);

		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)20000, null, null)));
		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);

		List<UtfDTO> listUtfDto = new ArrayList<>();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		Set<UtfDataDTO> listUtfData = new HashSet<>();
		for(int i = 0; i <= UtfDS.MAX_NUMBER_UTFDATA; i++){
			UtfDataDTO utfDataDto = new UtfDataDTO();
			listUtfData.add(utfDataDto);
			utfDto.setUtfDataDTO(listUtfData);
		}
		listUtfDto.add(utfDto);

		String gin = null;

		final Method method = utfDS.getClass().getDeclaredMethod("checkListValidity", List.class, String.class, boolean.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, listUtfDto, gin, false);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTFDATA_LIST_TOO_BIG.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void findByGinNormalCase() throws JrafDomainException, UtfException{
		List<UtfDTO> list = utfDS.findByGin("400123456789");
		UtfDTO utfDto = list.get(0);
		Assert.assertNotNull(utfDto);
		Assert.assertNotNull(utfDto.getUtfId());
		Assert.assertNotNull(utfDto.getUtfDataDTO());
		Assert.assertEquals(2, utfDto.getUtfDataDTO().size());
		for(UtfDataDTO utfDataDto : utfDto.getUtfDataDTO()){
			if(StringUtils.equals(utfDataDto.getKey(),"FIRST_NAME")){
				Assert.assertEquals("Stéphanie", utfDataDto.getValue());
			}
			if(StringUtils.equals(utfDataDto.getKey(),"LAST_NAME")){
				Assert.assertEquals("Ebrégûoï", utfDataDto.getValue());
			}
		}

	}

	@Test
	public void findByGinError1() throws JrafDomainException, UtfException{
		try{
			utfDS.findByGin("");
			Assert.fail();
		}catch(InvalidParameterException e){}
	}

	@Test
	public void findByGinError2() throws JrafDomainException, UtfException{
		try{
			utfDS.findByGin("   ");
			Assert.fail();
		}catch(InvalidParameterException e){}
	}

	@Test
	public void findByGinError3() throws JrafDomainException, UtfException{
		try{
			utfDS.findByGin(null);
			Assert.fail();
		}catch(InvalidParameterException e){}
	}

	@Test
	public void findByExampleUtfNormalCase() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setGin("400123456789");
		utfDto.setType("IND");
		List<UtfDTO> list = utfDS.findByExample(utfDto);
		utfDto = list.get(0);
		Assert.assertNotNull(utfDto);
		Assert.assertNotNull(utfDto.getUtfId());
		Assert.assertNotNull(utfDto.getUtfDataDTO());
		Assert.assertEquals(2, utfDto.getUtfDataDTO().size());
		for(UtfDataDTO utfDataDto : utfDto.getUtfDataDTO()){
			if(StringUtils.equals(utfDataDto.getKey(),"FIRST_NAME")){
				Assert.assertEquals("Stéphanie", utfDataDto.getValue());
			}
			if(StringUtils.equals(utfDataDto.getKey(),"LAST_NAME")){
				Assert.assertEquals("Ebrégûoï", utfDataDto.getValue());
			}
		}
	}


	@Test
	public void findByExampleErrorNull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, JrafDomainException{
		UtfDS utfDS = new UtfDS();

		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.findAll((Example<Utf>)EasyMock.anyObject(Example.class))).andReturn(new ArrayList<Utf>());
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);

		UtfDTO utf = new UtfDTO();

		List<UtfDTO> listUtfDto = utfDS.findByExample(utf);
		Assert.assertNotNull(listUtfDto);
		Assert.assertEquals(0, listUtfDto.size());
	}

	@Test
	public void findByExampleUtfError2() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setGin("BLALDUAHS");
		List<UtfDTO> list = utfDS.findByExample(utfDto);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void findByExampleUtfError3() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setGin("");
		List<UtfDTO> list = utfDS.findByExample(utfDto);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void findByExampleUtfInvalidParameter(){
		try {
			utfDS.findByExample(null);
			Assert.fail();
		} catch (JrafDomainException e) {}
	}


	@Test
	public void findByExampleUtfError() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setGin("BLALDUAHS");
		utfDto.setType("IND");
		List<UtfDTO> list = utfDS.findByExample(utfDto);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void createOrUpdateUpdate() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UtfException{
		UtfDTO utfDto = new UtfDTO();
		UtfDS utfDS = new UtfDS();
		utfDto.setUtfId(new Long(1));

		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySgin(EasyMock.anyString())).andReturn(UtfDS.MAX_NUMBER_UTF);
		utfRepository.updateUtf(EasyMock.anyObject(Utf.class), EasyMock.anyString(), EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		method.invoke(utfDS, utfDto, "t", "t");
	}


	@Test
	public void createOrUpdateCreateCreateWorking() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidParameterException, UtfException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");

		UtfDS utfDS = new UtfDS();

		
		
		
		RefUtfTypeRepository refUtfTypeRepository = EasyMock.createNiceMock(RefUtfTypeRepository.class);
		EasyMock.expect(refUtfTypeRepository.findById(EasyMock.anyString())).andReturn(Optional.of(new RefUtfType(null, null,null, (long)1, null, null)));

		EasyMock.replay(refUtfTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);
		
		
		
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySginAndRefUtfTypeScode(EasyMock.anyString(), EasyMock.anyString())).andReturn(0);
		EasyMock.expect(utfRepository.createUtf(EasyMock.anyObject(Utf.class), EasyMock.anyString(), EasyMock.anyString()))
		.andReturn((long) 42);
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		UtfDTO result = (UtfDTO) method.invoke(utfDS, utfDto, "t", "t");
		Assert.assertEquals(new Long(42), result.getUtfId());
	}


	@Test
	public void createOrUpdateCreateListTooBig() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UtfException{
		UtfDTO utfDto = new UtfDTO();
		UtfDS utfDS = new UtfDS();

		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySgin(EasyMock.anyString())).andReturn(UtfDS.MAX_NUMBER_UTF);
		utfRepository.updateUtf(EasyMock.anyObject(Utf.class), EasyMock.anyString(), EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTF_LIST_TOO_BIG.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void deleteUtfDataMissingParameter1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> utfDataDto = null;
		UtfDS utfDS = new UtfDS();

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);
		Set<UtfDataDTO> utfDataSet = (Set<UtfDataDTO>)method.invoke(utfDS, utfDataDto,utfDto);
		Assert.assertEquals(0, utfDataSet.size());
	}

	@Test
	public void deleteUtfDataMissingParameter2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{

		UtfDTO utfDto = null;
		Set<UtfDataDTO> utfDataDto = null;
		UtfDS utfDS = new UtfDS();

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDataDto,utfDto);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(e.getTargetException() instanceof InvalidParameterException)){
				Assert.fail();
			}
		}
	}

	@Test
	public void deleteUtfDataOnNonExistingUtfId() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> utfDataDto = new HashSet<>();
		UtfDS utfDS = new UtfDS();

		UtfDataDTO utfDataDto1 = new UtfDataDTO();
		utfDataDto1.setKey("TATA");
		UtfDataDTO utfDataDto2 = new UtfDataDTO();
		utfDataDto2.setKey("TATO");
		UtfDataDTO utfDataDto3 = new UtfDataDTO();
		utfDataDto3.setKey("TATS");
		UtfDataDTO utfDataDto4 = new UtfDataDTO();
		utfDataDto4.setKey("TATP");
		UtfDataDTO utfDataDto5 = new UtfDataDTO();
		utfDataDto5.setKey("TATM");
		UtfDataDTO utfDataDto6 = new UtfDataDTO();
		utfDataDto6.setKey("2TATM");

		utfDataDto.add(utfDataDto1);
		utfDataDto.add(utfDataDto2);
		utfDataDto.add(utfDataDto3);
		utfDataDto.add(utfDataDto4);
		utfDataDto.add(utfDataDto5);
		utfDataDto.add(utfDataDto6);

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDataDto,utfDto);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(UtfErrorCode.CANNOT_DELETE_UTDATA_ON_NON_EXISTING_UTF.equals(((UtfException)e.getTargetException()).getErrorCode()))){
				Assert.fail();
			}
		}
	}


	@Test
	public void deleteUtfDataNormalCase1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setUtfId(new Long(1));
		Set<UtfDataDTO> utfDataDto = new HashSet<>();
		UtfDS utfDS = new UtfDS();

		UtfDataDTO utfDataDto1 = new UtfDataDTO();
		utfDataDto1.setKey("TATA");
		UtfDataDTO utfDataDto2 = new UtfDataDTO();
		utfDataDto2.setKey("TATO");
		UtfDataDTO utfDataDto3 = new UtfDataDTO();
		utfDataDto3.setKey("TATS");
		UtfDataDTO utfDataDto4 = new UtfDataDTO();
		utfDataDto4.setKey("TATP");
		UtfDataDTO utfDataDto5 = new UtfDataDTO();
		utfDataDto5.setKey("TATM");
		UtfDataDTO utfDataDto6 = new UtfDataDTO();
		utfDataDto6.setKey("2TATM");

		utfDataDto.add(utfDataDto1);
		utfDataDto.add(utfDataDto2);
		utfDataDto.add(utfDataDto3);
		utfDataDto.add(utfDataDto4);
		utfDataDto.add(utfDataDto5);
		utfDataDto.add(utfDataDto6);

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);

		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);

		Set<UtfDataDTO> result = (Set<UtfDataDTO>)method.invoke(utfDS, utfDataDto,utfDto);
		Assert.assertEquals(0, result.size());
	}


	@Test
	public void deleteUtfDataNormalCase2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setUtfId(new Long(1));
		Set<UtfDataDTO> utfDataDto = new HashSet<>();
		UtfDS utfDS = new UtfDS();

		UtfDataDTO utfDataDto1 = new UtfDataDTO();
		utfDataDto1.setKey("TATA");
		utfDataDto1.setValue("TITI");
		UtfDataDTO utfDataDto2 = new UtfDataDTO();
		utfDataDto2.setKey("TATO");
		UtfDataDTO utfDataDto3 = new UtfDataDTO();
		utfDataDto3.setKey("TATS");
		UtfDataDTO utfDataDto4 = new UtfDataDTO();
		utfDataDto4.setKey("TATP");
		UtfDataDTO utfDataDto5 = new UtfDataDTO();
		utfDataDto5.setKey("TATM");
		UtfDataDTO utfDataDto6 = new UtfDataDTO();
		utfDataDto6.setKey("2TATM");
		utfDataDto6.setValue("PAP");
		utfDataDto.add(utfDataDto1);
		utfDataDto.add(utfDataDto2);
		utfDataDto.add(utfDataDto3);
		utfDataDto.add(utfDataDto4);
		utfDataDto.add(utfDataDto5);
		utfDataDto.add(utfDataDto6);

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);

		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);

		Set<UtfDataDTO> result = (Set<UtfDataDTO>)method.invoke(utfDS, utfDataDto,utfDto);
		Assert.assertEquals(2, result.size());
	}


	@Test
	public void deleteUtfDataUtfDataKeyCannotBeNull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setUtfId(new Long(1));
		Set<UtfDataDTO> utfDataDto = new HashSet<>();
		UtfDS utfDS = new UtfDS();

		UtfDataDTO utfDataDto1 = new UtfDataDTO();
		utfDataDto1.setKey("TATA");
		UtfDataDTO utfDataDto2 = new UtfDataDTO();
		utfDataDto2.setKey("TATO");
		UtfDataDTO utfDataDto3 = new UtfDataDTO();
		UtfDataDTO utfDataDto4 = new UtfDataDTO();
		utfDataDto4.setKey("TATP");
		UtfDataDTO utfDataDto5 = new UtfDataDTO();
		utfDataDto5.setKey("TATM");
		UtfDataDTO utfDataDto6 = new UtfDataDTO();

		utfDataDto.add(utfDataDto1);
		utfDataDto.add(utfDataDto2);
		utfDataDto.add(utfDataDto3);
		utfDataDto.add(utfDataDto4);
		utfDataDto.add(utfDataDto5);
		utfDataDto.add(utfDataDto6);

		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(EasyMock.anyLong(), EasyMock.anyString())).andReturn((long) 0);
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("deleteUtfData", Set.class, UtfDTO.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDataDto,utfDto);
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(UtfErrorCode.UTFDATA_KEY_CANNOT_BE_NULL.equals(((UtfException)e.getTargetException()).getErrorCode()))){
				Assert.fail();
			}
		}
	}


	@Test
	public void createOrUpdateMissingParameter() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UtfException{
		UtfDTO utfDto = null;
		UtfDS utfDS = new UtfDS();

		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySgin(EasyMock.anyString())).andReturn(UtfDS.MAX_NUMBER_UTF);
		utfRepository.updateUtf(EasyMock.anyObject(Utf.class), EasyMock.anyString(), EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDto, "", "");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(e.getTargetException() instanceof InvalidParameterException)){
				Assert.fail();
			}
		}
	}



	@Test(expected = Test.None.class /* no exception expected */)
	// if no utfData at all, just do nothing
	public void createOrUpdateUtfDataMissingParameter1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		Set<UtfDataDTO> setUtfDataDTO = null;
		final Method method = UtfDS.class.getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
	}

	@Test
	//if utf is null or type empty, abnormal -> invalid parameter exception
	public void createOrUpdateUtfDataMissingParameter3() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();
		final Method method = UtfDS.class.getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(e.getTargetException() instanceof InvalidParameterException)){
				Assert.fail();
			}
		}
	}




	@Test
	public void createOrUpdateUtfDataKeyDontExistForType() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, UtfException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		UtfDataDTO utfData = new UtfDataDTO();
		utfData.setKey("ZZZ");
		setUtfDataDTO.add(utfData);

		UtfDataDTO utfData2 = new UtfDataDTO();
		utfData2.setKey("TITI");
		utfData2.setValue("AA");
		setUtfDataDTO.add(utfData2);
		
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(false);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);


		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.countByUtfUtfId(EasyMock.anyLong())).andReturn(new Long(0)).anyTimes();
		EasyMock.expect(utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(EasyMock.anyString(), EasyMock.anyLong())).andReturn(new Long(-1)).anyTimes();
		EasyMock.expect(utfDataRepository.createUtfData(EasyMock.anyObject(UtfData.class), EasyMock.anyString(),
				EasyMock.anyString())).andReturn((long) 1).anyTimes();

		utfDataRepository.update(EasyMock.anyObject(UtfData.class), EasyMock.anyString(), EasyMock.anyString(),
				EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(UtfErrorCode.UTFDATA_KEY_DOESNT_EXIST_FOR_TYPE.equals(((UtfException)e.getTargetException()).getErrorCode()))){
				Assert.fail();
			}
		}
	}

	@Test
	public void createOrUpdateUtfDataNormalCaseUpdate() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UtfException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setUtfId(new Long(0));
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		UtfDataDTO utfData = new UtfDataDTO();
		utfData.setKey("FIRST_NAME");
		setUtfDataDTO.add(utfData);

		UtfDataDTO utfData2 = new UtfDataDTO();
		utfData2.setKey("LAST_NAME");
		utfData2.setValue("AA");
		setUtfDataDTO.add(utfData2);

		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		
		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.countByUtfUtfId(EasyMock.anyLong())).andReturn(new Long(UtfDS.MAX_NUMBER_UTFDATA-1)).anyTimes();
		EasyMock.expect(utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(EasyMock.anyString(), EasyMock.anyLong())).andReturn(new Long(1)).anyTimes();
		EasyMock.expect(utfDataRepository.createUtfData(EasyMock.anyObject(UtfData.class), EasyMock.anyString(),
				EasyMock.anyString())).andReturn((long) 1).anyTimes();


		utfDataRepository.update(EasyMock.anyObject(UtfData.class), EasyMock.anyString(), EasyMock.anyString(),
				EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");

	}



	@Test
	public void createOrUpdateUtfDataNormalCaseCreate() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UtfException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setUtfId(new Long(0));
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		UtfDataDTO utfData = new UtfDataDTO();
		utfData.setKey("FIRST_NAME");
		setUtfDataDTO.add(utfData);

		UtfDataDTO utfData2 = new UtfDataDTO();
		utfData2.setKey("LAST_NAME");
		utfData2.setValue("AA");
		setUtfDataDTO.add(utfData2);
		
		
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		
		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.countByUtfUtfId(EasyMock.anyLong())).andReturn(new Long(UtfDS.MAX_NUMBER_UTFDATA-1)).anyTimes();
		EasyMock.expect(utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(EasyMock.anyString(), EasyMock.anyLong())).andReturn(new Long(-1)).anyTimes();
		EasyMock.expect(utfDataRepository.createUtfData(EasyMock.anyObject(UtfData.class), EasyMock.anyString(),
				EasyMock.anyString())).andReturn((long) 1).anyTimes();
		
		utfDataRepository.update(EasyMock.anyObject(UtfData.class), EasyMock.anyString(), EasyMock.anyString(),
				EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");

	}

	@Test
	public void createOrUpdateUtfDataWayTooManyUtfData() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, UtfException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setUtfId(new Long(0));
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		UtfDataDTO utfData = new UtfDataDTO();
		utfData.setKey("FIRST_NAME");
		setUtfDataDTO.add(utfData);

		UtfDataDTO utfData2 = new UtfDataDTO();
		utfData2.setKey("LAST_NAME");
		utfData2.setValue("AA");
		setUtfDataDTO.add(utfData2);
		
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		
		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.countByUtfUtfId(EasyMock.anyLong())).andReturn(new Long(UtfDS.MAX_NUMBER_UTFDATA+1)).anyTimes();
		EasyMock.expect(utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(EasyMock.anyString(), EasyMock.anyLong())).andReturn(new Long(1)).anyTimes();
		EasyMock.expect(utfDataRepository.createUtfData(EasyMock.anyObject(UtfData.class), EasyMock.anyString(),
				EasyMock.anyString())).andReturn((long) 1).anyTimes();
	

		utfDataRepository.update(EasyMock.anyObject(UtfData.class), EasyMock.anyString(), EasyMock.anyString(),
				EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(UtfErrorCode.UTFDATA_LIST_WAY_TOO_BIG.equals(((UtfException)e.getTargetException()).getErrorCode()))){
				Assert.fail();
			}
		}
	}


	@Test
	public void createOrUpdateUtfDataTooManyUtfData() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, UtfException, InvalidParameterException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setUtfId(new Long(0));
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		UtfDataDTO utfData = new UtfDataDTO();
		utfData.setKey("FIRST_NAME");
		setUtfDataDTO.add(utfData);

		UtfDataDTO utfData2 = new UtfDataDTO();
		utfData2.setKey("LAST_NAME");
		utfData2.setValue("AA");
		setUtfDataDTO.add(utfData2);
		
		
		
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(EasyMock.anyString(), EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		
		UtfDataRepository utfDataRepository = EasyMock.createNiceMock(UtfDataRepository.class);
		EasyMock.expect(utfDataRepository.countByUtfUtfId(EasyMock.anyLong())).andReturn(new Long(UtfDS.MAX_NUMBER_UTFDATA)).anyTimes();
		EasyMock.expect(utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(EasyMock.anyString(), EasyMock.anyLong())).andReturn(new Long(1)).anyTimes();
		EasyMock.expect(utfDataRepository.createUtfData(EasyMock.anyObject(UtfData.class), EasyMock.anyString(),
				EasyMock.anyString())).andReturn((long) 1).anyTimes();

		utfDataRepository.update(EasyMock.anyObject(UtfData.class), EasyMock.anyString(), EasyMock.anyString(),
				EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(utfDataRepository);
		ReflectionTestUtils.setField(utfDS, "utfDataRepository", utfDataRepository);

		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(UtfErrorCode.UTFDATA_LIST_TOO_BIG.equals(((UtfException)e.getTargetException()).getErrorCode()))){
				Assert.fail();
			}
		}
	}



	@Test
	//if utf is null or type empty, abnormal -> invalid parameter exception
	public void createOrUpdateUtfDataMissingParameter2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDS utfDS = new UtfDS();
		UtfDTO utfDto = null;
		Set<UtfDataDTO> setUtfDataDTO = new HashSet<>();

		final Method method = UtfDS.class.getDeclaredMethod("createOrUpdateUtfData", Set.class, UtfDTO.class,
				String.class, String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, setUtfDataDTO, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!(e.getTargetException() instanceof InvalidParameterException)){
				Assert.fail();
			}
		}
	}



	@Test
	public void createOrUpdateUtTooManyTypeIND() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");

		UtfDS utfDS = new UtfDS();
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySgin(EasyMock.anyString())).andReturn(2).anyTimes();
		EasyMock.expect(utfRepository.countBySginAndRefUtfTypeScode(EasyMock.anyString(), EasyMock.anyString())).andReturn(1).anyTimes();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfTypeRepository", refUtfTypeRepository);


		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTF_TOO_MANY_UTF_WITH_THIS_TYPE.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Test
	public void createOrUpdateUtTooManyUTF() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException{
		UtfDTO utfDto = new UtfDTO();
		UtfDS utfDS = new UtfDS();
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		EasyMock.expect(utfRepository.countBySgin(EasyMock.anyString())).andReturn(UtfDS.MAX_NUMBER_UTF +1);
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);


		final Method method = utfDS.getClass().getDeclaredMethod("createOrUpdateUtf", UtfDTO.class, String.class,
				String.class);
		method.setAccessible(true);
		try{
			method.invoke(utfDS, utfDto, "t", "t");
			Assert.fail();
		}catch(InvocationTargetException e){
			if(!UtfErrorCode.UTF_LIST_WAY_TOO_BIG.equals(((UtfException)e.getTargetException()).getErrorCode())){
				Assert.fail();
			}
		}
	}

	public class TestUtfDS extends UtfDS{
		@Override
		protected UtfDTO createOrUpdateUtf(UtfDTO utfDto, final String signature, final String site) {
			return null;
		}
		@Override
		protected void createOrUpdateUtfData(Set<UtfDataDTO> setUtfDataDTO, UtfDTO utfDto, final String signature,
				final String site) {
		}
		@Override
		protected Set<UtfDataDTO> deleteUtfData(Set<UtfDataDTO> setUtfDataDTO, UtfDTO utfDto){
			if(setUtfDataDTO == null){return new HashSet<>();}
			return setUtfDataDTO;
		}
	}

	@Test
	public void processMissingParameter() throws JrafDomainException, UtfException{
		UtfDTO utfDto = null;
		TestUtfDS utfDS = new TestUtfDS();
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(false);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		utfRepository.deleteById(EasyMock.anyLong());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		utfDS.process(utfDto, "a", "a");
	}

	@Test
	public void processUtfTypeDontExist() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		TestUtfDS utfDS = new TestUtfDS();
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(false);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		utfRepository.deleteById(EasyMock.anyLong());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		try{
			utfDS.process(utfDto, "a", "a");
			Assert.fail();
		}catch(UtfException e){
			if(!UtfErrorCode.UTF_TYPE_DOESNT_EXIST.equals(e.getErrorCode())){
				Assert.fail();
			}
		}
	}



	@Test
	// normal create or update, no crash
	public void processNormalCreateOrUpdate() throws JrafDomainException, UtfException{
		UtfDTO utfDto = new UtfDTO();
		Set<UtfDataDTO> setUtfData = new HashSet<>();
		UtfDataDTO utfDataDto = new UtfDataDTO();
		utfDataDto.setKey("FIRST_NAME");
		utfDataDto.setValue("TOTO");
		setUtfData.add(utfDataDto);
		utfDto.setUtfDataDTO(setUtfData);
		utfDto.setType("IND");
		TestUtfDS utfDS = new TestUtfDS();
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		utfRepository.deleteById(EasyMock.anyLong());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		utfDS.process(utfDto, "a", "a");
	}


	@Test
	// normal delete, no crash
	public void processNormalDelete() throws JrafDomainException, UtfException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setUtfId(new Long(0));
		TestUtfDS utfDS = new TestUtfDS();
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		utfRepository.deleteById(EasyMock.anyLong());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		utfDS.process(utfDto, "a", "a");
	}




	@Test
	public void processNoUtfData() throws JrafDomainException{
		UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		TestUtfDS utfDS = new TestUtfDS();
		
		
		RefUtfKeyTypeRepository refUtfKeyTypeRepository = EasyMock.createNiceMock(RefUtfKeyTypeRepository.class);
		EasyMock.expect(refUtfKeyTypeRepository.existsByIdStype(EasyMock.anyString())).andReturn(true);
		EasyMock.replay(refUtfKeyTypeRepository);
		ReflectionTestUtils.setField(utfDS, "refUtfKeyTypeRepository", refUtfKeyTypeRepository);
		
		UtfRepository utfRepository = EasyMock.createNiceMock(UtfRepository.class);
		utfRepository.deleteById(EasyMock.anyLong());
		EasyMock.expectLastCall();
		EasyMock.replay(utfRepository);
		ReflectionTestUtils.setField(utfDS, "utfRepository", utfRepository);
		try{
			utfDS.process(utfDto, "a", "a");
			Assert.fail();
		}catch(UtfException e){
			if(!UtfErrorCode.AT_LEAST_1_UTFDATA_REQUIRED_TO_CREATE_UTF.equals(e.getErrorCode())){
				Assert.fail();
			}
		}
	}

	@Ignore
	@Test(expected = Test.None.class /* no exception expected */)
	public void deleteCascadeTestCreate() throws JrafDomainException, UtfException{

		UtfDTO utfDto = new UtfDTO();
		utfDto.setGin("1010");
		utfDto.setSignatureCreation("TestDeleteCasc");
		utfDto.setSiteCreation("JUNIT");
		utfDto.setType("IND");
		Set<UtfDataDTO> setUtfDataDto = new HashSet<>();
		UtfDataDTO firstName = new UtfDataDTO();
		firstName.setKey("FIRST_NAME");
		firstName.setValue("TOTO");
		firstName.setSignatureCreation("TestDeleteCasc");
		firstName.setSiteCreation("JUNIT");

		UtfDataDTO lastName = new UtfDataDTO();
		lastName.setKey("LAST_NAME");
		lastName.setValue("TATA");
		lastName.setSignatureCreation("TestDeleteCasc");
		lastName.setSiteCreation("JUNIT");

		setUtfDataDto.add(firstName);
		setUtfDataDto.add(lastName);

		utfDto.setUtfDataDTO(setUtfDataDto);
		utfDS.process(utfDto, "a", "a");


		lastName.setValue(null);
		utfDS.process(utfDto, "a", "a");

		utfDto.setUtfDataDTO(null);
		utfDS.process(utfDto, "a", "a");

	}
	
}
