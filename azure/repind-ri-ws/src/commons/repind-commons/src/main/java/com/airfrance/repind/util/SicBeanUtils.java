package com.airfrance.repind.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class SicBeanUtils {

    /**
     * 
     * @param pSource
     * @return property names whose value is not null
     */
    public static String[] getNotNullPropertyNames(Object pSource) {

        final BeanWrapper src = new BeanWrapperImpl(pSource);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> propertyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {

            if ("class".equals(pd.getName())) {
                continue;
            }
            
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                propertyNames.add(pd.getName());
            }
        }

        String[] result = new String[propertyNames.size()];
        return propertyNames.toArray(result);
    }

    
}
