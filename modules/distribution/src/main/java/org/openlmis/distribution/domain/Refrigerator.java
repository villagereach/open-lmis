/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.core.exception.DataException;
import org.openlmis.distribution.dto.Reading;
import org.openlmis.distribution.dto.RefrigeratorDTO;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

/**
 * Refrigerator represents real world Refrigerator entity with its attributes. Also provides methods to validate a refrigerator.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class Refrigerator extends BaseModel {

  private String brand;
  private String serialNumber;
  private String model;
  private String type;
  private Long facilityId;
  private Boolean enabled;

  public void validate() {
    if (isBlank(serialNumber)) {
      throw new DataException("error.invalid.reading.value");
    }
  }

  public Refrigerator(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public RefrigeratorDTO transform() {
    RefrigeratorDTO dto = new RefrigeratorDTO();
    dto.setId(id);
    dto.setCreatedBy(createdBy);
    dto.setCreatedDate(createdDate);
    dto.setModifiedBy(modifiedBy);
    dto.setModifiedDate(modifiedDate);
    dto.setBrand(new Reading(brand));
    dto.setSerialNumber(new Reading(serialNumber));
    dto.setModel(new Reading(model));
    dto.setType(new Reading(type));
    dto.setFacilityId(facilityId);
    dto.setEnabled(enabled);

    return dto;
  }
}
