package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.criteria.merge.MergeCriteria;
import com.afklm.rigui.criteria.merge.MergeProvideCriteria;
import com.afklm.rigui.criteria.merge.RoleHabilitationCriteria;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.MergeService;
import com.afklm.rigui.wrapper.merge.WrapperMerge;
import com.afklm.rigui.wrapper.merge.WrapperMergeRequestBloc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author AF-KLM
 */

@RestController
@RequestMapping("/merge")
public class MergeController {

    @Autowired
    MergeService mergeService;

    @RequestMapping(method = RequestMethod.GET, value = "/{identifiant1}/{identifiant2}/{force}", produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP', 'ROLE_MERGE_FB_MINOR')")
    public ResponseEntity<WrapperMerge> individualProvideForMerge(@PathVariable("identifiant1") String identifiant1,
                                                                  @PathVariable("identifiant2") String identifiant2, @PathVariable("force") boolean force,
                                                                  HttpServletRequest httpServletRequest) throws ServiceException, IOException {

        MergeProvideCriteria criteria = new MergeProvideCriteria(identifiant1, identifiant2,
                this.createRoleHabilitationCriteria(httpServletRequest),
                force);
        WrapperMerge response = mergeService.getMergeIndividuals(criteria);


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/resume/{gin0}/{gin1}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP', 'ROLE_MERGE_FB_MINOR')")
    public ResponseEntity<WrapperMerge> IndividualMergeResume(@PathVariable("gin0") String gin0,
                                                              @PathVariable("gin1") String gin1, @RequestBody List<WrapperMergeRequestBloc> blocs, HttpServletRequest httpServletRequest) throws ServiceException, IOException {

        MergeCriteria criteria = new MergeCriteria(gin0, gin1, blocs, false,
                this.createRoleHabilitationCriteria(httpServletRequest));

        WrapperMerge response = mergeService.individualMergeResume(criteria);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{gin0}/{gin1}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP', 'ROLE_MERGE_FB_MINOR')")
    public ResponseEntity<Void> IndividualMerge(@PathVariable("gin0") String gin0,
                                                @PathVariable("gin1") String gin1, @RequestBody List<WrapperMergeRequestBloc> blocs, HttpServletRequest httpServletRequest) throws ServiceException, IOException {
        MergeCriteria criteria = new MergeCriteria(gin0, gin1, blocs, true,
                this.createRoleHabilitationCriteria(httpServletRequest));


        mergeService.individualMerge(criteria);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private RoleHabilitationCriteria createRoleHabilitationCriteria(HttpServletRequest requestHTTP) {
        return new RoleHabilitationCriteria(requestHTTP.isUserInRole("ROLE_MERGE_FB"),
                requestHTTP.isUserInRole("ROLE_ADMIN"), requestHTTP.isUserInRole("ROLE_MERGE_GP"),
                requestHTTP.isUserInRole("ROLE_MERGE_FB_MINOR"));
    }

}
