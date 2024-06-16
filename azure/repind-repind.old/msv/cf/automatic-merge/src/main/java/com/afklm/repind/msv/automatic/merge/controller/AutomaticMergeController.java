package com.afklm.repind.msv.automatic.merge.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.automatic.merge.service.AutomaticMergeService;
import com.afklm.repind.msv.automatic.merge.wrapper.MergeCriteria;
import com.afklm.repind.msv.automatic.merge.wrapper.MergeProvideCriteria;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMergeRequestBloc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping(value = "/api-mgr/merge")
@Slf4j
@AllArgsConstructor
public class AutomaticMergeController {

    private final AutomaticMergeService automaticMergeService;

    /**
     * Endpoint: Retrieve the information of the pair of individuals
     *
     * @param ginSource Source individual
     * @param ginTarget Target individual
     * @return The information of the pair of individuals
     * @throws BusinessException Generic error
     */
    @ApiOperation(value = "Retrieve the information of the pair of individuals",
            notes = "Retrieve the information of the pair of individuals like telecoms, postal addresses, GP Roles, Contract, Emails.<br />"
            , response = WrapperMerge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success: Return the information for each individuals"),
            @ApiResponse(code = 400, message = "Bad request:<br />"
                    + "- business.412.003 : Gin or FrequentFlyerNumber must not be empty<br />"
                    + "- business.412.005 : An individual can not merge himself<br />"
                    + "- business.412.018 : FB of first GIN can not be merged<br />"
                    + "- business.412.019 : FB of second GIN can not be merged<br />"
                    + "- business.412.020 : FB of first and second GIN can not be merged<br />"
                    + "- business.412.021 : None of the customers has FB contract<br />"
                    + "- business.412.022 : A mandatory parameter is missing in input<br />"
                    + "- business.412.017 : The direction of merge can not be defined automatically<br />"
                    + "- business.412.010 : Business unknown error"),
            @ApiResponse(code = 403, message = "Forbidden: <br />"
                    + "- business.412.015 : You don't have the habilitation to merge this kind of individual!"),
            @ApiResponse(code = 404, message = "Not Found: <br />"
                    + "- business.404.001 : Gin or frequentFlyerNumber not found"),
            @ApiResponse(code = 500, message = "Internal Error: <br />"
                    + "- business.500.003 : Technical unknown error<br />"
                    + "- business.500.001 : Can't access to DB"
            ),
    })
    @GetMapping(value = "/{ginSource}/{ginTarget}", produces = "application/json; charset=utf-8")
    public ResponseEntity<WrapperMerge> individualProvideForMerge(@ApiParam(required = true, name = "ginSource", value = "Gin of the individual who will be merged with the target individual")
                                                                  @PathVariable("ginSource") String ginSource,
                                                                  @ApiParam(required = true, name = "ginTarget", value = "Gin of the individual who will receive the elements from the source individual")
                                                                  @PathVariable("ginTarget") String ginTarget,
                                                                  @ApiParam(name = "forceSwitchIndividual", defaultValue = "false", value = "Force the switch between source and target")
                                                                      @QueryParam(value = "forceSwitchIndividual") boolean forceSwitchIndividual) throws BusinessException {
        WrapperMerge response;

        MergeProvideCriteria criteria = new MergeProvideCriteria(ginSource, ginTarget, forceSwitchIndividual);

        response = automaticMergeService.getMergeIndividuals(criteria);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint: Provides the summary of the merge
     *
     * @param ginSource  Source individual
     * @param ginTarget  Target individual
     * @param blocs Selected items to be transferred to the target
     * @return The resume of the merge
     * @throws BusinessException Generic Error
     */
    @ApiOperation(value = "Provides the summary of the merge",
            notes = "Provides the summary of the merge (without committing to the database) which takes as parameter selected elements that will be transferred to the target."
            , response = WrapperMerge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success: Return the merge summary"),
            @ApiResponse(code = 400, message = "Bad request:<br />"
                    + "- business.412.011 : Too many addresses after merging (Max: 5)<br />"
                    + "- business.412.084 : Too many GP Roles<br />"
                    + "- business.412.085 : Gin cible format is invalid<br />"
                    + "- business.412.087 : Gin source format is invalid<br />"
                    + "- business.412.088 : An error has occurred during the process<br />"
                    + "- business.412.089 : An error has occurred independent from the process<br />"),
            @ApiResponse(code = 404, message = "Not Found: <br />"
                    + "- business.404.001 : Gin or frequentFlyerNumber not found"),
            @ApiResponse(code = 500, message = "Internal Error: <br />"
                    + "- business.500.003 : Technical unknown error<br />"
                    + "- business.500.001 : Can't access to DB"
            ),
    })
    @PostMapping(
            value = "/resume/{ginSource}/{ginTarget}",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<WrapperMerge> individualMergeResume(@ApiParam(required = true, name = "ginSource", value = "Gin of the individual who will be merged with the target individual")
                                                              @PathVariable("ginSource") String ginSource,
                                                              @ApiParam(required = true, name = "ginTarget", value = "Gin of the individual who will receive the elements from the source individual")
                                                              @PathVariable("ginTarget") String ginTarget,
                                                              @ApiParam(required = true, name = "blocs", value = "List of elements with their type that will be transferred to the target individual")
                                                              @RequestBody List<WrapperMergeRequestBloc> blocs) throws BusinessException {
        MergeCriteria criteria = new MergeCriteria(ginSource, ginTarget, blocs);

        WrapperMerge response = automaticMergeService.individualMergeResume(criteria);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint: Launch the merge of two individuals
     *
     * @param ginSource  Source individual
     * @param ginTarget  Target individual
     * @param blocs Selected items to be transferred to the target
     * @return An empty body
     * @throws BusinessException Generic Error
     */
    @ApiOperation(value = "Launch the merge of two individuals",
            notes = "Launch the merge of two individuals and commit it to the database"
            , response = WrapperMerge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request:<br />"
                    + "- business.412.011 : Too many addresses after merging (Max: 5)<br />"
                    + "- business.412.084 : Too many GP Roles<br />"
                    + "- business.412.085 : Gin cible format is invalid<br />"
                    + "- business.412.087 : Gin source format is invalid<br />"
                    + "- business.412.088 : An error has occurred during the process<br />"
                    + "- business.412.089 : An error has occurred independent from the process<br />"),
            @ApiResponse(code = 404, message = "Not Found: <br />"
                    + "- business.404.001 : Gin or frequentFlyerNumber not found"),
            @ApiResponse(code = 500, message = "Internal Error: <br />"
                    + "- business.500.003 : Technical unknown error<br />"
                    + "- business.500.001 : Can't access to DB"
            ),
    })
    @PostMapping(
            value = "/{ginSource}/{ginTarget}",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Void> individualMerge(@ApiParam(required = true, name = "ginSource", value = "Gin of the individual who will be merged with the target individual")
                                                @PathVariable("ginSource") String ginSource,
                                                @ApiParam(required = true, name = "ginTarget", value = "Gin of the individual who will receive the elements from the source individual")
                                                @PathVariable("ginTarget") String ginTarget,
                                                @ApiParam(required = true, name = "blocs", value = "List of elements with their type that will be transferred to the target individual")
                                                @RequestBody List<WrapperMergeRequestBloc> blocs) throws BusinessException {

        MergeCriteria criteria = new MergeCriteria(ginSource, ginTarget, blocs, true);

        automaticMergeService.individualMerge(criteria);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
