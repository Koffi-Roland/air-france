package com.afklm.rigui.services.exception;

import com.afklm.rigui.model.error.ErrorType;
/**
 * Part of Full rest error
 *
 * @author M312812
 */

public class RestError {

    private String code;
    private String name;
    private String description;
    private ErrorType severity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ErrorType getSeverity() {
        return severity;
    }

    public void setSeverity(ErrorType severity) {
        this.severity = severity;
    }
}