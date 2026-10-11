package com.us.taskmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String message;
    private Long taskId;
    private Long userId;
    private LocalDateTime createdAt;
}
