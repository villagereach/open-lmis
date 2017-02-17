/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

describe('Child coverage', function () {
  var childCoverage;

  beforeEach(function () {
    var childCoverageLineItem1, childCoverageLineItem2, openedVialLineItem1, openedVialLineItem2;

    childCoverageLineItem1 = {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", "healthCenter11Months": {value: 1},
      "maleOutreach11Months": {value: undefined}, "maleHealthCenter23Months": {value: 3}, "maleOutreach23Months": {value: undefined},
      "maleHealthCenter11Months": {value: undefined}, "maleOutreach11Months": {value: undefined}, "maleHealthCenter23Months": {value: undefined},
      "maleOutreach23Months": {value: undefined}};
    childCoverageLineItem2 = {"id": 26, "facilityVisitId": 3, "vaccination": "Polio (Newborn)", "maleHealthCenter11Months": {value: undefined},
      "maleOutreach11Months": {value: undefined}, "maleHealthCenter23Months": {value: undefined}, "maleOutreach23Months": {value: undefined},
      "maleHealthCenter11Months": {value: undefined}, "maleOutreach11Months": {value: undefined}, "maleHealthCenter23Months": {value: undefined},
      "maleOutreach23Months": {value: undefined}};

    openedVialLineItem1 = {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {value: null, notRecorded: true}};
    openedVialLineItem2 = {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {value: null, notRecorded: true}};

    childCoverage = new ChildCoverage(123, {
      childCoverageLineItems: [childCoverageLineItem1, childCoverageLineItem2],
      openedVialLineItems: [openedVialLineItem1, openedVialLineItem2]
    });

  });

  describe('Apply NR to all', function () {

    function verifyCoverageLineItemNotRecordedSet(lineItem, value) {
      expect(lineItem.maleHealthCenter11Months.notRecorded).toEqual(value);
      expect(lineItem.maleOutreach11Months.notRecorded).toEqual(value);
      expect(lineItem.maleHealthCenter23Months.notRecorded).toEqual(value);
      expect(lineItem.maleOutreach23Months.notRecorded).toEqual(value);
      expect(lineItem.femaleHealthCenter11Months.notRecorded).toEqual(value);
      expect(lineItem.femaleOutreach11Months.notRecorded).toEqual(value);
      expect(lineItem.femaleHealthCenter23Months.notRecorded).toEqual(value);
      expect(lineItem.femaleOutreach23Months.notRecorded).toEqual(value);
    }

    it('should set all NR flags to true', function () {
      childCoverage.notRecordedApplied = false;
      childCoverage.setNotRecorded();

      verifyCoverageLineItemNotRecordedSet(childCoverage.childCoverageLineItems[0], true);
      verifyCoverageLineItemNotRecordedSet(childCoverage.childCoverageLineItems[1], true);

      expect(childCoverage.openedVialLineItems[0].openedVial.notRecorded).toBeTruthy();
      expect(childCoverage.openedVialLineItems[1].openedVial.notRecorded).toBeTruthy();

      expect(childCoverage.notRecordedApplied).toBeTruthy();
    });

    it('should set all NR flags to false', function () {
      childCoverage.notRecordedApplied = true;
      childCoverage.setNotRecorded();

      verifyCoverageLineItemNotRecordedSet(childCoverage.childCoverageLineItems[0], false);
      verifyCoverageLineItemNotRecordedSet(childCoverage.childCoverageLineItems[1], false);

      expect(childCoverage.openedVialLineItems[0].openedVial.notRecorded).not.toBeTruthy();
      expect(childCoverage.openedVialLineItems[1].openedVial.notRecorded).not.toBeTruthy();

      expect(childCoverage.notRecordedApplied).not.toBeTruthy();

    });

    it('should create child coverage object from JSON', function () {
      spyOn($, 'extend');
      var childCoverageJSON = {blah: 'blah blah'};

      var childCoverage = new ChildCoverage(123, childCoverageJSON);

      expect($.extend).toHaveBeenCalledWith(true, childCoverage, childCoverageJSON);
    });
  });

  describe('compute status', function () {
    it('should set status as is-empty if form is blank', function () {
      var emptyChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG"},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio (Newborn)"}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10}
        ]
      });
      var status = emptyChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.EMPTY);
    });

    it('should set status as is-empty if no fields filled', function () {
      var unfilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", healthCenter11Months: {value: undefined}, outreach11Months: {value: undefined}, healthCenter23Months: {value: undefined}, outreach23Months: {value: undefined}},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio (Newborn)", healthCenter11Months: {value: undefined}, outreach11Months: {value: undefined}, healthCenter23Months: {value: undefined}, outreach23Months: {value: undefined}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {value: undefined}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {value: undefined}}
        ]
      });
      var status = unfilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.EMPTY);
    });

    it('should set status as is-incomplete if some child coverage fields filled', function () {
      var partiallyFilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG",  maleHealthCenter11Months: {value: 1}, maleOutreach11Months: {value: undefined},
            maleHealthCenter23Months: {value: undefined}, maleOutreach23Months: {value: undefined}, femaleHealthCenter11Months: {value: undefined},
            femaleOutreach11Months: {value: undefined}, femaleHealthCenter23Months: {value: undefined}, femaleOutreach23Months: {value: undefined}},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio (Newborn)", maleHealthCenter11Months: {value: undefined}, maleOutreach11Months: {value: undefined},
            maleHealthCenter23Months: {value: undefined}, maleOutreach23Months: {value: undefined}, femaleHealthCenter11Months: {value: undefined},
            femaleOutreach11Months: {value: undefined}, femaleHealthCenter23Months: {value: undefined}, femaleOutreach23Months: {value: undefined}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {value: undefined}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {value: undefined}}
        ]
      });
      var status = partiallyFilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.INCOMPLETE);
    });

    it('should set status as complete if all child coverage fields are filled', function () {
      var partiallyFilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", maleHealthCenter11Months: {value: 32}, maleOutreach11Months: {notRecorded: true},
            maleHealthCenter23Months: {value: 1}, maleOutreach23Months: {value: 3}, femaleHealthCenter11Months: {value: 2},
            femaleOutreach11Months: {value: 2}, femaleHealthCenter23Months: {value: 7}, femaleOutreach23Months: {value: 1}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {value: 21}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {notRecorded: true}}
        ]
      });
      var status = partiallyFilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.COMPLETE);
    });

    it('should set status as complete even if polio new born 23 month data is not filled', function () {
      var partiallyFilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", maleHealthCenter11Months: {value: 32}, maleOutreach11Months: {notRecorded: true},
            maleHealthCenter23Months: {value: 1}, maleOutreach23Months: {value: 3}, femaleHealthCenter11Months: {value: 2},
            femaleOutreach11Months: {value: 2}, femaleHealthCenter23Months: {value: 7}, femaleOutreach23Months: {value: 1}},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio (Newborn)", maleHealthCenter11Months: {value: 32}, maleOutreach11Months: {notRecorded: true},
             maleHealthCenter23Months: {value: undefined}, maleOutreach23Months: {value: undefined}, femaleHealthCenter11Months: {value: 2},
             femaleOutreach11Months: {value: 2}, femaleHealthCenter23Months: {value: undefined}, femaleOutreach23Months: {value: undefined}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {value: 21}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {notRecorded: true}}
        ]
      });
      var status = partiallyFilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.COMPLETE);
    });

    it('should set status as complete if all field are not recorded', function () {
      var partiallyFilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", healthCenter11Months: {notRecorded: true}, outreach11Months: {notRecorded: true}, healthCenter23Months: {notRecorded: true}, outreach23Months: {notRecorded: true}},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio10", healthCenter11Months: {notRecorded: true}, outreach11Months: {notRecorded: true}, healthCenter23Months: {notRecorded: true}, outreach23Months: {notRecorded: true}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {notRecorded: true}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {notRecorded: true}}
        ]
      });
      var status = partiallyFilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.COMPLETE);
    });

    it('should set status as incomplete if some opened vial not filled', function () {
      var partiallyFilledChildCoverage = new ChildCoverage(12, {
        childCoverageLineItems: [
          {"id": 5, "facilityVisitId": 3, "vaccination": "BCG", maleHealthCenter11Months: {notRecorded: true}, maleOutreach11Months: {notRecorded: true},
            maleHealthCenter23Months: {notRecorded: true}, maleOutreach23Months: {notRecorded: true}, femaleHealthCenter11Months: {notRecorded: true},
            femaleOutreach11Months: {notRecorded: true}, femaleHealthCenter23Months: {notRecorded: true}, femaleOutreach23Months: {notRecorded: true}},
          {"id": 26, "facilityVisitId": 3, "vaccination": "Polio10", maleHealthCenter11Months: {notRecorded: true}, maleOutreach11Months: {notRecorded: true},
            maleHealthCenter23Months: {notRecorded: true}, maleOutreach23Months: {notRecorded: true}, femaleHealthCenter11Months: {notRecorded: true},
            femaleOutreach11Months: {notRecorded: true}, femaleHealthCenter23Months: {notRecorded: true}, femaleOutreach23Months: {notRecorded: true}}
        ],
        openedVialLineItems: [
          {"id": 15, "facilityVisitId": 3, "productVialName": "BCG", "packSize": 10, openedVial: {}},
          {"id": 16, "facilityVisitId": 3, "productVialName": "Polio10", "packSize": 10, openedVial: {}}
        ]
      });
      var status = partiallyFilledChildCoverage.computeStatus();
      expect(status).toEqual(DistributionStatus.INCOMPLETE);
    });
  });
});