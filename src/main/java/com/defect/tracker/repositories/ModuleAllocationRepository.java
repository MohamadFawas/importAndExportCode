package com.defect.tracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.ModuleAllocation;

public interface ModuleAllocationRepository extends JpaRepository<ModuleAllocation, Long> {
  boolean existsByModuleId(Long moduleId);

  boolean existsBySubModuleId(Long subModuleId);

  boolean existsByEmployeeId(Long employeeId);

  public List<ModuleAllocation> findByModuleId(Long ModuleId);

  public List<ModuleAllocation> findBySubModuleId(Long subModuleId);

  public List<ModuleAllocation> findByEmployeeId(Long employeeId);

  boolean existsByModuleIdAndSubModuleId(Long moduleId, Long subModuleId);
 // boolean existsBySubModuleId(Long id);
}
