package com.afklm.rigui.services.helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.afklm.rigui.util.UList;

public class GenerateCSVHelper {


	/**
	 * Create a CSV using array<String>. Get all object data of object pass and
	 * display it on CSV USE IT FOR CUSTOM QUERY THAT RETURNED List<Object[]>
	 * 
	 * @param datas
	 *            -> list of data to transform to CSV
	 * @param ->
	 *            name of header tab in csv
	 * @return InputStream wich is the CSV file
	 */
	public static InputStream generateCSVObject(List<Object[]> datas, String[] tableHeader) {
		if (UList.isNullOrEmpty(datas)) {
			return null;
		}

		//Create the header column of the CSV
		StringBuilder result = new StringBuilder();
		for (String header : tableHeader) {
			result.append(header);
			result.append(";");
		}

		//For each object in the list, we iterate to get all data available
		for (Object[] data : datas) {
			StringBuilder line = new StringBuilder();

			//For data in object
			for (int i = 0; i < data.length; i++) {
				line.append(data[i]);
				line.append(";");
			}
			result.append("\n");
			result.append(line.toString());
		}

		return new ByteArrayInputStream(result.toString().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Create a CSV using array<String>. Get all field of object type pass and
	 * display it on CSV
	 * 
	 * @param datas
	 *            -> list of data to transform to CSV
	 * @param ->
	 *            name of header tab in csv
	 * @return InputStream wich is the CSV file
	 */
	public static InputStream generateCSV(List<Object> datas, String[] tableHeader) {
		if (UList.isNullOrEmpty(datas)) {
			return null;
		}

		//Create the header column of the CSV
		StringBuilder result = new StringBuilder();
		for(String header : tableHeader) {
			result.append(header + ";");
		}

		//For each object in the list, we iterate to get all fields available
		for (Object data : datas) {
			String line = "";
			Field[] fields = data.getClass().getDeclaredFields();
			//For each fields, we write the value if not empty
			for (Field field : fields) {
				String value;
				value = GetValueOfFieldPrivate(field, data);
				if (!StringUtils.isEmpty(value)) {
					line += value + ";";
				} else {
					line += "";
				}
			}
			result.append("\n" + line);
		}

		return new ByteArrayInputStream(result.toString().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Create a CSV using a hashMap. Search all key if exist has field of class
	 * object or all superclass (excluding Object.class).
	 * 
	 * @param datas
	 *            -> list of data to transform to CSV
	 * @param tableHeader
	 *            -> hashmap using : - Key -> name of field to search in object
	 *            and to display in csv - Value -> name of header tab in csv
	 * @return InputStream wich is the CSV file
	 */
	public static InputStream generateCSV(List<Object> datas, HashMap<String, String> tableHeader) {
		if (UList.isNullOrEmpty(datas)) {
			return null;
		}

		//Create the header column of the CSV
		StringBuilder result = new StringBuilder();
		for(String header : tableHeader.values()) {
			result.append(header + ";");
		}

		//For each object in the list, we iterate to get only value available in the hashmap
		for (Object data : datas) {
			String line = "";
			//For each key, we are looking for a field for this object
			for (String fieldName : tableHeader.keySet()) {
				String value = "";
				Field field = null;
				//Get the class of the object
				Class classToAnalyse = data.getClass();
				//While the class isn't Object, we iterate
				while (classToAnalyse != Object.class) {
					try {
						//Get the field if existing for this class
						field = classToAnalyse.getDeclaredField(fieldName);
						if(field != null) {
							value = GetValueOfFieldPrivate(field, data);
							//Set to Object to break the loop
							classToAnalyse = Object.class;
						}
					} catch(NoSuchFieldException ex) {
						//If we catch the exception that means that the field isn't for the class, maybe it's a field of superclass
						classToAnalyse = classToAnalyse.getSuperclass();
					}
				}
				if (!StringUtils.isEmpty(value)) {
					line += value + ";";
				} else {
					line += "";
				}
		}
			result.append("\n" + line);
		}
		return new ByteArrayInputStream(result.toString().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Return the value of a privateField
	 * 
	 * @param privateField
	 *            -> the field to get the value
	 * @param fromObject
	 *            -> the object from which we want the value
	 * @return String which is the value
	 */
	private static String GetValueOfFieldPrivate(Field privateField, Object fromObject) {
		String value = "";
		try {
			//Mandatory for getting value of private field
			privateField.setAccessible(true);
			//Get the data from field
			value = privateField.get(fromObject).toString();
			//Reset has private
			privateField.setAccessible(false);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Take 2 array of string to create a hashmap<String,String> The Hashmap
	 * will have the min length of the two array
	 * 
	 * @param key
	 *            -> the array of key
	 * @param value
	 *            -> The array of value
	 * @return
	 */
	public static HashMap<String, String> generateHashMap(String[] key, String[] value) {
		HashMap<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < key.length; i++) {
			if (i < value.length) {
				result.put(key[i], value[i]);
			}
		}
		return result;
	}
}
