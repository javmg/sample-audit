package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TokenRequest {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String password;
}
