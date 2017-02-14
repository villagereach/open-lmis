/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

function ChildCoverage(facilityVisitId, childCoverageJSON) {
  $.extend(true, this, childCoverageJSON);
  this.facilityVisitId = facilityVisitId;

  $(this.childCoverageLineItems).each(function (index, lineItem) {
    lineItem.maleHealthCenter23Months = lineItem.maleHealthCenter23Months || {};
    lineItem.maleOutreach23Months = lineItem.maleOutreach23Months || {};
    lineItem.femaleHealthCenter23Months = lineItem.femaleHealthCenter23Months || {};
    lineItem.femaleOutreach23Months = lineItem.femaleOutreach23Months || {};
  });

  $(this.openedVialLineItems).each(function (index, lineItem) {
    lineItem.openedVial = lineItem.openedVial || {};
  });

  var mandatoryFields = ['healthCenter11Months', 'outreach11Months', 'healthCenter23Months', 'outreach23Months'];

  function init() {
    var childCoverageFieldsStatus = countNRStatus(this.childCoverageLineItems, mandatoryFields);
    var openedVialFieldsStatus = countNRStatus(this.openedVialLineItems, ['openedVial']);
    this.notRecordedApplied = (childCoverageFieldsStatus.notRecorded  +  openedVialFieldsStatus.notRecorded >
     childCoverageFieldsStatus.recorded + openedVialFieldsStatus.recorded);
  }

  init.call(this);
}

ChildCoverage.prototype.computeStatus = function (visited, review, ignoreSyncStatus) {
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
    $(lineItems).each(function (index, lineItem) {
      $(mandatoryFields).each(function (index, field) {
        if (lineItem.vaccination === 'Polio (Newborn)' && ['maleHealthCenter23Months', 'femaleHealthCenter23Months',
            'maleOutreach23Months', 'femaleOutreach23Months'].indexOf(field) !== -1)
          return true;
        if (lineItem.vaccination === 'IPV' && ['maleHealthCenter23Months', 'femaleHealthCenter23Months',
            'maleOutreach23Months', 'femaleOutreach23Months'].indexOf(field) !== -1)
          return true;
        if (lineItem.vaccination === 'Sarampo 2a dose' && ['maleHealthCenter11Months', 'femaleHealthCenter11Months',
            'maleOutreach11Months', 'femaleOutreach11Months'].indexOf(field) !== -1)
          return true;
        if ((status === DistributionStatus.COMPLETE || !status) && isValid(lineItem[field])) {
          status = DistributionStatus.COMPLETE;
          return true;
        } else if ((status === DistributionStatus.EMPTY || !status) && !isValid(lineItem[field])) {
          status = DistributionStatus.EMPTY;
          return true;
        } else if ((status === DistributionStatus.EMPTY && isValid(lineItem[field])) || (status === DistributionStatus.COMPLETE && !isValid(lineItem[field]))) {
          status = DistributionStatus.INCOMPLETE;
          return false;
        }
        return true;
      });
      return status !== DistributionStatus.INCOMPLETE;
    });
  }

  validateLineItems(this.childCoverageLineItems, ['maleHealthCenter11Months', 'femaleHealthCenter11Months',
    'maleOutreach11Months', 'femaleOutreach11Months', 'maleHealthCenter23Months', 'femaleHealthCenter23Months',
    'maleOutreach23Months', 'femaleOutreach23Months']);
  validateLineItems(this.openedVialLineItems, ['openedVial']);

  this.status = status;
  return this.status;
};

function toggleNotRecordedChildCoverage(field, notRecordedApplied) {
  if (field) {
    delete field.value;
    field.notRecorded = !notRecordedApplied;
    return field;
  } else {
    return {notRecorded: !notRecordedApplied};
  }

}

ChildCoverage.prototype.setNotRecorded = function () {
  var _this = this;
  this.childCoverageLineItems.forEach(function (lineItem) {
    lineItem.maleHealthCenter11Months = toggleNotRecordedChildCoverage(lineItem.healthCenter11Months, _this.notRecordedApplied);
    lineItem.maleHealthCenter23Months = setNotRecorded(lineItem.maleHealthCenter23Months);
    lineItem.maleOutreach11Months = setNotRecorded(lineItem.maleOutreach11Months);
    lineItem.maleOutreach23Months = setNotRecorded(lineItem.maleOutreach23Months);
    lineItem.femaleHealthCenter11Months = setNotRecorded(lineItem.femaleHealthCenter11Months);
    lineItem.femaleHealthCenter23Months = setNotRecorded(lineItem.femaleHealthCenter23Months);
    lineItem.femaleOutreach11Months = setNotRecorded(lineItem.femaleOutreach11Months);
    lineItem.femaleOutreach23Months = setNotRecorded(lineItem.femaleOutreach23Months);
  });
  this.openedVialLineItems.forEach(function (lineItem) {
    lineItem.openedVial = toggleNotRecordedChildCoverage(lineItem.openedVial, _this.notRecordedApplied);
  });
  this.notRecordedApplied = !this.notRecordedApplied;
};
