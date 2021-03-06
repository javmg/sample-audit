package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.exception.NotFoundCodeException;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends GenericRepository<Comment>, CommentSearchRepository {

    @Override
    @EntityGraph(attributePaths = {"creator", "editor"})
    Page<Comment> findAll(Predicate predicate, Pageable page);

    @Override
    @EntityGraph(attributePaths = {"creator", "editor"})
    Optional<Comment> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"creator", "editor"})
    List<Comment> findAll();

    //
    //

    @Override
    default NotFoundCodeException getNotFoundException(Long id) {
        return new NotFoundCodeException(
                "COMMENT_NOT_FOUND", String.format("Comment with id '%s' not found.", id)
        );
    }
}
