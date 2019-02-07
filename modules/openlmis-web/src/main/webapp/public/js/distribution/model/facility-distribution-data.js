/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function FacilityDistribution(facilityDistribution) {

  $.extend(true, this, facilityDistribution);

  this.facilityVisitId = facilityDistribution.facilityVisit.id;
  this.epiUse = new EpiUse(facilityDistribution.epiUse);
  this.epiInventory = new EpiInventory(facilityDistribution.epiInventory);
  this.refrigerators = new Refrigerators(this.facilityVisitId, facilityDistribution.refrigerators);
  this.facilityVisit = new FacilityVisit(facilityDistribution.facilityVisit);

  this.status = facilityDistribution.status;
  this.typeCode = facilityDistribution.facilityTypeCode ? facilityDistribution.facilityTypeCode.toLowerCase() : "";
  var WRH = "wrh";
  var WAREHOUSE = "warehouse";

  function initCoverageScreens() {
    //As of 1/25/2019, we should never display the coverage tabs
    /*
    this.fullCoverage = !isWarehouse(this.typeCode) ? new FullCoverage(this.facilityVisitId, facilityDistribution.fullCoverage) : null;
    this.childCoverage = !isWarehouse(this.typeCode) ? new ChildCoverage(this.facilityVisitId, facilityDistribution.childCoverage) : null;
    this.adultCoverage = !isWarehouse(this.typeCode) ? new AdultCoverage(this.facilityVisitId, facilityDistribution.adultCoverage) : null; */
    this.fullCoverage =  this.childCoverage = this.adultCoverage = null;
  }

  initCoverageScreens.call(this);

  function getFormsForComputingStatus() {
      return [this.epiUse, this.refrigerators, this.facilityVisit, this.epiInventory];
  }

  function isWarehouse(typeCode) {
    return typeCode === WAREHOUSE || typeCode === WRH;
  }

  FacilityDistribution.prototype.computeStatus = function (review, ignoreSyncStatus) {
    if (review) {
      this.status = DistributionStatus.SYNCED;
      return this.status;
    }

    var forms = getFormsForComputingStatus.call(this);
    var overallStatus;
    if (!ignoreSyncStatus && (this.status === DistributionStatus.SYNCED || this.status === DistributionStatus.DUPLICATE)) {
      return this.status;
    }
    var that = this;
    $.each(forms, function (index, form) {
      var computedStatus = form.computeStatus(that.facilityVisit.visited && that.facilityVisit.visited.value, that.facilityVisit.called && that.facilityVisit.called.value, review, ignoreSyncStatus);
      if (computedStatus === DistributionStatus.COMPLETE && (overallStatus === DistributionStatus.COMPLETE || !overallStatus)) {
        overallStatus = DistributionStatus.COMPLETE;
      } else if (computedStatus === DistributionStatus.EMPTY && (!overallStatus || overallStatus == DistributionStatus.EMPTY)) {
        overallStatus = DistributionStatus.EMPTY;
      } else if (computedStatus === DistributionStatus.INCOMPLETE || (computedStatus === DistributionStatus.EMPTY && overallStatus === DistributionStatus.COMPLETE) ||
        (computedStatus === DistributionStatus.COMPLETE && overallStatus === DistributionStatus.EMPTY)) {
        overallStatus = DistributionStatus.INCOMPLETE;
        return false;
      }
      return true;
    });

    this.status = overallStatus;
    return overallStatus;

  };

  FacilityDistribution.prototype.isDisabled = function (tabName, review) {
    if (review) {
      var editMode = review.editMode[this.facilityId][tabName || review.currentScreen];

      if (["refrigerator-data", "epi-inventory", "epi-use"].indexOf(tabName || review.currentScreen) != -1) {
        return !editMode || !this.facilityContacted();
      }
      return !editMode;
    }

    if ([DistributionStatus.SYNCED, DistributionStatus.DUPLICATE].indexOf(this.status) != -1) {
      return true;
    }

    return ( !this.facilityContacted() && ["refrigerator-data", "epi-inventory", "epi-use"].indexOf(tabName) != -1);
  };

  FacilityDistribution.prototype.facilityContacted = function() {
    var facilityContacted = (this.facilityVisit.visited && this.facilityVisit.visited.value === true) || (this.facilityVisit.called && this.facilityVisit.called.value === true);
    return facilityContacted;
  };

  FacilityDistribution.prototype.getByScreen = function (screenName) {
    switch(screenName) {
      case 'visit-info':        return { property: 'facilityVisit', bean: this.facilityVisit };
      case 'refrigerator-data': return { property: 'refrigerators', bean: this.refrigerators };
      case 'epi-inventory':     return { property: 'epiInventory', bean: this.epiInventory };
      case 'epi-use':           return { property: 'epiUse', bean: this.epiUse };
      case 'full-coverage':     return { property: 'fullCoverage', bean: this.fullCoverage };
      case 'child-coverage':    return { property: 'childCoverage', bean: this.childCoverage };
      case 'adult-coverage':    return { property: 'adultCoverage', bean: this.adultCoverage };
    }
  };

  FacilityDistribution.prototype.shouldDisplayCoverageScreen = function () {
    //return !isWarehouse(this.typeCode);
    //As of 1/25/2019, we should never display the coverage tabs
    return false;
  };

}
