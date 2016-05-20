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
This table is used to track vaccines, and previously did so using vials (ie: packs) as the unit. Now, however, we’re
going to start asking people to use doses as the unit. Because the number of doses in a vial/pack can potentially
change overtime, we need to record the packSize/vialSize along with the number of vials. Together, these two values
allow us to determine the number of vials that we existing, spoiled, or delivered at a given point in time.
 */
ALTER TABLE epi_inventory_line_items ADD COLUMN packSize INTEGER;
