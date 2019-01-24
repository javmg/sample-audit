package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class TokenData {

    private String username;

    private Set<String> authorities = new LinkedHashSet<>();
}