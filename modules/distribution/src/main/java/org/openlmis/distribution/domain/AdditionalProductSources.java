package org.openlmis.distribution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.distribution.dto.AdditionalProductSourcesDTO;
import org.openlmis.distribution.dto.Reading;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class AdditionalProductSources extends BaseModel {
  Long facilityVisitId;
  Boolean anotherHealthFacility;
  Boolean zonalWarehouse;
  Boolean other;
  String additionalProductSourcesOther;

  public AdditionalProductSourcesDTO transform() {
    AdditionalProductSourcesDTO dto = new AdditionalProductSourcesDTO();
    dto.setId(id);
    dto.setCreatedBy(createdBy);
    dto.setCreatedDate(createdDate);
    dto.setModifiedBy(modifiedBy);
    dto.setModifiedDate(modifiedDate);
    dto.setFacilityVisitId(facilityVisitId);
    dto.setAnotherHealthFacility(new Reading(anotherHealthFacility));
    dto.setZonalWarehouse(new Reading(zonalWarehouse));
    dto.setOther(new Reading(other));
    dto.setAdditionalProductSourcesOther(new Reading(additionalProductSourcesOther));

    return dto;
  }
}
