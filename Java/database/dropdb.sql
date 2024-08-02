SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'twisdale_databook';

DROP DATABASE twisdale_databook;

DROP USER db_admin;
DROP USER db_user;
