package com.jmgits.sample.audit.mapper;

import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.view.CommentSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentSimple transformSimple(Comment entity) {

        if (entity == null){
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

    public List<CommentSimple> transformListSimple(List<Comment> entities) {
        return entities.stream().map(this::transformSimple).collect(Collectors.toList());
    }
}
