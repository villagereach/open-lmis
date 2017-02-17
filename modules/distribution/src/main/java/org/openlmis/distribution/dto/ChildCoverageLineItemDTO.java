/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

package org.openlmis.distribution.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.distribution.domain.ChildCoverageLineItem;
import org.openlmis.distribution.domain.CoverageLineItem;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

/**
 *  DTO for ChildCoverageLineItem. It contains the client side representation of ChildCoverageLineItem attributes.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
public class ChildCoverageLineItemDTO extends CoverageLineItem {

  private String vaccination;
  private Integer displayOrder;
  private Reading healthCenter11Months;
  private Reading outreach11Months;
  private Reading healthCenter23Months;
  private Reading outreach23Months;
  private Reading maleHealthCenter11Months;
  private Reading maleOutreach11Months;
  private Reading maleHealthCenter23Months;
  private Reading maleOutreach23Months;
  private Reading femaleHealthCenter11Months;
  private Reading femaleOutreach11Months;
  private Reading femaleHealthCenter23Months;
  private Reading femaleOutreach23Months;

  public ChildCoverageLineItem transform() {
    Integer healthCenter11Months = Reading.safeRead(this.healthCenter11Months).parsePositiveInt();
    Integer healthCenter23Months = Reading.safeRead(this.healthCenter23Months).parsePositiveInt();
    Integer outreach11Months = Reading.safeRead(this.outreach11Months).parsePositiveInt();
    Integer outreach23Months = Reading.safeRead(this.outreach23Months).parsePositiveInt();
    Integer maleHealthCenter11Months = Reading.safeRead(this.maleHealthCenter11Months).parsePositiveInt();
    Integer maleHealthCenter23Months = Reading.safeRead(this.maleHealthCenter23Months).parsePositiveInt();
    Integer maleOutreach11Months = Reading.safeRead(this.maleOutreach11Months).parsePositiveInt();
    Integer maleOutreach23Months = Reading.safeRead(this.maleOutreach23Months).parsePositiveInt();
    Integer femaleHealthCenter11Months = Reading.safeRead(this.femaleHealthCenter11Months).parsePositiveInt();
    Integer femaleOutreach11Months = Reading.safeRead(this.femaleOutreach11Months).parsePositiveInt();
    Integer femaleHealthCenter23Months = Reading.safeRead(this.femaleHealthCenter23Months).parsePositiveInt();
    Integer femaleOutreach23Months = Reading.safeRead(this.femaleOutreach23Months).parsePositiveInt();

    ChildCoverageLineItem lineItem = new ChildCoverageLineItem();
    lineItem.setHealthCenter11Months(healthCenter11Months);
    lineItem.setHealthCenter23Months(healthCenter23Months);
    lineItem.setOutreach11Months(outreach11Months);
    lineItem.setOutreach23Months(outreach23Months);
    lineItem.setFemaleHealthCenter11Months(femaleHealthCenter11Months);
    lineItem.setFemaleHealthCenter23Months(femaleHealthCenter23Months);
    lineItem.setFemaleOutreach11Months(femaleOutreach11Months);
    lineItem.setFemaleOutreach23Months(femaleOutreach23Months);
    lineItem.setMaleHealthCenter11Months(maleHealthCenter11Months);
    lineItem.setMaleOutreach11Months(maleOutreach11Months);
    lineItem.setMaleHealthCenter23Months(maleHealthCenter23Months);
    lineItem.setMaleOutreach23Months(maleOutreach23Months);
    lineItem.setId(this.id);
    lineItem.setModifiedBy(this.modifiedBy);


    return lineItem;
  }
}
