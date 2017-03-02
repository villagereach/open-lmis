/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function ReviewDataController($scope, SynchronizedDistributions, ReviewDataFilters, distributionService, $http, $location, SharedDistributions, $rootScope, messageService, $dialog, $window) {
  var empty = {};

  $scope.message = '';
  $scope.error = '';

  $scope.reload = function() {
    window.location.reload();
  };

  $scope.close = function() {
    $rootScope.appCacheState = undefined;
  };

  $scope.filters = ReviewDataFilters.get(empty, function () {
    $scope.filters.selected = {
      order: {
        column: 'synchronized',
        descending: true
      }
    };
  });

  $scope.reloadList = function () {
    $scope.distributionsList = SynchronizedDistributions.get(empty, $scope.filters.selected, undefined, $scope.onFailure);
  };

  $scope.sort = function (column) {
    if ($scope.filters.selected.order.column === column) {
      $scope.filters.selected.order.descending = !$scope.filters.selected.order.descending;
    } else {
      $scope.filters.selected.order.column = column;
      $scope.filters.selected.order.descending = true;
    }

    $scope.reloadList();
  };

  $scope.isSort = function (column) {
    if ($scope.filters.selected && $scope.filters.selected.order.column === column) {
      if ($scope.filters.selected.order.descending) {
        return 'icon-angle-down';
      } else {
        return 'icon-angle-up';
      }
    }

    return 'hide';
  };

  $scope.cacheData = function (item, action) {
    var distribution = {
          deliveryZone: item.deliveryZone,
          program: $scope.filters.selected.program,
          period: item.period
        };

    function goTo(distribution) {
      distributionService.distributionReview = {
        edit: item.edit,
        view: item.view,
        editMode: {}
      };

      $.each(distribution.facilityDistributions, function (facilityId) {
        distributionService.distributionReview.editMode[facilityId] = {};
      });

      $http.post('/review-data/distribution/lastViewed', distribution.id);
      $location.path('/record-facility-data/' + distribution.id + '/');
    }

    function onSuccess(data, status) {
      var message = data.success;
      distribution = data.distribution;

      if (!distribution.facilityDistributions) {
        $scope.message = messageService.get("message.no.facility.available", program.name, deliveryZone.name);
        return;
      }

      distributionService.save(distribution, true);
      $scope.message = message;
      goTo(distribution);
    }

    function getDistribution() {
      if (!distributionService.isCached(distribution)) {
        $http.post('/review-data/distribution/get.json', distribution).success(onSuccess).error($scope.onFailure);
      } else {
        sachedDistribution = distributionService.get(distribution);

        if (SharedDistributions.isReview(sachedDistribution)) {
            goTo(sachedDistribution);
        } else {
            distributionService.deleteDistribution(sachedDistribution.id);
            $http.post('/review-data/distribution/get.json', distribution).success(onSuccess).error($scope.onFailure);
        }
      }
    }

    function dialogCallback(result) {
      if (result) {
        getDistribution();
      }
    }

    function onCheckSuccess(data) {
      if (data.inProgress) {
        var dialogOpts = {
          id: 'distributionInProgress',
          header: 'label.distribution.in.progress',
          body: messageService.get('message.distribution.already.edit', data.inProgress.user.userName, data.inProgress.startedAt)
        };

        OpenLmisDialog.newDialog(dialogOpts, dialogCallback, $dialog);
      } else {
        getDistribution();
      }
    }

    $http.post('/review-data/distribution/check.json', distribution).success(onCheckSuccess).error($scope.onFailure);
  };

  $scope.getPDF = function (distributionId) {
    $window.open('/review-data/distribution/' + distributionId + '/pdf', '_blank');
  };

  $scope.onFailure = function (data) {
    $scope.error = data.error ? data.error : messageService.get("error.network.check");
  }
}

