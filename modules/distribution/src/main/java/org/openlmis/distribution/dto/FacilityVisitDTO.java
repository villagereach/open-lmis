package org.openlmis.distribution.dto;

import com.google.common.base.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.core.domain.BaseModel;
import org.openlmis.distribution.domain.Facilitator;
import org.openlmis.distribution.domain.FacilityVisit;
import org.openlmis.distribution.domain.ReasonForNotVisiting;
import org.openlmis.distribution.domain.AdditionalProductSources;
import org.openlmis.distribution.domain.StockoutCauses;

import java.util.Date;

import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class FacilityVisitDTO extends BaseModel {

    private Long distributionId;
    private Long facilityId;

    private Facilitator confirmedBy;
    private Facilitator verifiedBy;
    private Reading observations;

    private Reading visitDate;

    private Reading visited;
    private Reading vehicleId;
    private Reading reasonForNotVisiting;
    private Reading otherReasonDescription;

    private Reading synced;

    private Reading stockouts;
    private StockoutCauses stockoutCauses;

    private Reading hasAdditionalProductSources;
    private AdditionalProductSources additionalProductSources;

    private Reading stockCardsUpToDate;

    public FacilityVisit transform() {
        Facilitator confirmedBy = Optional.fromNullable(this.confirmedBy).or(new Facilitator());
        Facilitator verifiedBy = Optional.fromNullable(this.verifiedBy).or(new Facilitator());
        String observations = Optional.fromNullable(this.observations).or(Reading.EMPTY).getEffectiveValue();
        Date visitDate = Optional.fromNullable(this.visitDate).or(Reading.EMPTY).parseDate();
        Boolean visited = Optional.fromNullable(this.visited).or(Reading.EMPTY).parseBoolean();
        String vehicleId = Optional.fromNullable(this.vehicleId).or(Reading.EMPTY).getEffectiveValue();
        ReasonForNotVisiting reasonForNotVisiting = null;
        String otherReasonDescription = Optional.fromNullable(this.otherReasonDescription).or(Reading.EMPTY).getEffectiveValue();
        Boolean synced = Optional.fromNullable(this.synced).or(Reading.EMPTY).parseBoolean();
        Boolean stockouts = Optional.fromNullable(this.stockouts).or(Reading.EMPTY).parseBoolean();
        StockoutCauses stockoutCauses = Optional.fromNullable(this.stockoutCauses).or(new StockoutCauses());
        Boolean hasAdditionalProductSources = Optional.fromNullable(this.hasAdditionalProductSources).or(Reading.EMPTY).parseBoolean();
        AdditionalProductSources additionalProductSources = Optional.fromNullable(this.additionalProductSources).or(new AdditionalProductSources());
        Boolean stockCardsUpToDate = Optional.fromNullable(this.stockCardsUpToDate).or(Reading.EMPTY).parseBoolean();

        if (null != this.reasonForNotVisiting && isFalse(this.reasonForNotVisiting.getNotRecorded())) {
            reasonForNotVisiting = ReasonForNotVisiting.valueOf(this.reasonForNotVisiting.getEffectiveValue());
        }

        FacilityVisit facilityVisit = new FacilityVisit();

        facilityVisit.setId(this.id);
        facilityVisit.setCreatedBy(this.createdBy);
        facilityVisit.setModifiedBy(this.modifiedBy);
        facilityVisit.setCreatedDate(this.createdDate);
        facilityVisit.setModifiedDate(this.modifiedDate);

        facilityVisit.setDistributionId(distributionId);
        facilityVisit.setFacilityId(facilityId);

        facilityVisit.setConfirmedBy(confirmedBy);
        facilityVisit.setVerifiedBy(verifiedBy);
        facilityVisit.setObservations(observations);

        facilityVisit.setVisitDate(visitDate);

        facilityVisit.setVisited(visited);
        facilityVisit.setVehicleId(vehicleId);
        facilityVisit.setReasonForNotVisiting(reasonForNotVisiting);
        facilityVisit.setOtherReasonDescription(otherReasonDescription);

        facilityVisit.setSynced(synced);

        facilityVisit.setStockouts(stockouts);
        facilityVisit.setStockoutCauses(stockoutCauses);

        facilityVisit.setHasAdditionalProductSources(hasAdditionalProductSources);
        facilityVisit.setAdditionalProductSources(additionalProductSources);

        facilityVisit.setStockCardsUpToDate(stockCardsUpToDate);

        return facilityVisit;
    }

}
