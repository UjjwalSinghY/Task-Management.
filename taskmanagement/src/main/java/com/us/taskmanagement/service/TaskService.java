package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.RequestTask;
import org.springframework.http.HttpStatusCode;

public interface TaskService {


    Object addTask(RequestTask task);

    Object getAllTask();

    Object findById(Long id);

    Object updateById(Long id);


    Object deleteById(Long id);
}
