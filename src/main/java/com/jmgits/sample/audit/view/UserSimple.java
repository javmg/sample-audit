package com.jmgits.sample.audit.view;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserSimple {

    private Long id;

    private String email;

    private Boolean enabled;

    private String name;

    @JsonIgnore
    private String password;

    private String username;
}
