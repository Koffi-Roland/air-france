package com.afklm.repind.msv.automatic.merge.config.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HttpLog {
    private HttpType type;
    private String id;
    private String uri;
    private String method;
    private Map<String, Object> headers;
    private List<Map<String, Object>> body;
    private Integer status;
    private Long duration;
}
