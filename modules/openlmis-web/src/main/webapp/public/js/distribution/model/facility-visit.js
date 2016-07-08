/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function FacilityVisit(facilityVisitJson) {
  $.extend(true, this, facilityVisitJson);
  var mandatoryList = ['visitDate'];

  function isEmpty(field) {
    return !field || (isUndefined(field.value) && !field.notRecorded);
  }

  function isUndefinedOrFalse(value) {
    return isUndefined(value) || value === false;
  }

  function isBlank(value) {
    return isUndefined(value) || value.length === 0;
  }

  FacilityVisit.prototype.computeStatus = function (review) {
    if (review) {
      return DistributionStatus.SYNCED;
    }

    if (isEmpty(this.visited)) {
      return DistributionStatus.EMPTY;
    }

    if (this.visited && this.visited.value) {
      if (isEmpty(this.stockouts) || isEmpty(this.hasAdditionalProductSources) || isEmpty(this.stockCardsUpToDate)) {
        return DistributionStatus.INCOMPLETE;
      }

      if (this.stockouts.value) {
        if (isUndefined(this.stockoutCauses)) {
            return DistributionStatus.INCOMPLETE;
        }

        // if no cause was selected
        if (isUndefinedOrFalse(this.stockoutCauses.coldChainEquipmentFailure) &&
          isUndefinedOrFalse(this.stockoutCauses.incorrectEstimationNeeds) &&
          isUndefinedOrFalse(this.stockoutCauses.stockoutZonalWarehouse) &&
          isUndefinedOrFalse(this.stockoutCauses.deliveryNotOnTime) &&
          isUndefinedOrFalse(this.stockoutCauses.productsTransferedAnotherFacility) &&
          isUndefinedOrFalse(this.stockoutCauses.other)) {
          return DistributionStatus.INCOMPLETE;
        }

        // if selected other cause but description is empty
        if (this.stockoutCauses.other === true && isBlank(this.stockoutCauses.stockoutCausesOther)) {
          return DistributionStatus.INCOMPLETE;
        }
      }

      if (this.hasAdditionalProductSources.value) {
        if (isUndefined(this.additionalProductSources)) {
            return DistributionStatus.INCOMPLETE;
        }

        // if no source was selected
        if (isUndefinedOrFalse(this.additionalProductSources.anotherHealthFacility) &&
          isUndefinedOrFalse(this.additionalProductSources.zonalWarehouse) &&
          isUndefinedOrFalse(this.additionalProductSources.other)) {
          return DistributionStatus.INCOMPLETE;
        }

        // if selected other source but description is empty
        if (this.additionalProductSources.other === true && isBlank(this.additionalProductSources.additionalProductSourcesOther)) {
          return DistributionStatus.INCOMPLETE;
        }
      }

      var visitedObservationStatus = computeStatusForObservation.call(this);
      return visitedObservationStatus === DistributionStatus.EMPTY ? DistributionStatus.INCOMPLETE : visitedObservationStatus;
    }

    if (this.reasonForNotVisiting && this.reasonForNotVisiting.value === 'OTHER') {
      return (isEmpty(this.otherReasonDescription) ? DistributionStatus.INCOMPLETE : DistributionStatus.COMPLETE);
    }
    return isEmpty(this.reasonForNotVisiting) ? DistributionStatus.INCOMPLETE : DistributionStatus.COMPLETE;
  };

  function computeStatusForObservation() {
    var status;
    var _this = this;

    function validateFields(fieldName) {
      if (['observations', 'visitDate'].indexOf(fieldName) != -1) return !isEmpty(_this[fieldName]);
      return !(isEmpty(_this[fieldName].name) || isEmpty(_this[fieldName].title));
    }

    function isValid(fieldName) {
      if (!_this[fieldName]) return false;
      return validateFields(fieldName);
    }

    function _isEmpty(fieldName) {
      if (!_this[fieldName]) return true;
      return validateFields(fieldName);
    }

    $(mandatoryList).each(function (i, fieldName) {
      if (isValid(fieldName) && (status == DistributionStatus.COMPLETE || !status)) {
        status = DistributionStatus.COMPLETE;
      } else if (!isValid(fieldName) && _isEmpty(fieldName) && (!status || status == DistributionStatus.EMPTY)) {
        status = DistributionStatus.EMPTY;
      } else if ((!isValid(fieldName) && status === DistributionStatus.COMPLETE) || (isValid(fieldName) && status === DistributionStatus.EMPTY) || (!_isEmpty(fieldName))) {
        status = DistributionStatus.INCOMPLETE;
        return false;
      }
      return true;
    });

    this.status = status;

    return this.status;
  }

}
