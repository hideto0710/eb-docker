CREATE DATABASE eb;
CREATE USER eb;
SET PASSWORD FOR eb = PASSWORD('ebdocker');
GRANT ALL ON eb.* TO eb;