package com.afklm.rigui.util;

import java.util.Collection;
import java.util.Map;

/**
 *
 * Check a list or map is Null or Empty 
 * 
 */
public final class UList {

	public static boolean isNullOrEmpty( final Collection< ? > c ) {
	    return c == null || c.isEmpty();
	}

	public static boolean isNullOrEmpty( final Map< ?, ? > m ) {
	    return m == null || m.isEmpty();
	}
}
