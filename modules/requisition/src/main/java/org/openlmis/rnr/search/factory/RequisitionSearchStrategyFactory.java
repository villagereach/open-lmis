/*
 * Copyright © 2013 VillageReach. All Rights Reserved. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 *
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.openlmis.rnr.search.factory;

import lombok.NoArgsConstructor;
import org.openlmis.core.service.ProcessingScheduleService;
import org.openlmis.core.service.ProgramService;
import org.openlmis.rnr.repository.RequisitionRepository;
import org.openlmis.rnr.search.criteria.RequisitionSearchCriteria;
import org.openlmis.rnr.search.strategy.*;
import org.openlmis.rnr.service.RequisitionPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class RequisitionSearchStrategyFactory {

  @Autowired
  private ProcessingScheduleService processingScheduleService;

  @Autowired
  private RequisitionRepository requisitionRepository;

  @Autowired
  private ProgramService programService;

  @Autowired
  private RequisitionPermissionService requisitionPermissionService;


  public RequisitionSearchStrategy getSearchStrategy(RequisitionSearchCriteria criteria) {
    if (criteria.isEmergency()) {
      return new EmergencyRequisitionSearch(criteria, requisitionRepository);
    } else if (criteria.isWithoutLineItems()) {
      return new RequisitionOnlySearch(criteria, requisitionRepository);
    } else if (criteria.getPeriodId() != null) {
      return new FacilityProgramPeriodSearch(criteria, requisitionRepository);
    } else if (criteria.getProgramId() == null) {
      return new FacilityDateRangeSearch(criteria, processingScheduleService, requisitionRepository, programService);
    }
    return new FacilityProgramDateRangeSearch(criteria, requisitionPermissionService, processingScheduleService, requisitionRepository);
  }
}
