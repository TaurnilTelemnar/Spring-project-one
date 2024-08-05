package com.javarush.projectspring.service;

import com.javarush.projectspring.dao.TaskDAO;
import com.javarush.projectspring.domain.Task;
import com.javarush.projectspring.dto.TaskTo;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.javarush.projectspring.mapper.Dto.MAPPER;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskTo get(Long id) {
        Task task = taskDAO.findById(id).orElse(null);
        return MAPPER.from(task);
    }

    public long getAllCount() {
        return taskDAO.count();
    }

    public Collection<TaskTo> getAllByPage(int number, int size) {
        Pageable pageable = PageRequest.of(number - 1, size);
        Page<Task> taskPage = taskDAO.findAll(pageable);
        return taskPage.getContent()
                .stream()
                .map(MAPPER::from)
                .collect(Collectors.toList());
    }


    @Transactional
    public TaskTo save(TaskTo taskTo) {
        Task task = taskDAO.saveAndFlush(MAPPER.from(taskTo));
        return MAPPER.from(task);
    }

    @Transactional
    public void delete(TaskTo taskTo) {
        taskDAO.delete(MAPPER.from(taskTo));
    }


}
