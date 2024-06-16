package com.afklm.rigui.repository.custom;

import java.util.List;

public interface MergeStatistiquesRepositoryCustom {
	List<Object[]> findByGinMergeNotNull();
}