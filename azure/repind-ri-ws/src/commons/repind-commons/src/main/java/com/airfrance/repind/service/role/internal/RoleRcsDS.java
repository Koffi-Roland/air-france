package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.role.RoleRcsRepository;
import com.airfrance.repind.dto.role.RoleRcsDTO;
import com.airfrance.repind.dto.role.RoleRcsTransform;
import com.airfrance.repind.entity.role.RoleRcs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleRcsDS {
	
    @Autowired
    private RoleRcsRepository roleRcsRepository;

	
	@Deprecated
	public RoleRcsDTO get(RoleRcsDTO dto) throws JrafDomainException {
		return get(dto.getCle());
	}

	public RoleRcsDTO get(String id) throws JrafDomainException {
		Optional<RoleRcs> roleRcs = roleRcsRepository.findById(id);
		if (!roleRcs.isPresent()) {
			return null;
		}
		return RoleRcsTransform.bo2DtoLight(roleRcs.get());
	}

	public RoleRcsRepository getRoleRcsRepository() {
		return roleRcsRepository;
	}

	public void setRoleRcsRepository(RoleRcsRepository roleRcsRepository) {
		this.roleRcsRepository = roleRcsRepository;
	}
	
}
