package com.jmgits.sample.audit.listener;

import com.jmgits.sample.audit.domain.ActivityLog;
import com.jmgits.sample.audit.repository.ActivityLogRepository;
import com.jmgits.sample.audit.view.ActivityLogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class ActivityLogEventListener {

    private final ActivityLogRepository activityLogRepository;

    @EventListener
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void handleBefore(ActivityLogEvent event) {

        ActivityLog entity = new ActivityLog();

        entity.setEvent(event.getEvent());
        entity.setUsername(event.getUsername());

        activityLogRepository.save(entity);
    }
}
