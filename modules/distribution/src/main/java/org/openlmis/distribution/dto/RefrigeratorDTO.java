package org.openlmis.distribution.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.core.exception.DataException;
import org.openlmis.distribution.domain.Refrigerator;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class RefrigeratorDTO extends BaseModel {

  private Reading brand;
  private Reading serialNumber;
  private Reading model;
  private Reading type;
  private Long facilityId;
  private Boolean enabled;

  public void validate() {
    if (isBlank(Reading.safeRead(serialNumber).getEffectiveValue())) {
      throw new DataException("error.invalid.reading.value");
    }
  }

  public Refrigerator transform() {
    String brand = Reading.safeRead(this.brand).getEffectiveValue();
    String serialNumber = Reading.safeRead(this.serialNumber).getEffectiveValue();
    String model = Reading.safeRead(this.model).getEffectiveValue();
    String type = Reading.safeRead(this.type).getEffectiveValue();

    Refrigerator refrigerator = new Refrigerator(brand, serialNumber, model, type, facilityId, enabled);

    refrigerator.setId(this.id);
    refrigerator.setCreatedBy(this.createdBy);
    refrigerator.setModifiedBy(this.modifiedBy);
    refrigerator.setCreatedDate(this.createdDate);
    refrigerator.setModifiedDate(this.modifiedDate);
    return refrigerator;
  }
}
