package com.airfrance.jraf.batch.event.individu;

import com.airfrance.jraf.batch.common.BlockDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeData {

    private List<BlockDTO> blockDTOList;

    private RoleContratsDTO contractData;

}
