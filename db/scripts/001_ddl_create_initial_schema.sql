CREATE TABLE if not exists auto_user
	(id         serial primary key,
	 login      varchar UNIQUE      NOT NULL,
	 password   varchar             NOT NULL);

CREATE TABLE if not exists auto_post
	(id            serial primary key,
	 description   varchar                       NOT NULL,
	 created       boolean                       NOT NULL,
	 auto_user_id  int REFERENCES auto_user (id) NOT NULL);