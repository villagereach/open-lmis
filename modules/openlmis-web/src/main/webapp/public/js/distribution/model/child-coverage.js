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
    var childCoverageFieldsStatus = countNRStatus(this.childCoverageLineItems, this.mandatoryFields);
    var openedVialFieldsStatus = countNRStatus(this.openedVialLineItems, ['openedVial']);
    this.notRecordedApplied = (childCoverageFieldsStatus.notRecorded  +  openedVialFieldsStatus.notRecorded >
     childCoverageFieldsStatus.recorded + openedVialFieldsStatus.recorded);
  }

  this.isOutdatedDistribution = checkIfIsOutdatedDistribution.call(this);
  this.mandatoryFields = getMandatoryFields.call(this);
  init.call(this);

  function getMandatoryFields() {
    if(this.isOutdatedDistribution) {
      return ['totalHealthCenter11Months', 'totalOutreach11Months', 'totalHealthCenter23Months', 'totalOutreach23Months',
 'femaleHealthCenter9Years', 'femaleOutreach9Years'];
    } else {
      return ['maleHealthCenter11Months', 'femaleHealthCenter11Months',
         'maleOutreach11Months', 'femaleOutreach11Months', 'maleHealthCenter23Months', 'femaleHealthCenter23Months',
         'maleOutreach23Months', 'femaleOutreach23Months', 'femaleHealthCenter9Years', 'femaleOutreach9Years'];
    }
  }

  function isEmpty(obj) {
    return (isUndefined(obj) || isUndefined(obj.value));
  }

  function maleFemaleFieldsNotPresent(maleField, femaleField, totalField) {
    return (isEmpty(maleField) && isEmpty(femaleField) && !isEmpty(totalField));
  }

  function isCoverage11Field(field) {
    return ((['maleHealthCenter11Months', 'femaleHealthCenter11Months','maleOutreach11Months',
     'femaleOutreach11Months'].indexOf(field) !== -1 && !this.isOutdatedDistribution) ||
     (['healthCenter11Months', 'outreach11Months'].indexOf(field) !== -1 && this.isOutdatedDistribution));
  }

  function isCoverage23Field(field) {
    return ((['maleHealthCenter23Months', 'femaleHealthCenter23Months','maleOutreach23Months',
     'femaleOutreach23Months'].indexOf(field) !== -1 && !this.isOutdatedDistribution) ||
     (['healthCenter23Months', 'outreach23Months'].indexOf(field) !== -1 && this.isOutdatedDistribution));
  }

  function isCoverage9YField(field) {
    return (['femaleHealthCenter9Years', 'femaleOutreach9Years'].indexOf(field) !== -1);
  }

  function checkIfIsOutdatedDistribution() {
    var outdated = false;
    $(this.childCoverageLineItems).each(function (index, lineItem) {
      if(maleFemaleFieldsNotPresent(lineItem.maleHealthCenter11Months, lineItem.femaleHealthCenter11Months,
          lineItem.totalHealthCenter11Months)) {
        outdated = true;
        return outdated;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleHealthCenter23Months, lineItem.femaleHealthCenter23Months,
          lineItem.totalHealthCenter23Months)) {
        outdated = true;
        return outdated;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleOutreach11Months, lineItem.femaleOutreach11Months,
          lineItem.totalOutreach11Months)) {
        outdated = true;
        return outdated;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleOutreach23Months, lineItem.femaleOutreach23Months,
          lineItem.totalOutreach23Months)) {
        outdated = true;
        return outdated;
      }
    });
    return outdated;
  }

  ChildCoverage.prototype.computeStatus = function (visited, called, review, ignoreSyncStatus) {
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
          if ((lineItem.vaccination === 'Polio (Newborn)' || lineItem.vaccination === 'IPV' || lineItem.vaccination === 'HPV') &&
            isCoverage23Field(field))
            return true;
          if ((lineItem.vaccination === 'Sarampo 2a dose' || lineItem.vaccination === 'HPV') && isCoverage11Field(field))
            return true;
          if (lineItem.vaccination !== 'HPV' && isCoverage9YField(field))
            return true;
          if (lineItem.productVialName === 'MSD')
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
  }

  ChildCoverage.prototype.setNotRecorded = function () {
    var _this = this;
    this.childCoverageLineItems.forEach(function (lineItem) {
      $(_this.mandatoryFields).each(function (index, field) {
        lineItem[field] = toggleNotRecordedChildCoverage(lineItem[field], _this.notRecordedApplied);
      });
    });
    this.openedVialLineItems.forEach(function (lineItem) {
      lineItem.openedVial = toggleNotRecordedChildCoverage(lineItem.openedVial, _this.notRecordedApplied);
    });
    this.notRecordedApplied = !this.notRecordedApplied;
  };

}
