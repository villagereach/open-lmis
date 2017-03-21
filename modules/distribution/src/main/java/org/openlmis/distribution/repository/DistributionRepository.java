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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.joda.time.DateTime;
import org.openlmis.core.domain.DeliveryZone;
import org.openlmis.core.domain.ProcessingPeriod;
import org.openlmis.core.domain.Program;
import org.openlmis.distribution.domain.Distribution;
import org.openlmis.distribution.domain.DistributionEdit;
import org.openlmis.distribution.domain.DistributionStatus;
import org.openlmis.distribution.domain.DistributionsEditHistory;
import org.openlmis.distribution.repository.mapper.DistributionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Repository class for distribution related database operations.
 */

@Repository
public class DistributionRepository {

  @Autowired
  DistributionMapper mapper;

  @Value("${distribution.period.allowed.months}")
  private Integer distributionPeriodAllowedMonths;

  public Distribution create(Distribution distribution) {
    distribution.setStatus(DistributionStatus.INITIATED);
    mapper.insert(distribution);
    return distribution;
  }

  public Distribution get(Distribution distribution) {
    return mapper.get(distribution);
  }

  public void updateDistributionStatus(Long distributionId, DistributionStatus status, Long modifiedBy) {
    mapper.updateDistributionStatus(distributionId, status, modifiedBy);

    if (status == DistributionStatus.SYNCED) {
      mapper.updateSyncDate(distributionId);
    }
  }

  public void updateLastViewed(Long distributionId) {
    mapper.updateLastViewed(distributionId);
  }

  public List<Long> getSyncedPeriodsForDeliveryZoneAndProgram(Long zoneId, Long programId) {
    return mapper.getSyncedPeriodsForDeliveryZoneAndProgram(zoneId, programId);
  }

  public Distribution getBy(Long distributionId) {
    return mapper.getBy(distributionId);
  }

  public Distribution getFullSyncedDistribution(Distribution distribution) {
    return mapper.getFullSyncedDistribution(distribution);
  }

  public Distribution getDistribution(Distribution distribution) {
    return mapper.getDistribution(distribution);
  }

  public List<Distribution> getFullSyncedDistributions() {
    return mapper.getFullSyncedDistributions();
  }

  public List<Distribution> getFullSyncedDistributions(Program program, DeliveryZone deliveryZone, ProcessingPeriod period) {
    List<Distribution> distributions;

    if (null != deliveryZone && null != period) {
      distributions = mapper.getFullSyncedDistributionsForProgramAndDeliveryZoneAndPeriod(program.getId(), deliveryZone.getId(), period.getId());
    } else if (null != deliveryZone) {
      distributions = mapper.getFullSyncedDistributionsForProgramAndDeliveryZone(program.getId(), deliveryZone.getId());
    } else if (null != period) {
      distributions = mapper.getFullSyncedDistributionsForProgramAndPeriod(program.getId(), period.getId());
    } else {
      distributions = mapper.getFullSyncedDistributionsForProgram(program.getId());
    }

    return FluentIterable.from(distributions).filter(new DistributionPeriodPredicate()).toImmutableList();
  }

  public void insertEditInProgress(Long userId, Long distributionId) {
    DistributionEdit edit = mapper.getEditInProgressForUser(distributionId, userId);

    if (null == edit) {
      mapper.insertEditInProgress(userId, distributionId);
    }
  }

  public List<DistributionEdit> getEditInProgress(Long distributionId, Long userId, Long periodInSeconds) {
    return mapper.getEditInProgress(distributionId, userId, periodInSeconds);
  }

  public void deleteDistributionEdit(Long distributionId, Long userId) {
    mapper.deleteDistributionEdit(distributionId, userId);
  }

  public List<DistributionsEditHistory> getHistory(Long distributionId) {
    return mapper.getHistory(distributionId);
  }

  public DistributionsEditHistory getLastHistory(Long distributionId) {
    return mapper.getLastHistory(distributionId);
  }

  public void insertHistory(DistributionsEditHistory history) {
    mapper.insertHistory(history);
  }

  private final class DistributionPeriodPredicate implements Predicate<Distribution> {
    @Override
    public boolean apply(@Nullable Distribution input) {
      return null != input && null != input.getPeriod() &&
              new DateTime(input.getPeriod().getStartDate()).isAfter(DateTime.now().minusMonths(distributionPeriodAllowedMonths));
    }
  }
}
