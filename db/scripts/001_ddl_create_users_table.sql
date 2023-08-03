CREATE TABLE if not exists users
	(id         serial primary key,
	 login      varchar UNIQUE      NOT NULL,
	 password   varchar             NOT NULL);