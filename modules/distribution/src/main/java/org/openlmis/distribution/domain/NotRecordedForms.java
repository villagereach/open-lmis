package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotRecordedForms {

  private Long facilityVisitId;
  private Boolean epiUseNotRecorded;
  private Boolean epiInventoryNotRecorded;
  private Boolean childCoverageNotRecorded;
  private Boolean adultCoverageNotRecorded;
  private Boolean fullCoverageNotRecorded;
}
