package com.jmgits.sample.audit.domain;

import com.jmgits.sample.audit.listener.CustomRevisionListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "revision")
public class CustomRevisionEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @RevisionNumber
    private Long id;

    // envers doesn't support java.time yet so we're stuck with java.util.Date
    @RevisionTimestamp
    private Date date;

    private String username;
}
