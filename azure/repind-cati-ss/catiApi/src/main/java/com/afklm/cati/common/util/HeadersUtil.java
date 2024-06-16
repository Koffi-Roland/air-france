package com.afklm.cati.common.util;

import javax.servlet.http.HttpServletRequest;

public class HeadersUtil {

    public static final StringBuffer formatRequestUrl(HttpServletRequest request) {
        return request.getRequestURL()
                .append((request.getRequestURL().toString()
                        .endsWith("/") ? "" : "/"));
    }
}
