package com.hse.units.services;

import com.hse.units.domain.Form;
import com.hse.units.domain.Task;
import com.hse.units.repos.FormRepository;
import com.hse.units.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FormService {
    @Autowired
    private FormRepository formRepository;


    public List<Form> getForm() {
        return StreamSupport.stream(formRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void addTask(Form form) {
        formRepository.save(form);
    }

    public void deleteTask(Long id) {
        formRepository.deleteById(id);
    }

    public Form getTaskById(Long id) {
        return formRepository.findById(id).orElse(null);
    }

}
