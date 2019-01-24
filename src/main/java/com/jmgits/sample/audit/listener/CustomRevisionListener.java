package com.jmgits.sample.audit.listener;

import com.jmgits.sample.audit.domain.CustomRevisionEntity;
import com.jmgits.sample.audit.util.SecurityUtils;

/**
 * Created by javg 10/08/18.
 */
public class CustomRevisionListener implements org.hibernate.envers.RevisionListener {

    @Override
    public void newRevision(Object o) {

        SecurityUtils.findTokenData().ifPresent(tokenData -> {

            CustomRevisionEntity revisionEntity = (CustomRevisionEntity) o;

            revisionEntity.setUsername(tokenData.getUsername());
        });
    }
}