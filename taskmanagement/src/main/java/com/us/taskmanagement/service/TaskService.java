package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.TaskRequest;
import com.us.taskmanagement.dto.TaskResponse;
import com.us.taskmanagement.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskResponse addTask(TaskRequest request);

    Page<TaskResponse> getAllTask(Pageable pageable);

    TaskResponse findById(Long id);

    TaskResponse updateById(Long id, TaskRequest request);

    TaskResponse deleteById(Long id);
}
