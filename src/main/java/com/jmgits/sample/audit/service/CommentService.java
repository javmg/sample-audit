package com.jmgits.sample.audit.service;

import com.jmgits.sample.audit.view.CommentCreateOrUpdate;
import com.jmgits.sample.audit.view.CommentSearch;
import com.jmgits.sample.audit.view.CommentSimple;
import com.jmgits.sample.audit.view.TokenData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentSimple create(CommentCreateOrUpdate criteria, TokenData tokenData);

    Page<CommentSimple> search(CommentSearch criteria, Pageable page, TokenData tokenData);

    List<CommentSimple> getAll(TokenData tokenData);

    CommentSimple update(Long id, CommentCreateOrUpdate criteria, TokenData tokenData);

    void delete(Long id, TokenData tokenData);
}
