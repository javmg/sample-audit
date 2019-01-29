package com.jmgits.sample.audit.repository.impl;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.domain.QComment;
import com.jmgits.sample.audit.repository.CommentRepository;
import com.jmgits.sample.audit.repository.CommentSearchRepository;
import com.jmgits.sample.audit.view.CommentSearch;
import com.jmgits.sample.audit.view.TokenData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static java.util.Optional.ofNullable;

public class CommentRepositoryImpl implements CommentSearchRepository {

    private final QComment qComment = QComment.comment;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> search(CommentSearch criteria, Pageable page, TokenData tokenData) {

        BooleanBuilder clause = new BooleanBuilder();

        ofNullable(criteria.getCreator()).ifPresent(creator ->
                clause.and(qComment.creator.id.eq(creator.getId()))
        );

        ofNullable(criteria.getDescription()).ifPresent(description ->
                clause.and(qComment.description.containsIgnoreCase(description))
        );

        ofNullable(criteria.getOwn()).ifPresent(own -> {

            BooleanExpression subClause = qComment.creator.username.eq(tokenData.getUsername());

            clause.and(own ? subClause : subClause.not());
        });

        ofNullable(criteria.getTitle()).ifPresent(title ->
                clause.and(qComment.title.containsIgnoreCase(title))
        );

        return commentRepository.findAll(clause, page);
    }
}
