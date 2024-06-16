package com.afklm.batch.deletecontract.mapper;

import com.airfrance.repind.entity.role.RoleContrats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RoleContratsMapperTest {

    private final RoleContratsMapper mapper = new RoleContratsMapper();

    @Mock
    private ResultSet rs;

    @Test
    public void mapRow() throws SQLException {
        when(rs.getString("SRIN")).thenReturn("RIN");
        when(rs.getString("SGIN")).thenReturn("GIN");
        when(rs.getString("STYPE_CONTRAT")).thenReturn("TYPE_CONTRAT");
        when(rs.getString("SNUMERO_CONTRAT")).thenReturn("NUMERO_CONTRAT");
        RoleContrats result = mapper.mapRow(rs, 0);
        assertEquals("RIN", result.getSrin());
        assertEquals("GIN", result.getGin());
        assertEquals("TYPE_CONTRAT", result.getTypeContrat());
        assertEquals("NUMERO_CONTRAT", result.getNumeroContrat());
    }

}
