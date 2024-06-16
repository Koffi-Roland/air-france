package com.afklm.batch.deletecontract.mapper;

import com.airfrance.repind.entity.role.RoleUCCR;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleUCCRMapper implements RowMapper<RoleUCCR> {
    @Override
    public RoleUCCR mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RoleUCCR roleUCCR = new RoleUCCR();
        roleUCCR.setUccrID(rs.getString("UCCR_ID"));
        roleUCCR.setGin(rs.getString("SGIN"));
        roleUCCR.setEtat(rs.getString("SETAT"));
        roleUCCR.setCleRole(rs.getInt("ICLE_ROLE"));

        return roleUCCR;
    }
}

