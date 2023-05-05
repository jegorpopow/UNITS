package com.hse.units.repos;

import com.hse.units.domain.Form;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hse.units.domain.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends CrudRepository<Form, Long>, PagingAndSortingRepository<Form, Long> {
    List<Form> findByCreator(long creator);

    Form findById(long id);

    Form findFormByName(String name);


    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE form_contains", nativeQuery = true)
    void removeMapping();
}
