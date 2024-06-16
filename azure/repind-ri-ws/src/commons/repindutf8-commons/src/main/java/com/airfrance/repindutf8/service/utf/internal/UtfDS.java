package com.airfrance.repindutf8.service.utf.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.dao.RefUtfKeyTypeRepository;
import com.airfrance.repindutf8.dao.RefUtfTypeRepository;
import com.airfrance.repindutf8.dao.UtfRepository;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataTransform;
import com.airfrance.repindutf8.dto.utf.UtfTransform;
import com.airfrance.repindutf8.entity.RefUtfType;
import com.airfrance.repindutf8.dao.UtfDataRepository;
import com.airfrance.repindutf8.entity.Utf;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class UtfDS {

	public final static int MAX_NUMBER_UTF = 100;
	public final static int MAX_NUMBER_UTFDATA = 20;
	private static final Log LOG = LogFactory.getLog(UtfDS.class);

	@Autowired
	private UtfRepository utfRepository;

	@Autowired
	private UtfDataRepository utfDataRepository;
	
	@Autowired
	private RefUtfTypeRepository refUtfTypeRepository;
	@Autowired
	private RefUtfKeyTypeRepository refUtfKeyTypeRepository;

	@Transactional(value = "transactionManagerRepindUtf8")
	// If "isProvide" is false: throw an exception if incorrect data is found
	// If "isProvide" is true: create a best effort data list if incorrect data is found
	public List<UtfDTO> checkListValidity(final List<UtfDTO> listUtfDTO, final String gin, final boolean isProvide)
			throws UtfException, InvalidParameterException {
		
		// This is a "bricolage" to return a "best effort" list for the Provide service event is the database contains
		// incorrect data
		List<UtfDTO> fixedList = new ArrayList<UtfDTO>();
		
		if (listUtfDTO == null) {
			return fixedList;
		}		
		int currentNumberOfUtf = 0;

		final Map<String, Integer> currentNumberOfUtfForType = new HashMap<>();
		for (final UtfDTO utfDto : listUtfDTO) {
			
			UtfDTO fixedDto = utfDto;
			List<UtfDataDTO> fixedDataDTO = new ArrayList<>();
			
			if (!refUtfKeyTypeRepository.existsByIdStype(utfDto.getType())) {
				UtfException e = UtfException.generateException(UtfErrorCode.UTF_TYPE_DOESNT_EXIST, gin);
				LOG.error(e.getMessage());
				if(isProvide) {
					continue;
				}
				throw e;
			}

			if (!currentNumberOfUtfForType.containsKey(utfDto.getType())) {
				currentNumberOfUtfForType.put(utfDto.getType(), 0);
			}

			// check if we don't exceed the max limit of UTF by type
			if (currentNumberOfUtfForType.get(utfDto.getType()) >= refUtfTypeRepository.findById(utfDto.getType()).orElse(new RefUtfType(null, null,null, (long)0, null, null)).getMaxOccurrences()) {
				UtfException e = UtfException.generateException(UtfErrorCode.UTF_TOO_MANY_UTF_WITH_THIS_TYPE, utfDto.getGin());
				LOG.error(e.getMessage());
				if(isProvide) {
					continue;
				}
				throw e;
			}
			
			if(currentNumberOfUtf >= UtfDS.MAX_NUMBER_UTF) {
				UtfException e = UtfException.generateException(UtfErrorCode.UTF_LIST_TOO_BIG, utfDto.getGin());
				LOG.error(e.getMessage());
				if(isProvide) {
					continue;
				}
				throw e;
			}
			
			currentNumberOfUtfForType.put(utfDto.getType(), currentNumberOfUtfForType.get(utfDto.getType()) + 1);

			if (utfDto.getUtfDataDTO() == null) {
				UtfException e = UtfException.generateException(UtfErrorCode.UTFDATA_LIST_CANNOT_BE_NULL, gin);
				LOG.error(e.getMessage());
				if(isProvide) {
					continue;
				}
				throw e;
			}
			if (utfDto.getUtfDataDTO().size() > UtfDS.MAX_NUMBER_UTFDATA) {
				UtfException e = UtfException.generateException(UtfErrorCode.UTFDATA_LIST_TOO_BIG, gin);
				LOG.error(e.getMessage());
				if(isProvide) {
					continue;
				}
				throw e;
			}
			final List<String> knownKey = new ArrayList<>();
			for (final UtfDataDTO utfDataDto : utfDto.getUtfDataDTO()) {
				if (knownKey.contains(utfDataDto.getKey())) {
					// UTFDATA KEY MUST BE UNIQUE, if it s not unique,
					// client won't be able to know what data they want + won't be modifiable by
					// createorupdateindividualV8
					UtfException e = UtfException.generateException(UtfErrorCode.UTFDATA_KEY_ALREADY_EXIST, gin);
					LOG.error(e.getMessage());
					if(isProvide) {
						continue;
					}
					throw e;
				}
				knownKey.add(utfDataDto.getKey());
				if (!refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(utfDto.getType(), utfDataDto.getKey())) {
					UtfException e = UtfException.generateException(UtfErrorCode.UTFDATA_KEY_DOESNT_EXIST_FOR_TYPE, gin);
					LOG.error(e.getMessage());
					if(isProvide) {
						continue;
					}
					throw e;
				}
				if (StringUtils.isBlank(utfDataDto.getValue())) {
					UtfException e = UtfException.generateException(UtfErrorCode.UTFDATA_VALUE_CANNOT_BE_NULL, gin);
					LOG.error(e.getMessage());
					if(isProvide) {
						continue;
					}
					throw e;
				}
				fixedDataDTO.add(utfDataDto);
			}
			fixedList.add(fixedDto);
			currentNumberOfUtf++;
		}
		return fixedList;
	}

	/*
	 * Creating new UTF object & respecting the size limitation in
	 * createOrUpdateAnIndividualV8 SSD + provideAnIndividualDataV7 (MAX_NUMBER_UTF)
	 */
	protected UtfDTO createOrUpdateUtf(final UtfDTO utfDto, final String signature, final String site)
			throws UtfException, JrafDomainException {

		if (utfDto == null) {
			throw new InvalidParameterException("utfDto must not be null");
		}

		final int numberOfUtf = utfRepository.countBySgin(utfDto.getGin());
		// doesn't allow to modify an existing UTF if the list of UTF already exceed the
		// limit
		if (numberOfUtf > UtfDS.MAX_NUMBER_UTF) {
			throw UtfException.generateException(UtfErrorCode.UTF_LIST_WAY_TOO_BIG, utfDto.getGin());
		}
		if (utfDto.getUtfId() != null) {
			utfRepository.updateUtf(UtfTransform.dto2BoLight(utfDto), signature, site);
			// UtfDS.LOG.info("Update UTF " + utfDto.getUtfId());
			return utfDto;
		}

		// doesn't allow to create an existing UTF if the list of UTF already exceed the
		// limit
		if (numberOfUtf == UtfDS.MAX_NUMBER_UTF) {
			throw UtfException.generateException(UtfErrorCode.UTF_LIST_TOO_BIG, utfDto.getGin());
		}

		// check if the maximum amount of UTF for this type have been reached
		if (utfRepository.countBySginAndRefUtfTypeScode(utfDto.getGin(), utfDto.getType()) >= refUtfTypeRepository.findById(utfDto.getType()).orElse(new RefUtfType(null, null,null, (long)0, null, null)).getMaxOccurrences()) {
			throw UtfException.generateException(UtfErrorCode.UTF_TOO_MANY_UTF_WITH_THIS_TYPE, utfDto.getGin());
		}

		utfDto.setUtfId(utfRepository.createUtf(UtfTransform.dto2BoLight(utfDto), signature, site));
		UtfDS.LOG.debug("Create UTF" + utfDto.getUtfId());
		return utfDto;

	}

	/*
	 * Creating new UTFDATA object in DB & respecting the size limitation in
	 * createOrUpdateAnIndividualV8 SSD + provideAnIndividualDataV7
	 * (MAX_NUMBER_UTFDATA)
	 *
	 * Also, the key must be in the ref table to be accepted
	 */
	protected void createOrUpdateUtfData(final Set<UtfDataDTO> setUtfDataDTO, final UtfDTO utfDto,
			final String signature, final String site)
					throws UtfException, JrafDomainException {
		if (setUtfDataDTO == null) {
			return;
		}
		if (utfDto == null || StringUtils.isBlank(utfDto.getType())) {
			throw new InvalidParameterException("type is blank");
		}

		for (final UtfDataDTO utfDataDto : setUtfDataDTO) {
			// if no value, don't save
			if (StringUtils.isBlank(utfDataDto.getValue())) {
				continue;
			}

			// check if the key is allowed or not with the type
			if (!refUtfKeyTypeRepository.existsByIdStypeAndIdSkey(utfDto.getType(), utfDataDto.getKey())) {
				throw UtfException.generateException(UtfErrorCode.UTFDATA_KEY_DOESNT_EXIST_FOR_TYPE, utfDto.getGin());
			}

			utfDataDto.setUtfId(utfDto.getUtfId());
			final Long numberOfUtfData = utfDataRepository.countByUtfUtfId(utfDto.getUtfId());
			final Long utfDataId = utfDataRepository.findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(utfDataDto.getKey(), utfDto.getUtfId());
			if (utfDataId == null) {
				utfDataDto.setUtfDataId(
						utfDataRepository.createUtfData(UtfDataTransform.dto2BoLight(utfDataDto), signature, site));
				// UtfDS.LOG.info("Create UTF DATA " + utfDataDto.getUtfDataId());
			} else {

				if (numberOfUtfData > UtfDS.MAX_NUMBER_UTFDATA) {
					throw UtfException.generateException(UtfErrorCode.UTFDATA_LIST_WAY_TOO_BIG, utfDto.getGin());
				}
				if (numberOfUtfData == UtfDS.MAX_NUMBER_UTFDATA) {
					throw UtfException.generateException(UtfErrorCode.UTFDATA_LIST_TOO_BIG, utfDto.getGin());
				}
				utfDataDto.setUtfDataId(utfDataId);
				utfDataRepository.update(UtfDataTransform.dto2BoLight(utfDataDto), utfDto.getGin(), signature, site);
				// UtfDS.LOG.info("Update UTF DATA " + utfDataDto.getUtfDataId());
			}
		}

	}

	/*
	 * Delete all the utfdata from database when the key is null from SSD
	 * createOrUpdateAnIndividualV8
	 * "To delete a utfData (update Utf process): Get only Key without Value"
	 */
	protected Set<UtfDataDTO> deleteUtfData(final Set<UtfDataDTO> setUtfDataDTO, final UtfDTO utfDto)
			throws JrafDomainException, UtfException {
		if (utfDto == null) {
			throw new InvalidParameterException("utfDto cannot be null");
		}
		if (setUtfDataDTO == null) {
			return new HashSet<>();
		}
		final Set<UtfDataDTO> newSetDataDTO = new HashSet<>();
		for (final UtfDataDTO utfDataDTO : setUtfDataDTO) {
			if (StringUtils.isBlank(utfDataDTO.getKey())) {
				throw UtfException.generateException(UtfErrorCode.UTFDATA_KEY_CANNOT_BE_NULL, utfDto.getGin());
			}
			if (StringUtils.isBlank(utfDataDTO.getValue())) {
				if (utfDto.getUtfId() == null) {
					throw UtfException.generateException(UtfErrorCode.CANNOT_DELETE_UTDATA_ON_NON_EXISTING_UTF,
							utfDto.getGin());
				}
				utfDataRepository.deleteByUtfUtfIdAndRefUtfDataKeyScode(utfDto.getUtfId(), utfDataDTO.getKey());
				continue;
			}
			newSetDataDTO.add(utfDataDTO);
		}
		return newSetDataDTO;
	}

	@Transactional(value = "transactionManagerRepindUtf8")
	public List<UtfDTO> findByExample(final UtfDTO dto) throws JrafDomainException {
		if (dto == null) {
			throw new InvalidParameterException("dto cannot be null");
		}
		final Example<Utf> object = Example.of(UtfTransform.dto2BoLight(dto));
		final List<Utf> listUtf = utfRepository.findAll(object);
		final List<UtfDTO> result = new ArrayList<>();
		if (listUtf == null) {
			return result;
		}
		for (final Utf utf : listUtf) {
			result.add(UtfTransform.bo2Dto(utf));
		}
		return result;
	}

	@Transactional(value = "transactionManagerRepindUtf8")
	public List<UtfDTO> findByGin(final String gin) throws JrafDomainException {
		if (StringUtils.isBlank(gin)) {
			throw new InvalidParameterException("Gin is blank");
		}
		final UtfDTO dto = new UtfDTO();
		dto.setGin(gin);
		final List<UtfDTO> listUtfDTO = findByExample(dto);
		return listUtfDTO;
	}

	/*
	 * Rules ( from SSD createOrUpdateAnInvidualV8 ) Identification of a utf : If
	 * present : - With a bloc utfDatas -> Update - Without utfDatas bloc -> Delete
	 * If not present : - Create a new Utf
	 */
	@Transactional(value = "transactionManagerRepindUtf8")
	public void process(UtfDTO utfDto, String signature, String site) throws JrafDomainException, UtfException {
		UtfDS.LOG.info("Start process UTF");
		if (utfDto == null) {
			return;
		}

		final Set<UtfDataDTO> setUtfDataDTO = deleteUtfData(utfDto.getUtfDataDTO(), utfDto);

		// since the number of utf type allowed is hardcoded, no need to call the DB to
		// check if a type exist
		if (!refUtfKeyTypeRepository.existsByIdStype(utfDto.getType())) {
			throw UtfException.generateException(UtfErrorCode.UTF_TYPE_DOESNT_EXIST, utfDto.getGin());
		}

		// to create a UTF, we REQUIRE at least 1 UTF DATA
		if (setUtfDataDTO.size() == 0 && utfDto.getUtfId() == null) {
			throw UtfException.generateException(UtfErrorCode.AT_LEAST_1_UTFDATA_REQUIRED_TO_CREATE_UTF,
					utfDto.getGin());
		}

		// if not UTF data block, then delete the UTF & all the UTF data linked.
		// the deletion of 1 UTF should trigger the deletion of every UTF DATA linked to
		// him by SQL cascade deletion
		if (setUtfDataDTO.size() == 0) {
			utfRepository.deleteById(utfDto.getUtfId());
			UtfDS.LOG.info("Delete UTF " + utfDto.getUtfId());
			return;
		}

		utfDto = createOrUpdateUtf(utfDto, signature, site);
		createOrUpdateUtfData(setUtfDataDTO, utfDto, signature, site);

	}



}
