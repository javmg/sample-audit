package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.exception.NotFoundCodeException;

public interface CommentRevisionRepository extends GenericRevisionRepository<Comment> {

    @Override
    default NotFoundCodeException getNotFoundException(Long id){

        return new NotFoundCodeException(
                "ERROR_COMMENT_REVISION_NOT_FOUND",
                String.format("Latest revision for comment with id '%s not found.", id)
        );
    }

    @Override
    default NotFoundCodeException getNotFoundException(Long id, Long revisionId){

        return new NotFoundCodeException(
                "ERROR_COMMENT_REVISION_NOT_FOUND",
                String.format("Comment and revision with ids '%s and %s' not found.", id, revisionId)
        );
    }
}
