package com.hse.units.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByAuthor(long author);

    Task findById(long id);

    List<Task> findByTitle(String title);
}
