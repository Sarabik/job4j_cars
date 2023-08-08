CREATE TABLE if NOT EXISTS history_owners(
    id SERIAL PRIMARY KEY,
    car_id int NOT NULL REFERENCES cars(id),
    owner_id int NOT NULL REFERENCES owners(id),
    start_at TIMESTAMP WITHOUT TIME ZONE,
    end_at TIMESTAMP WITHOUT TIME ZONE,
    UNIQUE (car_id, owner_id)
);