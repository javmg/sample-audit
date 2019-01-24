package com.jmgits.sample.audit.repository;

import com.jmgits.sample.audit.domain.AbstractEntity;
import com.jmgits.sample.audit.exception.NotFoundCodeException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {

    List<T> findByIdIn(Collection<Long> ids);

    Optional<T> findById(Long id);

    //
    //

    NotFoundCodeException getNotFoundException(Long id);

    //
    //

    default List<T> getAllById(Collection<Long> ids) {

        List<T> results = findByIdIn(ids);

        Map<Long, T> mapIdAndEntity = results.stream().collect(Collectors.toMap(T::getId, Function.identity()));

        ids.forEach(id -> {
            if (!mapIdAndEntity.containsKey(id)) {
                throw getNotFoundException(id);
            }
        });

        return results;
    }

    default T getById(Long id){
        return findById(id).orElseThrow(() -> getNotFoundException(id));
    }
}
