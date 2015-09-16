/*
 * Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
 *
 * Copyright (C) 2015  John Snow, Inc (JSI). This program was produced for the U.S. Agency for International Development (USAID). It was prepared under the USAID | DELIVER PROJECT, Task Order 4.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openlmis.vaccine.repository.mapper.reports;

import org.apache.ibatis.annotations.*;
import org.openlmis.core.domain.Facility;
import org.openlmis.core.domain.ProcessingPeriod;
import org.openlmis.vaccine.domain.reports.*;
import org.openlmis.vaccine.dto.ReportStatusDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface VaccineReportMapper {

  @Insert("INSERT into vaccine_reports (periodId, programId, facilityId, status, supervisoryNodeId, majorImmunizationActivities, fixedImmunizationSessions, outreachImmunizationSessions,outreachImmunizationSessionsCanceled, createdBy, createdDate, modifiedBy, modifiedDate) " +
    " values (#{periodId}, #{programId}, #{facilityId}, #{status}, #{supervisoryNodeId}, #{majorImmunizationActivities}, #{fixedImmunizationSessions}, #{outreachImmunizationSessions}, #{outreachImmunizationSessionsCanceled}, #{createdBy}, NOW(), #{modifiedBy}, NOW() )")
  @Options(useGeneratedKeys = true)
  Integer insert(VaccineReport report);

  @Select("SELECT * from vaccine_reports where id = #{id}")
  VaccineReport getById(@Param("id") Long id);

  @Select("SELECT * from vaccine_reports where facilityId = #{facilityId} and programId = #{programId} and periodId = #{periodId}")
  VaccineReport getByPeriodFacilityProgram(@Param("facilityId") Long facilityId, @Param("periodId") Long periodId, @Param("programId") Long programId);

  @Select("SELECT * from vaccine_reports where id = #{id}")
  @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "facilityId", column = "facilityId"),
    @Result(property = "periodId", column = "periodId"),
    @Result(property = "programId", column = "programId"),
    @Result(property = "logisticsLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportLogisticsLineItemMapper.getLineItems")),
    @Result(property = "coverageLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportCoverageMapper.getLineItems")),
    @Result(property = "adverseEffectLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportAdverseEffectMapper.getLineItems")),
    @Result(property = "columnTemplate", javaType = List.class, column = "programId",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.VaccineColumnTemplateMapper.getForProgram")),
    @Result(property = "coldChainLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportColdChainMapper.getLineItems")),
    @Result(property = "campaignLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportCampaignLineItemMapper.getLineItems")),
    @Result(property = "diseaseLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportDiseaseLineItemMapper.getLineItems")),
    @Result(property = "vitaminSupplementationLineItems", javaType = List.class, column = "id",
      many = @Many(select = "org.openlmis.vaccine.repository.mapper.reports.VitaminSupplementationLineItemMapper.getLineItems")),
    @Result(property = "reportStatusChanges", javaType = List.class, column = "id",
      many = @Many( select = "org.openlmis.vaccine.repository.mapper.reports.VaccineReportStatusChangeMapper.getChangeLogByReportId")),
    @Result(property = "period", javaType = ProcessingPeriod.class, column = "periodId",
      many = @Many(select = "org.openlmis.core.repository.mapper.ProcessingPeriodMapper.getById")),
    @Result(property = "facility", javaType = Facility.class, column = "facilityId",
      many = @Many(select = "org.openlmis.core.repository.mapper.FacilityMapper.getById"))
  })
  VaccineReport getByIdWithFullDetails(@Param("id") Long id);

  @Update("UPDATE vaccine_reports" +
      " set" +
      " periodId = #{periodId}, " +
      " programId = #{programId}, " +
      " facilityId = #{facilityId}, " +
      " status = #{status}, " +
      " supervisoryNodeId = #{supervisoryNodeId}, " +
      " majorImmunizationActivities = #{majorImmunizationActivities}, " +
      " fixedImmunizationSessions = #{fixedImmunizationSessions}, " +
      " outreachImmunizationSessions = #{outreachImmunizationSessions}, " +
      " outreachImmunizationSessionsCanceled = #{outreachImmunizationSessionsCanceled}, " +
      " modifiedBy = #{modifiedBy}, " +
      " modifiedDate = NOW() " +
    "where id = #{id}")
  void update(VaccineReport report);

  @Select("select max(s.scheduleId) id from requisition_group_program_schedules s " +
    " join requisition_group_members m " +
    "     on m.requisitionGroupId = s.requisitionGroupId " +
    " where " +
    "   s.programId = #{programId} " +
    "   and m.facilityId = #{facilityId} ")
  Long getScheduleFor(@Param("facilityId") Long facilityId, @Param("programId") Long programId);

  @Select("select * from vaccine_reports " +
    "   where " +
    "     facilityId = #{facilityId} and programId = #{programId} order by id desc limit 1")
  VaccineReport getLastReport(@Param("facilityId") Long facilityId, @Param("programId") Long programId);


  @Select("select r.id, p.name as periodName, r.facilityId, r.status, r.programId " +
    " from vaccine_reports r " +
    "   join processing_periods p on p.id = r.periodId " +
    " where r.facilityId = #{facilityId} and r.programId = #{programId}" +
    " order by p.startDate desc")
  List<ReportStatusDTO> getReportedPeriodsForFacility(@Param("facilityId") Long facilityId, @Param("programId") Long programId);

  @Select("Select id from vaccine_reports where facilityid = #{facilityId} and periodid = #{periodId}")
  Long getReportIdForFacilityAndPeriod(@Param("facilityId")Long facilityId, @Param("periodId")Long periodId);

  @Select("select COALESCE(cases, 0) as cases, COALESCE(death, 0) as death, COALESCE(cum_cases, 0) as cumulative, disease_name as diseaseName \n" +
          "from vw_vaccine_disease_surveillance \n" +
          "where report_id = #{reportId}")
  List<DiseaseLineItem> getDiseaseSurveillance(@Param("reportId")Long reportId);

  @Select("select disease_name as diseaseName,\n" +
          "SUM(COALESCE(cum_cases,0)) cumulative,\n" +
          "SUM(COALESCE(cum_deaths,0)) calculatedCumulativeDeaths,\n" +
          "SUM(COALESCE(cases, 0)) cases,\n" +
          "SUM(COALESCE(death,0)) death\n" +
          "from vw_vaccine_disease_surveillance\n" +
          "INNER JOIN vw_districts vd ON vd.district_id = geographic_zone_id\n" +
          "where period_id = #{periodId} and (vd.parent = #{zoneId} or vd.district_id = #{zoneId} or vd.region_id = #{zoneId} or vd.zone_id = #{zoneId} )\n" +
          "group by disease_name \n")
  List<DiseaseLineItem>  getDiseaseSurveillanceAggregateByGeoZone(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("select equipment_type_name as equipmentName, model, minTemp, maxTemp, minEpisodeTemp, maxEpisodeTemp, energy_source as energySource from vw_vaccine_cold_chain \n" +
          "where report_id = #{reportId}")
  List<ColdChainLineItem> getColdChain(@Param("reportId")Long reportId);
  @Select("select equipment_type_name as equipmentName,\n" +
          "model,\n" +
          "MIN(COALESCE(minTemp,0)) minTemp,\n" +
          "MAX(COALESCE(maxTemp,0)) maxTemp,\n" +
          "SUM(COALESCE(minEpisodeTemp,0)) minEpisodeTemp,\n" +
          "SUM(COALESCE(maxEpisodeTemp,0)) maxEpisodeTemp,\n" +
          "MAX(energy_source ) as energySource \n" +
          "from vw_vaccine_cold_chain \n" +
          "join vw_districts d ON d.district_id = geographic_zone_id\n" +
          "where period_id = #{periodId} and (d.parent = #{zoneId} or d.district_id = #{zoneId} or d.region_id = #{zoneId} or d.zone_id = #{zoneId})\n" +
          "group by model, equipment_type_name \n" )
  List<ColdChainLineItem> getColdChainAggregateReport(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

    @Select("select product_name as productName, aefi_expiry_date as expiry, aefi_case as cases, aefi_batch as batch, manufacturer, is_investigated as isInvestigated from vw_vaccine_iefi \n" +
          "where report_id = #{reportId}")
  List<AdverseEffectLineItem> getAdverseEffectReport(@Param("reportId")Long reportId);

  @Select("select MAX(product_name) as productName,\n" +
          "MAX(aefi_expiry_date) as expiry,\n" +
          "SUM(COALESCE(aefi_case,0)) as cases, \n" +
          "MAX(aefi_batch) as batch,\n" +
          "MAX(manufacturer) as manufacturer,\n" +
          "every(is_investigated) as isInvestigated \n" +
          "from vw_vaccine_iefi \n" +
          "join vw_districts d ON d.district_id = geographic_zone_id\n" +
          "where period_id = #{periodId} and (d.parent = #{zoneId} or d.district_id = #{zoneId} or d.region_id = #{zoneId} or d.zone_id = #{zoneId} )\n" +
          "group by product_code")
  List<AdverseEffectLineItem> getAdverseEffectAggregateReport(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("select product_name,display_name, COALESCE(within_male, 0) within_male, COALESCE(within_female,0) within_female, COALESCE(within_total,0) within_total, COALESCE(within_coverage, 0) within_coverage, \n" +
          "COALESCE(outside_male, 0) outside_male, COALESCE(outside_female,0) outside_female, COALESCE(outside_total, 0) outside_total,\n" +
          "COALESCE(within_outside_total, 0) within_outside_total, COALESCE(within_outside_coverage,0) within_outside_coverage,\n" +
          " COALESCE(cum_within_total,0) cum_within_total, COALESCE(cum_within_coverage,0) cum_within_coverage,\n" +
          "  COALESCE(cum_outside_total,0) cum_outside_total, COALESCE(cum_within_outside_total,0) cum_within_outside_total,\n" +
          "   COALESCE(cum_within_outside_coverage ,0) cum_within_outside_coverage\n" +
          "from vw_vaccine_coverage \n" +
          "where report_id = #{reportId}")
  List<HashMap<String, Object>> getVaccineCoverageReport(@Param("reportId")Long reportId);

   @Select("select MAX(product_name) product_name,\n" +
           "MAX(display_name) display_name, \n" +
           "SUM(COALESCE(within_male, 0)) within_male, \n" +
           "SUM(COALESCE(within_female,0)) within_female, \n" +
           "SUM(COALESCE(within_total,0)) within_total, \n" +
           "SUM(COALESCE(within_coverage, 0)) within_coverage, \n" +
           "SUM(COALESCE(outside_male, 0)) outside_male, \n" +
           "SUM(COALESCE(outside_female,0)) outside_female,\n" +
           "SUM(COALESCE(outside_total, 0)) outside_total,\n" +
           "SUM(COALESCE(within_outside_total, 0)) within_outside_total, \n" +
           "SUM(COALESCE(within_outside_coverage,0)) within_outside_coverage,\n" +
           "SUM(COALESCE(cum_within_total,0)) cum_within_total, \n" +
           "SUM(COALESCE(cum_within_coverage,0)) cum_within_coverage,\n" +
           "SUM(COALESCE(cum_outside_total,0)) cum_outside_total, \n" +
           "SUM(COALESCE(cum_within_outside_total,0)) cum_within_outside_total,\n" +
           "SUM(COALESCE(cum_within_outside_coverage ,0)) cum_within_outside_coverage\n" +
           "from vw_vaccine_coverage \n" +
           "INNER JOIN vw_districts vd ON vd.district_id = geographic_zone_id\n" +
           "where period_id = #{periodId} and (vd.parent = #{zoneId} or vd.district_id = #{zoneId} or vd.region_id = #{zoneId} or vd.zone_id = #{zoneId} )\n" +
           "group by product_code\n" )
  List<HashMap<String, Object>> getVaccineCoverageAggregateReportByGeoZone(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("SELECT COALESCE(fixedimmunizationsessions, 0) fixedimmunizationsessions, COALESCE(outreachimmunizationsessions, 0) outreachimmunizationsessions, COALESCE(outreachimmunizationsessionscanceled, 0) outreachimmunizationsessionscanceled FROM vaccine_reports WHERE id = #{reportId} ")
  List<VaccineReport> getImmunizationSession(@Param("reportId")Long reportId);

  @Select("SELECT \n" +
          "sum(COALESCE(fixedimmunizationsessions, 0)) fixedimmunizationsessions, \n" +
          "sum(COALESCE(outreachimmunizationsessions, 0)) outreachimmunizationsessions, \n" +
          "sum(COALESCE(outreachimmunizationsessionscanceled, 0)) outreachimmunizationsessionscanceled \n" +
          "FROM vaccine_reports r\n" +
          "join facilities f on r.facilityid = r.facilityid\n" +
          "join vw_districts d ON d.district_id = f.geographiczoneid\n" +
          "where r.periodid = #{periodId} and (d.parent = #{zoneId} or d.district_id = #{zoneId} or d.region_id = #{zoneId} or d.zone_id = #{zoneId} )\n")

  List<VaccineReport> getImmunizationSessionAggregate(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("select * from vw_vaccine_stock_status where product_category_code = (select value from configuration_settings where key = #{productCategoryCode}) and report_id = #{reportId}")
  List<HashMap<String, Object>> getVaccinationReport(@Param("productCategoryCode") String categoryCode, @Param("reportId")Long reportId);

  @Select("select * from vw_vaccine_target_population\n" +
          "where facility_id = #{facilityId} and year =  (select date_part('year'::text, processing_periods.startdate) from processing_periods where id = #{periodId})\n" +
          "order by category_id\n")
  List<HashMap<String, Object>> getTargetPopulation(@Param("facilityId") Long facilityId, @Param("periodId") Long periodId);

  @Select(" select tp.category_name,   \n" +
          " sum(COALESCE(tp.target_value_annual,0)) target_value_annual,  \n" +
          " round(sum(COALESCE(tp.target_value_annual,0))/12) target_value_monthly  \n" +
          " from vw_vaccine_target_population tp  \n" +
          "  join vw_districts d on d.district_id = tp.geographic_zone_id  \n" +
          " where  tp.year = (select date_part('year'::text, processing_periods.startdate) from processing_periods where id = #{periodId})  \n" +
          " and (d.parent = #{zoneId} or d.district_id = #{zoneId} or d.region_id = #{zoneId} or d.zone_id = #{zoneId})  \n" +
          " group by tp.category_id, tp.category_name  \n" +
          " order by tp.category_id  \n")
   List<HashMap<String, Object>> getTargetPopulationAggregateByGeoZone(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("Select age_group AS ageGroup, vitamin_name AS vitaminName, male_value AS maleValue, female_value AS femaleValue from vw_vaccine_vitamin_supplementation where report_id = #{reportId}")
  List<VitaminSupplementationLineItem> getVitaminSupplementationReport(@Param("reportId") Long reportId);

  @Select("Select MAX(age_group) AS ageGroup,\n" +
          "MAX(vitamin_name) AS vitaminName,\n" +
          "SUM(COALESCE(male_value, 0)) AS maleValue,\n" +
          "SUM(COALESCE(female_value,0)) AS femaleValue\n" +
          "from vw_vaccine_vitamin_supplementation\n" +
          "join vw_districts d ON d.district_id = geographic_zone_id\n" +
          "where period_id = #{periodId} and (d.parent = #{zoneId} or d.district_id = #{zoneId} or d.region_id = #{zoneId} or d.zone_id = #{zoneId} )\n")

  List<VitaminSupplementationLineItem> getVitaminSupplementationAggregateReport(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId);

  @Select("select COALESCE(fr.quantity_issued, 0) quantity_issued, COALESCE(fr.closing_balance, 0) closing_balance, pp.name period_name \n" +
          "from fn_vaccine_facility_n_rnrs('Vaccine',#{facilityCode}, #{productCode},4) fr \n" +
          "JOIN processing_periods pp ON pp.id = fr.period_id\n" +
          "order by pp.id asc;")
  List<HashMap<String, Object>> vaccineUsageTrend(@Param("facilityCode")String facilityCode, @Param("productCode")String productCode);

  @Select("SELECT product_code,\n" +
          "MAX(product_name) product_name,\n" +
          "sum(opening_balanace) opening_balance,\n" +
          "sum(quantity_received) quantity_received,\n" +
          "sum(quantity_issued) quantity_issued,\n" +
          "sum(quantity_vvm_alerted) quantity_vvm_alerted,\n" +
          "sum(quantity_freezed) quantity_freezed,\n" +
          "sum(quantity_expired) quantity_expired,\n" +
          "sum(quantity_discarded_unopened) quantity_discarded_unopened,\n" +
          "sum(quantity_discarded_opened) quantity_discarded_opened,\n" +
          "sum(quantity_wasted_other) quantity_wasted_other,\n" +
          "sum(closing_balance) closing_balance,\n" +
          "sum(expired) expired,\n" +
          "sum(broken) broken,\n" +
          "sum(cold_chain_failure) cold_chain_failure,\n" +
          "sum(other) other,\n" +
          "sum(days_stocked_out) days_stocked_out,\n" +
          "'' AS reason_for_discarding,\n" +
          "sum(children_immunized) children_immunized,\n" +
          "sum(pregnant_women_immunized) pregnant_women_immunized,\n" +
          "sum(COALESCE(vaccinated,0)::numeric) vaccinated,\n" +
          "sum(COALESCE(usage_denominator,0)::numeric) usage_denominator,\n" +
          "case when sum(usage_denominator) > 0 \n" +
          "then sum(vaccinated)::numeric/ sum(usage_denominator)::numeric * 100 else 0 \n" +
          "end usage_rate,\n" +
          "case when (\n" +
          "100 - case \n" +
          "    when sum(usage_denominator) = 0 then 0 \n" +
          "    when sum(usage_denominator) > 0 then \n" +
          "    sum(vaccinated)::numeric/ sum(usage_denominator)::numeric * 100\n" +
          "   else 0 \n" +
          "   end ) < 0 then 0 else\n" +
          "100 - case \n" +
          "    when sum(usage_denominator) = 0 then 0 \n" +
          "    when sum(usage_denominator) > 0 then \n" +
          "    sum(vaccinated)::numeric/ sum(usage_denominator)::numeric * 100\n" +
          "   else 0 \n" +
           "   end \n" +
          "end wastage_rate \n" +
          "from vw_vaccine_stock_status \n" +
          "INNER JOIN vw_districts vd ON vd.district_id = geographic_zone_id\n" +
          "where  product_category_code = (select value from configuration_settings where key = #{productCategoryCode}) and period_id = #{periodId} and (vd.parent = #{zoneId} or vd.district_id = #{zoneId} or vd.region_id = #{zoneId} or vd.zone_id = #{zoneId} )\n" +
          "group by product_code")
  List<HashMap<String, Object>> getVaccinationAggregateByGeoZoneReport(@Param("productCategoryCode") String categoryCode, @Param("periodId")Long periodId, @Param("zoneId") Long zoneId);
  @Select("select COALESCE(fr.quantity_issued, 0) quantity_issued, COALESCE(fr.closing_balance, 0) closing_balance, pp.name period_name \n" +
          "from fn_vaccine_geozone_n_rnrs('Vaccine', #{periodId}::integer ,#{zoneId}::integer, #{productCode},4) fr\n" +
          "JOIN processing_periods pp ON pp.id = fr.period_id\n" +
          "order by pp.id asc")
  List<HashMap<String, Object>>vaccineUsageTrendByGeographicZone(@Param("periodId") Long periodId, @Param("zoneId") Long zoneId, @Param("productCode") String productCode);


}
