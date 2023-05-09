package com.hse.units.repos;

import com.hse.units.domain.Form;
import com.hse.units.domain.FormResponse;
import com.hse.units.domain.Task;
import com.hse.units.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<FormResponse, Long>, PagingAndSortingRepository<FormResponse, Long> {
    List<FormResponse> findByUser(User author);

    List<FormResponse> findByForm(Form form);

    Page<FormResponse> findByForm(Form form, Pageable pageable);

    FormResponse findById(long id);
}
