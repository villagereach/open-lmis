package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.distribution.dto.Reading;
import org.openlmis.distribution.dto.StockoutCausesDTO;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class StockoutCauses extends BaseModel {
  Long facilityVisitId;
  Boolean coldChainEquipmentFailure;
  Boolean incorrectEstimationNeeds;
  Boolean stockoutZonalWarehouse;
  Boolean deliveryNotOnTime;
  Boolean productsTransferedAnotherFacility;
  Boolean other;
  String stockoutCausesOther;

  public StockoutCausesDTO transform() {
    StockoutCausesDTO dto = new StockoutCausesDTO();
    dto.setId(id);
    dto.setCreatedBy(createdBy);
    dto.setCreatedDate(createdDate);
    dto.setModifiedBy(modifiedBy);
    dto.setModifiedDate(modifiedDate);
    dto.setFacilityVisitId(facilityVisitId);
    dto.setColdChainEquipmentFailure(new Reading(coldChainEquipmentFailure));
    dto.setIncorrectEstimationNeeds(new Reading(incorrectEstimationNeeds));
    dto.setStockoutZonalWarehouse(new Reading(stockoutZonalWarehouse));
    dto.setDeliveryNotOnTime(new Reading(deliveryNotOnTime));
    dto.setProductsTransferedAnotherFacility(new Reading(productsTransferedAnotherFacility));
    dto.setOther(new Reading(other));
    dto.setStockoutCausesOther(new Reading(stockoutCausesOther));

    return dto;
  }
}
