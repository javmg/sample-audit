package com.jmgits.sample.audit.rest;

import com.jmgits.sample.audit.service.CommentService;
import com.jmgits.sample.audit.util.SecurityUtils;
import com.jmgits.sample.audit.view.CommentCreateOrUpdate;
import com.jmgits.sample.audit.view.CommentSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(tags = "Comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    @ApiOperation(value = "Create a comment")
    @ResponseStatus(CREATED)
    @PostMapping
    public CommentSimple create(@Validated @RequestBody CommentCreateOrUpdate criteria) {
        return commentService.create(criteria, SecurityUtils.getTokenData());
    }

    @ApiOperation(value = "Get all comments")
    @GetMapping
    public List<CommentSimple> getAll() {
        return commentService.getAll(SecurityUtils.getTokenData());
    }

    @ApiOperation(value = "Update the comment with the given id")
    @PutMapping(value = "/{id}")
    public CommentSimple update(@PathVariable Long id, @Validated @RequestBody CommentCreateOrUpdate criteria) {
        return commentService.update(id, criteria, SecurityUtils.getTokenData());
    }

    @ApiOperation(value = "Delete the comment with the given id")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id, SecurityUtils.getTokenData());
    }
}
