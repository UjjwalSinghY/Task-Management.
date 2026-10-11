package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.ProjectRequest;
import com.us.taskmanagement.entity.Project;
import com.us.taskmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project addProject(ProjectRequest project) {
        Project project1=new Project();
        project1.setName(project.getName());
        project1.setDescription(project.getDescription());
        project1.setCreated_at(LocalDateTime.now());
        project1.setUpdated_at(null);
        return repository.save(project1);
    }

    @Override
    public List<Project> getAllProject() {
        return repository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Project Not Found!"));
    }

    @Override
    public Project updateProjectById(Long id, Project updatedProject) {
        Project oldProject=repository.findById(id).orElseThrow(()->new RuntimeException("Project Not Found!"));
        updatedProject.setId(oldProject.getId());
        updatedProject.setUpdated_at(LocalDateTime.now());
        repository.save(updatedProject);
        return updatedProject;
    }

    @Override
    public Project deleteProjectById(Long id) {
        Project project=repository.findById(id).orElseThrow(()->new RuntimeException("Project Not Found!"));
        repository.deleteById(project.getId());
        return project;
    }
}
