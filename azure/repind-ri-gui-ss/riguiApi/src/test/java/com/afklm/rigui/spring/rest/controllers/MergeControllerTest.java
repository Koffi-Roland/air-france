package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.MergeService;
import com.afklm.rigui.wrapper.merge.WrapperEmailMerge;
import com.afklm.rigui.wrapper.merge.WrapperMerge;
import com.afklm.rigui.wrapper.merge.WrapperProvideMerge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MergeControllerTest {

    private static final String GIN1 = "123456789";
    private static final String GIN2 = "987654321";

    @Mock
    private MergeService mergeService;

    @InjectMocks
    private MergeController mergeController;

    @Test
    public void IndividualMerge() throws ServiceException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        mergeController.IndividualMerge(GIN1, GIN2, List.of(), request);
        verify(mergeService, times(1)).individualMerge(any());
    }

    @Test
    public void IndividualMergeResume() throws ServiceException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        mergeController.IndividualMergeResume(GIN1, GIN2, List.of(),request);
        verify(mergeService, times(1)).individualMergeResume(any());
    }

    @Test
    public void individualProvideForMerge() throws ServiceException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        WrapperMerge result = new WrapperMerge();
        result.isExpertRequired = true;
        result.isSamePerson = true;
        result.individualSource = new WrapperProvideMerge();
        WrapperEmailMerge emailSource = new WrapperEmailMerge();
        emailSource.email = new ModelEmail();
        emailSource.email.setEmail("source@toto.com");
        result.individualSource.emails = List.of(emailSource);
        result.individualTarget = new WrapperProvideMerge();
        WrapperEmailMerge emailTarget = new WrapperEmailMerge();
        emailTarget.email = new ModelEmail();
        emailTarget.email.setEmail("target@toto.com");
        result.individualTarget.emails = List.of(emailTarget);
        when(mergeService.getMergeIndividuals(any())).thenReturn(result);
        WrapperMerge response = mergeController.individualProvideForMerge("ID1", "ID2", true, request).getBody();
        assertTrue(response.isExpertRequired);
        assertTrue(response.isSamePerson);
        assertEquals("source@toto.com", response.individualSource.emails.get(0).email.getEmail());
        assertEquals("target@toto.com", response.individualTarget.emails.get(0).email.getEmail());
    }

}
