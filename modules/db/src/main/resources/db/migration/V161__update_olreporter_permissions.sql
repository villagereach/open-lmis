
REVOKE CREATE ON SCHEMA public FROM reporting_role;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO reporting_role;
