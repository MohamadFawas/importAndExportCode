package com.defect.tracker.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleAllocationResponse {
  private Long id;
  private Long moduleId;
  private Long subModuleId;
  private Long projectAllocationId;
  private String employeeName;

}
