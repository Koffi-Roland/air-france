package com.airfrance.repind.util.transformer;

import com.airfrance.repind.scope.FirmScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ghayth AYARI
 *
 */
public class ScopeTransformer {

	public static List<String> scopesList2StringList(List<FirmScope> scopesList) {
		
		// Prepare result to return
		List<String> stringList = null;
		
		if (scopesList != null) {
			stringList = new ArrayList<String>();
			
			// Loop over scopes
			for (FirmScope firmScope : scopesList) {
				// Get string scope and add it to the list
				stringList.add(firmScope.getFirmProvideScope().toString());
			}
		}
		
		return stringList;
	}
}
