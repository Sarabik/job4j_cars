CREATE TABLE if not exists posts (
	 id serial primary key,
	 image_file_id INT REFERENCES image_files(id),
	 description VARCHAR NOT NULL,
	 price BIGINT NOT NULL,
	 created TIMESTAMP without TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	 sold BOOLEAN DEFAULT FALSE,
	 car_id INT REFERENCES cars(id) NOT NULL,
	 user_id INT REFERENCES users(id) NOT NULL,
	 UNIQUE (car_id, user_id)
	 );