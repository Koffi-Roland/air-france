package com.afklm.spring.security.habile.websocket;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jakarta.websocket.DeploymentException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.HabilePrincipalFixture;
import com.afklm.spring.security.habile.SpringSecurityHabileApplication;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDao;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDaoImplPing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketTest.class);

    @MockBean
    private UserRetrieverDao userRetrieverDao;

    @MockBean
    private UserRetrieverDaoImplPing userRetrieverPingDao;

    static final String MSG_TO_SERVER = "THIS IS MY MESSAGE";
    static final String WEBSOCKET_TOPIC_OUT = "/app/topicToServer";
    static final String WEBSOCKET_TOPIC_OUT_NOT_GRANTED = "/app/topicToServerNotGranted";
    static final String WEBSOCKET_TOPIC_IN = "/topic/topicFromServer";

    @Value("${local.server.port}")
    private int port;
    private String URL;

    final private String userId = "toto";

    private BlockingQueue<String> blockingQueue;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();

        URL = "ws://localhost:" + port + "/api/websocket";
        HabilePrincipal habilePrincipal = HabilePrincipalFixture.habilePrincipal(userId, "P_TEST");

        Mockito.when(userRetrieverDao.getUser(Mockito.anyString(), Mockito.anyString())).thenReturn(habilePrincipal);
    }

    @Test
    public void testConnectAndSend()
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
        assertEquals(body.getStatusCode(), HttpStatus.OK);
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
            .connect(URL, new WebSocketHttpHeaders(headers), stompHeaders, new StompSessionHandlerAdapter() {}/*new DummySessionHandler()*/)
            .get(10, TimeUnit.SECONDS);
        LOGGER.info("asserting session id");
        assertThat(stompSession.getSessionId()).isNotBlank();

        stompSession.subscribe(WEBSOCKET_TOPIC_IN, new DefaultStompFrameHandler());

        // send message and retrieve a response back
        stompSession.send(WEBSOCKET_TOPIC_OUT, MSG_TO_SERVER.getBytes());
        LOGGER.info("asserting message send/retrieve");
        assertEquals(SpringSecurityHabileApplication.MSG_TO_CLIENT, blockingQueue.poll(1, SECONDS));

        // send message without a csrf token set but session is ok and check we do not have any response back
        stompSession.send(WEBSOCKET_TOPIC_OUT, MSG_TO_SERVER.getBytes());
        LOGGER.info("asserting message send/retrieve");
        assertEquals(SpringSecurityHabileApplication.MSG_TO_CLIENT, blockingQueue.poll(1, SECONDS));

        stompSession.disconnect();
        stompClient.stop();
    }

    @Test
    public void testConnectKo() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());

        assertThatExceptionOfType(ExecutionException.class).isThrownBy(() -> {
            stompClient.connect(URL, new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {}).get(1, SECONDS);
        })
            .withCauseInstanceOf(DeploymentException.class)
            .withMessageContaining("response code [401]");
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object payload) {
            LOGGER.info("DSFH -> handleFrame with payload: {}", payload);
            blockingQueue.offer(new String((byte[]) payload));
        }
    }
    
    class DummySessionHandler extends StompSessionHandlerAdapter {
        
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            LOGGER.info("DSH -> afterConnected -> session id is: {}", session.getSessionId());
        }

        @Override
        public void handleException(StompSession session, @Nullable StompCommand command,
                        StompHeaders headers, byte[] payload, Throwable exception) {
            LOGGER.info("DSH -> handlingException '{}' for command '{}'", exception.getMessage(), command.name());
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            LOGGER.info("DSH -> handleTransportError", exception);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            LOGGER.info("DSH -> handleFrame with payload: {}", payload);
            try {
                blockingQueue.offer((String) payload, 500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
