package com.afklm.batch.prospect.reader;

import com.afklm.batch.prospect.bean.ProspectInputRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import static com.afklm.batch.prospect.helper.AlimentationProspectConstant.EM_ADR_LABEL;

@Slf4j
public class ProspectLineMapperCustom extends DefaultLineMapper<ProspectInputRecord> {

    @Override
    public ProspectInputRecord mapLine(String line, int lineNumber) throws Exception {
        if (StringUtils.isBlank(line) || line.startsWith(EM_ADR_LABEL)) {
            return new ProspectInputRecord();
        }
        else {
            return super.mapLine(line, lineNumber);
        }
    }
}
