package com.afklm.repind.provideindividualreferencetable.v2.transformers;

import com.afklm.soa.stubs.w001588.v2.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v2.response.Column;
import com.afklm.soa.stubs.w001588.v2.response.Row;
import com.afklm.soa.stubs.w001588.v2.response.SubRefTable;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dto.reference.*;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.reference.RefPreferenceKeyType;
import com.airfrance.repind.util.service.PaginationResult;

import java.util.HashSet;
import java.util.Set;

public class ProvideIndividualReferenceTableTransformV2 {
	
	/**
	 * Transform to Provide the Table REF_COMPREF
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefComPrefToProvideIndividualReferenceTable(PaginationResult<RefComPrefDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults()); 
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefDTO refComPrefDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("refComPrefId", refComPrefDTO.getRefComprefId().toString()));
				row.getColumn().add(createColumn("domain", refComPrefDTO.getDomain().getCodeDomain()));
				row.getColumn().add(createColumn("groupType", refComPrefDTO.getComGroupeType().getCodeGType()));
				row.getColumn().add(createColumn("type", refComPrefDTO.getComType().getCodeType()));
				row.getColumn().add(createColumn("description", refComPrefDTO.getDescription()));
				row.getColumn().add(createColumn("mandatoryOptin", refComPrefDTO.getMandatoryOptin()));
				row.getColumn().add(createColumn("market", refComPrefDTO.getMarket()));
				row.getColumn().add(createColumn("defaultLanguage1", refComPrefDTO.getDefaultLanguage1()));
				row.getColumn().add(createColumn("defaultLanguage2", refComPrefDTO.getDefaultLanguage2()));
				row.getColumn().add(createColumn("defaultLanguage3", refComPrefDTO.getDefaultLanguage3()));
				row.getColumn().add(createColumn("defaultLanguage4", refComPrefDTO.getDefaultLanguage4()));
				row.getColumn().add(createColumn("defaultLanguage5", refComPrefDTO.getDefaultLanguage5()));
				row.getColumn().add(createColumn("defaultLanguage6", refComPrefDTO.getDefaultLanguage6()));
				row.getColumn().add(createColumn("defaultLanguage7", refComPrefDTO.getDefaultLanguage7()));
				row.getColumn().add(createColumn("defaultLanguage8", refComPrefDTO.getDefaultLanguage8()));
				row.getColumn().add(createColumn("defaultLanguage9", refComPrefDTO.getDefaultLanguage9()));
				row.getColumn().add(createColumn("defaultLanguage10", refComPrefDTO.getDefaultLanguage10()));
				row.getColumn().add(createColumn("fieldA", refComPrefDTO.getFieldA()));
				row.getColumn().add(createColumn("fieldN", refComPrefDTO.getFieldN()));
				row.getColumn().add(createColumn("fieldT", refComPrefDTO.getFieldT()));
				row.getColumn().add(createColumn("media", refComPrefDTO.getMedia().getCodeMedia()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
	/**
	 * Transform to Provide the Table REF_COMPREF_COUNTRY_MARKET
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefComPrefCountryMarketToProvideIndividualReferenceTable(PaginationResult<RefComPrefCountryMarketDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefCountryMarketDTO refComPrefCountryMarketDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codePays", refComPrefCountryMarketDTO.getCodePays()));
				row.getColumn().add(createColumn("market", refComPrefCountryMarketDTO.getMarket()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
	/**
	 * Transform to Provide the Table REF_PERMISSIONS_QUESTION
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefPermissionsQuestionToProvideIndividualReferenceTable(PaginationResult<RefPermissionsQuestionDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefPermissionsQuestionDTO refPermissionsQuestionDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("permissionQuestionId", refPermissionsQuestionDTO.getId().toString()));
				row.getColumn().add(createColumn("name", refPermissionsQuestionDTO.getName()));
				row.getColumn().add(createColumn("questionFr", refPermissionsQuestionDTO.getQuestion()));
				row.getColumn().add(createColumn("questionEn", refPermissionsQuestionDTO.getQuestionEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
	/**
	 * Transform to Provide the Table REF_PERMISSIONS
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefPermissionsToProvideIndividualReferenceTable(PaginationResult<RefPermissionsDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTablePermissions = new SubRefTable();
		SubRefTable subRefTableRefCompref = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTablePermissions.setSubRefTableName("REF_PERMISSIONS_QUESTION");
			
			subRefTableRefCompref.setSubRefTableName("REF_COMPREF");
			
			Set<RefPermissionsQuestionDTO> setRefPermissionsQuestionDTO = new HashSet<RefPermissionsQuestionDTO>();
			
			for (RefPermissionsDTO refPermissionsDTO : result.getListResults()) {
				setRefPermissionsQuestionDTO.add(refPermissionsDTO.getRefPermissionsId().getQuestionId());
				
				Row row = new Row();
				row.getColumn().add(createColumn("permissionQuestionId", refPermissionsDTO.getRefPermissionsId().getQuestionId().getId().toString()));
				row.getColumn().add(createColumn("domain", refPermissionsDTO.getRefPermissionsId().getRefComPrefDgt().getDomain().getCodeDomain()));
				row.getColumn().add(createColumn("groupType", refPermissionsDTO.getRefPermissionsId().getRefComPrefDgt().getGroupType().getCodeGType()));
				row.getColumn().add(createColumn("type", refPermissionsDTO.getRefPermissionsId().getRefComPrefDgt().getType().getCodeType()));
				subRefTableRefCompref.getRow().add(row);
			}
			
			for (RefPermissionsQuestionDTO refPermissionsQuestionDTO : setRefPermissionsQuestionDTO) {
				Row row = new Row();
				row.getColumn().add(createColumn("permissionQuestionId", refPermissionsQuestionDTO.getId().toString()));
				row.getColumn().add(createColumn("name", refPermissionsQuestionDTO.getName()));
				row.getColumn().add(createColumn("questionFr", refPermissionsQuestionDTO.getQuestion()));
				row.getColumn().add(createColumn("questionEn", refPermissionsQuestionDTO.getQuestionEN()));
				subRefTablePermissions.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTablePermissions);
			response.getSubRefTable().add(subRefTableRefCompref);
		}
		
		return response;
	}
	
	private static  Column createColumn(String key, String value) {
		Column column = new Column();
		column.setKey(key);
		
		if(value == null)
			value = "";
		
		column.setValue(value);
		
		return column;
	}

	public static ProvideIndividualReferenceTableResponse transformRefComPrefDomainToProvideIndividualReferenceTable(PaginationResult<RefComPrefDomainDTO> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefDomainDTO refComPrefDomainDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codeDomain", refComPrefDomainDTO.getCodeDomain()));
				row.getColumn().add(createColumn("libelleFr", refComPrefDomainDTO.getLibelleDomain()));
				row.getColumn().add(createColumn("libelleEn", refComPrefDomainDTO.getLibelleDomainEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}

		return response;
	}
	
	public static ProvideIndividualReferenceTableResponse transformRefComPrefTypeToProvideIndividualReferenceTable(PaginationResult<RefComPrefTypeDTO> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefTypeDTO refComPrefTypeDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codeType", refComPrefTypeDTO.getCodeType()));
				row.getColumn().add(createColumn("libelleTypeFR", refComPrefTypeDTO.getLibelleType()));
				row.getColumn().add(createColumn("libelleTypeEN", refComPrefTypeDTO.getLibelleTypeEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}

		return response;
	}
	
	public static ProvideIndividualReferenceTableResponse transformRefComPrefGTypeToProvideIndividualReferenceTable(PaginationResult<RefComPrefGTypeDTO> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefGTypeDTO refComPrefGTypeDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codeGType", refComPrefGTypeDTO.getCodeGType()));
				row.getColumn().add(createColumn("libelleGTypeFR", refComPrefGTypeDTO.getLibelleGType()));
				row.getColumn().add(createColumn("libelleGTypeEN", refComPrefGTypeDTO.getLibelleGTypeEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}

		return response;
	}
	
	public static ProvideIndividualReferenceTableResponse transformRefComPrefMediaToProvideIndividualReferenceTable(PaginationResult<RefComPrefMediaDTO> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefComPrefMediaDTO refComPrefMediaDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codeMedia", refComPrefMediaDTO.getCodeMedia()));
				row.getColumn().add(createColumn("libelleMediaFR", refComPrefMediaDTO.getLibelleMedia()));
				row.getColumn().add(createColumn("libelleMediaEN", refComPrefMediaDTO.getLibelleMediaEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;
	}

	private static String ToStringResponse(Object chaine) {
		return (String) (chaine == null ? "" : chaine.toString());
	}
	
	public static ProvideIndividualReferenceTableResponse transformPaysToProvideIndividualReferenceTable(PaginationResult<Pays> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (Pays pays : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("codePays", pays.getCodePays()));
				row.getColumn().add(createColumn("libellePays", pays.getLibellePays()));
				row.getColumn().add(createColumn("libellePaysEn", pays.getLibellePaysEn()));
				row.getColumn().add(createColumn("codeIata", ToStringResponse(pays.getCodeIata())));
				row.getColumn().add(createColumn("codeGestionCP", ToStringResponse(pays.getCodeGestionCP())));
				row.getColumn().add(createColumn("codeCapitale", ToStringResponse(pays.getCodeCapitale())));
				row.getColumn().add(createColumn("normalisable", ToStringResponse(pays.getNormalisable())));
				row.getColumn().add(createColumn("formatAdr", ToStringResponse(pays.getFormatAdr())));
				row.getColumn().add(createColumn("iFormatAdr", ToStringResponse(pays.getIformatAdr())));
				row.getColumn().add(createColumn("forcage", ToStringResponse(pays.getForcage())));
				row.getColumn().add(createColumn("iso3Code", ToStringResponse(pays.getIso3Code())));
				
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}

		return response;
	}

	public static ProvideIndividualReferenceTableResponse transformRefPreferenceKeyTypeToProvideIndividualReferenceTable(PaginationResult<RefPreferenceKeyType> result, TableReferenceEnum table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefPreferenceKeyType refPreferenceKeyType : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("type", refPreferenceKeyType.getType()));
				row.getColumn().add(createColumn("key", refPreferenceKeyType.getKey()));
				row.getColumn().add(createColumn("minLength", refPreferenceKeyType.getMinLength().toString()));
				row.getColumn().add(createColumn("maxLength", ToStringResponse(refPreferenceKeyType.getMaxLength())));
				row.getColumn().add(createColumn("dataType", ToStringResponse(refPreferenceKeyType.getDataType())));
				row.getColumn().add(createColumn("condition", ToStringResponse(refPreferenceKeyType.getCondition())));
				subRefTable.getRow().add(row);
			}
			response.getSubRefTable().add(subRefTable);
		}
		return response;
	}
	
	/**
	 * Transform to Provide the Table REF_HANDICAP_TYPE
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefHandicapTypeToProvideIndividualReferenceTable(PaginationResult<RefHandicapTypeDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefHandicapTypeDTO refHandicapTypeDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("code", refHandicapTypeDTO.getCode()));
				row.getColumn().add(createColumn("libelleFR", refHandicapTypeDTO.getLabelFR()));
				row.getColumn().add(createColumn("libelleEN", refHandicapTypeDTO.getLabelEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
	/**
	 * Transform to Provide the Table REF_HANDICAP_CODE
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefHandicapCodeToProvideIndividualReferenceTable(PaginationResult<RefHandicapCodeDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefHandicapCodeDTO refHandicapCodeDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("code", refHandicapCodeDTO.getCode()));
				row.getColumn().add(createColumn("libelleFR", refHandicapCodeDTO.getLabelFR()));
				row.getColumn().add(createColumn("libelleEN", refHandicapCodeDTO.getLabelEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
	/**
	 * Transform to Provide the Table REF_HANDICAP_DATA_KEY
	 * 
	 * @param result
	 * @param table
	 * @return ProvideIndividualReferenceTableResponse
	 */
	public static ProvideIndividualReferenceTableResponse transformRefHandicapDataKeyToProvideIndividualReferenceTable(PaginationResult<RefHandicapDataKeyDTO> result, TableReferenceEnum table){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		response.setTableName(table.name());
		response.setTotalResults(result.getTotalResults());
		
		SubRefTable subRefTable = new SubRefTable();
		
		if (result.getListResults() != null && result.getListResults().size() > 0) {
			subRefTable.setSubRefTableName(table.name());
			
			for (RefHandicapDataKeyDTO refHandicapDataKeyDTO : result.getListResults()) {
				Row row = new Row();
				row.getColumn().add(createColumn("code", refHandicapDataKeyDTO.getCode()));
				row.getColumn().add(createColumn("libelleFR", refHandicapDataKeyDTO.getLabelFR()));
				row.getColumn().add(createColumn("libelleEN", refHandicapDataKeyDTO.getLabelEN()));
				subRefTable.getRow().add(row);
			}
			
			response.getSubRefTable().add(subRefTable);
		}
		
		return response;	
	}
	
}
