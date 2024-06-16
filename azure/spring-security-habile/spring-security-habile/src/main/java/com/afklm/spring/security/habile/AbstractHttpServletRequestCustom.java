package com.afklm.spring.security.habile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * HttpServletRequest for Simulation
 * 
 * Enables the simulation of {@link HabileProxySimul} for some HTTP headers
 * 
 * @author m405991
 *
 */
public abstract class AbstractHttpServletRequestCustom extends HttpServletRequestWrapper {

    /**
     * Constructor
     * 
     * A hardcoded list of headers is built
     * 
     * @param request request
     */
    public AbstractHttpServletRequestCustom(HttpServletRequest request) {
        super(request);
    }

    /**
     * Overrides Header
     * 
     * @param name
     * @return true if header is overridden
     */
    protected abstract boolean overridesHeader(String name);

    /**
     * Get overridden header
     * 
     * @param name
     * @return value for overridden header
     */
    protected abstract String getOverridenHeader(String name);

    /**
     * Get set of overridden headers
     * 
     * @return overridden headers
     */
    protected abstract Set<String> getOverridenHeaders();

    @Override
    public String getHeader(String name) {
        String result;

        if (overridesHeader(name)) {
            result = getOverridenHeader(name);
        } else {
            result = super.getHeader(name);
        }
        return result;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Enumeration<String> result;
        if (overridesHeader(name)) {
            List<String> list = new ArrayList<>();
            String value = getOverridenHeader(name);
            // According to specification an empty collection is returned when header is not set
            list.add(value);
            result = Collections.enumeration(list);
        } else {
            result = super.getHeaders(name);
        }
        return result;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        ArrayList<String> l = Collections.list(super.getHeaderNames());
        l.addAll(getOverridenHeaders());

        return Collections.enumeration(l);
    }
}
