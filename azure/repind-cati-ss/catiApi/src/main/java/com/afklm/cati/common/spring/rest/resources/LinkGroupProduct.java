package com.afklm.cati.common.spring.rest.resources;

import java.util.List;

public class LinkGroupProduct extends CatiCommonResource {

    private Integer productId;
    private List<Integer> groupsId;
    
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public List<Integer> getGroupsId() {
		return groupsId;
	}
	public void setGroupsId(List<Integer> groupsId) {
		this.groupsId = groupsId;
	}
    
}
