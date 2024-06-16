package com.airfrance.batch.compref.injestadhocdata.mapper;

import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.UTF8BOM;

import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import com.airfrance.batch.compref.injestadhocdata.bean.InputRecord;

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
