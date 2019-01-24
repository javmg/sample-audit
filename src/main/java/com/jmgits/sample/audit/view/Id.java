package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Id {

    @NotNull
    private Long id;
}
