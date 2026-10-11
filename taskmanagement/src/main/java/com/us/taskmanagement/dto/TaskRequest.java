package com.us.taskmanagement.dto;

import com.us.taskmanagement.enums.TaskPriority;
import com.us.taskmanagement.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {

    /*private String title;

    private String description;

    private String status;

    private String priority;

    private Long projectId;*/

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "TODO|IN_PROGRESS|COMPLETED",
            message = "Status must be TODO, IN_PROGRESS, or COMPLETED"
    )
    private TaskStatus status;

    @NotBlank(message = "Priority is required")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH",
            message = "Priority must be LOW, MEDIUM, or HIGH"
    )
    private TaskPriority priority;

    @NotNull(message = "Project ID is required")
    private Long projectId;

}
