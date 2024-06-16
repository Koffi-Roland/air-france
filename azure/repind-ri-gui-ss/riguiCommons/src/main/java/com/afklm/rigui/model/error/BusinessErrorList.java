package com.afklm.rigui.model.error;

import java.util.EnumMap;
import java.util.Map;

/**
 * Business errors list
 *
 * @author m312812
 *
 */
public enum BusinessErrorList {


	API_MISSING_REQUEST_PARAMETER(new RestError("business.400", BusinessErorMessageList.ERROR_MESSAGE_400, null, ErrorType.WARNING)),
	
	API_FORBIDDEN_MS_ACCESS(
			new RestError("business.403.001", BusinessErorMessageList.ERROR_MESSAGE_403_001, null, ErrorType.WARNING)),

	API_CUSTOMER_NOT_FOUND(
			new RestError("business.404.001", BusinessErorMessageList.ERROR_MESSAGE_404_001, null, ErrorType.WARNING)),
	
	API_CUSTOMER_NOT_FOUND_MULTI_CRIT(
			new RestError("business.404.002", BusinessErorMessageList.ERROR_MESSAGE_404_002, null, ErrorType.WARNING)),

	API_BUSINESS_NO_DATA_FOUND(
			new RestError("business.404.003", BusinessErorMessageList.ERROR_MESSAGE_404_003, null, ErrorType.WARNING)),

	API_CONSTRAINT_VIOLATION(new RestError("business.412", BusinessErorMessageList.ERROR_MESSAGE_412, null, ErrorType.WARNING)),

	API_PARAMETER_TYPE_MISMATCH(new RestError("business.412.001", BusinessErorMessageList.ERROR_MESSAGE_412_001, null, ErrorType.WARNING)),
	
	API_INVALID_COMPLAINT_STATUS(
			new RestError("business.412.002", BusinessErorMessageList.ERROR_MESSAGE_412_002, null, ErrorType.WARNING)),
	
	API_CUSTOMER_EMPTY(
			new RestError("business.412.003", BusinessErorMessageList.ERROR_MESSAGE_412_003, null, ErrorType.WARNING)),

	API_INVALID_GIN(
			new RestError("business.412.004", BusinessErorMessageList.ERROR_MESSAGE_412_004, null, ErrorType.WARNING)),

	MERGE_GIN_EQUALS(
			new RestError("business.412.005", BusinessErorMessageList.ERROR_MESSAGE_412_005, null, ErrorType.WARNING)),
	
	MERGE_INVALID_TYPE(
			new RestError("business.412.006", BusinessErorMessageList.ERROR_MESSAGE_412_006, null, ErrorType.WARNING)),

	MERGE_INVALID_STATUS(
			new RestError("business.412.007", BusinessErorMessageList.ERROR_MESSAGE_412_007, null, ErrorType.WARNING)),

	API_INVALID_BUSINESS_RULE(new RestError("business.412.008", BusinessErorMessageList.ERROR_MESSAGE_412_008, null, ErrorType.WARNING)),
	
	API_INVALID_VALUE(
			new RestError("business.412.009", BusinessErorMessageList.ERROR_MESSAGE_412_009, null, ErrorType.WARNING)),

	API_UNKNOWN_BUSINESS_ERROR(
			new RestError("business.412.010", BusinessErorMessageList.ERROR_MESSAGE_412_010, null, ErrorType.WARNING)),
	
	MERGE_TOO_MANY_ADDRESSES(
			new RestError("business.412.011", BusinessErorMessageList.ERROR_MESSAGE_412_011, null, ErrorType.WARNING)),
	
	MERGE_ERROR_S04600(
			new RestError("business.412.012", BusinessErorMessageList.ERROR_MESSAGE_412_012, null, ErrorType.WARNING)),
	
	MERGE_INVALID_TYPE_GP(
			new RestError("business.412.013", BusinessErorMessageList.ERROR_MESSAGE_412_013, null, ErrorType.WARNING)),
	
	MERGE_ERROR_S06880(
			new RestError("business.412.014", BusinessErorMessageList.ERROR_MESSAGE_412_014, null, ErrorType.WARNING)),

	MERGE_INVALID_RIGHTS(
			new RestError("business.412.015", BusinessErorMessageList.ERROR_MESSAGE_412_015, null, ErrorType.WARNING)),
	
	MERGE_NOT_ALLOWED(
			new RestError("business.412.016", BusinessErorMessageList.ERROR_MESSAGE_412_016, null, ErrorType.WARNING)),
	
	API_FB_EXPERT_VALIDATION_REQUESTED(
			new RestError("business.412.017", BusinessErorMessageList.ERROR_MESSAGE_412_017, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_FIRST_GIN_FORBIDDEN_MERGE(
			new RestError("business.412.018", BusinessErorMessageList.ERROR_MESSAGE_412_018, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_SECOND_GIN_FORBIDDEN_MERGE(
			new RestError("business.412.019", BusinessErorMessageList.ERROR_MESSAGE_412_019, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_GINS_FORBIDDEN_MERGE(
			new RestError("business.412.020", BusinessErorMessageList.ERROR_MESSAGE_412_020, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_GINS_WITHOUT_FB(
			new RestError("business.412.021", BusinessErorMessageList.ERROR_MESSAGE_412_021, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_MISSING_PARAMETER(
			new RestError("business.412.022", BusinessErorMessageList.ERROR_MESSAGE_412_022, null, ErrorType.WARNING)),
	
	UPDATE_INVALID_EMAIL_ERROR(new RestError("business.412.023", BusinessErorMessageList.ERROR_MESSAGE_412_023, null, ErrorType.ERROR)),
	
	UPDATE_INVALID_COUNTRY_CODE_ERROR(new RestError("business.412.024", BusinessErorMessageList.ERROR_MESSAGE_412_024, null, ErrorType.ERROR)),
	
	UPDATE_TOO_SHORT_PHONENUMBER_ERROR(new RestError("business.412.025", BusinessErorMessageList.ERROR_MESSAGE_412_025, null, ErrorType.ERROR)),
	
	UPDATE_TOO_LONG_PHONENUMBER_ERROR(new RestError("business.412.026", BusinessErorMessageList.ERROR_MESSAGE_412_026, null, ErrorType.ERROR)), 
	
	UPDATE_INVALID_TELECOM_ERROR(new RestError("business.412.027", BusinessErorMessageList.ERROR_MESSAGE_412_027, null, ErrorType.ERROR)),
	
	TECHNICAL_ERROR(new RestError("business.412.028", BusinessErorMessageList.ERROR_MESSAGE_412_028, null, ErrorType.ERROR)), 
	
	INVALID_COUNTRY_ERROR(new RestError("business.412.029", BusinessErorMessageList.ERROR_MESSAGE_412_029, null, ErrorType.ERROR)), 
	
	INVALID_PARAMETER_ERROR(new RestError("business.412.030", BusinessErorMessageList.ERROR_MESSAGE_412_030, null, ErrorType.ERROR)), 
	
	CREATE_UPDATE_IND_NORMALIZATION_FAILED(
			new RestError("business.412.079", BusinessErorMessageList.ERROR_MESSAGE_412_079, null, ErrorType.ERROR)),
	
	API_SEARCH_MULTICRITERIA_MISSING_PARAMETER(
			new RestError("business.412.080", BusinessErorMessageList.ERROR_MESSAGE_412_080, null, ErrorType.ERROR)),
	
	API_SEARCH_MULTICRITERIA_TOO_MANY_RESULTS(
			new RestError("business.412.081", BusinessErorMessageList.ERROR_MESSAGE_412_081, null, ErrorType.ERROR)),
	
	API_SEARCH_MULTICRITERIA_TECHNICAL_ERROR(
			new RestError("business.412.082", BusinessErorMessageList.ERROR_MESSAGE_412_082, null, ErrorType.ERROR)),
	
	API_SEARCH_MULTICRITERIA_INVALID_EMAIL(
			new RestError("business.412.083", BusinessErorMessageList.ERROR_MESSAGE_412_083, null, ErrorType.ERROR)),

	MERGE_TOO_MANY_GP_ROLES(
			new RestError("business.412.084", BusinessErorMessageList.ERROR_MESSAGE_412_084, null, ErrorType.WARNING)),
	
	API_BUSINESS_GP_GIN_CIBLE_INVALID(
			new RestError("business.412.085", BusinessErorMessageList.ERROR_MESSAGE_412_085, null, ErrorType.WARNING)),
	
	API_BUSINESS_GP_GIN_NOT_FOUND(
			new RestError("business.412.086", BusinessErorMessageList.ERROR_MESSAGE_412_086, null, ErrorType.WARNING)),
	
	API_BUSINESS_GP_GIN_SOURCE_INVALID(
			new RestError("business.412.087", BusinessErorMessageList.ERROR_MESSAGE_412_087, null, ErrorType.WARNING)),
	
	API_BUSINESS_GP_PROCESSING_ERROR(
			new RestError("business.412.088", BusinessErorMessageList.ERROR_MESSAGE_412_088, null, ErrorType.WARNING)),
	
	API_BUSINESS_GP_TECHNICAL_ERROR(
			new RestError("business.412.089", BusinessErorMessageList.ERROR_MESSAGE_412_089, null, ErrorType.WARNING)),

	API_BUSINESS_UNMERGE_WRONG_STATUS_ERROR(
			new RestError("business.412.090", BusinessErorMessageList.ERROR_MESSAGE_412_090, null, ErrorType.WARNING)),

	API_BUSINESS_REACTIVATE_WRONG_STATUS_ERROR(
			new RestError("business.412.091", BusinessErorMessageList.ERROR_MESSAGE_412_091, null, ErrorType.WARNING)),

	API_BUSINESS_NOT_ALL_INDIVIDUS_FOUND(
			new RestError("business.412.092", BusinessErorMessageList.ERROR_MESSAGE_412_092, null, ErrorType.WARNING)),

	API_BUSINESS_TOO_MANY_INDIVIDUS_FOUND(
			new RestError("business.412.093", BusinessErorMessageList.ERROR_MESSAGE_412_093, null, ErrorType.WARNING)),

	API_BUSINESS_INVALID_PARAMETERS_ACCOUNT_REQUEST(
			new RestError("business.412.094", BusinessErorMessageList.ERROR_MESSAGE_412_094, null, ErrorType.WARNING)),

	API_CANT_ACCESS_DB(
			new RestError("business.500.001", BusinessErorMessageList.ERROR_MESSAGE_500_001, null, ErrorType.WARNING)),
	
	API_ERROR_FROM_TALEND(
			new RestError("business.500.002", BusinessErorMessageList.ERROR_MESSAGE_500_002, null, ErrorType.WARNING)),
	
	API_UNKNOWN_TECHNICAL_ERROR(
			new RestError("business.500.003", BusinessErorMessageList.ERROR_MESSAGE_500_003, null, ErrorType.WARNING)),

	API_MS_TECHNICAL_ERROR(
			new RestError("business.500.004", BusinessErorMessageList.ERROR_MESSAGE_500_004, null, ErrorType.WARNING)),

	API_BUSINESS_ERROR(
			new RestError("business.1000", BusinessErorMessageList.ERROR_MESSAGE_1000, null, ErrorType.WARNING)),
	
	API_BUSINESS_HACHIKO_EXPERT_VALIDATION(
			new RestError("business.1007", BusinessErorMessageList.ERROR_MESSAGE_1007, null, ErrorType.WARNING));
	
	
	static Map<BusinessErrorList, RestError> enumMap = new EnumMap<>(BusinessErrorList.class);

	static {
		for (final BusinessErrorList element : values()) {
			enumMap.put(element, element.getError());
		}
	}

	public static void setEnumMap(final Map<BusinessErrorList, RestError> enumMap) {
		BusinessErrorList.enumMap = enumMap;
	}

	private RestError error;

	private BusinessErrorList(final RestError error) {
		this.error = error;
	}

	public RestError getError() {
		return error;
	}

}
