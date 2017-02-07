/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.distribution.repository;


import org.openlmis.distribution.domain.Refrigerator;
import org.openlmis.distribution.repository.mapper.RefrigeratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RefrigeratorRepository is Repository class for Refrigerator related database operations.
 */

@Repository
public class RefrigeratorRepository {

  @Autowired
  RefrigeratorMapper mapper;

  public List<Refrigerator> getRefrigeratorsForADeliveryZoneAndProgram(Long deliveryZoneId, Long programId) {
    return mapper.getRefrigeratorsForADeliveryZoneAndProgram(deliveryZoneId, programId);
  }

  public void update(Refrigerator refrigerator) {
    mapper.update(refrigerator);
  }

  public List<Refrigerator> getAllBy(Long facilityId) {
    return mapper.getAllBy(facilityId);
  }

  public void insert(Refrigerator refrigerator) {
    mapper.insert(refrigerator);
  }

  public void disableAllFor(Long facilityId) {
    mapper.disableAllFor(facilityId);
  }
}
