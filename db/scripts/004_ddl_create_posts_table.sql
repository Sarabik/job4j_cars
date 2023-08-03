CREATE TABLE if not exists posts
	(id            serial primary key,
	 description   varchar                       NOT NULL,
	 created       TIMESTAMP without TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	 user_id  int REFERENCES users(id) NOT NULL,
	 car_id  int REFERENCES cars(id) NOT NULL);