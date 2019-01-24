package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.AbstractEntity;
import com.jmgits.sample.audit.exception.NotFoundCodeException;
import org.springframework.data.history.Revision;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

@NoRepositoryBean
public interface GenericRevisionRepository<E extends AbstractEntity> extends RevisionRepository<E, Long, Long> {

    default Revision<Long, E> getByIdAndRevisionId(Long id, Long revisionId) {

        try {

            return findRevision(id, revisionId).orElseThrow(RuntimeException::new);

        } catch (Exception e) {

            throw getNotFoundException(id, revisionId);
        }
    }

    default Revision<Long, E> getLatestById(Long id) {

        try {

            return findLastChangeRevision(id).orElseThrow(RuntimeException::new);

        } catch (Exception e) {

            throw getNotFoundException(id);

        }
    }

    NotFoundCodeException getNotFoundException(Long id, Long revisionId);

    NotFoundCodeException getNotFoundException(Long id);

}
