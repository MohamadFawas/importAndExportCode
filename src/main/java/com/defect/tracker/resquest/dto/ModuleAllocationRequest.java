package com.defect.tracker.resquest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleAllocationRequest {
  private Long id;
  private Long moduleId;
  private Long subModuleId;
  private Long projectAllocationId;

}
