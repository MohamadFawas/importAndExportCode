package com.defect.tracker.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.ProjectStatus;
import com.defect.tracker.entities.QProjectStatus;
import com.defect.tracker.repositories.ProjectStatusRepository;
import com.defect.tracker.response.dto.ProjectStatusResponse;
import com.defect.tracker.resquest.dto.ProjectStatusRequest;
import com.defect.tracker.search.response.ProjectStatusSearch;
import com.defect.tracker.service.ProjectStatusService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {
  @Autowired
  private ProjectStatusRepository projectStatusRepository;

  @Transactional
  public void saveProjectStatus(ProjectStatusRequest projectStatusRequest) {
    ProjectStatus projectStatus = new ProjectStatus();
    BeanUtils.copyProperties(projectStatusRequest, projectStatus);
    projectStatusRepository.save(projectStatus);
  }

  @Transactional
  public List<ProjectStatusResponse> getAllProjectStatus() {
    List<ProjectStatusResponse> projectStatusResponses = new ArrayList<>();
    List<ProjectStatus> projectStatuses = projectStatusRepository.findAll();

    for (ProjectStatus projectStatus : projectStatuses) {
      ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
      BeanUtils.copyProperties(projectStatus, projectStatusResponse);
      projectStatusResponses.add(projectStatusResponse);
    }
    return projectStatusResponses;
  }

  @Override
  public boolean isProjectStatusExists(String name) {
    return projectStatusRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public ProjectStatusResponse getProjectStatusById(Long id) {
    ProjectStatus projectStatus = projectStatusRepository.findById(id).get();
    ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
    BeanUtils.copyProperties(projectStatus, projectStatusResponse);
    return projectStatusResponse;
  }

  @Override
  public boolean existByProjectStatus(Long id) {
    return projectStatusRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedProjectStatusNameExist(Long id, String name) {
    return projectStatusRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public boolean isUpdatedProjectStatusColorExist(Long id, String color) {
    return projectStatusRepository.existsByColorIgnoreCaseAndIdNot(color, id);
  }

  @Override
  public void deleteProjectStatus(Long id) {
    projectStatusRepository.deleteById(id);
  }

  @Transactional
  public List<ProjectStatusResponse> multiSearchProjectStatus(Pageable pageable,
      Pagination pagination, ProjectStatusSearch projectStatusSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QProjectStatus qProjectStatus = QProjectStatus.projectStatus;
    if (Utils.isNotNullAndEmpty(projectStatusSearch.getName())) {
      booleanBuilder.and(qProjectStatus.name.containsIgnoreCase(projectStatusSearch.getName()));
    }

    List<ProjectStatusResponse> projectStatusResponses = new ArrayList<>();
    Page<ProjectStatus> projectStatuses = projectStatusRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(projectStatuses.getTotalElements());
    pagination.setTotalPages(projectStatuses.getTotalPages());
    for (ProjectStatus projectStatus : projectStatuses.toList()) {
      ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
      BeanUtils.copyProperties(projectStatus, projectStatusResponse);
      projectStatusResponses.add(projectStatusResponse);
    }
    return projectStatusResponses;
  }

@Override
public List<ProjectStatus> findAll() {
	// TODO Auto-generated method stub
	return (List<ProjectStatus>) projectStatusRepository.findAll();
}


@Transactional
@Override
public boolean importProjectStatusesFromCsv(MultipartFile file) {

  try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

    String line;

    while ((line = reader.readLine()) != null) {

      String[] data = line.split(",");

      ProjectStatusRequest projectStatusRequest = new ProjectStatusRequest();

      projectStatusRequest.setName(data[1]);

      projectStatusRequest.setColor(data[2]);

    //  employeeRequest.setEmail(data[3]);

    //  employeeRequest.setContactNumber(data[4]);

    //  employeeRequest.setAvailability(Double.parseDouble(data[6]));

   //   employeeRequest.setGender(data[7]);

    //  employeeRequest.setDesignationId(Long.parseLong(data[5]));

/*

      if (isEmployeeExistsByEmail(projectStatusRequest.getEmail())) {

        return false;



      }



      if (isEmployeeExistsByContactNumber(employeeRequest.getContactNumber())) {

        return false;

      }

*/

      ProjectStatus projectStatus = new ProjectStatus();
      BeanUtils.copyProperties(projectStatusRequest, projectStatus);
/*
      Designation designation = new Designation();

      designation.setId(employeeRequest.getDesignationId());

      employee.setDesignation(designation);


*/
      projectStatusRepository.save(projectStatus);
    }
    return true; // Import successful

  } catch (IOException e) {

    e.printStackTrace();
    return false; // Import failed
  }

}

}
