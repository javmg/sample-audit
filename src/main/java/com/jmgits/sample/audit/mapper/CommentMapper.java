package com.jmgits.sample.audit.mapper;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.view.CommentSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Page<CommentSimple> transformPageSimple(Page<Comment> entities, Pageable pageable) {

        return new PageImpl<>(
                entities.getContent().stream().map(this::transformSimple).collect(Collectors.toList()),
                pageable,
                entities.getTotalElements()
        );
    }

    public List<CommentSimple> transformListSimple(List<Comment> entities) {
        return entities.stream().map(this::transformSimple).collect(Collectors.toList());
    }

    public CommentSimple transformSimple(Comment entity) {

        if (entity == null) {
            return null;
        }

        CommentSimple dto = new CommentSimple();

        dto.setId(entity.getId());
        dto.setCreator(userMapper.transformToIdAndName(entity.getCreator()));
        dto.setEditor(userMapper.transformToIdAndName(entity.getEditor()));
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());

        return dto;
    }
}
