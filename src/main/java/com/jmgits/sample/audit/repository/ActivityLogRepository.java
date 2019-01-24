package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.ActivityLog;
import com.jmgits.sample.audit.exception.NotFoundCodeException;

public interface ActivityLogRepository extends GenericRepository<ActivityLog> {

    //
    //

    @Override
    default NotFoundCodeException getNotFoundException(Long id) {
        return new NotFoundCodeException(
                "ACTIVITY_LOG_NOT_FOUND", String.format("Activity log with id '%s' not found.", id)
        );
    }
}
