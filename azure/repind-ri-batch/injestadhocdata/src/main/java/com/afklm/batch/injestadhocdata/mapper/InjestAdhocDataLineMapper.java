package com.afklm.batch.injestadhocdata.mapper;

import com.afklm.batch.injestadhocdata.bean.InputRecord;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import static com.afklm.batch.injestadhocdata.helper.Constant.UTF8BOM;

public class InjestAdhocDataLineMapper extends DefaultLineMapper<InputRecord> {

	@Override
    public InputRecord mapLine(String line, int lineNumber) throws Exception {
        line = removeUTF8BOM(line);
        return super.mapLine(line, lineNumber);
    }

    public static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8BOM)) {
            s = s.substring(1);
        }
        return s;
    }
}
