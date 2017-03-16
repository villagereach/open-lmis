/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

describe('ViewLoadAmountController', function () {
  var scope, controller, httpBackend, program1, facilities;

  beforeEach(module('openlmis'));
  beforeEach(inject(function ($rootScope, $controller, $httpBackend) {
    scope = $rootScope.$new();
    controller = $controller;
    httpBackend = $httpBackend;
    program1 = {id: 1, name: 'Vaccine'};
    isa = {
      whoRatio: 1,
      adjustmentValue: 33,
      dosesPerYear: 45,
      wastageFactor: 1,
      bufferPercentage: 7,
      minimumValue: 20
    }
    var programProducts = [
      {product: {id: 1, name: 'polio10', productGroup: {name: 'polio'}, packSize: 1}, programProductIsa: isa},
      {product: {id: 2, name: 'polio20', productGroup: {name: 'polio'}, packSize: 1}, programProductIsa: isa},
      {product: {id: 3, name: 'penta1', productGroup: {name: 'penta'}, packSize: 1}, programProductIsa: isa},
      {product: {id: 4, name: 'blank', productGroup: {name: ''}}}
    ];
    facilities = [
      {id: 'F10', name: 'Village Dispensary', geographicZone: {id: 1, name: 'Ngrogoro', level: {name: 'City' }, parent: { name: 'Monduli' }}, catchmentPopulation: 200,
        supportedPrograms: [
          {program: program1, programProducts: programProducts}
        ]},
      {id: 'F11', name: 'Central Hospital', geographicZone: {id: 1, name: 'District 1', level: {name: 'City' }, parent: { name: 'District 2' }}, catchmentPopulation: 150,
        supportedPrograms: [
          {program: program1, programProducts: programProducts}
        ]}
    ];
    distributions = [
      {id: 522, facilityDistributions: {
        963: { geographicZone: "Ngrogoro", facilityName: 'Village Dispensary',
          epiUse: {
            lineItems: [
              {distributed: {notRecorded: false, value: 10}, loss: {notRecorded: false, value: 20}, productGroup: {name: 'polio'}},
              {distributed: {notRecorded: true}, loss: {notRecorded: true}, productGroup: {name: 'penta'}}
            ]
           }
        },
        964: { geographicZone: 'District 1', facilityName: 'Central Hospital',
          epiUse: {
            lineItems: [
              {distributed: {notRecorded: false, value: 10}, loss: {notRecorded: false, value: 5}, productGroup: {name: 'polio'}},
              {distributed: {notRecorded: true}, loss: {notRecorded: true}, productGroup: {name: 'penta'}}
            ]
          }
        }
      }},
      {id: 523, facilityDistributions: {
        963: { geographicZone: "Ngrogoro", facilityName: 'Village Dispensary',
          epiUse: {
            lineItems: [
              {distributed: {notRecorded: false, value: 20}, loss: {notRecorded: false, value: 30}, productGroup: {name: 'polio'}},
              {distributed: {notRecorded: true}, loss: {notRecorded: true}, productGroup: {name: 'penta'}}
            ]
           }
        },
        964: { geographicZone: 'District 1', facilityName: 'Central Hospital',
          epiUse: {
            lineItems: [
              {distributed: {notRecorded: true}, loss: {notRecorded: true}, productGroup: {name: 'polio'}},
              {distributed: {notRecorded: true}, loss: {notRecorded: true}, productGroup: {name: 'penta'}}
            ]
          }
        }
      }},
    ];
    controller(ViewLoadAmountController, {$scope: scope, facilities: facilities, period: {id: 1, name: 'period 1', numberOfMonths: 1}, deliveryZone: {id: 1}, fridges: {}, nexleafDeliveryZones: [], previousDistributions: distributions});

  }));

  it('should set no records found message if no facilities are found', function () {
    controller(ViewLoadAmountController, {$scope: scope, facilities: [], period: {}, deliveryZone: {}, fridges: {}, nexleafDeliveryZones: [], previousDistributions: {}});
    expect(scope.message).toEqual("msg.no.records.found");
  });

  it('should set no records found message if no facilities are undefined', function () {
    controller(ViewLoadAmountController, {$scope: scope, facilities: undefined, period: {}, deliveryZone: {}, fridges: {}, nexleafDeliveryZones: [], previousDistributions: {}});
    expect(scope.message).toEqual("msg.no.records.found");
  });

  it('should set Geographic zone level name', function () {
    expect(scope.geoZoneLevelName).toEqual('City');
  });

  it('should set program name', function () {
    expect(scope.program).toEqual(program1);
  });

  it('should set period', function () {
    expect(scope.period).toEqual({id: 1, name: 'period 1', numberOfMonths: 1});
  });

  it('should group program products of each facility by product group name', function () {
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['polio'].products.length).toEqual(2);
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['penta'].products.length).toEqual(1);
  });

  it('should sort program products of each facility by product group name', function () {
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].sortedProductGroup).toEqual(['penta', 'polio', '']);
  });

  it('should sort facilities geo zones on the basis of geo zone name', function () {
    expect(scope.sortedGeoZoneKeys).toEqual(['District 1', 'Ngrogoro']);
  });

  it('should get all program products of all product groups in facility in order by product group name as an array and push blank to last', function () {
    var programProducts = scope.getProgramProducts(facilities[0]);
    expect(programProducts.length).toEqual(4);
    expect(programProducts[0].product.name).toEqual('penta1');
    expect(programProducts[1].product.name).toEqual('polio10');
    expect(programProducts[2].product.name).toEqual('polio20');
    expect(programProducts[3].product.name).toEqual('blank');
  });


  it('should calculate total population of all geo zones', function () {
    expect(scope.zonesTotal['totalPopulation']).toEqual(350);
  });

  it('should calculate total population by each geo zone for all geo zones', function () {
    expect(scope.aggregateMap['Ngrogoro']['totalPopulation']).toEqual(200);
    expect(scope.aggregateMap['District 1']['totalPopulation']).toEqual(150);
  });

  it('should calculate isa', function () {
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['polio'].products[0].isaAmount).toEqual(42);
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['polio'].products[1].isaAmount).toEqual(42);
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['penta'].products[0].isaAmount).toEqual(42);

    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['polio'].products[0].isaAmount).toEqual(40);
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['polio'].products[1].isaAmount).toEqual(40);
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['penta'].products[0].isaAmount).toEqual(40);
  });

  it('should calculate consumption sum for all facilities', function () {
    expect(scope.facilityAmcMap['Village Dispensary'].productGroups['polio'].amcSum).toEqual(80);
    expect(scope.facilityAmcMap['Village Dispensary'].productGroups['polio'].availablePeriodsAmount).toEqual(2);
    expect(scope.facilityAmcMap['Village Dispensary'].productGroups['penta'].amcSum).toEqual(0);
    expect(scope.facilityAmcMap['Village Dispensary'].productGroups['penta'].availablePeriodsAmount).toEqual(0);

    expect(scope.facilityAmcMap['Central Hospital'].productGroups['polio'].amcSum).toEqual(15);
    expect(scope.facilityAmcMap['Central Hospital'].productGroups['polio'].availablePeriodsAmount).toEqual(1);
    expect(scope.facilityAmcMap['Central Hospital'].productGroups['penta'].amcSum).toEqual(0);
    expect(scope.facilityAmcMap['Central Hospital'].productGroups['penta'].availablePeriodsAmount).toEqual(0);
  });

  it('should calculate amc', function () {
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['polio'].amcValue).toEqual(40);
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['polio'].overwrittenByIsa).toEqual(false);
  });

  it('should set isa value if amc is not available', function () {
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['penta'].amcValue).toEqual(42);
    expect(scope.facilityMap['Ngrogoro'][0].supportedPrograms[0].programProductMap['penta'].overwrittenByIsa).toEqual(true);
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['penta'].amcValue).toEqual(40);
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['penta'].overwrittenByIsa).toEqual(true);
  });

  it('should set minimum isa if amc is lower', function () {
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['polio'].amcValue).toEqual(40);
    expect(scope.facilityMap['District 1'][0].supportedPrograms[0].programProductMap['polio'].overwrittenByIsa).toEqual(false);
  });

  it('should get program products for total of zones', function () {
    var product1 = {
      name: "product1",
      productGroup: {
        name: 'group1'
      }
    };
    scope.zonesTotal = {
      totalProgramProductsMap: {
        group1: {
          products: [
            {
              product: product1
            },
            {
              product: {
                productGroup: {
                  name: 'group1'
                }
              }
            }
          ],
        },
        group2: {
          products: [
            {
              product: {
                productGroup: {
                  name: 'group2'
                }
              }
            }
          ]
        },
        group3: {
          products: [
            {
              product: {
                productGroup: {
                  name: 'group3'
                }
              }
            }
          ]
        }
      }
    };
    scope.sortedGeoZoneKeys = ['zone1', 'zone2'];
    scope.aggregateMap = {zone1: {
      sortedProductGroup: [ "group1", "group2", "group3"]
    }};

    var programProducts = scope.getProgramProductsForAggregateRow(null, scope.zonesTotal);

    expect(programProducts.length).toEqual(4);
    expect(programProducts[0]).toEqual({product: product1});
  });
});

describe("View load amount resolve", function () {
  var $httpBackend, ctrl, $timeout, $route, $q;
  var deferredObject;
  beforeEach(module('openlmis'));
  beforeEach(inject(function (_$httpBackend_, $controller, _$timeout_, _$route_) {
    $httpBackend = _$httpBackend_;
    deferredObject = {promise: {id: 1}, resolve: function () {
    }};
    spyOn(deferredObject, 'resolve');
    $q = {defer: function () {
      return deferredObject
    }};
    $timeout = _$timeout_;
    ctrl = $controller;
    $route = _$route_;
  }));

  it('should get all facilities with their Isa amount in delivery zone', function () {
    $route = {current: {params: {deliveryZoneId: 1, programId: 2}}};
    $httpBackend.expect('GET', '/deliveryZones/1/programs/2/facilities.json').respond(200);

    ctrl(ViewLoadAmountController.resolve.facilities, {$q: $q, $route: $route});

    $timeout.flush();
    $httpBackend.flush();
    expect(deferredObject.resolve).toHaveBeenCalled();
  });

  it('should get period reference data', function () {
    $route = {current: {params: {periodId: 2}}};
    $httpBackend.expect('GET', '/periods/2.json').respond(200);
    ctrl(ViewLoadAmountController.resolve.period, {$q: $q, $route: $route});
    $timeout.flush();
    $httpBackend.flush();
    expect(deferredObject.resolve).toHaveBeenCalled();
  });

  it('should get period reference data', function () {
    $route = {current: {params: {deliveryZoneId: 2}}};
    $httpBackend.expect('GET', '/deliveryZones/2.json').respond(200);
    ctrl(ViewLoadAmountController.resolve.deliveryZone, {$q: $q, $route: $route});
    $timeout.flush();
    $httpBackend.flush();
    expect(deferredObject.resolve).toHaveBeenCalled();
  });


});
