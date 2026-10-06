package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.RequestTask;
import com.us.taskmanagement.entity.Task;
import com.us.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object addTask(RequestTask task) {
        Task task1= Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
        return repository.save(task1);
    }

    @Override
    public Object getAllTask() {
        return repository.findAll();
    }

    @Override
    public Object findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Object updateById(Long id) {
        return null;
    }

    @Override
    public Object deleteById(Long id) {
        Task task=repository.findById(id).get();
        repository.deleteById(id);
        return task;
    }
}
