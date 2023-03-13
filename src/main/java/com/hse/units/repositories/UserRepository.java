package com.hse.units.repositories;

import com.hse.units.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> findByName(String name);

    User findByUid(long uid);
}
