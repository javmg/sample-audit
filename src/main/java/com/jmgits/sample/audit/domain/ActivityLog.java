package com.jmgits.sample.audit.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "activity_log")
public class ActivityLog extends AbstractEntity {

    @Column(name = "event", nullable = false)
    private String event;

    @Column(name = "username", nullable = false)
    private String username;
}
