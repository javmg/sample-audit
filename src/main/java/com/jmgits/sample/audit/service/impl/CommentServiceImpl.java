package com.jmgits.sample.audit.service.impl;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.mapper.CommentMapper;
import com.jmgits.sample.audit.publisher.SimpleEventPublisher;
import com.jmgits.sample.audit.repository.CommentRepository;
import com.jmgits.sample.audit.repository.UserRepository;
import com.jmgits.sample.audit.service.CommentService;
import com.jmgits.sample.audit.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final SimpleEventPublisher simpleEventPublisher;

    private final CommentMapper commentMapper;

    @Override
    public CommentSimple create(CommentCreateOrUpdate criteria, TokenData tokenData) {

        Comment entity = new Comment();

        copy(criteria, entity);

        entity.setCreator(userRepository.getByUsername(tokenData.getUsername()));

        simpleEventPublisher.publishEvent(new ActivityLogEvent("Comment created.", tokenData.getUsername()));

        return commentMapper.transformSimple(commentRepository.save(entity));
    }

    @Override
    public Page<CommentSimple> search(CommentSearch criteria, Pageable page, TokenData tokenData) {

        Page<Comment> entities = commentRepository.search(criteria, page, tokenData);

        return commentMapper.transformPageSimple(entities, page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentSimple> getAll(TokenData tokenData){

        List<Comment> entities = commentRepository.findAll();

        return commentMapper.transformListSimple(entities);
    }

    @Override
    public CommentSimple update(Long id, CommentCreateOrUpdate criteria, TokenData tokenData) {

        Comment entity = commentRepository.getById(id);

        copy(criteria, entity);

        entity.setEditor(userRepository.getByUsername(tokenData.getUsername()));

        simpleEventPublisher.publishEvent(new ActivityLogEvent(String.format("Comment '%s' updated.", id), tokenData.getUsername()));

        return commentMapper.transformSimple(commentRepository.save(entity));
    }

    @Override
    public void delete(Long id, TokenData tokenData) {

        Comment entity = commentRepository.getById(id);

        simpleEventPublisher.publishEvent(new ActivityLogEvent(String.format("Comment '%s' deleted.", id), tokenData.getUsername()));

        commentRepository.delete(entity);
    }

    //
    // private

    private void copy(CommentCreateOrUpdate criteria, Comment entity) {

        entity.setDescription(criteria.getDescription());
        entity.setTitle(criteria.getTitle());
    }
}
