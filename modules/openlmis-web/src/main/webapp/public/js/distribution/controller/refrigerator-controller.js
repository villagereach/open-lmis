/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function RefrigeratorController($scope, $dialog, IndexedDB, $routeParams, distributionService) {
  $scope.distribution = distributionService.distribution;
  $scope.distributionReview = distributionService.distributionReview;
  $scope.selectedFacilityId = $routeParams.facility;

  $scope.edit = {};

  $scope.setEdit = function (serialNum, index) {
    angular.forEach($scope.edit, function (value, key) {
      $scope.edit[key] = false;
    });
    $scope.edit[serialNum] = true;
    if (!isUndefined(index)) {
      var refrigeratorEditButton = angular.element('#editReading' + index).offset().top;
      angular.element('body,html').animate({scrollTop: refrigeratorEditButton + 'px'}, 'slow', function () {
        $('input[name^="temperature"]:visible').focus();
      });
    }
  };

  $scope.showRefrigeratorModal = function (editMode, serialNumber) {
    $scope.editMode = editMode;
    $scope.addRefrigeratorModal = true;
    $scope.refrigeratorReading = null;
    if (editMode) {
      var existingRefrigeratorReading = $scope.findRefrigeratorReading(serialNumber);
      if (existingRefrigeratorReading) {
        $scope.oldSerialNumberValue = existingRefrigeratorReading.refrigerator.serialNumber.value;
        $scope.refrigeratorReading = {
          refrigerator: {
            serialNumber: { value: existingRefrigeratorReading.refrigerator.serialNumber.value },
            brand: { value: existingRefrigeratorReading.refrigerator.brand.value },
            model: { value: existingRefrigeratorReading.refrigerator.model.value },
            type: { value: existingRefrigeratorReading.refrigerator.type.value }
          }
        };
      }
    }
    $scope.isDuplicateSerialNumber = false;
  };

  $scope.addRefrigeratorToStore = function () {
    var existing = $scope.findRefrigeratorReading($scope.refrigeratorReading.refrigerator.serialNumber.value);
    if (existing) {
      $scope.isDuplicateSerialNumber = true;
      return;
    }
    $scope.distribution.facilityDistributions[$scope.selectedFacilityId].refrigerators.addReading(angular.copy($scope.refrigeratorReading));

    IndexedDB.put('distributions', $scope.distribution);

    $scope.addRefrigeratorModal = $scope.isDuplicateSerialNumber = undefined;
  };

  $scope.updateRefrigeratorInStore = function () {
    var editedRefrigerator = $scope.findRefrigeratorReading($scope.oldSerialNumberValue);
    var existing = $scope.findRefrigeratorReading($scope.refrigeratorReading.refrigerator.serialNumber.value);

    if (existing && $scope.refrigeratorReading.refrigerator.serialNumber.value !== $scope.oldSerialNumberValue) {
      $scope.isDuplicateSerialNumber = true;
    } else {
      editedRefrigerator.refrigerator.serialNumber.value = $scope.refrigeratorReading.refrigerator.serialNumber.value;
      editedRefrigerator.refrigerator.brand.value = $scope.refrigeratorReading.refrigerator.brand.value;
      editedRefrigerator.refrigerator.model.value = $scope.refrigeratorReading.refrigerator.model.value;
      editedRefrigerator.refrigerator.type.value = $scope.refrigeratorReading.refrigerator.type.value;

      IndexedDB.put('distributions', $scope.distribution);

      $scope.addRefrigeratorModal = $scope.editMode = $scope.isDuplicateSerialNumber = undefined;
    }
  };

  $scope.updateRefrigeratorReadingStatus = function (refrigeratorReading) {
    refrigeratorReading.computeStatus();
    $scope.edit[refrigeratorReading.refrigerator.serialNumber.value] = false;
  };

  $scope.isFormDisabled = function () {
    if ($scope.distributionReview) {
      return !$scope.distributionReview.editMode[$routeParams.facility][$scope.distributionReview.currentScreen];
    }

    var facilityDistribution = $scope.distribution.facilityDistributions[$scope.selectedFacilityId];
    var status = facilityDistribution.status;
    var facilityVisit = facilityDistribution.facilityVisit;
    var facilityContacted = (facilityVisit && facilityVisit.visited && facilityVisit.visited.value === true) || (facilityVisit && facilityVisit.called && facilityVisit.called.value === true);

    return (status === DistributionStatus.SYNCED) || (!facilityContacted);
  };

  $scope.showDeleteRefrigeratorConfirmationModel = function (serialNumberToDelete) {
    var dialogOpts = {
      id: "deleteRefrigeratorInfo",
      header: 'delete.refrigerator.readings.header',
      body: 'delete.refrigerator.readings.confirm'
    };

    var callback = function (serialNumberToDelete) {
      return function (result) {
        if (!result) return;
        $scope.distribution.facilityDistributions[$scope.selectedFacilityId].refrigerators.readings =
          _.reject($scope.distribution.facilityDistributions[$scope.selectedFacilityId].refrigerators.readings,
            function (refrigeratorReading) {
              return serialNumberToDelete == refrigeratorReading.refrigerator.serialNumber.value;
            });
        IndexedDB.put('distributions', $scope.distribution);
      };
    };

    OpenLmisDialog.newDialog(dialogOpts, callback(serialNumberToDelete), $dialog);
  };

  $scope.showProblemsDiv = function (idSent) {
    setTimeout(function () {
      $('body,html').animate({
        scrollTop: utils.parseIntWithBaseTen($('#' + idSent).offset().top) + 'px'
      }, 'fast');
    }, 0);
  };

  $scope.findRefrigeratorReading = function (serialNumber) {
    return _.find($scope.distribution.facilityDistributions[$scope.selectedFacilityId].refrigerators.readings,
      function (reading) {
        return reading.refrigerator.serialNumber.value.toLowerCase() === serialNumber.toLowerCase();
      });
  };
}
