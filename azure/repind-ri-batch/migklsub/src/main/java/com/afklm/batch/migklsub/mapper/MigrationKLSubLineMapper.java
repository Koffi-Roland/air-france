package com.afklm.batch.migklsub.mapper;

import com.airfrance.repind.entity.individu.Individu;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import static com.afklm.batch.migklsub.enums.IndividuFieldEnum.SF_KEY;

public class MigrationKLSubLineMapper extends DefaultLineMapper<Individu> {

    @Override
    public Individu mapLine(String line, int lineNumber) throws Exception {
        line = removeUTF8BOM(line);
        if(line.startsWith(SF_KEY.getValue())){
            return new Individu();
        }
        return super.mapLine(line, lineNumber);
    }

    public static String removeUTF8BOM(String s) {
        if (s.startsWith("\uFEFF")) {
            s = s.substring(1);
        }
        return s;
    }
}
