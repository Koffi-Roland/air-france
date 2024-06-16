package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.entity.reference.Pays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RefPaysDSTest {

    @InjectMocks
    private RefPaysDS instance;

    @Mock
    private PaysRepository paysRepository;

    private final String NORMALIZATION_YES = "O";
    private final String NORMALIZATION_NO = "N";

    @Test
    public void codePaysIsNormalizableSuccess() throws JrafDomainException {
        Pays pays = new Pays();
        pays.setNormalisable(NORMALIZATION_YES);
        Optional<Pays> optPays = Optional.of(pays);
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertTrue(instance.codePaysIsNormalizable(Mockito.any()));
    }

    @Test
    public void codePaysIsNormalizableFalse() throws JrafDomainException {
        Pays pays = new Pays();
        pays.setNormalisable(NORMALIZATION_NO);
        Optional<Pays> optPays = Optional.of(pays);
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertFalse(instance.codePaysIsNormalizable(Mockito.any()));
    }

    @Test
    public void codePaysIsNormalizableFalseWithNoPaysFound() throws JrafDomainException {
        Optional<Pays> optPays = Optional.empty();
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertFalse(instance.codePaysIsNormalizable(Mockito.any()));
    }

    @Test
    public void codePaysIsForcageNormalizableSuccess() throws JrafDomainException {
        Pays pays = new Pays();
        pays.setForcage(NORMALIZATION_YES);
        Optional<Pays> optPays = Optional.of(pays);
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertTrue(instance.codePaysIsForcage(Mockito.any()));
    }

    @Test
    public void codePaysIsForcageFalse() throws JrafDomainException {
        Pays pays = new Pays();
        pays.setForcage(NORMALIZATION_NO);
        Optional<Pays> optPays = Optional.of(pays);
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertFalse(instance.codePaysIsForcage(Mockito.any()));
    }

    @Test
    public void codePaysIsForcageFalseWithNoPaysFound() throws JrafDomainException {
        Optional<Pays> optPays = Optional.empty();
        Mockito.when(paysRepository.findById(Mockito.any())).thenReturn(optPays);
        Assert.assertFalse(instance.codePaysIsForcage(Mockito.any()));
    }
}
