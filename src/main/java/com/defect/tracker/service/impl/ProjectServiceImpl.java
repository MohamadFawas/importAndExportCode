package com.defect.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.defect.tracker.repositories.ProjectRepository;
import com.defect.tracker.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Override
  public boolean existsByProjectStatus(Long statusId) {
    return projectRepository.existsByStatusId(statusId);
  }

  @Override
  public boolean existsByProjectType(Long typeId) {
    return projectRepository.existsByTypeId(typeId);
  }
}
