/**
 * 
 */
package com.afklm.cati.common.test.mock;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

/**
 * @author DINB - TA
 *
 */
public final class MockUtils {
	
	/**
	 * Sets by reflection a value in the specified field of the instance.
	 * @param instance the instance on which to set the value.
	 * @param property the propertu 
	 */
	public static void setField(Object instance, String property, Object value) {
		Field findField = ReflectionUtils.findField(
				instance.getClass(), property);
		findField.setAccessible(true);
		ReflectionUtils.setField(findField, instance, value);
		findField.setAccessible(false);
	}

}
