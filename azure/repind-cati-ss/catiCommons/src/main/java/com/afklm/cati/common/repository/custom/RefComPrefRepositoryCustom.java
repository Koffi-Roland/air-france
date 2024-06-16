package com.afklm.cati.common.repository.custom;

import com.afklm.cati.common.entity.RefComPref;

import java.util.List;

;

public interface RefComPrefRepositoryCustom {

    Long countRefComPref(String domain, String language, String market);

    List<RefComPref> provideRefComPrefWithPagination(String domain, String language, String market, int index, int maxResults);

}
