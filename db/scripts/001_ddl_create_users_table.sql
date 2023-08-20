CREATE TABLE if not exists users
	(id         serial primary key,
	 email      varchar UNIQUE      NOT NULL,
	 password   varchar             NOT NULL,
	 phone_number varchar           NOT NULL
	 );