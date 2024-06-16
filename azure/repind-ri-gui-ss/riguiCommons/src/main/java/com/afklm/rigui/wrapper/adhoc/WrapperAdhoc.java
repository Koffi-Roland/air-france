package com.afklm.rigui.wrapper.adhoc;

import com.afklm.rigui.model.individual.ModelAdhocResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperAdhoc {
    public List<ModelAdhocResult> result = new ArrayList<>();
}
