package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.User;
import com.jmgits.sample.audit.exception.NotFoundCodeException;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {

    Optional<User> findByUsername(String username);

    //
    //

    @Override
    default NotFoundCodeException getNotFoundException(Long id) {
        return new NotFoundCodeException(
                "USER_NOT_FOUND", String.format("User with id '%s' not found.", id)
        );
    }

    default User getByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundCodeException(
                "USER_NOT_FOUND", String.format("User with username '%s' not found.", username)
        ));
    }
}
