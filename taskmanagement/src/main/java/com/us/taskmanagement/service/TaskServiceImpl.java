package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.TaskRequest;
import com.us.taskmanagement.dto.TaskResponse;
import com.us.taskmanagement.entity.Project;
import com.us.taskmanagement.entity.Task;
import com.us.taskmanagement.mapper.TaskMapper;
import com.us.taskmanagement.repository.ProjectRepository;
import com.us.taskmanagement.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

   /* private final TaskRepository repository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task addTask(TaskRequest task) {
        Task task1= Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        Long projectId = task.getProjectId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found: " + projectId));

        task1.setProject(project);
        return repository.save(task1);
    }

    @Override
    public List<Task> getAllTask() {
        return repository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Task not Found!!"));
    }

    @Override
    public Task updateById(Long id,Task updatedTask) {
        Task oldTask=repository.findById(id).orElseThrow(()->new RuntimeException("Task not Found!!"));
        updatedTask.setId(oldTask.getId());
        return repository.save(updatedTask);
    }

    @Override
    public Task deleteById(Long id) {
        Task task=repository.findById(id).get();
        repository.deleteById(id);
        return task;
    }*/
   private final TaskRepository repository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(
            TaskRepository repository,
            ProjectRepository projectRepository,
            TaskMapper taskMapper) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponse addTask(TaskRequest request) {
        Task task = taskMapper.toEntity(request);

        Project project = findProject(request.getProjectId());
        task.setProject(project);

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(null);

        Task savedTask = repository.save(task);

        return taskMapper.toResponse(savedTask);
    }

    @Override
    public Page<TaskResponse> getAllTask(Pageable pageable) {
        return repository.findAll(pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    public TaskResponse findById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found: " + id));

        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse updateById(Long id, TaskRequest request) {
        Task existingTask = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found: " + id));

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStatus(request.getStatus());
        existingTask.setPriority(request.getPriority());

        Project project = findProject(request.getProjectId());
        existingTask.setProject(project);
        existingTask.setUpdatedAt(LocalDateTime.now());

        Task savedTask = repository.save(existingTask);

        return taskMapper.toResponse(savedTask);
    }

    @Override
    public TaskResponse deleteById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found: " + id));

        repository.delete(task);

        return taskMapper.toResponse(task);
    }

    private Project findProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException(
                    "Project ID is required");
        }

        return projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found: " + projectId));
    }
}
