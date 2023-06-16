package com.defect.tracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.defect.tracker.common.response.BaseResponse;
import com.defect.tracker.common.response.ContentResponse;
import com.defect.tracker.resquest.dto.ModuleAllocationRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.service.ModuleAllocationService;
import com.defect.tracker.service.ModuleService;
import com.defect.tracker.service.ProjectAllocationService;
import com.defect.tracker.service.SubModuleService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class ModuleAllocationController {
  @Autowired
  private ModuleAllocationService moduleAllocationService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ModuleService moduleService;
  @Autowired
  private SubModuleService subModuleService;
  @Autowired
  private ProjectAllocationService projectAllocationService;
  private static final Logger logger = LoggerFactory.getLogger(ModuleAllocationController.class);

  @PostMapping(value = EndpointURI.MODULEALLOCATION)
  public ResponseEntity<Object> save(@RequestBody ModuleAllocationRequest moduleAllocationRequest) {
    if (moduleAllocationRequest.getProjectAllocationId() == null) {
      logger.info("must input the ProjectAllocation_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputProjectAllocationIdCode(),
          validationFailureResponseCode
              .getGetValidationModuleAllocationNotInputProjectAllocationId()));
    }
    if (moduleAllocationRequest.getModuleId() == null) {
      logger.info("must input the Module_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputModuleIdCode(),
          validationFailureResponseCode.getGetValidationModuleAllocationNotInputModuleId()));
    }
    if (moduleAllocationRequest.getSubModuleId() == null) {
      logger.info("must input the SubModule_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputSubModuleCode(),
          validationFailureResponseCode.getGetValidationModuleAllocationNotInputSubModuleId()));
    }
    if (!(subModuleService.existsByModuleIdAndId(moduleAllocationRequest.getModuleId(),
        moduleAllocationRequest.getSubModuleId())
        && projectAllocationService
            .existByProjectAllocation(moduleAllocationRequest.getProjectAllocationId()))) {
      logger.info("Invalid inputs (modulleId and subModuleId and projectAllocationId) ");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationInvalidInputtCode(),
          validationFailureResponseCode.getValidationModuleAllocationInvalidInputMassage()));
    }
    if (moduleAllocationService.existsByModuleIdAndSubModuleId(
        moduleAllocationRequest.getModuleId(), moduleAllocationRequest.getSubModuleId())) {
      logger.info("already exists inputs");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationAlreadyExists(),
          validationFailureResponseCode.getValidationModuleAllocationAlreadyExists()));
    }
    logger.info("ModuleAllocation added successfully");
    moduleAllocationService.saveModuleAllocation(moduleAllocationRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveModuleAllocationSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULEALLOCATION)
  public ResponseEntity<Object> getAllModuleAllocation() {
    logger.info("All moduleAllocation retrieved successfully");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULE_ALLOCTIONS,
        moduleAllocationService.getAllModuleAllocation(), RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetAllModuleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULEALLOCATION_BY_ID)
  public ResponseEntity<Object> getModuleAllocationById(@PathVariable Long id) {
    if (!moduleAllocationService.existByModuleAllocation(id)) {
      logger.info("ModuleAllocation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotExistesCode(),
          validationFailureResponseCode.getModuleAllocationNotExistsMessage()));
    }
    logger.info("ModuleAllocation retrieved successfully for given id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULE_ALLOCTION,
        moduleAllocationService.getModuleAllocationById(id), RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetmoduleAllocationByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.MODULEALLOCATION)
  public ResponseEntity<Object> updateModuleAllocation(
      @RequestBody ModuleAllocationRequest moduleAllocationRequest) {

    if (moduleAllocationRequest.getProjectAllocationId() == null) {
      logger.info("must input the ProjectAllocation_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputProjectAllocationIdCode(),
          validationFailureResponseCode
              .getGetValidationModuleAllocationNotInputProjectAllocationId()));
    }
    if (moduleAllocationRequest.getModuleId() == null) {
      logger.info("must input the Module_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputModuleIdCode(),
          validationFailureResponseCode.getGetValidationModuleAllocationNotInputModuleId()));
    }
    if (moduleAllocationRequest.getSubModuleId() == null) {
      logger.info("must input the SubModule_id");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotInputSubModuleCode(),
          validationFailureResponseCode.getGetValidationModuleAllocationNotInputSubModuleId()));
    }
    if (!moduleAllocationService.existByModuleAllocation(moduleAllocationRequest.getId())) {
      logger.info("ModuleAllocation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotExistesCode(),
          validationFailureResponseCode.getModuleAllocationNotExistsMessage()));
    }
    if (moduleAllocationService.existsByModuleIdAndSubModuleId(
        moduleAllocationRequest.getModuleId(), moduleAllocationRequest.getSubModuleId())) {
      logger.info("already exists inputs");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationAlreadyExists(),
          validationFailureResponseCode.getValidationModuleAllocationAlreadyExists()));
    }
    if (!(subModuleService.existsByModuleIdAndId(moduleAllocationRequest.getModuleId(),
        moduleAllocationRequest.getSubModuleId())
        && projectAllocationService
            .existByProjectAllocation(moduleAllocationRequest.getProjectAllocationId()))) {
      logger.info("Invalid inputs (modulleId and subModuleId and projectAllocationId) ");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationInvalidInputtCode(),
          validationFailureResponseCode.getValidationModuleAllocationInvalidInputMassage()));
    }
    logger.info("ModuleAllocation updated successfully");
    moduleAllocationService.saveModuleAllocation(moduleAllocationRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateModuleAllocationSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.MODULEALLOCATION_BY_ID)
  public ResponseEntity<Object> deleteModuleAllocation(@PathVariable Long id) {
    if (!moduleAllocationService.existByModuleAllocation(id)) {
      logger.info("ModuleAllocation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAllocationNotExistesCode(),
          validationFailureResponseCode.getModuleAllocationNotExistsMessage()));
    }
    logger.info("ModuleAllocation deleted successfully");
    moduleAllocationService.deleteModuleAllocation(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteModuleAllocationeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULEALLOCATION_BY_MODULE_ID)
  public ResponseEntity<Object> getAllModuleAllocationByModuleId(@PathVariable Long id) {
    if (!moduleService.existByModule(id)) {
      logger.info("Module_Id is not exists in the Module_Allocation table");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getGetModuleAllocationNotExistesByModuleIdCode(),
          validationFailureResponseCode.getGetModuleAllocationNotExistsByModuleIdMessage()));
    }
    logger.info("Module Allocation retrieved successfully for given module_id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULE_ALLOCTION,
        moduleAllocationService.getAllModuleAllocationByModuleId(id),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetmoduleAllocationByModuleIdSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULEALLOCATION_BY_SUBMODULE_ID)
  public ResponseEntity<Object> getAllModuleAllocationBySubModuleId(@PathVariable Long id) {
    if (!subModuleService.existBySubModule(id)) {
      logger.info("SubModule_Id is not exists in the Module_Allocation table");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getGetModuleAllocationNotExistesBySubModuleIdCode(),
          validationFailureResponseCode.getGetModuleAllocationNotExistsBySubModuleIdMessage()));
    }
    logger.info("Module Allocation retrieved successfully for given SubModule_id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULE_ALLOCTION,
        moduleAllocationService.getAllModuleAllocationBySubModuleId(id),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetmoduleAllocationBySubModuleIdSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULEALLOCATION_BY_PROJECTALLOCATION_ID)
  public ResponseEntity<Object> getAllModuleAllocationByEmployeeId(@PathVariable Long id) {
    if (!projectAllocationService.existByProjectAllocation(id)) {
      logger.info("ProjectAllocation_Id is not exists in the Module_Allocation table");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getGetModuleAllocationNotExistesByProjectAllocationIdCode(),
          validationFailureResponseCode
              .getGetModuleAllocationNotExistsByProjectAllocationIdMessage()));
    }
    logger.info("Module Allocation retrieved successfully for given SubModule_id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULE_ALLOCTION,
        moduleAllocationService.getAllModuleAllocationByEmployeeId(id),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetmoduleAllocationByProjectAllocationIdSuccessMessage()));
  }
}
