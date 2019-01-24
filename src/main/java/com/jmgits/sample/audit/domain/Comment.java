package com.jmgits.sample.audit.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "comment")
@Audited
public class Comment extends AbstractEntity {

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "user_creator_id")
    private User creator;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_editor_id")
    private User editor;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;
}
