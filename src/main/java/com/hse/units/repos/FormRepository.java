package com.hse.units.repos;

import com.hse.units.domain.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.Task;

import java.util.List;

public interface FormRepository extends CrudRepository<Form, Long>, PagingAndSortingRepository<Form, Long> {
    List<Form> findByCreator(long author);

    Form findById(long id);
}
