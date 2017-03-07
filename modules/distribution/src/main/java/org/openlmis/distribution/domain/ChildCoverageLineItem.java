/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.Facility;
import org.openlmis.distribution.dto.ChildCoverageLineItemDTO;
import org.openlmis.distribution.dto.Reading;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

/**
 *  ChildCoverageLineItem represents data captured against a vaccination to determine coverage for a particular
 *  category. It extends CoverageLineItem to inherit the facilityVisitId and targetGroup for the corresponding
 *  vaccination.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildCoverageLineItem extends CoverageLineItem {

  private String vaccination;
  private Integer displayOrder;
  private Integer totalHealthCenter11Months;
  private Integer totalOutreach11Months;
  private Integer totalHealthCenter23Months;
  private Integer totalOutreach23Months;
  private Integer maleHealthCenter11Months;
  private Integer maleOutreach11Months;
  private Integer maleHealthCenter23Months;
  private Integer maleOutreach23Months;
  private Integer femaleHealthCenter11Months;
  private Integer femaleOutreach11Months;
  private Integer femaleHealthCenter23Months;
  private Integer femaleOutreach23Months;

  public ChildCoverageLineItem(FacilityVisit facilityVisit, Facility facility, TargetGroupProduct targetGroupProduct,
                               String vaccination, Integer displayOrder, Integer processingPeriodMonths) {
    super(facilityVisit, facility, targetGroupProduct, processingPeriodMonths);
    this.vaccination = vaccination;
    this.displayOrder = displayOrder;
  }

  public ChildCoverageLineItemDTO transform() {
    ChildCoverageLineItemDTO dto = new ChildCoverageLineItemDTO();
    dto.setId(id);
    dto.setCreatedBy(createdBy);
    dto.setCreatedDate(createdDate);
    dto.setModifiedBy(modifiedBy);
    dto.setModifiedDate(modifiedDate);
    dto.setVaccination(vaccination);
    dto.setDisplayOrder(displayOrder);
    dto.setTotalHealthCenter11Months(new Reading(totalHealthCenter11Months));
    dto.setTotalHealthCenter23Months(new Reading(totalHealthCenter23Months));
    dto.setTotalOutreach11Months(new Reading(totalOutreach11Months));
    dto.setTotalOutreach23Months(new Reading(totalOutreach23Months));
    dto.setFacilityVisitId(facilityVisitId);
    dto.setTargetGroup(targetGroup);
    dto.setFemaleHealthCenter11Months(new Reading(femaleHealthCenter11Months));
    dto.setFemaleHealthCenter23Months(new Reading(femaleHealthCenter23Months));
    dto.setFemaleOutreach11Months(new Reading(femaleOutreach11Months));
    dto.setFemaleOutreach23Months(new Reading(femaleOutreach23Months));
    dto.setMaleHealthCenter11Months(new Reading(maleHealthCenter11Months));
    dto.setMaleHealthCenter23Months(new Reading(maleHealthCenter23Months));
    dto.setMaleOutreach11Months(new Reading(maleOutreach11Months));
    dto.setMaleOutreach23Months(new Reading(maleOutreach23Months));

    return dto;
  }
}
