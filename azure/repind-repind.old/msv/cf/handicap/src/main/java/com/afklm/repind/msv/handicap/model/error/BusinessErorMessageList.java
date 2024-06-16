package com.afklm.repind.msv.handicap.model.error;

public class BusinessErorMessageList {

	public static final String ERROR_MESSAGE_400_001 = "Gin is mandatory";
	public static final String ERROR_MESSAGE_400_002 = "Type is mandatory";
	public static final String ERROR_MESSAGE_400_003 = "Code is mandatory";
	public static final String ERROR_MESSAGE_400_004 = "Application is mandatory";
	public static final String ERROR_MESSAGE_400_005 = "Data are mandatory";
	public static final String ERROR_MESSAGE_400_006 = "Id is mandatory";
	public static final String ERROR_MESSAGE_400_007 = "Key is mandatory";
	public static final String ERROR_MESSAGE_404_001 = "Handicap for this gin not found!";
	public static final String ERROR_MESSAGE_412_002 = "Invalid value for the 'gin' parameter, length must be equal to 12";
	public static final String ERROR_MESSAGE_412_003 = "Type or code not allowed";
	public static final String ERROR_MESSAGE_412_004 = "Key is not allowed for this type and this code";
	public static final String ERROR_MESSAGE_412_005 = "Key or value in the field 'data' can not be blank";
	public static final String ERROR_MESSAGE_412_006 = "The field 'data' must be filled";
	public static final String ERROR_MESSAGE_412_007 = "Gin not found";
	public static final String ERROR_MESSAGE_412_008 = "The code provided is not a valid code";
	public static final String ERROR_MESSAGE_412_009 = "Invalid type";
	public static final String ERROR_MESSAGE_412_010 = "Invalid application";
	public static final String ERROR_MESSAGE_412_011 = "This code is not allowed";
	public static final String ERROR_MESSAGE_412_012 = "The key is present more than once";
	public static final String ERROR_MESSAGE_412_013 = "Value in data is too long";
	public static final String ERROR_MESSAGE_412_014 = "Value in data is too short";
	public static final String ERROR_MESSAGE_412_015 = "The gin doesn't correspond to the customer of the handicap";
	public static final String ERROR_MESSAGE_412_016 = "Invalid date format";
	public static final String ERROR_MESSAGE_412_017 = "Unknow value. Boolean expected";
	public static final String ERROR_MESSAGE_412_018 = "Too much handicap with this type";
	public static final String ERROR_MESSAGE_412_019 = "Duplicate type and code";
	public static final String ERROR_MESSAGE_412_020 = "Key not existing for this Type and Code";
	public static final String ERROR_MESSAGE_412_021 = "This key is mandatory for this Type and Code";
	public static final String ERROR_MESSAGE_412_022 = "Key not found for this Id";

}
