package com.jmgits.sample.audit.service;

import com.jmgits.sample.audit.view.CommentCreateOrUpdate;
import com.jmgits.sample.audit.view.CommentSimple;
import com.jmgits.sample.audit.view.TokenData;

import java.util.List;

public interface CommentService {

    CommentSimple create(CommentCreateOrUpdate criteria, TokenData tokenData);

    List<CommentSimple> getAll(TokenData tokenData);

    CommentSimple update(Long id, CommentCreateOrUpdate criteria, TokenData tokenData);

    void delete(Long id, TokenData tokenData);
}
