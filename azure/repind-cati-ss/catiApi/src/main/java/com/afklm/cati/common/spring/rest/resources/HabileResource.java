package com.afklm.cati.common.spring.rest.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing data returned by the {@link com.afklm.popcorn.spring.rest.controllers}
 *
 * @author TECC
 */
@Getter
@Setter
public class HabileResource {
    private String userName;
    private String lastName;
    private String firstName;
    private String email;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();
}
