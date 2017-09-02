--
-- This program is part of the OpenLMIS logistics management information system platform software.
-- Copyright © 2013 VillageReach
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
-- You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
--

ALTER TABLE refrigerators ADD COLUMN type VARCHAR(20);
ALTER TABLE refrigerator_readings ADD COLUMN refrigeratorType VARCHAR(20);

ALTER TABLE refrigerators ALTER COLUMN brand TYPE varchar(30);
ALTER TABLE refrigerator_readings ALTER COLUMN refrigeratorbrand TYPE varchar(30);

UPDATE refrigerators SET type = brand;
UPDATE refrigerators SET brand = serialNumber;

UPDATE refrigerator_readings SET refrigeratorType = refrigeratorBrand;
UPDATE refrigerator_readings SET refrigeratorBrand = refrigeratorSerialNumber;






