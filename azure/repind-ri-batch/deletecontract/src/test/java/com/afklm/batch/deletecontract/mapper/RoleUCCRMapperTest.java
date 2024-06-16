package com.afklm.batch.deletecontract.mapper;

import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.entity.role.RoleUCCR;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RoleUCCRMapperTest {
    private final RoleUCCRMapper mapper = new RoleUCCRMapper();

    @Mock
    private ResultSet rs;

    @Test
    public void mapRow() throws SQLException {
        when(rs.getString("UCCR_ID")).thenReturn("ID");
        when(rs.getString("SGIN")).thenReturn("GIN");
        when(rs.getString("SETAT")).thenReturn("ETAT");
        when(rs.getInt("ICLE_ROLE")).thenReturn(2);


        RoleUCCR result = mapper.mapRow(rs, 0);
        assertEquals("ID", result.getUccrID());
        assertEquals("GIN", result.getGin());
        assertEquals("ETAT", result.getEtat());
        assertEquals(2, result.getCleRole());
    }
}
