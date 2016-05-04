CREATE TABLE account
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(500) NOT NULL,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
  CONSTRAINT account_mail_address_key UNIQUE (email)
);