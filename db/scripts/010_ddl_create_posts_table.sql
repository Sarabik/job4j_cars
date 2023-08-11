CREATE TABLE if not exists posts (
	 id serial PRIMARY KEY,
	 image_file_id INT REFERENCES image_files(id),
	 description VARCHAR NOT NULL,
	 price BIGINT NOT NULL,
	 created TIMESTAMP without TIME ZONE NOT NULL,
	 sold BOOLEAN DEFAULT FALSE,
	 car_id INT REFERENCES cars(id) NOT NULL,
	 user_id INT REFERENCES users(id) NOT NULL,
	 UNIQUE (car_id, user_id)
	 );