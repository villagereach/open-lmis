package org.openlmis.distribution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.distribution.domain.StockoutCauses;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class StockoutCausesDTO extends BaseModel {
  Long facilityVisitId;
  Reading coldChainEquipmentFailure;
  Reading incorrectEstimationNeeds;
  Reading stockoutZonalWarehouse;
  Reading deliveryNotOnTime;
  Reading productsTransferedAnotherFacility;
  Reading other;
  Reading stockoutCausesOther;

  public StockoutCauses transform() {
    Boolean coldChainEquipmentFailure = Reading.safeRead(this.coldChainEquipmentFailure).parseBoolean();
    Boolean incorrectEstimationNeeds = Reading.safeRead(this.incorrectEstimationNeeds).parseBoolean();
    Boolean stockoutZonalWarehouse = Reading.safeRead(this.stockoutZonalWarehouse).parseBoolean();
    Boolean deliveryNotOnTime = Reading.safeRead(this.deliveryNotOnTime).parseBoolean();
    Boolean productsTransferedAnotherFacility = Reading.safeRead(this.productsTransferedAnotherFacility).parseBoolean();
    Boolean other = Reading.safeRead(this.other).parseBoolean();
    String stockoutCausesOther = Reading.safeRead(this.stockoutCausesOther).getEffectiveValue();
    
    StockoutCauses stockoutCauses = new StockoutCauses();
    stockoutCauses.setId(id);
    stockoutCauses.setCreatedBy(createdBy);
    stockoutCauses.setCreatedDate(createdDate);
    stockoutCauses.setModifiedBy(modifiedBy);
    stockoutCauses.setModifiedDate(modifiedDate);
    stockoutCauses.setFacilityVisitId(facilityVisitId);
    stockoutCauses.setColdChainEquipmentFailure(coldChainEquipmentFailure);
    stockoutCauses.setIncorrectEstimationNeeds(incorrectEstimationNeeds);
    stockoutCauses.setStockoutZonalWarehouse(stockoutZonalWarehouse);
    stockoutCauses.setDeliveryNotOnTime(deliveryNotOnTime);
    stockoutCauses.setProductsTransferedAnotherFacility(productsTransferedAnotherFacility);
    stockoutCauses.setOther(other);
    stockoutCauses.setStockoutCausesOther(stockoutCausesOther);
    
    return stockoutCauses;
  }

}
