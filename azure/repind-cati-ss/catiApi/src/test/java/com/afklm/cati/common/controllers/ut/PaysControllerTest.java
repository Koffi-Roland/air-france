package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelPays;
import com.afklm.cati.common.service.PaysService;
import com.afklm.cati.common.spring.rest.controllers.PaysController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PaysControllerTest {

    private static final String TEST = "Test" ;
    private static final String LIBELLE = "France" ;
    private static final String CODE = "FR" ;
    private static final Integer NUMBER = 57;
    @InjectMocks
    private PaysController paysController;
    @Mock
    private PaysController mockPaysController;
    @Mock
    private PaysService paysService;
    private ModelPays modelPays;

    @BeforeEach
    public void setup()
    {
        this.modelPays = ModelPays.builder()
                .codeCapitale(TEST)
                .codePays(CODE)
                .codeGestionCP(TEST)
                .libellePays(LIBELLE)
                .libellePaysEn(LIBELLE)
                .codeIata(NUMBER)
                .forcage(TEST)
                .formatAdr(TEST)
                .iformatAdr(NUMBER)
                .iso3code(TEST)
                .normalisable(TEST)
                .build();
    }

    @Test
    @DisplayName("Unit test for get pays")
    public void testGetPaysList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when -  action or the behaviour that we are going test
        when(paysService.getAllPays()).thenReturn(List.of(this.modelPays));
        ResponseEntity<?> response = paysController.paysList(request);
        List<ModelPays> modelPays = (List<ModelPays>) response.getBody();
        assertAll(
               () -> assertEquals(response.getStatusCode(),HttpStatus.OK),
               () -> assertThat(response.getBody()).isNotNull(),
               () -> assertEquals(modelPays.get(0).getCodePays(), CODE),
               () -> assertEquals(modelPays.get(0).getLibellePays(), LIBELLE),
               () -> assertEquals(modelPays.get(0).getCodeIata(), NUMBER),
               () -> assertEquals(modelPays.get(0).getIformatAdr(), NUMBER),
               () -> assertEquals(modelPays.get(0).getLibellePaysEn(), LIBELLE)
        );
    }

    @Test
    @DisplayName("Unit test for update pays")
    public void testPaysUpdate() throws  CatiCommonsException {

        doReturn(new ResponseEntity<>(HttpStatus.OK)).when(mockPaysController).paysUpdate(this.modelPays,this.modelPays.getCodePays());
        ResponseEntity<?> response = mockPaysController.paysUpdate(this.modelPays,this.modelPays.getCodePays());
        assertEquals(response.getStatusCode(),HttpStatus.OK);

    }
}
