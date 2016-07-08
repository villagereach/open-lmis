package org.openlmis.distribution.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashMap;
import java.util.Map;

public final class UIMapping {
  private static final String PARENT = "<<parent>>";

  private static final Map<String, String> DATA_SCREEN_MAPPING = new HashMap<>();
  private static final Table<String, String, String> FIELD_MAPPING = HashBasedTable.create();

  static {
    DATA_SCREEN_MAPPING.put("FacilityVisit", "label.visit.information");
    DATA_SCREEN_MAPPING.put("Facilitator", "label.visit.information");
    DATA_SCREEN_MAPPING.put("StockoutCauses", "label.visit.information");
    DATA_SCREEN_MAPPING.put("AdditionalProductSources", "label.visit.information");

    DATA_SCREEN_MAPPING.put("EpiInventoryLineItem", "label.epi.inventory");

    DATA_SCREEN_MAPPING.put("RefrigeratorReading", "label.refrigerators");
    DATA_SCREEN_MAPPING.put("RefrigeratorProblem", "label.refrigerators");

    DATA_SCREEN_MAPPING.put("EpiUseLineItem", "label.epi.use");

    DATA_SCREEN_MAPPING.put("VaccinationFullCoverage", "label.coverage.full");

    DATA_SCREEN_MAPPING.put("ChildCoverageLineItem", "label.coverage.children");
    DATA_SCREEN_MAPPING.put("VaccinationChildCoverage", "label.coverage.children");

    DATA_SCREEN_MAPPING.put("AdultCoverageLineItem", "label.coverage.adult");
    DATA_SCREEN_MAPPING.put("VaccinationAdultCoverage", "label.coverage.adult");

    DATA_SCREEN_MAPPING.put("OpenedVialLineItem", PARENT);
  }

  static {
    FIELD_MAPPING.put("FacilityVisit", "observations", "label.observations");
    FIELD_MAPPING.put("FacilityVisit", "visitDate", "label.visit.date");
    FIELD_MAPPING.put("FacilityVisit", "visited", "label.visited");
    FIELD_MAPPING.put("FacilityVisit", "vehicleId", "label.vehicle.id");
    FIELD_MAPPING.put("FacilityVisit", "reasonForNotVisiting", "label.reason.for.no.visit");
    FIELD_MAPPING.put("FacilityVisit", "otherReasonDescription", "label.description");
    FIELD_MAPPING.put("FacilityVisit", "confirmedBy", "label.confirmed.by");
    FIELD_MAPPING.put("FacilityVisit", "verifiedBy", "label.verified.by");
    FIELD_MAPPING.put("FacilityVisit", "stockouts", "label.was.stockouts");
    FIELD_MAPPING.put("FacilityVisit", "stockoutCauses", "label.stockouts.select");
    FIELD_MAPPING.put("FacilityVisit", "hasAdditionalProductSources", "label.was.received");
    FIELD_MAPPING.put("FacilityVisit", "additionalProductSources", "label.received.select");
    FIELD_MAPPING.put("FacilityVisit", "stockCardsUpToDate", "label.was.stock.cards.up.to.date");

    FIELD_MAPPING.put("Facilitator", "name", "label.name");
    FIELD_MAPPING.put("Facilitator", "title", "label.title");

    FIELD_MAPPING.put("AdditionalProductSources", "anotherHealthFacility", "label.received.select.another.health.facility");
    FIELD_MAPPING.put("AdditionalProductSources", "zonalWarehouse", "label.received.select.zonal.warehouse");
    FIELD_MAPPING.put("AdditionalProductSources", "other", "label.received.select.other");
    FIELD_MAPPING.put("AdditionalProductSources", "additionalProductSourcesOther", "label.received.select.other");

    FIELD_MAPPING.put("StockoutCauses", "coldChainEquipmentFailure", "label.stockouts.select.cold.chain.equipment.failure");
    FIELD_MAPPING.put("StockoutCauses", "incorrectEstimationNeeds", "label.stockouts.select.incorrect.estimation.needs");
    FIELD_MAPPING.put("StockoutCauses", "stockoutZonalWarehouse", "label.stockouts.select.stockout.zonal.warehouse");
    FIELD_MAPPING.put("StockoutCauses", "deliveryNotOnTime", "label.stockouts.select.delivery.not.on.time");
    FIELD_MAPPING.put("StockoutCauses", "productsTransferedAnotherFacility", "label.stockouts.select.products.transfered.another.facility");
    FIELD_MAPPING.put("StockoutCauses", "other", "label.stockouts.select.other");
    FIELD_MAPPING.put("StockoutCauses", "stockoutCausesOther", "label.stockouts.select.other");

    FIELD_MAPPING.put("EpiInventoryLineItem", "existingQuantity", "label.ideal.quantity");
    FIELD_MAPPING.put("EpiInventoryLineItem", "spoiledQuantity", "label.existing.quantity");
    FIELD_MAPPING.put("EpiInventoryLineItem", "deliveredQuantity", "label.delivered.quantity");

    FIELD_MAPPING.put("RefrigeratorReading", "temperature", "label.refrigerator.temperature");
    FIELD_MAPPING.put("RefrigeratorReading", "functioningCorrectly", "label.refrigerator.working.correctly");
    FIELD_MAPPING.put("RefrigeratorReading", "lowAlarmEvents", "label.number.low.alarms");
    FIELD_MAPPING.put("RefrigeratorReading", "highAlarmEvents", "label.number.high.alarms");
    FIELD_MAPPING.put("RefrigeratorReading", "problemSinceLastTime", "label.problem.last.visit");
    FIELD_MAPPING.put("RefrigeratorReading", "notes", "label.notes");
    FIELD_MAPPING.put("RefrigeratorReading", "hasMonitoringDevice", "label.has.monitoring.device");
    FIELD_MAPPING.put("RefrigeratorReading", "monitoringDeviceType", "label.monitoring.device.type");
    FIELD_MAPPING.put("RefrigeratorReading", "monitoringDeviceOtherType", "msg.monitoringDeviceType.otherDevice");
    FIELD_MAPPING.put("RefrigeratorReading", "temperatureReportingForm", "label.temperature.reporting.form");
    FIELD_MAPPING.put("RefrigeratorReading", "highestTemperatureReported", "label.number.highest.temperature");
    FIELD_MAPPING.put("RefrigeratorReading", "lowestTemperatureReported", "label.number.lowest.temperature");
    FIELD_MAPPING.put("RefrigeratorReading", "problemOccurredDate", "label.problem.occurred.date");
    FIELD_MAPPING.put("RefrigeratorReading", "problemReportedDate", "label.problem.reported.date");
    FIELD_MAPPING.put("RefrigeratorReading", "equipmentRepaired", "label.equipment.repaired");
    FIELD_MAPPING.put("RefrigeratorReading", "equipmentRepairedDate", "label.equipment.repaired.date");
    FIELD_MAPPING.put("RefrigeratorReading", "totalDaysCceUptime", "label.total.days.cce.uptime");

    FIELD_MAPPING.put("RefrigeratorProblem", "operatorError", "label.operator.error");
    FIELD_MAPPING.put("RefrigeratorProblem", "burnerProblem", "label.burner.problem");
    FIELD_MAPPING.put("RefrigeratorProblem", "gasLeakage", "label.gas.problem");
    FIELD_MAPPING.put("RefrigeratorProblem", "egpFault", "label.egp.fault");
    FIELD_MAPPING.put("RefrigeratorProblem", "thermostatSetting", "label.thermostat.setting");
    FIELD_MAPPING.put("RefrigeratorProblem", "other", "label.other");
    FIELD_MAPPING.put("RefrigeratorProblem", "otherProblemExplanation", "label.description");

    FIELD_MAPPING.put("EpiUseLineItem", "stockAtFirstOfMonth", "header.epi.use.startingStock");
    FIELD_MAPPING.put("EpiUseLineItem", "stockAtEndOfMonth", "header.epi.use.endingStock");
    FIELD_MAPPING.put("EpiUseLineItem", "received", "header.epi.use.received");
    FIELD_MAPPING.put("EpiUseLineItem", "lossOverHeated", "header.epi.use.lossOverHeated");
    FIELD_MAPPING.put("EpiUseLineItem", "lossFrozen", "header.epi.use.lossFrozen");
    FIELD_MAPPING.put("EpiUseLineItem", "lossExpired", "header.epi.use.lossExpired");
    FIELD_MAPPING.put("EpiUseLineItem", "lossOther", "header.epi.use.lossOther");
    FIELD_MAPPING.put("EpiUseLineItem", "distributed", "eader.epi.use.distributed");
    FIELD_MAPPING.put("EpiUseLineItem", "expirationDate", "description.column.expiration.date");
    FIELD_MAPPING.put("EpiUseLineItem", "numberOfStockoutDays", "header.epi.use.numberOfStockoutDays");

    FIELD_MAPPING.put("VaccinationFullCoverage", "femaleHealthCenter", "label.coverage.health.center");
    FIELD_MAPPING.put("VaccinationFullCoverage", "femaleOutreach", "label.coverage.outreach");
    FIELD_MAPPING.put("VaccinationFullCoverage", "maleHealthCenter", "label.coverage.health.center");
    FIELD_MAPPING.put("VaccinationFullCoverage", "maleOutreach", "label.coverage.outreach");

    FIELD_MAPPING.put("ChildCoverageLineItem", "healthCenter11Months", "label.coverage.health.center");
    FIELD_MAPPING.put("ChildCoverageLineItem", "healthCenter23Months", "label.coverage.health.center");
    FIELD_MAPPING.put("ChildCoverageLineItem", "outreach11Months", "label.coverage.outreach");
    FIELD_MAPPING.put("ChildCoverageLineItem", "outreach23Months", "label.coverage.outreach");

    FIELD_MAPPING.put("AdultCoverageLineItem", "healthCenterTetanus1", "label.coverage.health.center");
    FIELD_MAPPING.put("AdultCoverageLineItem", "outreachTetanus1", "label.coverage.outreach");
    FIELD_MAPPING.put("AdultCoverageLineItem", "healthCenterTetanus2To5", "label.coverage.health.center");
    FIELD_MAPPING.put("AdultCoverageLineItem", "outreachTetanus2To5", "label.coverage.outreach");

    FIELD_MAPPING.put("OpenedVialLineItem", "openedVials", "label.coverage.opened.vials");
  }

  private UIMapping() {
    throw new UnsupportedOperationException();
  }

  public static String getDataScreen(String classSimpleName, String parentSimpleName) {
    String value = DATA_SCREEN_MAPPING.get(classSimpleName);

    if (PARENT.equals(value)) {
      return DATA_SCREEN_MAPPING.get(parentSimpleName);
    }

    return value;
  }

  public static EditedItemUI getField(String classSimpleName, String propertyName,
                    String parentSimpleName, String parentPropertyName,
                    String addictional) {
    EditedItemUI item = new EditedItemUI();
    item.addTransalte(FIELD_MAPPING.get(classSimpleName, propertyName));

    switch (classSimpleName) {
      case "FacilityVisit":
        if (propertyName.equals("otherReasonDescription")) {
          item.addTransalte(FIELD_MAPPING.get(classSimpleName, "reasonForNotVisiting"));
        }
        break;
      case "Facilitator":
        item.addTransalte(FIELD_MAPPING.get(parentSimpleName, parentPropertyName));
        break;
      case "RefrigeratorProblem":
        if (propertyName.equals("otherProblemExplanation")) {
          item.addTransalte(FIELD_MAPPING.get(classSimpleName, "other"));
        }
        break;
      case "VaccinationFullCoverage":
        switch (propertyName) {
          case "femaleHealthCenter":
          case "femaleOutreach":
            item.addTransalte("label.coverage.females");
            break;
          case "maleHealthCenter":
          case "maleOutreach":
            item.addTransalte("label.coverage.males");
            break;
        }
    }

    if (null != addictional) {
      item.addNoTransalte(addictional);
    }

    return item;
  }

}
