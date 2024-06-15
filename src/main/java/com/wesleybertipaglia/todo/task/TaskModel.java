package com.wesleybertipaglia.todo.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;
    private String title;
    private String content;
    private Priority priority = Priority.LOW;
    private Status status = Status.PENDING;

    @Column(nullable = true)
    private String assignee;

    @Column(nullable = true)
    private LocalDateTime startedAt;

    @Column(nullable = true)
    private LocalDateTime finishedAt;

    @Column(nullable = true)
    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
