package com.hse.units.repositories;

import com.hse.units.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByAuthor(long author);

    Task findById(long id);
}
