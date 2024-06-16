package com.afklm.rigui.util.service;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.TableReferenceEnum;

public class PaginationUtils {
	
    private static final int MAX_RESULTS_BY_DEFAULT = 200;

	public static <T> void getIndexAndMaxResultsForPagination(PaginationResult<T> paginationResult, int index, int maxResults, TableReferenceEnum table, Long totalResultsFound) throws JrafDomainException {
		Integer totalResults = (totalResultsFound == null) ? 0 : totalResultsFound.intValue();
		
		//First index is 0 in the DB
		if (index <= 0) {
			throw new InvalidParameterException("Index starts at 1");
		}
		if (index > 0 && index > totalResults && totalResults > 0) {
			throw new InvalidParameterException("Index is too high");
		}
		if (maxResults < 0) {
			throw new InvalidParameterException("maxResult must be positive");
		}
		
		//If consumer ask with maxResults = 0 with use the DefaultMaxResult set in TableReferenceEnum
		//If a table default max is 0, we set to 200 (max in output of the wsdl)
		
		if (maxResults == 0) {
			if (table.getDefaultMaxResults() == 0) {
				maxResults = MAX_RESULTS_BY_DEFAULT;
			} else {
				maxResults = table.getDefaultMaxResults();
			}
		//If a value is filled, we check with default max for the related table
		} else if (table.getDefaultMaxResults() == 0) {
			
			if (maxResults > MAX_RESULTS_BY_DEFAULT) {
				maxResults = MAX_RESULTS_BY_DEFAULT;
			}
			
		} else {
			if (maxResults > table.getDefaultMaxResults()) {
				maxResults = table.getDefaultMaxResults() ;
			}
		}
		
		paginationResult.setIndex(index - 1);
		paginationResult.setMaxResults(maxResults);
		paginationResult.setTotalResults(totalResults);
	}
	
	
}
