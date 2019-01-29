package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearch {

    private Id creator;

    private String description;

    private Boolean own;

    private String title;
}
