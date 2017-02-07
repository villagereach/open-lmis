/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 *  Copyright © 2013 VillageReach
 *
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *    
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function AdultCoverage(facilityVisitId, adultCoverageJSON) {
  $.extend(true, this, adultCoverageJSON);
  this.facilityVisitId = facilityVisitId;
  var _this = this;

  $(this.adultCoverageLineItems).each(function (i, lineItem) {
    _this.adultCoverageLineItems[i] = new AdultCoverageLineItem(lineItem);
  });

  $(this.openedVialLineItems).each(function (i, lineItem) {
    _this.openedVialLineItems[i] = new OpenedVialLineItem(lineItem);
  });

  var fieldList = ['healthCenterTetanus1', 'outreachTetanus1', 'healthCenterTetanus2To5', 'outreachTetanus2To5'];

  function init() {
    var countNotNR = 0;
    var countNR = 0;
    $(this.adultCoverageLineItems).each(function (i, lineItem) {
      $(fieldList).each(function (i, fieldName) {
        if(isUndefined(lineItem[fieldName]) || lineItem[fieldName].notRecorded === false) {
            countNotNR++;
        }
        else if(lineItem[fieldName].notRecorded === true) {
            countNR++;
        }
      });
    });
    $(this.openedVialLineItems).each(function (i, lineItem) {
      if(isUndefined(lineItem.openedVial) || lineItem.openedVial.notRecorded === false) {
          countNotNR++;
      }
      else if(lineItem.openedVial.notRecorded === true) {
          countNR++;
      }
    });
    if(countNR > countNotNR) {
       this.notRecordedApplied = true;
    } else {
       this.notRecordedApplied = false;
    }
  }

  init.call(this);
}

AdultCoverage.prototype.computeStatus = function (visited, review, ignoreSyncStatus) {
  if (review && !ignoreSyncStatus) {
    return DistributionStatus.SYNCED;
  }

  var status;

  var isValid = function (field) {
    if (!field)
      return false;
    return !(isUndefined(field.value) && !field.notRecorded);
  };

  function validateLineItems(lineItems, mandatoryFields) {

    var nonHealthCenterDemographicGroups = ["MIF 15-49 years - Students", "MIF 15-49 years - Workers", "Students not MIF", "Workers not MIF"];
    var attributesNotDefined = ["healthCenterTetanus1", "healthCenterTetanus2To5"];

    $(lineItems).each(function (index, lineItem) {
      $(mandatoryFields).each(function (index, field) {
        if (nonHealthCenterDemographicGroups.indexOf(lineItem.demographicGroup) >= 0 && attributesNotDefined.indexOf(field) >= 0)
          return true;
        if (isValid(lineItem[field]) && (status === DistributionStatus.COMPLETE || !status)) {
          status = DistributionStatus.COMPLETE;
          return true;
        } else if (!isValid(lineItem[field]) && (status === DistributionStatus.EMPTY || !status)) {
          status = DistributionStatus.EMPTY;
          return true;
        } else if ((isValid(lineItem[field]) && status === DistributionStatus.EMPTY) ||
          (!isValid(lineItem[field])) && status === DistributionStatus.COMPLETE) {
          status = DistributionStatus.INCOMPLETE;
          return false;
        }
        return true;
      });
      return status !== DistributionStatus.INCOMPLETE;
    });
  }

  validateLineItems(this.adultCoverageLineItems, ['healthCenterTetanus1', 'outreachTetanus1', 'healthCenterTetanus2To5', 'outreachTetanus2To5']);
  validateLineItems(this.openedVialLineItems, ['openedVial']);

  this.status = status;
  return this.status;
};

AdultCoverage.prototype.wastageRate = function (lineItem) {
  return lineItem.wastageRate(this.totalTetanus());
};

AdultCoverage.prototype.setNotRecorded = function () {
  var _this = this;
  $(this.adultCoverageLineItems).each(function (i, lineItem) {
    lineItem.setNotRecorded(_this);
  });

  $(this.openedVialLineItems).each(function (i, lineItem) {
    lineItem.setNotRecorded(_this);
  });
  this.notRecordedApplied = !this.notRecordedApplied;
};

AdultCoverage.prototype.totalHealthCenterTetanus1 = function () {
  return this.sumOfAttributes('healthCenterTetanus1');
};
AdultCoverage.prototype.totalOutreachTetanus1 = function () {
  return this.sumOfAttributes('outreachTetanus1');
};
AdultCoverage.prototype.totalHealthCenterTetanus2To5 = function () {
  return this.sumOfAttributes('healthCenterTetanus2To5');
};
AdultCoverage.prototype.totalOutreachTetanus2To5 = function () {
  return this.sumOfAttributes('outreachTetanus2To5');
};

AdultCoverage.prototype.totalTetanus1 = function () {
  return this.totalHealthCenterTetanus1() + this.totalOutreachTetanus1();
};

AdultCoverage.prototype.totalTetanus2To5 = function () {
  return this.totalHealthCenterTetanus2To5() + this.totalOutreachTetanus2To5();
};

AdultCoverage.prototype.totalTetanus = function () {
  return this.totalTetanus1() + this.totalTetanus2To5();
};

AdultCoverage.prototype.sumOfAttributes = function (attribute) {
  var total = 0;
  $(this.adultCoverageLineItems).each(function (i, lineItem) {
    var currentValue = isUndefined(lineItem[attribute]) ? 0 : lineItem[attribute].value;
    total = utils.sum(total, currentValue);
  });
  return total;
};

function AdultCoverageLineItem(lineItem) {
  $.extend(true, this, lineItem);
  this.healthCenterTetanus1 = this.healthCenterTetanus1 || {};
  this.healthCenterTetanus2To5 = this.healthCenterTetanus2To5 || {};
}

function setNotRecorded(field, notRecordedApplied) {
  if(!notRecordedApplied) {
    if (field) {
      delete field.value;
      field.notRecorded = true;

      return field;
    } else {
      return {notRecorded: true};
    }
  } else {
    return {notRecorded: false};
  }
}

AdultCoverageLineItem.prototype.setNotRecorded = function (_this) {
  this.healthCenterTetanus1 = setNotRecorded(this.healthCenterTetanus1, _this.notRecordedApplied);
  this.outreachTetanus1 = setNotRecorded(this.outreachTetanus1, _this.notRecordedApplied);
  this.healthCenterTetanus2To5 = setNotRecorded( this.healthCenterTetanus2To5m, _this.notRecordedApplied);
  this.outreachTetanus2To5 = setNotRecorded(this.outreachTetanus2To5m, _this.notRecordedApplied);
};

AdultCoverageLineItem.prototype.totalTetanus1 = function () {
  var value1 = isUndefined(this.healthCenterTetanus1) ? 0 : this.healthCenterTetanus1.value;
  var value2 = isUndefined(this.outreachTetanus1) ? 0 : this.outreachTetanus1.value;
  return utils.sum(value1, value2);
};

AdultCoverageLineItem.prototype.totalTetanus2To5 = function () {
  var value1 = isUndefined(this.healthCenterTetanus2To5) ? 0 : this.healthCenterTetanus2To5.value;
  var value2 = isUndefined(this.outreachTetanus2To5) ? 0 : this.outreachTetanus2To5.value;
  return utils.sum(value1, value2);
};

AdultCoverageLineItem.prototype.totalTetanus = function () {
  return this.totalTetanus1() + this.totalTetanus2To5();
};

AdultCoverageLineItem.prototype.coverageRate = function () {
  if (isUndefined(this.targetGroup) || this.targetGroup === 0) return null;
  return Math.round((this.totalTetanus2To5() / this.targetGroup) * 100);
};

function OpenedVialLineItem(lineItem) {
  $.extend(true, this, lineItem);
}

OpenedVialLineItem.prototype.wastageRate = function (totalTetanus) {
  if (isUndefined(this.openedVial) || isUndefined(this.openedVial.value) || isUndefined(this.packSize)) return null;
  var totalDosesConsumed = this.openedVial.value * this.packSize;
  if (totalDosesConsumed === 0) return null;
  return Math.round((totalDosesConsumed - totalTetanus) / totalDosesConsumed * 100);
};

OpenedVialLineItem.prototype.setNotRecorded = function (_this) {
  this.openedVial = setNotRecorded(this.openedVial, _this.notRecordedApplied);
};
