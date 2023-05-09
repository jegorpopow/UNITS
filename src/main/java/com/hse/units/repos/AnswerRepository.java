package com.hse.units.repos;

import com.hse.units.domain.Answer;
import com.hse.units.domain.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long> {
    Answer findById(long id);
}