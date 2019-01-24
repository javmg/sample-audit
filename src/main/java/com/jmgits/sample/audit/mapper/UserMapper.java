package com.jmgits.sample.audit.mapper;

import com.jmgits.sample.audit.domain.User;
import com.jmgits.sample.audit.view.UserIdAndName;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserIdAndName transformToIdAndName(User entity) {

        if (entity == null) {
            return null;
        }

        UserIdAndName dto = new UserIdAndName();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;

    }
}
