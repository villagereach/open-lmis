--
-- This program is part of the OpenLMIS logistics management information system platform software.
-- Copyright © 2013 VillageReach
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
-- You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
--

ALTER TABLE refrigerator_readings ADD COLUMN hasMonitoringDevice VARCHAR(1);
ALTER TABLE refrigerator_readings ADD COLUMN monitoringDeviceType VARCHAR(50);
ALTER TABLE refrigerator_readings ADD COLUMN monitoringDeviceOtherType VARCHAR(140);
ALTER TABLE refrigerator_readings ADD COLUMN temperatureReportingForm VARCHAR(1);
ALTER TABLE refrigerator_readings ADD COLUMN highestTemperatureReported NUMERIC(2);
ALTER TABLE refrigerator_readings ADD COLUMN lowestTemperatureReported NUMERIC(2);
ALTER TABLE refrigerator_readings ADD COLUMN problemOccurredDate TIMESTAMP;
ALTER TABLE refrigerator_readings ADD COLUMN problemReportedDate TIMESTAMP;
ALTER TABLE refrigerator_readings ADD COLUMN equipmentRepaired VARCHAR(1);
ALTER TABLE refrigerator_readings ADD COLUMN equipmentRepairedDate TIMESTAMP;
ALTER TABLE refrigerator_readings ADD COLUMN totalDaysCceUptime NUMERIC(2);
