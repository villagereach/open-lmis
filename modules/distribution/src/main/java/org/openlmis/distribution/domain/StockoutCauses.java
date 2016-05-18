package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;

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
}
