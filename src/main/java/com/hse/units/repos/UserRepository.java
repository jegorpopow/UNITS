package com.hse.units.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> findByName(String name);

    User findByUid(long uid);

    Optional<User> findByEmail(String email);

    boolean existsUserByName(String name);

    Optional<User> findUserByName(String name);

    User findByActivationCode(String code);
}
