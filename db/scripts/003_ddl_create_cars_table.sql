CREATE TABLE if NOT EXISTS cars(
    id serial PRIMARY KEY,
    name VARCHAR NOT NULL,
    engine_id INT NOT NULL UNIQUE REFERENCES engines(id)
);