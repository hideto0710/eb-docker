# --- !Ups

create table account
(
  id bigint not null auto_increment primary key,
  email varchar(500) not null,
  password varchar(100) not null,
  created_at timestamp not null default current_timestamp,
  constraint account_mail_address_key unique (email)
);

insert into account(email, password) values ('h.inamura0710@gmail.com', 'f39c9a429682bbbbe35669c7d2b97aaa5e06f29328b8fc90d7f0d078110e95a9');

# --- !Downs

drop table account;
