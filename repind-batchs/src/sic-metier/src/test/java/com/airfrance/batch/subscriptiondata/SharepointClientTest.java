package com.airfrance.batch.subscriptiondata;

import com.airfrance.batch.subscriptiondata.config.CollectSharepointSubscriptionDataProperties;
import com.airfrance.batch.subscriptiondata.model.SharePointGetListResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

class SharepointClientTest {
    private WebClient webClient;
    private SharepointClient sharepointClient;
    public static MockWebServer mockBackEnd;
    private CollectSharepointSubscriptionDataProperties collectSharepointSubscriptionDataPropertiesMock;
    private ConfigurableEnvironment environmentMock;


    @BeforeEach
    public void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        this.webClient = WebClient.builder().build();
        this.environmentMock = Mockito.mock(ConfigurableEnvironment.class);
        this.collectSharepointSubscriptionDataPropertiesMock = Mockito.mock(CollectSharepointSubscriptionDataProperties.class);
        sharepointClient = new SharepointClient(
                this.webClient,
                this.collectSharepointSubscriptionDataPropertiesMock,
                this.environmentMock
        );
    }

    @AfterEach
    public void cleanUp() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getListWithSkipTokenOnFirstMessage() {
        String urlMock = String.format("http://localhost:%s/", mockBackEnd.getPort());
        Mockito.when(collectSharepointSubscriptionDataPropertiesMock.getSharePointUrl())
                .thenReturn(urlMock);
        // First anwser with next link added by skip token
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\": [{\"id\":\"id1\", \"name\":\"name1\"}],\"@odata.nextLink\": \"$skiptoken=\"}")
                .addHeader("Content-Type", "application/json"));
        // Second answer with no skip token so should stop aftetr this one
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\":  [{\"id\":\"id2\", \"name\":\"name2\"}],\"@odata.nextLink\": \"nothing\"}")
                .addHeader("Content-Type", "application/json"));
        Mockito.when(environmentMock.getProperty("site")).thenReturn("mySite");
        SharePointGetListResponse response = sharepointClient.getList();
        Assertions.assertEquals(2, response.getValue().size());
        Assertions.assertEquals("id1", response.getValue().get(0).getId());
        Assertions.assertEquals("id2", response.getValue().get(1).getId());
    }

    @Test
    void getListWithSkipTokenOnFirstAndSecondMessage() {
        String urlMock = String.format("http://localhost:%s/", mockBackEnd.getPort());
        Mockito.when(collectSharepointSubscriptionDataPropertiesMock.getSharePointUrl())
                .thenReturn(urlMock);
        // First anwser with next link added by skip token
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\": [{\"id\":\"id1\", \"name\":\"name1\"}],\"@odata.nextLink\": \"$skiptoken=1\"}")
                .addHeader("Content-Type", "application/json"));
        // Second answer with skip token
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\":  [{\"id\":\"id2\", \"name\":\"name2\"}],\"@odata.nextLink\": \"$skiptoken=2\"}")
                .addHeader("Content-Type", "application/json"));
        // Third message with no skip token
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\":  [{\"id\":\"id3\", \"name\":\"name3\"}],\"@odata.nextLink\": \"nothing\"}")
                .addHeader("Content-Type", "application/json"));
        Mockito.when(environmentMock.getProperty("site")).thenReturn("mySite");
        SharePointGetListResponse response = sharepointClient.getList();
        Assertions.assertEquals(3, response.getValue().size());
        Assertions.assertEquals("id1", response.getValue().get(0).getId());
        Assertions.assertEquals("id2", response.getValue().get(1).getId());
        Assertions.assertEquals("id3", response.getValue().get(2).getId());
    }

    @Test
    void getListWithNoSkipTokenOnFirstMessage() {
        String urlMock = String.format("http://localhost:%s/", mockBackEnd.getPort());
        Mockito.when(collectSharepointSubscriptionDataPropertiesMock.getSharePointUrl())
                .thenReturn(urlMock);
        // First anwser with no skip token
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\": [{\"id\":\"id1\", \"name\":\"name1\"}],\"@odata.nextLink\": \"nothing\"}")
                .addHeader("Content-Type", "application/json"));
        // Second answer but we should have to call it
        mockBackEnd.enqueue(new MockResponse()
                .setBody("{\"value\":  [{\"id\":\"id2\", \"name\":\"name2\"}],\"@odata.nextLink\": \"nothing\"}")
                .addHeader("Content-Type", "application/json"));
        Mockito.when(environmentMock.getProperty("site")).thenReturn("mySite");
        SharePointGetListResponse response = sharepointClient.getList();
        Assertions.assertEquals(1, response.getValue().size());
        Assertions.assertEquals("id1", response.getValue().get(0).getId());
    }
}