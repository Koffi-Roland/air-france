package com.airfrance.batch.contract.deletecontract.mapper;

import com.airfrance.repind.entity.role.RoleContrats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleContratsMapper implements RowMapper<RoleContrats> {
    @Override
    public RoleContrats mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RoleContrats roleContrats = new RoleContrats();
        roleContrats.setSrin(rs.getString("SRIN"));
        roleContrats.setGin(rs.getString("SGIN"));
        roleContrats.setTypeContrat(rs.getString("STYPE_CONTRAT"));
        roleContrats.setNumeroContrat(rs.getString("SNUMERO_CONTRAT"));

        return roleContrats;
    }
}
