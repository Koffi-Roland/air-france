package com.afklm.repind.msv.mapping.wrapper;

import com.afklm.repind.msv.mapping.model.MappingLanguageModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperMappingTableForContext {

	private String context;
	private List<MappingLanguageModel> mappingLanguages;
}
