package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPref;

import java.util.List;

public interface RefComPrefRepositoryCustom {
	
	Long countRefComPref(String domain, String language, String market);
	
	List<RefComPref> provideRefComPrefWithPagination(String domain, String language, String market, int index, int maxResults);
	
}
