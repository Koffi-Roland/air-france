package com.airfrance.repind.service.internal.unitservice.adresse;


import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.client.dqe.RNVP;
import com.airfrance.repind.client.dqe.RNVP1;
import com.airfrance.repind.client.dqe.RNVPDqeClient;
import com.airfrance.repind.dao.reference.RefProvinceRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.refTable.RefTableCAT_MED;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.repind.entity.reference.RefProvince;
import com.airfrance.repind.service.reference.internal.RefPaysDS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DqeUsTest {

    @InjectMocks
    private DqeUS dqeUS;

    @Mock
    private RNVPDqeClient rnvpDqeClient;

    @Mock
    private RefPaysDS refPaysDS;

    @Mock
    private RefProvinceRepository refProvinceRepository;

    private final String CORRECT_ADDRESS = "400 Avenue Roumanille";
    private final String INCORRECT_ADDRESS = "400 Roumanil";
    private final String POSTAL_CODE = "06410";
    private final String TOWN = "Biot";
    private final String PROVINCE = "";
    private final String COUNTRY = "FRA";
    private final String COUNTRY_CODE = "FR";

    @Before
    public void init(){
        RefProvince refProvince = new RefProvince();
        refProvince.setCode(PROVINCE);
        Mockito.when(refProvinceRepository.findByCodeAndCodePays(Mockito.any(),Mockito.any())).thenReturn(Optional.of(refProvince));
    }

    @Test
    public void successWithCheckedCase() throws JrafDomainException {
        PostalAddress po = buildPostalAddress();
        Mockito.when(refPaysDS.codePaysIsValide(Mockito.any())).thenReturn(true);
        Mockito.when(refPaysDS.codePaysIsNormalizable(Mockito.any())).thenReturn(true);
        Mockito.when(rnvpDqeClient.execute(Mockito.any())).thenReturn(buildRNVP(10));
        dqeUS.normalizeAddress(po, true , true);
        checkPostalAddress(po);
    }

    @Test
    public void successNormalizationFalse() throws JrafDomainException {
        PostalAddress po = buildPostalAddress();
        Mockito.when(refPaysDS.codePaysIsValide(Mockito.any())).thenReturn(true);
        Mockito.when(refPaysDS.codePaysIsNormalizable(Mockito.any())).thenReturn(false);
        dqeUS.normalizeAddress(po, false , true);
        checkForcagePostalAddress(po);
    }

    @Test
    public void successWithoutCheckedCase() throws JrafDomainException {
        PostalAddress po = buildPostalAddress();
        Mockito.when(refPaysDS.codePaysIsValide(Mockito.any())).thenReturn(true);
        Mockito.when(refPaysDS.codePaysIsNormalizable(Mockito.any())).thenReturn(true);
        Mockito.when(rnvpDqeClient.execute(Mockito.any())).thenReturn(buildRNVP(10));
        dqeUS.normalizeAddress(po, false , true);
        checkPostalAddress(po);
    }

    @Test(expected = JrafDomainRollbackException.class)
    public void errorWithCheckedCase() throws JrafDomainException {
        PostalAddress po = buildPostalAddress();
        Mockito.when(refPaysDS.codePaysIsValide(Mockito.any())).thenReturn(true);
        Mockito.when(refPaysDS.codePaysIsNormalizable(Mockito.any())).thenReturn(true);
        Mockito.when(rnvpDqeClient.execute(Mockito.any())).thenReturn(buildRNVP(20));
        dqeUS.normalizeAddress(po, true , true);
        checkPostalAddress(po);
    }

    @Test(expected = JrafDomainRollbackException.class)
    public void errorWithoutCheckedCase() throws JrafDomainException {
        PostalAddress po = buildPostalAddress();
        Mockito.when(refPaysDS.codePaysIsValide(Mockito.any())).thenReturn(true);
        Mockito.when(refPaysDS.codePaysIsNormalizable(Mockito.any())).thenReturn(true);
        Mockito.when(rnvpDqeClient.execute(Mockito.any())).thenReturn(buildRNVP(20));
        dqeUS.normalizeAddress(po, false , true);
        checkPostalAddress(po);
    }

    private void checkPostalAddress(PostalAddress po) {
        Assert.assertEquals(CORRECT_ADDRESS , po.getSno_et_rue());
        Assert.assertEquals(POSTAL_CODE, po.getScode_postal());
        Assert.assertEquals(TOWN, po.getSville());
        Assert.assertEquals(COUNTRY_CODE, po.getScode_pays());
        Assert.assertEquals(PROVINCE , po.getScode_province());
    }

    private void checkForcagePostalAddress(PostalAddress po) {
        Assert.assertEquals(INCORRECT_ADDRESS , po.getSno_et_rue());
        Assert.assertEquals(POSTAL_CODE, po.getScode_postal());
        Assert.assertEquals(TOWN, po.getSville());
        Assert.assertEquals(COUNTRY_CODE, po.getScode_pays());
        Assert.assertEquals("" , po.getScode_province());
    }

    private RNVP buildRNVP(Integer iDqeCodeDetails){
        RNVP rnvp = new RNVP();
        RNVP1 rnvp1 = new RNVP1();
        rnvp1.setDqECodeDetail(iDqeCodeDetails);
        rnvp1.setAdresse(CORRECT_ADDRESS);
        rnvp1.setCodePostal(POSTAL_CODE);
        rnvp1.setLocalite(TOWN);
        rnvp1.setProvince(PROVINCE);
        rnvp1.setPays(COUNTRY);
        rnvp.set1(rnvp1);
        return rnvp;
    }

    private PostalAddress buildPostalAddress() {
        PostalAddress po = new PostalAddress();
        po.setScode_medium(RefTableCAT_MED._REF_L);
        po.setSno_et_rue(INCORRECT_ADDRESS);
        po.setSville(TOWN);
        po.setScode_postal(POSTAL_CODE);
        po.setScode_pays(COUNTRY_CODE);
        po.setSstatut_medium(RefTableREF_STA_MED._REF_V);
        po.setScode_province("");
        return po;
    }


}
