package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSimple {

    private Long id;

    private UserIdAndName creator;

    private UserIdAndName editor;

    private String description;

    private String title;
}


