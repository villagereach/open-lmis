--
-- This program is part of the OpenLMIS logistics management information system platform software.
-- Copyright © 2013 VillageReach
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
-- You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
--

/*
See SIIL-5 and SIIL-34. This change fixes a problem that kept packSize from being included within
adultCoverage.openedVialLineItems entries, and which thereby precluded WastageRates from being calculated.
This problem has been a recurring one, and the fix seems appropriate to include within a SQL migration
script because our Java hardcodes the value of “Tetanus.” The change made below is thus code related as much
or more than configuration-related.
*/

UPDATE coverage_product_vials
SET vial = 'Tetanus'
WHERE vial = 'TT2';
