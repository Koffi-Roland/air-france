package com.afklm.spring.security.habile.demo.web;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDao;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {

    static final String MSG_TO_SERVER = "THIS IS MY MESSAGE";
    static final String WEBSOCKET_TOPIC_OUT = "/app/topicToServerWithViewAuth";
    static final String WEBSOCKET_TOPIC_OUT_UPD = "/app/topicToServerWithUpdateAuth";
    static final String WEBSOCKET_TOPIC_IN = "/topic/topicFromServer";

    @Value("${local.server.port}")
    private int port;
    private String URL;
    final private String userId = "mytestUser";

    private CompletableFuture<String> completableFuture;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserRetrieverDao userRetrieverDao;

    @BeforeEach
    public void setup() {
        URL = "ws://localhost:" + port + "/api/websocket";
        HabilePrincipal habilePrincipal = habilePrincipal(userId, "P_XXXXX_USER");
        Mockito.when(userRetrieverDao.getUser(userId, "N/A")).thenReturn(habilePrincipal);
    }

    @Test
    public void testConnectedMode()
            throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        //
        // Step 1 call /me endpoint to get jsessionId and csrf token
        //
        HttpHeaders headers = new HttpHeaders();
        headers.add("SM_USER", userId);
        StringBuilder sb = new StringBuilder();
        ResponseEntity<String> body = this.restTemplate.exchange("/me",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class);
        // Should be connected now
        assertThat(body.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, HttpCookie> cookiesMap = new HashMap<>();
        // Process returned cookies
        for (String header : body.getHeaders().get("Set-Cookie")) {
            for (HttpCookie cookie : HttpCookie.parse(header)) {
                cookiesMap.put(cookie.getName(), cookie);
                if ("XSRF-TOKEN".equals(cookie.getName())) {
                    headers.add("X-XSRF-TOKEN", cookie.getValue());
                }
                sb.append(cookie.getName()).append('=').append(cookie.getValue()).append(";");
            }
        }
        // Check that cookies jsessionId and XSRF-TOKEN exist
        assertThat(cookiesMap).hasSize(2);
        assertThat(cookiesMap).containsKeys("JSESSIONID", "XSRF-TOKEN");

        String xXsrfToken = headers.get("X-XSRF-TOKEN").get(0);
        headers = new HttpHeaders();
        headers.add("Cookie", sb.toString());

        // SM_USER set to "" to copy the AF F5 way of working
        headers.add("SM_USER", "");

        //
        // Step 2 Connect WebSocket
        //
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        // stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("X-XSRF-TOKEN", xXsrfToken);
        StompSession stompSession = stompClient
            .connect(URL, new WebSocketHttpHeaders(headers), stompHeaders, new StompSessionHandlerAdapter() {})
            .get(1, TimeUnit.SECONDS);
        assertThat(stompSession.getSessionId()).isNotBlank();

        stompSession.subscribe(WEBSOCKET_TOPIC_IN, new DefaultStompFrameHandler());

        // send message and retrieve a response back
        completableFuture = new CompletableFuture<>();
        stompSession.send(WEBSOCKET_TOPIC_OUT, MSG_TO_SERVER.getBytes());
        Assertions.assertEquals(
            "BROADCAST VIEW: " + MSG_TO_SERVER,
            completableFuture.get(1, SECONDS),
            "Error during post to endpoint protected with UPDATE authority");

        // send message to a protected resource and make sure there is no response back
        // we wait 10 seconds to to make sure there is no latency in the result
        completableFuture = new CompletableFuture<>();
        stompSession.send(WEBSOCKET_TOPIC_OUT_UPD, MSG_TO_SERVER.getBytes());
        assertThatExceptionOfType(TimeoutException.class).isThrownBy(() -> {
            completableFuture.get(10, SECONDS);
        });

        stompSession.disconnect();
        stompClient.stop();
    }

    @Test
    public void testConnectNotAuthorized() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        assertThatExceptionOfType(ExecutionException.class)
            .isThrownBy(() -> {
                stompClient.connect(URL, new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {}).get(3, SECONDS);
            })
            .withMessageContaining("401");
    }

    private HabilePrincipal habilePrincipal(String id, String... profilesToBeInjected) {
        String userId = "JUNIT-" + id;
        String firstName = "firstName_" + id;
        String lastName = "lastName_" + id;
        String email = lastName + "@fxture.org";
        List<String> profiles = new ArrayList<>();
        for (String profile : profilesToBeInjected) {
            profiles.add(profile);
        }
        return new HabilePrincipal(userId, firstName, lastName, email, profiles);
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            // blockingQueue.offer(new String((byte[]) o));
            completableFuture.complete(new String((byte[]) o));
        }
    }

}
