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

import com.google.common.base.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.core.domain.Refrigerator;
import org.openlmis.distribution.domain.MonitoringDeviceType;
import org.openlmis.distribution.domain.RefrigeratorProblem;
import org.openlmis.distribution.domain.RefrigeratorReading;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;
import static org.openlmis.distribution.dto.Reading.EMPTY;

/**
 *  This DTO contains facilityVisitId, Refrigerator entity, Refrigerator Problem entity and client side
 *  representation of Refrigerator Reading attributes.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class RefrigeratorReadingDTO extends BaseModel {

  Refrigerator refrigerator;
  //Readings
  Long facilityVisitId;
  Reading temperature;
  Reading functioningCorrectly;
  Reading lowAlarmEvents;
  Reading highAlarmEvents;
  Reading problemSinceLastTime;
  RefrigeratorProblem problems;
  String notes;
  Reading hasMonitoringDevice;
  Reading monitoringDeviceType;
  Reading monitoringDeviceOtherType;
  Reading temperatureReportingForm;
  Reading highestTemperatureReported;
  Reading lowestTemperatureReported;
  Reading problemOccurredDate;
  Reading problemReportedDate;
  Reading equipmentRepaired;
  Reading equipmentRepairedDate;
  Reading totalDaysCceUptime;

  public RefrigeratorReading transform() {
    refrigerator.setModifiedBy(this.modifiedBy);
    refrigerator.setCreatedBy(this.createdBy);
    refrigerator.validate();

    if ("N".equalsIgnoreCase(functioningCorrectly.getEffectiveValue())) {
      problems.validate();
    } else {
      problems = null;
    }

    String hasMonitoringDevice = Optional.fromNullable(this.hasMonitoringDevice).or(EMPTY).getEffectiveValue();
    MonitoringDeviceType monitoringDeviceType = null != this.monitoringDeviceType ? MonitoringDeviceType.valueOf(this.monitoringDeviceType.getEffectiveValue()) : null;
    String monitoringDeviceOtherType = Optional.fromNullable(this.monitoringDeviceOtherType).or(EMPTY).getEffectiveValue();
    String temperatureReportingForm = Optional.fromNullable(this.temperatureReportingForm).or(EMPTY).getEffectiveValue();
    Integer highestTemperatureReported = Optional.fromNullable(this.highestTemperatureReported).or(EMPTY).parsePositiveInt();
    Integer lowestTemperatureReported = Optional.fromNullable(this.lowestTemperatureReported).or(EMPTY).parsePositiveInt();
    String problemOccurredDate = Optional.fromNullable(this.problemOccurredDate).or(EMPTY).getEffectiveValue();
    String problemReportedDate = Optional.fromNullable(this.problemReportedDate).or(EMPTY).getEffectiveValue();
    String equipmentRepaired = Optional.fromNullable(this.equipmentRepaired).or(EMPTY).getEffectiveValue();
    String equipmentRepairedDate = Optional.fromNullable(this.equipmentRepairedDate).or(EMPTY).getEffectiveValue();
    Integer totalDaysCceUptime = Optional.fromNullable(this.totalDaysCceUptime).or(EMPTY).parsePositiveInt();

    RefrigeratorReading reading = new RefrigeratorReading(this.refrigerator, this.facilityVisitId,
      this.temperature.parseFloat(),
      this.functioningCorrectly.getEffectiveValue(),
      this.lowAlarmEvents.parsePositiveInt(),
      this.highAlarmEvents.parsePositiveInt(),
      this.problemSinceLastTime.getEffectiveValue(),
      this.problems,
      this.notes,
      hasMonitoringDevice, monitoringDeviceType, monitoringDeviceOtherType, temperatureReportingForm,
      highestTemperatureReported, lowestTemperatureReported, problemOccurredDate, problemReportedDate,
      equipmentRepaired, equipmentRepairedDate, totalDaysCceUptime);
    reading.setCreatedBy(this.createdBy);
    reading.setModifiedBy(this.modifiedBy);
    return reading;
  }
}
