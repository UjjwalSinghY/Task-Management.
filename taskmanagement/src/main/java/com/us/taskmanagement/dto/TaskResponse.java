package com.us.taskmanagement.dto;

import com.us.taskmanagement.enums.TaskPriority;
import com.us.taskmanagement.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Long projectId;

    private String projectName;
}

