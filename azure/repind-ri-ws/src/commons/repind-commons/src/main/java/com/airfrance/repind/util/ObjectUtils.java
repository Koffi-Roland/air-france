package com.airfrance.repind.util;

import com.airfrance.ref.exception.BadDateFormatException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;


/**
 * @author e6349052
 *
 */
public class ObjectUtils {

    private static Log LOGGER  = LogFactory.getLog(ObjectUtils.class);
    
    
    private ObjectUtils() {
        
    }
    
    
    
    
    /**
     * Check if the object is null, it is null if :
     * all values are null or it's a string equals to "".
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        if(object != null) {
            Map<String, String> map = null;
            try {
                map = BeanUtils.describe(object);
            } catch (IllegalAccessException e) {
                LOGGER.error("", e);
            } catch (InvocationTargetException e) {
                LOGGER.error("", e);
            } catch (NoSuchMethodException e) {
                LOGGER.error("", e); 
            }
            if(map == null){
            	return true;
            }
            boolean objectIsNull = true;
            for(Object key : map.keySet()) {
                if(!key.toString().equals("class")) {
                    Object value = map.get(key);
                    //setEmptyStringToNull
                    if(value != null && (value instanceof String) && value.toString().equals("")) {
                        value = null;
                    } else if(value != null && !(value instanceof String) && isNull(value)) {
                        value = null;
                    }

                    if(value != null) {
                        objectIsNull = false;
                    }
                }
            }
            return objectIsNull;
        }
        return true;
    }
    
    
    
    
    
    /**
     * @param value
     * @return
     */
    public static String getStringValue(String value) {
        if(isNull(value)) {
            return null;
        } else {
            return value;
        }
    }
    
    /**
     * @param value
     * @return
     */
    public static Boolean getBooleanValue(String value) {
    	Boolean result = null;
        if(value!=null) {
        	if ("O".equalsIgnoreCase(value)) {
        		result = true;
        	} else {
        		result = false;
        	}
            
        }
        return result;
    }
    
    /**
     * @param value
     * @return
     * @throws JrafApplicativeException 
     * @throws BadDateFormatException 
     */
    public static Date getDateValue(String date) throws BadDateFormatException, JrafApplicativeException {
    	Date result = null;
        if(date!=null) {
        		result = SicDateUtils.stringToDate(date);
        }
        return result;
    }
    
    public static boolean isAdhNull(Object object) throws JrafException {
        boolean result = true;
        
        try {            
            Class classObject = object.getClass();
            //LOGGER.info("isAdhNull sur "+classObject.getName());
            Method[] methods = classObject.getMethods();
            boolean existGetter = false;
            for (int i = 0; i < methods.length && result == true; i++) {
                Method method = methods[i];
                if (isAdhGetter(method)) {
                    existGetter = true;
                    //LOGGER.info(method.getName());
                    Object objetFils = method.invoke(object);                    
                    result = isAdhNull(objetFils);
                }
            }
            if (!existGetter) {
                Method getValue = classObject.getMethod("isEmpty");
                if (getValue != null) {
                    boolean isEmpty = ((Boolean)getValue.invoke(object)).booleanValue();
                    LOGGER.debug(classObject.getName() + " isEmpty : "+isEmpty);
                    if (!isEmpty ) result = false;
                }
            }
        } catch (Exception e) {
            throw new JrafException("isAdhNull ", e); 
        }        
        return result;
    }
    
    public static boolean isAdhGetter(Method method){
          if(!method.getName().startsWith("get"))      return false;
          if(method.getParameterTypes().length != 0)   return false;  
          if(void.class.equals(method.getReturnType())) return false;
          if (  method.getName().equals("getLength") ||
                method.getName().equals("getOccurs") ||
                method.getName().equals("getValue") ||
                method.getName().equals("getException") ||
                method.getName().equals("getClass") ) return false;
          return true;
        }

}
