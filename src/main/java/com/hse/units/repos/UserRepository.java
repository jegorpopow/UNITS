package com.hse.units.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> findByName(String name);

    User findByUid(long uid);

    boolean existsUserByName(String name);

    User findUserByName(String name);
}
