package com.us.taskmanagement.controller;

import com.us.taskmanagement.dto.RequestTask;
import com.us.taskmanagement.entity.Task;
import com.us.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

   /* POST /api/tasks
    GET  /api/tasks
    GET  /api/tasks/{id}
    PUT  /api/tasks/{id}
    DELETE /api/tasks/{id}*/

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody RequestTask task){
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
    public ResponseEntity updateTaskById(@PathVariable Long id){
        return new ResponseEntity(service.updateById(id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable Long id){
        return new ResponseEntity(service.deleteById(id),HttpStatus.OK);
    }
}
