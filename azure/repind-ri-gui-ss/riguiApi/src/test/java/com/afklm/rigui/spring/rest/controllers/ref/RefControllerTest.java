package com.afklm.rigui.spring.rest.controllers.ref;

import com.afklm.rigui.services.ref.RefService;
import com.afklm.rigui.wrapper.ref.WrapperRefTable;
import com.airfrance.reef.reftable.RefTableChamp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RefControllerTest {

    @InjectMocks
    private RefController refController;
    @Mock
    private RefService refService;
    @Test
    public void getRefCivilities() {
        RefTableChamp data = new RefTableChamp(
               "CIV",
               "FR",
               "EN"
        );
        when(refService.findRefCivilities()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefCivilities().getBody();
        assertEquals(1, fields.size());
        assertEquals("CIV", fields.get(0).getCode());
    }
    @Test
    public void getRefTitles() {
        RefTableChamp data = new RefTableChamp(
                "TITLE",
                "FR",
                "EN"
        );
        when(refService.findRefTitles()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefTitles().getBody();
        assertEquals(1, fields.size());
        assertEquals("TITLE", fields.get(0).getCode());
    }
    @Test
    public void getRefGenders() {
        RefTableChamp data = new RefTableChamp(
                "GENDER",
                "FR",
                "EN"
        );
        when(refService.findRefGenders()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefGenders().getBody();
        assertEquals(1, fields.size());
        assertEquals("GENDER", fields.get(0).getCode());
    }
    @Test
    public void getRefStatus() {
        RefTableChamp data = new RefTableChamp(
                "STATUS",
                "FR",
                "EN"
        );
        when(refService.findRefStatus()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefStatus().getBody();
        assertEquals(1, fields.size());
        assertEquals("STATUS", fields.get(0).getCode());
    }
    @Test
    public void getRefNat() {
        RefTableChamp data = new RefTableChamp(
                "NAT",
                "FR",
                "EN"
        );
        when(refService.findRefNat()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefNat().getBody();
        assertEquals(1, fields.size());
        assertEquals("NAT", fields.get(0).getCode());
    }
    @Test
    public void getRefBranches() {
        RefTableChamp data = new RefTableChamp(
                "BRANCHE",
                "FR",
                "EN"
        );
        when(refService.findRefBranches()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefBranches().getBody();
        assertEquals(1, fields.size());
        assertEquals("BRANCHE", fields.get(0).getCode());
    }
    @Test
    public void getRefLanguages() {
        RefTableChamp data = new RefTableChamp(
                "LANGUAGE",
                "FR",
                "EN"
        );
        when(refService.findRefLanguages()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefLanguages().getBody();
        assertEquals(1, fields.size());
        assertEquals("LANGUAGE", fields.get(0).getCode());
    }
    @Test
    public void getRefCountryCodes() {
        RefTableChamp data = new RefTableChamp(
                "COUNTRY",
                "FR",
                "EN"
        );
        when(refService.findRefCountryCodes()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefCountryCodes().getBody();
        assertEquals(1, fields.size());
        assertEquals("COUNTRY", fields.get(0).getCode());
    }
    @Test
    public void getRefFBTierLevels() {
        RefTableChamp data = new RefTableChamp(
                "FBTIERLEVEL",
                "FR",
                "EN"
        );
        when(refService.findRefFBTierLevels()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefFBTierLevels().getBody();
        assertEquals(1, fields.size());
        assertEquals("FBTIERLEVEL", fields.get(0).getCode());
    }
    @Test
    public void getRefStatesRoleContract() {
        RefTableChamp data = new RefTableChamp(
                "STATEROLECONTRACT",
                "FR",
                "EN"
        );
        when(refService.findRefStatesRoleContract()).thenReturn(List.of(data));
        List<RefTableChamp> fields = (List<RefTableChamp>) refController.getRefStatesRoleContract().getBody();
        assertEquals(1, fields.size());
        assertEquals("STATEROLECONTRACT", fields.get(0).getCode());
    }
    @Test
    public void getRefStatesRoleContract2() {
        WrapperRefTable data = new WrapperRefTable();
        data.setReferenceDatas(List.of());
        when(refService.findRefTable("table")).thenReturn(data);
        WrapperRefTable refTable = (WrapperRefTable) refController.getRefStatesRoleContract("table").getBody();
        assertEquals(0,refTable.getReferenceDatas().size());
    }
}
