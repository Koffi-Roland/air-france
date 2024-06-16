package com.afklm.batch.subscriptiondata;

import com.afklm.batch.subscriptiondata.config.CollectSharepointSubscriptionDataProperties;
import com.afklm.batch.subscriptiondata.model.SharePointGetItemsResponse;
import com.afklm.batch.subscriptiondata.model.SharePointGetListResponse;
import com.afklm.batch.subscriptiondata.model.SharePointItemsFields;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import javax.inject.Inject;
import java.util.Map;

@Slf4j
@Service
public class SharepointClient {
    public static final String SHAREPOINT_REGISTRATION_ID = "sharepoint";

    @Inject
    private WebClient webClient;
    @Inject
    CollectSharepointSubscriptionDataProperties collectSharepointSubscriptionDataProperties;
    @Inject
    private ConfigurableEnvironment environment;


    public SharePointGetListResponse getList() {

        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        log.info("Trying to get lists from share Point URL : " +sharePointUrl);
        return webClient.get()
                .uri(sharePointUrl + "/lists")
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
                        .clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointGetListResponse.class).log().block();
    }

    public SharePointGetItemsResponse getListItems(String listId) {
        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        return webClient.get()
                .uri(sharePointUrl + "/lists/" + listId + "/items?expand=fields&top=30000")
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointGetItemsResponse.class).log().block();
    }

    public void deleteItemListId(String listId, String itemId) {
        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        webClient.post()
                .uri(sharePointUrl + "/lists/" + listId + "/items/" + itemId)
                .header("X-HTTP-Method", "DELETE")
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointGetItemsResponse.class).log().block();
    }

    public void updateItemListId(String listId, String itemId, Map<String, String> fields) {
        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        webClient.patch()
                .uri(sharePointUrl + "/lists/" + listId + "/items/" + itemId + "/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(fields))
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointItemsFields.class).log().block();
    }
}
