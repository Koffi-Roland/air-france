package com.afklm.spring.security.habile.test;

import com.sun.xml.ws.transport.http.client.HttpTransportPipe;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles({"test"})
@ContextConfiguration() // Don't ask
public class SpringIntegrationTest {

    static {
        HttpTransportPipe.dump = true;

    }

}
