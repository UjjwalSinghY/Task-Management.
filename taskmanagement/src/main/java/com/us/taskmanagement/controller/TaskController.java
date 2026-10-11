package com.us.taskmanagement.controller;

import com.us.taskmanagement.dto.TaskRequest;
import com.us.taskmanagement.dto.TaskResponse;
import com.us.taskmanagement.entity.Task;
import com.us.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

/*   *//* POST /api/tasks
    GET  /api/tasks
    GET  /api/tasks/{id}
    PUT  /api/tasks/{id}
    DELETE /api/tasks/{id}*//*

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Task> addTask(@Valid @RequestBody TaskRequest task){
        return new ResponseEntity(service.addTask(task),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        return new ResponseEntity(service.getAllTask(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return new ResponseEntity(service.findById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateTaskById(@PathVariable Long id,@RequestBody Task updatedTask){
        return new ResponseEntity(service.updateById(id,updatedTask),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable Long id){
        return new ResponseEntity(service.deleteById(id),HttpStatus.OK);
    }*/

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addTask(request));
    }

  /*  @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTask() {
        return ResponseEntity.ok(service.getAllTask());
    }*/
  @GetMapping
  public ResponseEntity<Page<TaskResponse>> getAllTask(
          Pageable pageable) {
      return ResponseEntity.ok(service.getAllTask(pageable));
  }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTaskById(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(service.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTaskById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
