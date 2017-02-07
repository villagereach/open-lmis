/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

function EpiInventory(epiInventory) {
  $.extend(true, this, epiInventory);

  $(this.lineItems).each(function (index, lineItem) {
    lineItem.existingQuantity = this.existingQuantity || {};
    lineItem.spoiledQuantity = this.spoiledQuantity || {};
  });

  var mandatoryFields = ['existingQuantity', 'deliveredQuantity', 'spoiledQuantity'];
  var nrFields = ['existingQuantity', 'spoiledQuantity'];

  function init() {
    var countNotNR = 0;
    var countNR = 0;
    $(this.lineItems).each(function (i, lineItem) {
      $(nrFields).each(function (i, fieldName) {
        if(isUndefined(lineItem[fieldName]) || lineItem[fieldName].notRecorded === false) {
            countNotNR++;
        }
        else if(lineItem[fieldName].notRecorded === true) {
            countNR++;
        }
      });
    });
    if(countNR > countNotNR) {
       this.notRecordedApplied = true;
    } else {
       this.notRecordedApplied = false;
    }
  }

  init.call(this);

  EpiInventory.prototype.setNotRecorded = function () {
    if(!this.notRecordedApplied) {
      $(this.lineItems).each(function (index, lineItem) {
        lineItem.existingQuantity.notRecorded = true;
        lineItem.spoiledQuantity.notRecorded = true;
      });
    } else {
       $(this.lineItems).each(function (index, lineItem) {
         lineItem.existingQuantity.notRecorded = false;
         lineItem.spoiledQuantity.notRecorded = false;
       });
    }
    this.notRecordedApplied = !this.notRecordedApplied;
  };

  function isValid(lineItem, field) {
    if (isUndefined(lineItem[field]) || field === 'deliveredQuantity') {
      return !isUndefined(lineItem[field]) && !isUndefined(lineItem[field].value);
    }

    return (!isUndefined(lineItem[field].value) || lineItem[field].notRecorded);
  }

  EpiInventory.prototype.computeStatus = function (visited, review, ignoreSyncStatus) {
    if (review && !ignoreSyncStatus) {
      return DistributionStatus.SYNCED;
    }

    if (visited === false) {
      return DistributionStatus.COMPLETE;
    }
    var statusClass;
    $(this.lineItems).each(function (index, lineItem) {
      $(mandatoryFields).each(function (index, field) {
        if (isValid(lineItem, field) && (!statusClass || statusClass === DistributionStatus.COMPLETE)) {
          statusClass = DistributionStatus.COMPLETE;
        } else if (!isValid(lineItem, field) && (!statusClass || statusClass === DistributionStatus.EMPTY)) {
          statusClass = DistributionStatus.EMPTY;
        } else if ((!isValid(lineItem, field) && statusClass === DistributionStatus.COMPLETE) || (isValid(lineItem, field) && statusClass === DistributionStatus.EMPTY)) {
          statusClass = DistributionStatus.INCOMPLETE;
          return false;
        }
        return true;
      });
    });
    this.status = statusClass || DistributionStatus.COMPLETE;
    return this.status;
  };

}
