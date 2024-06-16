package com.airfrance.batch.subscriptiondata;

import com.airfrance.batch.subscriptiondata.config.CollectSharepointSubscriptionDataProperties;
import com.airfrance.batch.subscriptiondata.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Map;

@Slf4j
@Service
public class SharepointClient {
    public static final String SHAREPOINT_REGISTRATION_ID = "sharepoint";

    public static final String LISTS_DIRECTORY = "/lists/";
    public static final String ITEMS_DIRECTORY = "/items/";

    private final WebClient webClient;

    private final CollectSharepointSubscriptionDataProperties collectSharepointSubscriptionDataProperties;

    private final ConfigurableEnvironment environment;

    @Inject
    public SharepointClient(WebClient webClient, CollectSharepointSubscriptionDataProperties collectSharepointSubscriptionDataProperties, ConfigurableEnvironment environment) {
        this.webClient = webClient;
        this.collectSharepointSubscriptionDataProperties = collectSharepointSubscriptionDataProperties;
        this.environment = environment;
    }


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
                .uri(sharePointUrl + LISTS_DIRECTORY + listId + "/items?expand=fields&top=30000")
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointGetItemsResponse.class).log().block();
    }

    public void deleteItemListId(String listId, String itemId) {
        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        webClient.post()
                .uri(sharePointUrl + LISTS_DIRECTORY + listId + ITEMS_DIRECTORY + itemId)
                .header("X-HTTP-Method", "DELETE")
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    if (response.statusCode() == HttpStatus.BAD_REQUEST) {
                        // Log the error and move on to the next request
                        log.error("Failed to delete item {} from list {}. Error: {}", itemId, listId, response.statusCode());
                        return Mono.empty();
                    } else {
                        // Throw an exception for other error codes
                        return response.createException();
                    }
                })
                .bodyToMono(SharePointGetItemsResponse.class).log().block();
    }

    public void updateItemListId(String listId, String itemId, Map<String, String> fields) {
        String sharePointUrl = collectSharepointSubscriptionDataProperties.getSharePointUrl()+environment.getProperty("site");
        webClient.patch()
                .uri(sharePointUrl + LISTS_DIRECTORY + listId + ITEMS_DIRECTORY + itemId + "/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(fields))
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(SHAREPOINT_REGISTRATION_ID))
                .retrieve().onStatus(HttpStatus::isError, ClientResponse::createException).
                bodyToMono(SharePointItemsFields.class).log().block();
    }
}
