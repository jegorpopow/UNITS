package com.hse.units.services;

import com.hse.units.domain.Form;
import com.hse.units.domain.Task;
import com.hse.units.repos.FormRepository;
import com.hse.units.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FormService {
    @Autowired
    private FormRepository formRepository;


    public List<Form> getForms() {
        return StreamSupport.stream(formRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Form getFormByName(String name) {
        return formRepository.findFormByName(name);
    }

    public void addForm(Form form) {
        formRepository.save(form);
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }

    public Form getFormById(Long id) {
        return formRepository.findById(id).orElse(null);
    }

    public Page<Form> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.formRepository.findAll(pageable);
    }

}
