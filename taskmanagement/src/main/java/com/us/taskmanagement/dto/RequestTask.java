package com.us.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RequestTask {

    private String title;

    private String description;

    private String status;

    private String priority;

}
