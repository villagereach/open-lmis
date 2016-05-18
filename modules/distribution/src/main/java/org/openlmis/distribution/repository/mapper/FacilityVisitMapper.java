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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.openlmis.distribution.domain.FacilityVisit;
import org.openlmis.distribution.domain.ReceivedProducts;
import org.openlmis.distribution.domain.StockoutCauses;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * It maps the FacilityVisit entity to corresponding representation in database.
 */

@Repository
public interface FacilityVisitMapper {

  @Insert({"INSERT INTO facility_visits (distributionId, facilityId, facilityCatchmentPopulation, createdBy, modifiedBy)",
    "VALUES (#{distributionId}, #{facilityId}, #{facilityCatchmentPopulation}, #{createdBy}, #{modifiedBy})"})
  @Options(useGeneratedKeys = true)
  public void insert(FacilityVisit facilityVisit);

  @Insert({"INSERT INTO stockout_causes(facilityVisitId, coldChainEquipmentFailure, incorrectEstimationNeeds, stockoutZonalWarehouse, deliveryNotOnTime, productsTransferedAnotherFacility, other, stockoutCausesOther, createdBy, modifiedBy) ",
          "VALUES (#{facilityVisitId}, COALESCE(#{coldChainEquipmentFailure}, FALSE), COALESCE(#{incorrectEstimationNeeds}, FALSE), COALESCE(#{stockoutZonalWarehouse}, FALSE),",
          "COALESCE(#{deliveryNotOnTime}, FALSE), COALESCE(#{productsTransferedAnotherFacility}, FALSE), COALESCE(#{other}, FALSE), #{stockoutCausesOther}, #{createdBy}, #{modifiedBy})"})
  @Options(useGeneratedKeys = true)
  void insertStockoutCauses(StockoutCauses stockoutCauses);

  @Insert({"INSERT INTO received_products(facilityVisitId, anotherHealthFacility, zonalWarehouse, other, receivedProductsSourcesOther, createdBy, modifiedBy) ",
          "VALUES (#{facilityVisitId}, COALESCE(#{anotherHealthFacility}, FALSE), COALESCE(#{zonalWarehouse}, FALSE), COALESCE(#{other}, FALSE),",
          "#{receivedProductsSourcesOther}, #{createdBy}, #{modifiedBy})"})
  @Options(useGeneratedKeys = true)
  void insertReceivedProducts(ReceivedProducts receivedProducts);

  @Select("SELECT * FROM facility_visits WHERE distributionId = #{distributionId} AND facilityId = #{facilityId}")
  @Results({
    @Result(property = "verifiedBy.name", column = "verifiedByName"),
    @Result(property = "verifiedBy.title", column = "verifiedByTitle"),
    @Result(property = "confirmedBy.name", column = "confirmedByName"),
    @Result(property = "confirmedBy.title", column = "confirmedByTitle")
  })
  public FacilityVisit getBy(@Param(value = "facilityId") Long facilityId, @Param(value = "distributionId") Long distributionId);

  @Update({"UPDATE facility_visits SET visited = #{visited}, visitDate = #{visitDate}, vehicleId = #{vehicleId}, ",
    "stockouts = #{stockouts}, received = #{received}, stockCardsUpToDate = #{stockCardsUpToDate}, ",
    "confirmedByName = #{confirmedBy.name}, confirmedByTitle = #{confirmedBy.title}, ",
    "verifiedByName = #{verifiedBy.name}, verifiedByTitle = #{verifiedBy.title}, ",
    "observations = #{observations}, synced = #{synced}, modifiedBy = #{modifiedBy}, modifiedDate = DEFAULT," +
      "reasonForNotVisiting = #{reasonForNotVisiting}, otherReasonDescription = #{otherReasonDescription} WHERE id = #{id}"})
  public void update(FacilityVisit facilityVisit);

  @Update({"UPDATE stockout_causes SET facilityVisitId = #{facilityVisitId}, coldChainEquipmentFailure = COALESCE(#{coldChainEquipmentFailure}, FALSE), ",
          "incorrectEstimationNeeds = COALESCE(#{incorrectEstimationNeeds}, FALSE), stockoutZonalWarehouse = COALESCE(#{stockoutZonalWarehouse}, FALSE), ",
          "deliveryNotOnTime = COALESCE(#{deliveryNotOnTime}, FALSE), productsTransferedAnotherFacility = COALESCE(#{productsTransferedAnotherFacility}, FALSE), ",
          "other = COALESCE(#{other}, FALSE), stockoutCausesOther = #{stockoutCausesOther}, modifiedBy = #{modifiedBy}, modifiedDate = DEFAULT WHERE id = #{id}"})
  void updateStockoutCauses(StockoutCauses stockoutCauses);

  @Update({"UPDATE received_products SET facilityVisitId = #{facilityVisitId}, anotherHealthFacility = COALESCE(#{anotherHealthFacility}, FALSE), ",
          "zonalWarehouse = COALESCE(#{zonalWarehouse}, FALSE), other = COALESCE(#{other}, FALSE), receivedProductsSourcesOther = #{receivedProductsSourcesOther}, ",
          "modifiedBy = #{modifiedBy}, modifiedDate = DEFAULT WHERE id = #{id}"})
  void updateReceivedProducts(ReceivedProducts receivedProducts);

  @Select({"SELECT * FROM facility_visits WHERE id = #{id}"})
  public FacilityVisit getById(Long id);


  @Select({"SELECT * FROM facility_visits WHERE distributionId = #{distributionId} AND synced = false"})
  List<FacilityVisit> getUnSyncedFacilities(Long distributionId);

  @Select({"SELECT count(*) FROM facility_visits WHERE distributionId = #{distributionId} AND synced = false"})
  Integer getUnsyncedFacilityCountForDistribution(Long distributionId);

  @Select({"SELECT * FROM stockout_causes WHERE facilityVisitId = #{facilityVisitId}"})
  StockoutCauses getStockoutCausesByFacilityVisitId(Long facilityVisitId);

  @Select({"SELECT * FROM received_products WHERE facilityVisitId = #{facilityVisitId}"})
  ReceivedProducts getReceivedProductsByFacilityVisitId(Long facilityVisitId);
}
