/*
 *
 *  * This program is part of the OpenLMIS logistics management information system platform software.
 *  * Copyright © 2013 VillageReach
 *  *
 *  * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  *  
 *  * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 *
 */

package org.openlmis.distribution.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.openlmis.distribution.domain.FacilityVisit;
import org.openlmis.distribution.domain.MotorbikeProblems;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * It maps the FacilityVisit entity to corresponding representation in database.
 */

@Repository
public interface FacilityVisitMapper {

  @Insert({"INSERT INTO facility_visits (distributionId, facilityId, facilityCatchmentPopulation, priorObservations, createdBy, modifiedBy)",
    "VALUES (#{distributionId}, #{facilityId}, #{facilityCatchmentPopulation}, #{priorObservations}, #{createdBy}, #{modifiedBy})"})
  @Options(useGeneratedKeys = true)
  public void insert(FacilityVisit facilityVisit);

  @Insert({"INSERT INTO motorbike_problems(facilityVisitId, lackOfFundingForFuel, repairsSchedulingProblem, lackOfFundingForRepairs, missingParts, other, motorbikeProblemOther, createdBy, modifiedBy) ",
    "VALUES (#{facilityVisitId}, COALESCE(#{lackOfFundingForFuel}, FALSE), COALESCE(#{repairsSchedulingProblem}, FALSE), COALESCE(#{lackOfFundingForRepairs}, FALSE),",
    "COALESCE(#{missingParts}, FALSE), COALESCE(#{other}, FALSE), #{motorbikeProblemOther}, #{createdBy}, #{modifiedBy})"})
  @Options(useGeneratedKeys = true)
  void insertMotorbikeProblems(MotorbikeProblems motorbikeProblems);

  @Select("SELECT * FROM facility_visits WHERE distributionId = #{distributionId} AND facilityId = #{facilityId}")
  @Results({
    @Result(property = "verifiedBy.name", column = "verifiedByName"),
    @Result(property = "verifiedBy.title", column = "verifiedByTitle"),
    @Result(property = "confirmedBy.name", column = "confirmedByName"),
    @Result(property = "confirmedBy.title", column = "confirmedByTitle")
  })
  public FacilityVisit getBy(@Param(value = "facilityId") Long facilityId, @Param(value = "distributionId") Long distributionId);

  @Update({"UPDATE facility_visits SET visited = #{visited}, visitDate = #{visitDate}, vehicleId = #{vehicleId}, ",
    "numberOfOutreachVisitsPlanned = #{numberOfOutreachVisitsPlanned}, numberOfOutreachVisitsCompleted = #{numberOfOutreachVisitsCompleted}, ",
    "numberOfMotorbikesAtHU = #{numberOfMotorbikesAtHU}, numberOfFunctioningMotorbikes = #{numberOfFunctioningMotorbikes}, ",
    "numberOfMotorizedVehiclesWithProblems = #{numberOfMotorizedVehiclesWithProblems}, numberOfDaysWithLimitedTransport = #{numberOfDaysWithLimitedTransport}, ",
    "confirmedByName = #{confirmedBy.name}, confirmedByTitle = #{confirmedBy.title}, ",
    "verifiedByName = #{verifiedBy.name}, verifiedByTitle = #{verifiedBy.title}, ",
    "observations = #{observations}, priorObservations = #{priorObservations}, synced = #{synced}, modifiedBy = #{modifiedBy}, modifiedDate = DEFAULT," +
      "reasonForNotVisiting = #{reasonForNotVisiting}, otherReasonDescription = #{otherReasonDescription} WHERE id = #{id}"})
  public void update(FacilityVisit facilityVisit);

  @Update({"UPDATE motorbike_problems SET facilityVisitId = #{facilityVisitId}, lackOfFundingForFuel = COALESCE(#{lackOfFundingForFuel}, FALSE), ",
    "repairsSchedulingProblem = COALESCE(#{repairsSchedulingProblem}, FALSE), lackOfFundingForRepairs = COALESCE(#{lackOfFundingForRepairs}, FALSE), ",
    "missingParts = COALESCE(#{missingParts}, FALSE), other = COALESCE(#{other}, FALSE), motorbikeProblemOther = #{motorbikeProblemOther}, ",
    "modifiedBy = #{modifiedBy}, modifiedDate = DEFAULT WHERE id = #{id}"})
  void updateMotorbikeProblems(MotorbikeProblems motorbikeProblems);


  @Select({"SELECT * FROM facility_visits WHERE id = #{id}"})
  @Results({
          @Result(property = "verifiedBy.name", column = "verifiedByName"),
          @Result(property = "verifiedBy.title", column = "verifiedByTitle"),
          @Result(property = "confirmedBy.name", column = "confirmedByName"),
          @Result(property = "confirmedBy.title", column = "confirmedByTitle")
  })
  public FacilityVisit getById(Long id);


  @Select({"SELECT * FROM facility_visits WHERE distributionId = #{distributionId} AND synced = false"})
  List<FacilityVisit> getUnSyncedFacilities(Long distributionId);

  @Select({"SELECT count(*) FROM facility_visits WHERE distributionId = #{distributionId} AND synced = false"})
  Integer getUnsyncedFacilityCountForDistribution(Long distributionId);

  @Select("SELECT * FROM facility_visits WHERE distributionId = #{distributionId}")
  @Results({
          @Result(property = "verifiedBy.name", column = "verifiedByName"),
          @Result(property = "verifiedBy.title", column = "verifiedByTitle"),
          @Result(property = "confirmedBy.name", column = "confirmedByName"),
          @Result(property = "confirmedBy.title", column = "confirmedByTitle")
  })
  List<FacilityVisit> getByDistributionId(Long distributionId);

  @Select({"SELECT * FROM motorbike_problems WHERE facilityVisitId = #{facilityVisitId}"})
  MotorbikeProblems getMotorbikeProblemsByFacilityVisitId(Long facilityVisitId);
}
