package com.defect.tracker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  boolean existsByStatusId(Long statusId);

  boolean existsByTypeId(Long typeId);
}
