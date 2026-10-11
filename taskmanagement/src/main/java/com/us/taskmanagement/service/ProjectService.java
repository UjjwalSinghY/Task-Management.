package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.ProjectRequest;
import com.us.taskmanagement.entity.Project;

import java.util.List;

public interface ProjectService {
    Project addProject(ProjectRequest project);

    List<Project> getAllProject();

    Project getProjectById(Long id);

    Project updateProjectById(Long id, Project updatedProject);

    Project deleteProjectById(Long id);
}
