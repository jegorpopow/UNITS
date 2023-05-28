package com.hse.units.repos;

import com.hse.units.domain.TaskTag;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByAuthor(long author);

    Page<Task> findTaskByTagsId(long tagId, Pageable pageable);

    List<Task> findTaskByTagsId(long tagId);


    Page<Task> findAll(Pageable pageable);

    List<Task> findAll();

    Task findById(long id);

    List<Task> findByTitle(String title);
}
