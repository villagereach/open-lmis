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

  this.isOutdatedDistribution = checkIfIsOutdatedDistribution.call(this);
  this.mandatoryFields = getMandatoryFields.call(this);
  init.call(this);

  function init() {
    var countNotNR = 0;
    var countNR = 0;
    var fields = this.mandatoryFields;
    $(this.childCoverageLineItems).each(function (i, lineItem) {
      $(fields).each(function (i, fieldName) {
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

  function getMandatoryFields() {
    if(this.isOutdatedDistribution) {
      return ['healthCenter11Months', 'outreach11Months', 'healthCenter23Months', 'outreach23Months', 'femaleHealthCenter9YMonths',
         'femaleOutreach9YMonths'];
    } else {
      return ['maleHealthCenter11Months', 'femaleHealthCenter11Months',
         'maleOutreach11Months', 'femaleOutreach11Months', 'maleHealthCenter23Months', 'femaleHealthCenter23Months',
         'maleOutreach23Months', 'femaleOutreach23Months', 'femaleHealthCenter9YMonths', 'femaleOutreach9YMonths'];
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
    return (['femaleHealthCenter9YMonths', 'femaleOutreach9YMonths'].indexOf(field) !== -1);
  }

  function checkIfIsOutdatedDistribution() {
    outdated = false;
    $(this.childCoverageLineItems).each(function (index, lineItem) {
      if(maleFemaleFieldsNotPresent(lineItem.maleHealthCenter11Months, lineItem.femaleHealthCenter11Months,
          lineItem.healthCenter11Months)) {
        outdated = true;
        return;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleHealthCenter23Months, lineItem.femaleHealthCenter23Months,
          lineItem.healthCenter23Months)) {
        outdated = true;
        return;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleOutreach11Months, lineItem.femaleOutreach11Months,
          lineItem.outreach11Months)) {
        outdated = true;
        return;
      }
      if(maleFemaleFieldsNotPresent(lineItem.maleOutreach23Months, lineItem.femaleOutreach23Months,
          lineItem.outreach23Months)) {
        outdated = true;
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
          if ((lineItem.vaccination === 'Polio (Newborn)' || lineItem.vaccination === 'IPV' || lineItem.vaccination === 'HPV') &&
            isCoverage23Field(field))
            return true;
          if ((lineItem.vaccination === 'Sarampo 2a dose' || lineItem.vaccination === 'HPV') && isCoverage11Field(field))
            return true;
          if (lineItem.vaccination !== 'HPV' && isCoverage9YField(field))
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

  function setNotRecordedChild(field, notRecordedApplied) {
    if(!notRecordedApplied) {
      if (field) {
        delete field.value;
        field.notRecorded = true;
        return field;
      } else {
        return {notRecorded: true};
      }
    } else {
      if (field) {
        field.notRecorded = false;
        return field;
      } else {
        return {notRecorded: false};
      }
    }
  }

  ChildCoverage.prototype.setNotRecorded = function () {
    var _this = this;
    fields = this.mandatoryFields;
    this.childCoverageLineItems.forEach(function (lineItem) {
      $(fields).each(function (index, field) {
        lineItem[field] = setNotRecordedChild(lineItem[field], _this.notRecordedApplied);
      });
    });
    this.openedVialLineItems.forEach(function (lineItem) {
      lineItem.openedVial = setNotRecordedChild(lineItem.openedVial, _this.notRecordedApplied);
    });
    this.notRecordedApplied = !this.notRecordedApplied;
  };
}
