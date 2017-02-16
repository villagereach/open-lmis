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

  function init() {
    var childCoverageFieldsStatus = countNRStatus(this.childCoverageLineItems, mandatoryFields);
    var openedVialFieldsStatus = countNRStatus(this.openedVialLineItems, ['openedVial']);
    this.notRecordedApplied = (childCoverageFieldsStatus.notRecorded  +  openedVialFieldsStatus.notRecorded >
     childCoverageFieldsStatus.recorded + openedVialFieldsStatus.recorded);
  }

  init.call(this);
}

  this.isOutdatedDistribution = checkIfIsOutdatedDistribution.call(this);
  this.mandatoryFields = getMandatoryFields.call(this);

  function getMandatoryFields() {
    if(this.isOutdatedDistribution) {
      return ['healthCenter11Months', 'outreach11Months', 'healthCenter23Months', 'outreach23Months'];
    } else {
      return ['maleHealthCenter11Months', 'femaleHealthCenter11Months',
         'maleOutreach11Months', 'femaleOutreach11Months', 'maleHealthCenter23Months', 'femaleHealthCenter23Months',
         'maleOutreach23Months', 'femaleOutreach23Months'];
    }
  }

  function isEmpty(obj) {
    return (isUndefined(obj) || (isUndefined(obj.value) && !obj.notRecorded));
  }

  function maleFemaleFieldsNotPresent(maleField, femaleField, totalField) {
    return (isEmpty(maleField) && isEmpty(femaleField) && !isEmpty(totalField));
  }

  function checkIfIsOutdatedDistribution() {
    outdated = true;
    $(this.childCoverageLineItems).each(function (index, lineItem) {
      if(!maleFemaleFieldsNotPresent(lineItem.maleHealthCenter11Months, lineItem.femaleHealthCenter11Months,
          lineItem.healthCenter11Months)) {
        outdated = false;
        return;
      }
      if(!maleFemaleFieldsNotPresent(lineItem.maleHealthCenter23Months, lineItem.femaleHealthCenter23Months,
          lineItem.healthCenter23Months)) {
        outdated = false;
        return;
      }
      if(!maleFemaleFieldsNotPresent(lineItem.maleOutreach11Months, lineItem.femaleOutreach11Months,
          lineItem.outreach11Months)) {
        outdated = false;
        return;
      }
      if(!maleFemaleFieldsNotPresent(lineItem.maleOutreach23Months, lineItem.femaleOutreach23Months,
          lineItem.outreach23Months)) {
        outdated = false;
        return;
      }
    });
    return outdated;
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
          if (lineItem.vaccination === 'Polio (Newborn)' &&
              mandatoryFields.slice(mandatoryFields.length / 2, mandatoryFields.length).indexOf(field) !== -1)
            return true;
          if (lineItem.vaccination === 'IPV' &&
              mandatoryFields.slice(mandatoryFields.length / 2, mandatoryFields.length).indexOf(field) !== -1)
            return true;
          if (lineItem.vaccination === 'Sarampo 2a dose' &&
              mandatoryFields.slice(0, mandatoryFields.length / 2).indexOf(field) !== -1)
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

    validateLineItems(this.childCoverageLineItems, this.mandatoryFields);
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

  ChildCoverage.prototype.setNotRecorded = function () {
    fields = this.mandatoryFields;
    this.childCoverageLineItems.forEach(function (lineItem) {
      $(fields).each(function (index, field) {
        lineItem[field] = setNotRecorded(lineItem[field]);
      });
    });
    this.openedVialLineItems.forEach(function (lineItem) {
      lineItem.openedVial = setNotRecorded(lineItem.openedVial);
    });
    this.notRecordedApplied = !this.notRecordedApplied;
  };
}
