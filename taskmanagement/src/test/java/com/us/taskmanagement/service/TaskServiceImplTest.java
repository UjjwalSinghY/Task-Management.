package com.us.taskmanagement.service;


import com.us.taskmanagement.dto.TaskRequest;
import com.us.taskmanagement.dto.TaskResponse;
import com.us.taskmanagement.entity.Project;
import com.us.taskmanagement.entity.Task;
import com.us.taskmanagement.mapper.TaskMapper;
import com.us.taskmanagement.repository.ProjectRepository;
import com.us.taskmanagement.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequest request;
    private Task task;
    private TaskResponse response;
    private Project project;

    @BeforeEach
    void setUp() {
        request = new TaskRequest();
        request.setTitle("Create Login API");
        request.setDescription("Implement JWT login");
        request.setProjectId(1L);

        task = new Task();
        task.setId(10L);
        task.setTitle("Create Login API");
        task.setDescription("Implement JWT login");

        response = mock(TaskResponse.class);

        project = new Project();
        project.setId(1L);
    }

    @Test
    void addTaskShouldSaveAndReturnResponse() {
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));
        when(repository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.addTask(request);

        assertSame(response, result);
        assertSame(project, task.getProject());
        assertNotNull(task.getCreatedAt());
        assertNull(task.getUpdatedAt());

        verify(repository).save(task);
        verify(taskMapper).toResponse(task);
    }

    @Test
    void addTaskShouldFailWhenProjectIdIsNull() {
        request.setProjectId(null);

        when(taskMapper.toEntity(request)).thenReturn(task);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.addTask(request)
        );

        assertEquals("Project ID is required", exception.getMessage());
        verify(repository, never()).save(any(Task.class));
    }

    @Test
    void addTaskShouldFailWhenProjectDoesNotExist() {
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(projectRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.addTask(request)
        );

        assertEquals("Project not found: 1", exception.getMessage());
        verify(repository, never()).save(any(Task.class));
    }

    @Test
    void findByIdShouldReturnTaskResponse() {
        when(repository.findById(10L))
                .thenReturn(Optional.of(task));
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.findById(10L);

        assertSame(response, result);
    }

    @Test
    void findByIdShouldFailWhenTaskDoesNotExist() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.findById(99L)
        );

        assertEquals("Task not found: 99", exception.getMessage());
        verify(taskMapper, never()).toResponse(any(Task.class));
    }

    @Test
    void getAllTaskShouldReturnPagedResponses() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(repository.findAll(pageable)).thenReturn(taskPage);
        when(taskMapper.toResponse(task)).thenReturn(response);

        Page<TaskResponse> result = taskService.getAllTask(pageable);

        assertEquals(1, result.getTotalElements());
        assertSame(response, result.getContent().get(0));
        verify(repository).findAll(pageable);
    }

    @Test
    void updateByIdShouldUpdateAndReturnResponse() {
        request.setTitle("Update Login API");
        request.setDescription("Update JWT login");

        when(repository.findById(10L))
                .thenReturn(Optional.of(task));
        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));
        when(repository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.updateById(10L, request);

        assertSame(response, result);
        assertEquals("Update Login API", task.getTitle());
        assertEquals("Update JWT login", task.getDescription());
        assertSame(project, task.getProject());
        assertNotNull(task.getUpdatedAt());

        verify(repository).save(task);
    }

    @Test
    void deleteByIdShouldDeleteAndReturnResponse() {
        when(repository.findById(10L))
                .thenReturn(Optional.of(task));
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.deleteById(10L);

        assertSame(response, result);
        verify(repository).delete(task);
    }

    @Test
    void deleteByIdShouldFailWhenTaskDoesNotExist() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.deleteById(99L)
        );

        assertEquals("Task not found: 99", exception.getMessage());
        verify(repository, never()).delete(any(Task.class));
    }
}

