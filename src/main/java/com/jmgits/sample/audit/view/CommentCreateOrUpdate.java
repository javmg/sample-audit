package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentCreateOrUpdate {

    @Size(max = 255)
    private String description;

    @Size(max = 100)
    private String title;
}
