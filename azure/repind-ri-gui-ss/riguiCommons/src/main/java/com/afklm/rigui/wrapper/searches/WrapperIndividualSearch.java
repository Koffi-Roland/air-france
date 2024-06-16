package com.afklm.rigui.wrapper.searches;

import java.util.ArrayList;
import java.util.List;

import com.afklm.rigui.model.individual.ModelBasicIndividualData;

public class WrapperIndividualSearch {
	
	public int count;
	public List<ModelBasicIndividualData> data;

	public WrapperIndividualSearch() {
		this.count = 0;
		this.data = new ArrayList<ModelBasicIndividualData>();
	}

}
