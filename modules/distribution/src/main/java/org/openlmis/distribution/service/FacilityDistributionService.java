/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

package org.openlmis.distribution.service;

import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.openlmis.core.domain.Facility;
import org.openlmis.core.exception.DataException;
import org.openlmis.core.service.FacilityService;
import org.openlmis.core.service.MessageService;
import org.openlmis.distribution.domain.Distribution;
import org.openlmis.distribution.domain.DistributionDataFilter;
import org.openlmis.distribution.domain.DistributionRefrigerators;
import org.openlmis.distribution.domain.EpiInventory;
import org.openlmis.distribution.domain.EpiUse;
import org.openlmis.distribution.domain.FacilityDistribution;
import org.openlmis.distribution.domain.FacilityVisit;
import org.openlmis.distribution.domain.ProductVial;
import org.openlmis.distribution.domain.Refrigerator;
import org.openlmis.distribution.domain.RefrigeratorReading;
import org.openlmis.distribution.domain.TargetGroupProduct;
import org.openlmis.distribution.domain.VaccinationAdultCoverage;
import org.openlmis.distribution.domain.VaccinationChildCoverage;
import org.openlmis.distribution.domain.VaccinationFullCoverage;
import org.openlmis.distribution.dto.DistributionDTO;
import org.openlmis.distribution.dto.FacilityDistributionDTO;
import org.openlmis.distribution.dto.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.collect;
import static org.apache.commons.collections.CollectionUtils.select;

/**
 * Exposes the services for handling FacilityDistribution entity.
 */

@Service
@NoArgsConstructor
public class FacilityDistributionService {

  @Autowired
  private FacilityService facilityService;

  @Autowired
  private RefrigeratorService refrigeratorService;

  @Autowired
  private EpiUseService epiUseService;

  @Autowired
  private FacilityVisitService facilityVisitService;

  @Autowired
  private DistributionRefrigeratorsService distributionRefrigeratorsService;

  @Autowired
  private EpiInventoryService epiInventoryService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private VaccinationCoverageService vaccinationCoverageService;

  @Autowired
  private DistributionService distributionService;

  public Map<Long, FacilityDistribution> createFor(Distribution distribution) {
    Long deliveryZoneId = distribution.getDeliveryZone().getId();
    Long programId = distribution.getProgram().getId();

    Map<Long, FacilityDistribution> facilityDistributions = new HashMap<>();

    List<Facility> facilities = facilityService.getAllForDeliveryZoneAndProgram(deliveryZoneId, programId);
    if (facilities.size() == 0) {
      throw new DataException(messageService.message("message.no.facility.available", distribution.getProgram().getName(),
        distribution.getDeliveryZone().getName()));
    }
    List<Refrigerator> distributionRefrigerators = refrigeratorService.getRefrigeratorsForADeliveryZoneAndProgram(deliveryZoneId, programId);
    List<TargetGroupProduct> targetGroupProducts = vaccinationCoverageService.getVaccinationProducts();
    List<TargetGroupProduct> childrenTargetGroupProducts = new ArrayList<>();
    List<TargetGroupProduct> adultTargetGroupProducts = new ArrayList<>();
    filterTargetGroupProducts(targetGroupProducts, childrenTargetGroupProducts, adultTargetGroupProducts);
    List<ProductVial> productVials = vaccinationCoverageService.getProductVials();
    List<ProductVial> childProductVials = new ArrayList<>();
    List<ProductVial> adultProductVials = new ArrayList<>();
    filterProductVials(productVials, childProductVials, adultProductVials);
    DistributionDTO previousDistribution = distributionService.getPreviousDistribution(distribution, new DistributionDataFilter(false));

    for (Facility facility : facilities) {
      facilityDistributions.put(facility.getId(), createDistributionData(facility, distribution, distributionRefrigerators, childrenTargetGroupProducts,
        adultTargetGroupProducts, childProductVials, adultProductVials, previousDistribution));
    }

    return facilityDistributions;
  }

  private void filterProductVials(List<ProductVial> productVials, List<ProductVial> childProductVials, List<ProductVial> adultProductVials) {
    CollectionUtils.select(productVials, new Predicate() {
      @Override
      public boolean evaluate(Object o) {
        return ((ProductVial) o).getChildCoverage();
      }
    }, childProductVials);
    CollectionUtils.selectRejected(productVials, new Predicate() {
      @Override
      public boolean evaluate(Object o) {
        return ((ProductVial) o).getChildCoverage();
      }
    }, adultProductVials);
  }

  private void filterTargetGroupProducts(List<TargetGroupProduct> targetGroupProducts, List<TargetGroupProduct> childrenTargetGroupProducts, List<TargetGroupProduct> adultTargetGroupProducts) {
    CollectionUtils.select(targetGroupProducts, new Predicate() {
      @Override
      public boolean evaluate(Object o) {
        return ((TargetGroupProduct) o).getChildCoverage();
      }
    }, childrenTargetGroupProducts);
    CollectionUtils.selectRejected(targetGroupProducts, new Predicate() {
      @Override
      public boolean evaluate(Object o) {
        return ((TargetGroupProduct) o).getChildCoverage();
      }
    }, adultTargetGroupProducts);
  }

  FacilityDistribution createDistributionData(final Facility facility,
                                              Distribution distribution,
                                              List<Refrigerator> refrigerators,
                                              List<TargetGroupProduct> childrenTargetGroupProducts,
                                              List<TargetGroupProduct> adultTargetGroupProducts,
                                              List<ProductVial> childProductVials, List<ProductVial> adultProductVials,
                                              DistributionDTO previousDistribution) {
    List<RefrigeratorReading> refrigeratorReadings = getRefrigeratorReadings(facility.getId(), refrigerators, null);

    FacilityVisit facilityVisit = createFacilityVisitData(facility, distribution, previousDistribution);
    facilityVisitService.save(facilityVisit);
    FacilityDistribution facilityDistribution = new FacilityDistribution(facilityVisit, facility, distribution, refrigeratorReadings,
      childrenTargetGroupProducts, adultTargetGroupProducts, childProductVials, adultProductVials);
    epiUseService.save(facilityDistribution.getEpiUse());
    epiInventoryService.save(facilityDistribution.getEpiInventory());
    vaccinationCoverageService.saveChildCoverage(facilityDistribution.getChildCoverage());
    vaccinationCoverageService.saveAdultCoverage(facilityDistribution.getAdultCoverage());
    return facilityDistribution;
  }

  public FacilityDistribution save(FacilityDistribution facilityDistribution) {
    facilityVisitService.save(facilityDistribution.getFacilityVisit());
    if (facilityDistribution.getFacilityVisit().getVisited() || facilityDistribution.getFacilityVisit().getCalled()) {
      epiInventoryService.save(facilityDistribution.getEpiInventory());
      distributionRefrigeratorsService.save(facilityDistribution.getFacilityVisit().getFacilityId(), facilityDistribution.getRefrigerators());
      epiUseService.save(facilityDistribution.getEpiUse());
    }
    vaccinationCoverageService.save(facilityDistribution);
    return facilityDistribution;
  }

  public FacilityDistribution setSynced(FacilityDistribution facilityDistribution) {
    facilityVisitService.setSynced(facilityDistribution.getFacilityVisit());
    return facilityDistribution;
  }

  private List<RefrigeratorReading> getRefrigeratorReadings(final Long facilityId, List<Refrigerator> refrigerators, final Long facilityVisitId) {
    return (List<RefrigeratorReading>) collect(select(refrigerators, new Predicate() {
      @Override
      public boolean evaluate(Object o) {
        return ((Refrigerator) o).getFacilityId().equals(facilityId);
      }
    }), new Transformer() {
      @Override
      public Object transform(Object o) {
        Refrigerator refrigerator = (Refrigerator) o;

        if (null == facilityVisitId) {
          return new RefrigeratorReading(refrigerator);
        }

        RefrigeratorReading reading = distributionRefrigeratorsService.getByRefrigeratorIdAndSerialNumber(refrigerator.getId(), refrigerator.getSerialNumber(), facilityVisitId);
        return null == reading ? new RefrigeratorReading(refrigerator) : reading;
      }
    });
  }

  public Map<Long, FacilityDistribution> get(Distribution distribution, DistributionDataFilter distributionDataFilter) {
    List<FacilityVisit> unSyncedFacilities = facilityVisitService.getUnSyncedFacilities(distribution.getId());
    return getFacilityDistributions(distribution, unSyncedFacilities, distributionDataFilter);
  }

  public Map<Long, FacilityDistribution> getData(Distribution distribution, DistributionDataFilter distributionDataFilter, Integer limit) {
    List<FacilityVisit> visits = facilityVisitService.getByDistributionId(distribution.getId(), limit);
    return getFacilityDistributions(distribution, visits, distributionDataFilter);
  }

  public FacilityDistribution getDistributionData(FacilityVisit facilityVisit, Distribution distribution, DistributionDataFilter distributionDataFilter) {
    FacilityDistribution facilityDistribution = new FacilityDistribution();

    EpiUse epiUse = distributionDataFilter.isReadEpiUseData() ? epiUseService.getBy(facilityVisit.getId()) : null;

    List<Refrigerator> refrigerators = distributionDataFilter.isReadRefrigeratorsData() ?
            refrigeratorService.getRefrigeratorsForADeliveryZoneAndProgram(distribution.getDeliveryZone().getId(), distribution.getProgram().getId()) : null;
    DistributionRefrigerators distributionRefrigerators = distributionDataFilter.isReadRefrigeratorsData() ?
            new DistributionRefrigerators(getRefrigeratorReadings(facilityVisit.getFacilityId(), refrigerators, facilityVisit.getId())) : null;

    EpiInventory epiInventory = distributionDataFilter.isReadEpiInventoryData() ? epiInventoryService.getBy(facilityVisit.getId()) : null;
    VaccinationFullCoverage coverage = distributionDataFilter.isReadFullCoverageData() ? vaccinationCoverageService.getFullCoverageBy(facilityVisit.getId()) : null;
    VaccinationChildCoverage childCoverage = distributionDataFilter.isReadChildCoverageData() ? vaccinationCoverageService.getChildCoverageBy(facilityVisit.getId()) : null;
    VaccinationAdultCoverage adultCoverage = distributionDataFilter.isReadAdulCoverageData() ? vaccinationCoverageService.getAdultCoverageBy(facilityVisit.getId()) : null;

    facilityDistribution = new FacilityDistribution(facilityVisit, epiUse, distributionRefrigerators, epiInventory, coverage,
              childCoverage, adultCoverage);

    Facility facility = facilityService.getById(facilityVisit.getFacilityId());
    facilityDistribution.setFacility(facility);
    return facilityDistribution;
  }

  private Map<Long, FacilityDistribution> getFacilityDistributions(Distribution distribution, List<FacilityVisit> facilityVisits, DistributionDataFilter distributionDataFilter) {
    Map<Long, FacilityDistribution> facilityDistributions = new HashMap<>();

    for (FacilityVisit facilityVisit : facilityVisits) {
      facilityDistributions.put(facilityVisit.getFacilityId(), getDistributionData(facilityVisit, distribution, distributionDataFilter));
    }

    return facilityDistributions;
  }

  private FacilityVisit createFacilityVisitData(Facility facility, Distribution distribution, DistributionDTO previousDistribution) {
    FacilityVisit facilityVisit = new FacilityVisit(facility, distribution);
    if (previousDistribution != null) {
      FacilityDistributionDTO previousFacilityDistribution = previousDistribution.getFacilityDistributions().get(facility.getId());

      if (previousFacilityDistribution != null) {
        Reading previousObservations = previousFacilityDistribution.getFacilityVisit().getObservations();
        Reading previousTechnicalStaff = previousFacilityDistribution.getFacilityVisit().getTechnicalStaff();

        facilityVisit.setPriorObservations(Reading.safeRead(previousObservations).getEffectiveValue());
        facilityVisit.setTechnicalStaff(Reading.safeRead(previousTechnicalStaff).parsePositiveInt());
      }
    }

    return facilityVisit;
  }
}
