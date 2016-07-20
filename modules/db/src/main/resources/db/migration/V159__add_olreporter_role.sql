CREATE ROLE reporting_role;
 
GRANT SELECT ON TABLE facility_visits TO reporting_role;
GRANT SELECT ON TABLE facilities TO reporting_role;
GRANT SELECT ON TABLE geographic_zones TO reporting_role;
GRANT SELECT ON TABLE delivery_zones TO reporting_role;
GRANT SELECT ON TABLE processing_periods TO reporting_role;
GRANT SELECT ON TABLE distributions TO reporting_role;
GRANT SELECT ON TABLE refrigerator_problems TO reporting_role;
GRANT SELECT ON TABLE refrigerator_readings TO reporting_role;
/*GRANT SELECT ON TABLE vrmis_refrig_readings TO reporting_role;*/
 
CREATE USER olreporter with password 'changeme';
GRANT reporting_role TO olreporter;