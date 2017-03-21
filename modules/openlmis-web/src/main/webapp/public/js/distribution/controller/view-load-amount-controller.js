/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function ViewLoadAmountController($scope, facilities, period, deliveryZone, fridges, nexleafDeliveryZones, previousDistributions) {
  if (!isUndefined(facilities) && facilities.length > 0) {
    $scope.calculationMethod = 'amc';
    $scope.message = "";
    $scope.program = facilities[0].supportedPrograms[0].program;
    $scope.period = period;
    $scope.deliveryZone = deliveryZone;
    $scope.facilities = facilities;
    var otherGroupName = "";
    $scope.geoZoneLevelName = facilities[0].geographicZone.level.name;

    $scope.provincesForColdChainStatus = nexleafDeliveryZones;
    $scope.viewColdChainStatusAvailable = _.contains(nexleafDeliveryZones, deliveryZone.code);

    $scope.aggregateMap = {};
    $scope.facilityAmcMap = {};
    if ($scope.viewColdChainStatusAvailable) {
      if (!fridges.coldTraceData) {
        $scope.apimessage = "message.api.error";
      } else if (fridges.coldTraceData) {
        $scope.fridges = fridges.coldTraceData.fridges;

        $scope.getFacitilityStatus = function (code) {
          if (!isUndefined(code)) {
              for (var i = 0; i < $scope.fridges.length; i++) {
                  var fridge = $scope.fridges[i];
                  if (fridge.FacilityID == code) {
                      return fridge.Status;
                  }
              }
          }
          return 1;
        };

        $scope.isDataAvailable = function (code) {
            if (!isUndefined(code)) {
                for (var i = 0; i < $scope.fridges.length; i++) {
                    var fridge = $scope.fridges[i];
                    if (fridge.FacilityID == code && fridge.Status !== 0) {
                        return true;
                    }
                }
            }
            $scope.apimessage = "message.api.error.no.data";
            return false;
        };

        $scope.getURL = function (code) {
          if (!isUndefined(code)) {
              for (var i = 0; i < $scope.fridges.length; i++) {
                  var fridge = $scope.fridges[i];
                  if (fridge.FacilityID == code) {
                      return fridge.URL;
                  }
              }
          }
          return ".";
        };
      }
    }

    handlePreviousDistributionsForAmc(previousDistributions);

    $(facilities).each(function (i, facility) {
      var totalForGeoZone = $scope.aggregateMap[facility.geographicZone.name];
      if (isUndefined(totalForGeoZone)) {
        totalForGeoZone = {totalPopulation: "--"};
        $scope.aggregateMap[facility.geographicZone.name] = totalForGeoZone;
      }
      var totalPopulation = totalForGeoZone.totalPopulation;
      if (!isNaN(utils.parseIntWithBaseTen(facility.catchmentPopulation))) {
        totalPopulation = calculateTotalForPopulation(facility.catchmentPopulation, totalPopulation);
      } else {
        facility.catchmentPopulation = "--";
      }
      totalForGeoZone.totalPopulation = totalPopulation;

      var programProductsWithISA = [];
      $(facility.supportedPrograms[0].programProducts).each(function (j, programProduct) {
        var programProductWithISA = new ProgramProduct(programProduct);
        programProductWithISA.calculateISA(facility, period);
        programProductsWithISA.push(programProductWithISA);
      });

      facility.supportedPrograms[0].programProducts = programProductsWithISA;

      facility.supportedPrograms[0].programProductMap = ProgramProduct.groupProductsMapByName(facility, otherGroupName);
      transformProductsMap(facility.supportedPrograms[0].programProductMap);
      calculateAmcValues(facility.name, facility.supportedPrograms[0].programProductMap);

      facility.supportedPrograms[0].sortedProductGroup = _.sortBy(_.keys(facility.supportedPrograms[0].programProductMap), function (key) {
        return key;
      });

      var totalForProducts = $scope.aggregateMap[facility.geographicZone.name].totalProgramProductsMap;
      if (isUndefined(totalForProducts)) {
        totalForProducts = {};
        $scope.aggregateMap[facility.geographicZone.name].totalProgramProductsMap = totalForProducts;
      }

      $(facility.supportedPrograms[0].sortedProductGroup).each(function (index, productGroup) {
        calculateTotalForEachFacilityGroupedByProductGroup(totalForProducts, productGroup, facility);
      });

      $scope.aggregateMap[facility.geographicZone.name].totalProgramProductsMap = totalForProducts;
      pushBlankProductGroupToLast(facility);

      $scope.aggregateMap[facility.geographicZone.name].sortedProductGroup = facility.supportedPrograms[0].sortedProductGroup;
    });


    $scope.facilityMap = _.groupBy(facilities, function (facility) {
      return facility.geographicZone.name;
    });

    $scope.sortedGeoZoneKeys = _.sortBy(_.keys($scope.facilityMap), function (key) {
      return key;
    });

    $($scope.sortedGeoZoneKeys).each(function (i, geoZoneKey) {
      var totalPopulation = 0;
      var facilities = $scope.facilityMap[geoZoneKey];
      $(facilities).each(function (j, facility) {
        totalPopulation += facility.catchmentPopulation;
      });
    });

    calculateTotalForGeoZoneParent();
  } else {
    $scope.message = "msg.no.records.found";
  }

  $scope.getProgramProducts = function (facility) {
    if (!isUndefined(facility)) {
      var programProducts = [];
      $(facility.supportedPrograms[0].sortedProductGroup).each(function (index, sortedProductGroupKey) {
        programProducts = programProducts.concat(facility.supportedPrograms[0].programProductMap[sortedProductGroupKey].products);
      });
      return programProducts;
    }
  };

  $scope.getProgramProductGroups = function (includeOtherGroup) {
    if (includeOtherGroup) {
      return $scope.facilityMap[$scope.sortedGeoZoneKeys[0]][0].supportedPrograms[0].sortedProductGroup;
    } else {
      return _.without($scope.facilityMap[$scope.sortedGeoZoneKeys[0]][0].supportedPrograms[0].sortedProductGroup, otherGroupName);
    }
  };

  $scope.getProgramProductsForAggregateRow = function (geoZoneName, zonesTotal) {
    var programProducts = [];
    if (!zonesTotal) {
      $($scope.aggregateMap[geoZoneName].sortedProductGroup).each(function (index, sortedProductGroupKey) {
        programProducts = programProducts.concat($scope.aggregateMap[geoZoneName].totalProgramProductsMap[sortedProductGroupKey].products);
      });
    } else {
      $($scope.aggregateMap[$scope.sortedGeoZoneKeys[0]].sortedProductGroup).each(function (index, sortedProductGroupKey) {
        programProducts = programProducts.concat($scope.zonesTotal.totalProgramProductsMap[sortedProductGroupKey].products);
      });
    }
    return programProducts;
  };

  function calculateTotalForPopulation(population, presentTotalPopulation) {
    if (presentTotalPopulation == "--") {
      return  population;
    } else {
      return presentTotalPopulation + population;
    }
  }

  function calculateTotalForGeoZoneParent() {
    $scope.zonesTotal = {totalPopulation: "--", totalProgramProductsMap: {}};
    $($scope.sortedGeoZoneKeys).each(function (i, geoZoneKey) {
      if (!isNaN(utils.parseIntWithBaseTen($scope.aggregateMap[geoZoneKey].totalPopulation))) {
        var population = calculateTotalForPopulation($scope.aggregateMap[geoZoneKey].totalPopulation,
            $scope.zonesTotal.totalPopulation);
        $scope.zonesTotal.totalPopulation = population;
      }
      $($scope.aggregateMap[geoZoneKey].sortedProductGroup).each(function (index, sortedProductGroupKey) {
        var totalForGroup = $scope.zonesTotal.totalProgramProductsMap[sortedProductGroupKey];
        if (isUndefined(totalForGroup)) {
          totalForGroup = {products: [], amcValue: 0};
        }

        totalForGroup.amcValue += $scope.aggregateMap[geoZoneKey].totalProgramProductsMap[sortedProductGroupKey].amcValue;
        $($scope.aggregateMap[geoZoneKey].totalProgramProductsMap[sortedProductGroupKey].products).each(function (index, aggregateProduct) {
          var productTotal = _.find(totalForGroup.products, function (totalProduct) {
            return totalProduct.code == aggregateProduct.product.code;
          });
          if (productTotal) {
            ProgramProduct.calculateProductIsaTotal(aggregateProduct, productTotal);

          } else {
            totalForGroup.products.push({code: aggregateProduct.product.code, isaAmount: aggregateProduct.isaAmount});
            $scope.zonesTotal.totalProgramProductsMap[sortedProductGroupKey] = totalForGroup;
          }
        });
      });
    });
  }

  function calculateTotalForEachFacilityGroupedByProductGroup(totalForProducts, productGroup, facility) {
    var total = totalForProducts[productGroup] || {products: [], amcValue: 0};
    var products = facility.supportedPrograms[0].programProductMap[productGroup].products;

    total.amcValue += facility.supportedPrograms[0].programProductMap[productGroup].amcValue;
    $(products).each(function (index, programProduct) {
      var existingTotal = _.find(total.products, function (totalProduct) {
        return totalProduct.product.code == programProduct.product.code;
      });

      if (existingTotal) {
        ProgramProduct.calculateProductIsaTotal(programProduct, existingTotal);
      } else {
        total.products.push({product: {code: programProduct.product.code}, isaAmount: programProduct.isaAmount});
      }

    });
    totalForProducts[productGroup] = total;
  }

  function calculateAmcValues(facilityName, programProductMap) {
    $(_.keys(programProductMap)).each(function (i, groupName) {
      var facilityAmcMapEntry = $scope.facilityAmcMap[facilityName],
          programProductGroup = programProductMap[groupName];
          overwriteByIsa = true;

      if (!isUndefined(facilityAmcMapEntry) && !isUndefined(facilityAmcMapEntry.productGroups)) {
        var groupEntry = facilityAmcMapEntry.productGroups[groupName];

        if (!isUndefined(groupEntry) && groupEntry.availablePeriodsAmount > 0) {
          overwriteByIsa = false;
          programProductGroup.amcValue = Math.round(groupEntry.amcSum / groupEntry.availablePeriodsAmount);
        }
      }

      if (overwriteByIsa) {
        programProductGroup.amcValue = calculateIsaSumForProductGroup(programProductGroup.products);
        programProductGroup.overwrittenByIsa = true;
      }

      var isaMinimumSum = calculateIsaMinimumSumForProductGroup(programProductGroup.products);
      if (isaMinimumSum > programProductGroup.amcValue) {
        programProductGroup.amcValue = isaMinimumSum;
      }
    });
  }

  function calculateIsaMinimumSumForProductGroup(programGroupProducts) {
    var isaMinimumSum = 0;

    $(programGroupProducts).each(function (i, programGroupProduct) {
      if (!isUndefined(programGroupProduct.programProductIsa) && !isNaN(programGroupProduct.programProductIsa.minimumValue)) {
        isaMinimumSum += programGroupProduct.programProductIsa.minimumValue;
      }
    });
    return isaMinimumSum;
  }

  function calculateIsaSumForProductGroup(programGroupProducts) {
    var isaSum = 0;

    $(programGroupProducts).each(function (i, programGroupProduct) {
      if (!isNaN(programGroupProduct.isaAmount)) {
        isaSum += programGroupProduct.isaAmount;
      }
    });
    return isaSum;
  }

  function calculateConsumptionSum(productGroupAmcData, lineItem) {
    if (!lineItem.distributed.notRecorded || !lineItem.loss.notRecorded) {
      productGroupAmcData.availablePeriodsAmount++;

      if (!isNaN(lineItem.distributed.value)) {
        productGroupAmcData.amcSum += lineItem.distributed.value;
      }

      if (!isNaN(lineItem.loss.value)) {
        productGroupAmcData.amcSum += lineItem.loss.value;
      }
    }
  }

  function transformProductsMap(programProductsMap) {
    $(_.keys(programProductsMap)).each(function (i, key) {
      var products = programProductsMap[key];

      programProductsMap[key] = {
        products: products,
        amcValue: 0,
        overwrittenByIsa: false
      };
    });
  }

  function handlePreviousDistributionsForAmc(previousDistributions) {
    if (!isUndefined(previousDistributions) && previousDistributions.length > 0) {
      $(previousDistributions).each(function (i, distribution) {
        $(_.keys(distribution.facilityDistributions)).each(function (i, key) {
          var facilityDistribution = distribution.facilityDistributions[key];
          $(facilityDistribution.epiUse.lineItems).each(function (i, lineItem) {
            var facilityDistributionData = $scope.facilityAmcMap[facilityDistribution.facilityName];
            if (isUndefined(facilityDistributionData)) {
              facilityDistributionData = {
                facilityName: facilityDistribution.facilityName,
                geographicZone: facilityDistribution.geographicZone,
                productGroups: {}
              };
            }

            var productGroupAmcData = facilityDistributionData.productGroups[lineItem.productGroup.name];
            if (isUndefined(productGroupAmcData)) {
              productGroupAmcData = {amcSum: 0, availablePeriodsAmount: 0};
            }

            calculateConsumptionSum(productGroupAmcData, lineItem);
            facilityDistributionData.productGroups[lineItem.productGroup.name] = productGroupAmcData;
            $scope.facilityAmcMap[facilityDistribution.facilityName] = facilityDistributionData;
          });
        });
      });
    }
  }

  function pushBlankProductGroupToLast(facility) {
    if (_.indexOf(facility.supportedPrograms[0].sortedProductGroup, otherGroupName) > -1) {
      facility.supportedPrograms[0].sortedProductGroup = _.without(facility.supportedPrograms[0].sortedProductGroup, otherGroupName);
      facility.supportedPrograms[0].sortedProductGroup.push(otherGroupName);
    }
  }
}

ViewLoadAmountController.resolve = {
  facilities: function (DeliveryZoneFacilities, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      DeliveryZoneFacilities.get({deliveryZoneId: $route.current.params.deliveryZoneId, programId: $route.current.params.programId}, function (data) {
        deferred.resolve(data.facilities);
      }, {});
    }, 100);

    return deferred.promise;
  },

  period: function (Period, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      Period.get({id: $route.current.params.periodId}, function (data) {
        deferred.resolve(data.period);
      }, {});
    }, 100);

    return deferred.promise;
  },

  deliveryZone: function (DeliveryZone, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      DeliveryZone.get({id: $route.current.params.deliveryZoneId}, function (data) {
        deferred.resolve(data.zone);
      }, {});
    }, 100);

    return deferred.promise;
  },

  fridges: function (Fridges, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      Fridges.get({deliveryZoneId: $route.current.params.deliveryZoneId}, function (data) {
        deferred.resolve(data);
      }, {});
    }, 100);

    return deferred.promise;
  },

  nexleafDeliveryZones: function (NexleafDeliveryZones, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      NexleafDeliveryZones.get(function (data) {
        deferred.resolve(data.deliveryZones);
      }, function (data) {
        deferred.resolve([]);
      });
    }, 100);

    return deferred.promise;
  },

  previousDistributions: function (PreviousDistributions, $route, $timeout, $q) {
    var deferred = $q.defer();
    $timeout(function () {
      PreviousDistributions.get({deliveryZoneId: $route.current.params.deliveryZoneId, programId: $route.current.params.programId,
                                 currentPeriodId: $route.current.params.periodId, n: 3}, function (data) {
        deferred.resolve(data.distributions);
      }, {});
    }, 100);

    return deferred.promise;
  },
};
