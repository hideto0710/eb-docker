# --- !Ups

CREATE TABLE account
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(500) NOT NULL,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
  CONSTRAINT account_mail_address_key UNIQUE (email)
);

INSERT INTO account(email, password) VALUES ('h.inamura0710@gmail.com', 'f39c9a429682bbbbe35669c7d2b97aaa5e06f29328b8fc90d7f0d078110e95a9');

# --- !Downs

DROP TABLE account;
