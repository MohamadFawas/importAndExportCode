package com.defect.tracker.service;

public interface ProjectService {
  public boolean existsByProjectStatus(Long status);

  public boolean existsByProjectType(Long typeId);
}
