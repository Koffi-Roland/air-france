package com.afklm.spring.security.habile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A Test controller to mock endpoints for test purposes.
 */
@RestController()
public class TestController {

    @PostMapping("/ws/tryme")
    public HttpStatus postTryMe() {
        return HttpStatus.OK;
    }
}
