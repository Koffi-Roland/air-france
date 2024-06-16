package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefPermissionsQuestionResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
public class RefPermissionsQuestionResourceTest {

    private static final String TEST = "Test";

    @Test
    @DisplayName("Unit test ref group product resource")
    public void permissionsQuestionResourceTest() {
        RefPermissionsQuestionResource refGroupProductResource = this.mockPermissionsQuestionResource();
        assertAll(
                () -> assertEquals(refGroupProductResource.getName(), TEST),
                () -> assertEquals(refGroupProductResource.getQuestion(), TEST),
                () -> assertEquals(refGroupProductResource.getQuestionEN(), TEST),
                () -> assertEquals(refGroupProductResource.getSignatureCreation(), TEST),
                () -> assertEquals(refGroupProductResource.getSignatureModification(), TEST),
                () -> assertEquals(refGroupProductResource.getSiteCreation(), TEST),
                () -> assertEquals(refGroupProductResource.getSignatureCreation(), TEST)
        );
    }
    private RefPermissionsQuestionResource mockPermissionsQuestionResource()
    {
        RefPermissionsQuestionResource refPermissionsQuestionResource = new RefPermissionsQuestionResource();

        refPermissionsQuestionResource.setName(TEST);
        refPermissionsQuestionResource.setQuestion(TEST);
        refPermissionsQuestionResource.setQuestionEN(TEST);
        Date date = new Date();
        refPermissionsQuestionResource.setDateCreation(date);
        refPermissionsQuestionResource.setDateModification(date);
        refPermissionsQuestionResource.setSignatureCreation(TEST);
        refPermissionsQuestionResource.setSignatureModification(TEST);
        refPermissionsQuestionResource.setSiteCreation(TEST);
        refPermissionsQuestionResource.setSiteModification(TEST);

        return refPermissionsQuestionResource;
    }
}
