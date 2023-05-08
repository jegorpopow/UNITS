package com.hse.units.repos;

import com.hse.units.domain.Task;
import com.hse.units.domain.TaskTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TaskTag, Long>, PagingAndSortingRepository<TaskTag, Long> {
    List<TaskTag> findByName(String name);

    TaskTag findById(long id);

    boolean existsTagByName(String name);
}
