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
  private Reading totalHealthCenter11Months;
  private Reading totalOutreach11Months;
  private Reading totalHealthCenter23Months;
  private Reading totalOutreach23Months;
  private Reading maleHealthCenter11Months;
  private Reading maleOutreach11Months;
  private Reading maleHealthCenter23Months;
  private Reading maleOutreach23Months;
  private Reading femaleHealthCenter11Months;
  private Reading femaleOutreach11Months;
  private Reading femaleHealthCenter23Months;
  private Reading femaleOutreach23Months;
  private Reading femaleHealthCenter9YMonths;
  private Reading femaleOutreach9YMonths;

  public ChildCoverageLineItem transform() {
    Integer totalHealthCenter11Months = Reading.safeRead(this.totalHealthCenter11Months).parsePositiveInt();
    Integer totalHealthCenter23Months = Reading.safeRead(this.totalHealthCenter23Months).parsePositiveInt();
    Integer totalOutreach11Months = Reading.safeRead(this.totalOutreach11Months).parsePositiveInt();
    Integer totalOutreach23Months = Reading.safeRead(this.totalOutreach23Months).parsePositiveInt();
    Integer maleHealthCenter11Months = Reading.safeRead(this.maleHealthCenter11Months).parsePositiveInt();
    Integer maleHealthCenter23Months = Reading.safeRead(this.maleHealthCenter23Months).parsePositiveInt();
    Integer maleOutreach11Months = Reading.safeRead(this.maleOutreach11Months).parsePositiveInt();
    Integer maleOutreach23Months = Reading.safeRead(this.maleOutreach23Months).parsePositiveInt();
    Integer femaleHealthCenter11Months = Reading.safeRead(this.femaleHealthCenter11Months).parsePositiveInt();
    Integer femaleOutreach11Months = Reading.safeRead(this.femaleOutreach11Months).parsePositiveInt();
    Integer femaleHealthCenter23Months = Reading.safeRead(this.femaleHealthCenter23Months).parsePositiveInt();
    Integer femaleOutreach23Months = Reading.safeRead(this.femaleOutreach23Months).parsePositiveInt();
    Integer femaleHealthCenter9YMonths = Reading.safeRead(this.femaleHealthCenter9YMonths).parsePositiveInt();
    Integer femaleOutreach9YMonths = Reading.safeRead(this.femaleOutreach9YMonths).parsePositiveInt();

    ChildCoverageLineItem lineItem = new ChildCoverageLineItem();
    lineItem.setTotalHealthCenter11Months(totalHealthCenter11Months);
    lineItem.setTotalHealthCenter23Months(totalHealthCenter23Months);
    lineItem.setTotalOutreach11Months(totalOutreach11Months);
    lineItem.setTotalOutreach23Months(totalOutreach23Months);
    lineItem.setFemaleHealthCenter11Months(femaleHealthCenter11Months);
    lineItem.setFemaleHealthCenter23Months(femaleHealthCenter23Months);
    lineItem.setFemaleOutreach11Months(femaleOutreach11Months);
    lineItem.setFemaleOutreach23Months(femaleOutreach23Months);
    lineItem.setMaleHealthCenter11Months(maleHealthCenter11Months);
    lineItem.setMaleOutreach11Months(maleOutreach11Months);
    lineItem.setMaleHealthCenter23Months(maleHealthCenter23Months);
    lineItem.setMaleOutreach23Months(maleOutreach23Months);
    lineItem.setFemaleHealthCenter9YMonths(femaleHealthCenter9YMonths);
    lineItem.setFemaleOutreach9YMonths(femaleOutreach9YMonths);
    lineItem.setId(this.id);
    lineItem.setModifiedBy(this.modifiedBy);


    return lineItem;
  }
}
