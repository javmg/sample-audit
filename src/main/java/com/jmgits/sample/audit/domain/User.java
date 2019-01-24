package com.jmgits.sample.audit.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "UK_user__username", columnNames = {"username"}))
@Audited
public class User extends AbstractEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;
}
