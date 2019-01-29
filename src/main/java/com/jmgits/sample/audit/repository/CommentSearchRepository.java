package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.view.CommentSearch;
import com.jmgits.sample.audit.view.TokenData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentSearchRepository {

    Page<Comment> search(CommentSearch criteria, Pageable page, TokenData tokenData);

}
