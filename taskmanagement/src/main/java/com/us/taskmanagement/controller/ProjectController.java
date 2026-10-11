package com.us.taskmanagement.controller;

import com.us.taskmanagement.dto.ProjectRequest;
import com.us.taskmanagement.entity.Project;
import com.us.taskmanagement.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  /*  POST   /api/projects
    GET    /api/projects
    GET    /api/projects/{id}
    PUT    /api/projects/{id}
    DELETE /api/projects/{id}*/

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody ProjectRequest project){
        return new ResponseEntity<>(service.addProject(project), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Project>> getProject(){
        return new ResponseEntity<>(service.getAllProject(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
     public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        return new ResponseEntity<>(service.getProjectById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
     public ResponseEntity<Project> updateProjectById(@PathVariable Long id,@RequestBody Project updatedProject){
        return new ResponseEntity<>(service.updateProjectById(id,updatedProject),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
     public ResponseEntity<Project> deleteProjectById(@PathVariable Long id){
        return new ResponseEntity<>(service.deleteProjectById(id),HttpStatus.OK);
    }
}
