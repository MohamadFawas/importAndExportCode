package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defect.tracker.entities.Module;
import com.defect.tracker.entities.ModuleAllocation;
import com.defect.tracker.entities.ProjectAllocation;
import com.defect.tracker.entities.SubModule;
import com.defect.tracker.repositories.ModuleAllocationRepository;
import com.defect.tracker.response.dto.ModuleAllocationResponse;
import com.defect.tracker.resquest.dto.ModuleAllocationRequest;
import com.defect.tracker.service.ModuleAllocationService;


@Service
public class ModuleAllocationServiceImpl implements ModuleAllocationService {
  @Autowired
  private ModuleAllocationRepository moduleAllocationRepository;

  @Transactional
  public void saveModuleAllocation(ModuleAllocationRequest moduleAllocationRequest) {
    ModuleAllocation moduleAllocation = new ModuleAllocation();
    BeanUtils.copyProperties(moduleAllocationRequest, moduleAllocation);
    Module module = new Module();
    module.setId(moduleAllocationRequest.getModuleId());
    moduleAllocation.setModule(module);
    SubModule subModule = new SubModule();
    subModule.setId(moduleAllocationRequest.getSubModuleId());
    moduleAllocation.setSubModule(subModule);
    ProjectAllocation projectAllocation = new ProjectAllocation();
    projectAllocation.setId(moduleAllocationRequest.getProjectAllocationId());
    moduleAllocation.setEmployee(projectAllocation);
    moduleAllocationRepository.save(moduleAllocation);
  }

  @Override
  public boolean existByModuleAllocation(Long id) {
    return moduleAllocationRepository.existsById(id);
  }

  @Override
  public boolean existBySubModuleId(Long id) {
    return moduleAllocationRepository.existsBySubModuleId(id);
  }

  @Override
  public boolean existByModuleId(Long id) {
    return moduleAllocationRepository.existsByModuleId(id);
  }

  @Transactional
  public List<ModuleAllocationResponse> getAllModuleAllocation() {

    List<ModuleAllocationResponse> moduleAllocationResponses = new ArrayList<>();
    List<ModuleAllocation> moduleAllocations = moduleAllocationRepository.findAll();

    for (ModuleAllocation moduleAllocation : moduleAllocations) {
      ModuleAllocationResponse moduleAllocationResponse = new ModuleAllocationResponse();
      BeanUtils.copyProperties(moduleAllocation, moduleAllocationResponse);
      moduleAllocationResponse.setModuleId(moduleAllocation.getModule().getId());
      moduleAllocationResponse.setSubModuleId(moduleAllocation.getSubModule().getId());
      moduleAllocationResponse.setProjectAllocationId(moduleAllocation.getEmployee().getId());
      moduleAllocationResponse
          .setEmployeeName(moduleAllocation.getEmployee().getEmployee().getFirstName());
      moduleAllocationResponses.add(moduleAllocationResponse);
    }
    return moduleAllocationResponses;
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationByModuleId(Long id) {
    return moduleAllocationRepository.findByModuleId(id);
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationBySubModuleId(Long id) {
    return moduleAllocationRepository.findBySubModuleId(id);
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationByEmployeeId(Long id) {
    return moduleAllocationRepository.findByEmployeeId(id);
  }

  @Override
  public ModuleAllocationResponse getModuleAllocationById(Long id) {

    ModuleAllocation moduleAllocation = moduleAllocationRepository.findById(id).get();
    ModuleAllocationResponse moduleAllocationResponse = new ModuleAllocationResponse();
    BeanUtils.copyProperties(moduleAllocation, moduleAllocationResponse);
    moduleAllocationResponse.setModuleId(moduleAllocation.getModule().getId());
    moduleAllocationResponse.setSubModuleId(moduleAllocation.getSubModule().getId());
    moduleAllocationResponse.setProjectAllocationId(moduleAllocation.getEmployee().getId());
    moduleAllocationResponse
        .setEmployeeName(moduleAllocation.getEmployee().getEmployee().getFirstName());
    return moduleAllocationResponse;
  }

  @Override
  public boolean existsByProjectAllocatonId(Long projectAllocationId) {
    return moduleAllocationRepository.existsByEmployeeId(projectAllocationId);
  }

  @Override
  public void deleteModuleAllocation(Long id) {
    moduleAllocationRepository.deleteById(id);

  }

  @Override
  public boolean existsByModuleIdAndSubModuleId(Long moduleId, Long subModuleId) {
    return moduleAllocationRepository.existsByModuleIdAndSubModuleId(moduleId, subModuleId);
  }

  @Override
  public boolean existsBySubModule(Long subModuleId) {
    return moduleAllocationRepository.existsBySubModuleId(subModuleId);
  }
}
