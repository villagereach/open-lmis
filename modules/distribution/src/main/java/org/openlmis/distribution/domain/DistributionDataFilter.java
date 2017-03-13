package org.openlmis.distribution.domain;

import lombok.Data;

@Data
public class DistributionDataFilter {
  private boolean readEpiUseData;
  private boolean readRefrigeratorsData;
  private boolean readEpiInventoryData;
  private boolean readFullCoverageData;
  private boolean readChildCoverageData;
  private boolean readAdulCoverageData;

  public DistributionDataFilter(boolean shouldReadData) {
    this.readEpiUseData = shouldReadData;
    this.readRefrigeratorsData = shouldReadData;
    this.readEpiInventoryData = shouldReadData;
    this.readFullCoverageData = shouldReadData;
    this.readChildCoverageData = shouldReadData;
    this.readAdulCoverageData = shouldReadData;
  }

  public DistributionDataFilter(boolean readEpiUseData, boolean readRefrigeratorsData, boolean readEpiInventoryData,
                                boolean readFullCoverageData, boolean readChildCoverageData, boolean readAdulCoverageData) {
    this.readEpiUseData = readEpiUseData;
    this.readRefrigeratorsData = readRefrigeratorsData;
    this.readEpiInventoryData = readEpiInventoryData;
    this.readFullCoverageData = readFullCoverageData;
    this.readChildCoverageData = readChildCoverageData;
    this.readAdulCoverageData = readAdulCoverageData;
  }
}
