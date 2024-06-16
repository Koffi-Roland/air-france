package com.afklm.batch.subscriptiondata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SharePointGetItemsResponse {
    private List<SharePointGetItemstItem> value;
}
