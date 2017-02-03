package org.openlmis.distribution.service;


import lombok.NoArgsConstructor;
import org.openlmis.distribution.domain.FacilityDistribution;
import org.openlmis.distribution.domain.NotRecordedForms;
import org.openlmis.distribution.repository.DistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service used for transforming not recorded forms
 */
@Service
@NoArgsConstructor
public class NotRecordedService {

  @Autowired
  DistributionRepository repository;

  public void saveInformationAboutNotRecordedForms(FacilityDistribution facilityDistribution) {
    NotRecordedForms notRecordedForms = new NotRecordedForms();
    notRecordedForms.setFacilityVisitId(facilityDistribution.getFacilityVisit().getId());
    notRecordedForms.setAdultCoverageNotRecorded(facilityDistribution.getAdultCoverage().getNotRecordedApplied());
    notRecordedForms.setChildCoverageNotRecorded(facilityDistribution.getChildCoverage().getNotRecordedApplied());
    notRecordedForms.setFullCoverageNotRecorded(facilityDistribution.getFullCoverage().getNotRecordedApplied());
    if(!facilityDistribution.getFacilityVisit().getVisited()) {
      notRecordedForms.setEpiInventoryNotRecorded(true);
      notRecordedForms.setEpiUseNotRecorded(true);
    } else {
      notRecordedForms.setEpiInventoryNotRecorded(facilityDistribution.getEpiInventory().getNotRecordedApplied());
      notRecordedForms.setEpiUseNotRecorded(facilityDistribution.getEpiUse().getNotRecordedApplied());
    }
    repository.insertNotRecorded(notRecordedForms);
  }

  public void applyNotRecordedInformationToForms(Map<Long, FacilityDistribution> facilityDistributionMap) {
    for(Map.Entry<Long, FacilityDistribution> entry : facilityDistributionMap.entrySet()) {
      FacilityDistribution facilityDistribution = entry.getValue();
      NotRecordedForms notRecordedForms = repository.getNotRecorded(facilityDistribution.getFacilityVisit().getId());
      facilityDistribution.getAdultCoverage().setNotRecordedApplied(notRecordedForms.getAdultCoverageNotRecorded());
      facilityDistribution.getChildCoverage().setNotRecordedApplied(notRecordedForms.getChildCoverageNotRecorded());
      facilityDistribution.getFullCoverage().setNotRecordedApplied(notRecordedForms.getFullCoverageNotRecorded());
      facilityDistribution.getEpiInventory().setNotRecordedApplied(notRecordedForms.getEpiInventoryNotRecorded());
      facilityDistribution.getEpiUse().setNotRecordedApplied(notRecordedForms.getEpiUseNotRecorded());
    }
  }

}
