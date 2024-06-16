package com.afklm.spring.security.habile.secmobil;

import com.afklm.spring.security.habile.AbstractHttpServletRequestCustom;
import com.afklm.spring.security.habile.GenericHeaders;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * HttpServletRequest for Simulation
 * 
 * Enables the simulation of {@link SecMobilFilter} for some HTTP headers
 * 
 * @author m405991
 *
 */
public class HttpServletRequestSecMobil extends AbstractHttpServletRequestCustom {

    /** List of HardCoded headers to return */
    // According to servlet specification, headers are case insensitive
    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Constructor
     * 
     * A hardcoded list of headers is built
     * 
     * @param request request
     * @param smUser smUser
     */
    public HttpServletRequestSecMobil(HttpServletRequest request, String smUser) {
        super(request);
        headers.put(GenericHeaders.SM_USER, smUser);
        headers.put(GenericHeaders.SM_SESSION, smUser);
    }

    @Override
    protected boolean overridesHeader(String name) {
        return headers.containsKey(name);
    }

    @Override
    protected String getOverridenHeader(String name) {
        return headers.get(name);
    }

    @Override
    protected Set<String> getOverridenHeaders() {
        return headers.keySet();
    }
}
