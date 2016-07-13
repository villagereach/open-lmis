package org.openlmis.distribution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.distribution.domain.AdditionalProductSources;

import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class AdditionalProductSourcesDTO extends BaseModel {
  Long facilityVisitId;
  Reading anotherHealthFacility;
  Reading zonalWarehouse;
  Reading other;
  Reading additionalProductSourcesOther;
  Boolean notRecorded;

  public AdditionalProductSources transform() {
    AdditionalProductSources sources = new AdditionalProductSources();
    sources.setId(id);
    sources.setCreatedBy(createdBy);
    sources.setCreatedDate(createdDate);
    sources.setModifiedBy(modifiedBy);
    sources.setModifiedDate(modifiedDate);
    sources.setFacilityVisitId(facilityVisitId);

    if (isFalse(notRecorded)) {
      Boolean anotherHealthFacility = Reading.safeRead(this.anotherHealthFacility).parseBoolean();
      Boolean zonalWarehouse = Reading.safeRead(this.zonalWarehouse).parseBoolean();
      Boolean other = Reading.safeRead(this.other).parseBoolean();
      String additionalProductSourcesOther = Reading.safeRead(this.additionalProductSourcesOther).getEffectiveValue();

      sources.setAnotherHealthFacility(anotherHealthFacility);
      sources.setZonalWarehouse(zonalWarehouse);
      sources.setOther(other);
      sources.setAdditionalProductSourcesOther(additionalProductSourcesOther);
    }

    return sources;
  }
}
