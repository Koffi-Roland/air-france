package com.afklm.repind.msv.provide.last.activity.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.dto.LastActivityDto;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.service.LastActivityService;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Last activity controller
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/last-activity")
public class LastActivityController {

    /**
     * Last activity service - inject by spring
     */
    private final LastActivityService lastActivityService;

    /**
     * Last activity mapper - inject by spring
     */
    private final LastActivityMapper lastActivityMapper;


    /**
     * Provide Last activity according to the given gin
     *
     * @param gin Individual number
     * @return Last Activity
     */
    @Operation(summary = "Last activity of an individual", description = "Get last activity according to gin of an individual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Missing request  :<br />"
                    + "- business.400.006 : Gin is mandatory"),
            @ApiResponse(responseCode = "404", description = "Not found : <br />"
                    + "- business.404.001 : Last activity not found for this Gin")
    })
    @GetMapping(value = "/{gin}", produces = "application/json; charset=utf-8")
    public ResponseEntity<LastActivityDto> provideLastActivityByGin(
            @Parameter(required = true, name = "gin", description = "Gin of an individual") @PathVariable("gin") final String gin) throws BusinessException
    {
        log.info("GET for " + gin);
        LastActivity lastActivity = this.lastActivityService.getLastActivityByGin(gin);
        // Apply mapper and return last activity dto
        return ResponseEntity.ok(this.lastActivityMapper.mapToLastActivityDto(lastActivity));

    }
}
