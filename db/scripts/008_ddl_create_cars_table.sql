CREATE TABLE if NOT EXISTS cars(
    id serial PRIMARY KEY,
    car_model_id INT NOT NULL REFERENCES car_models(id),
    body_type_id INT NOT NULL REFERENCES body_types(id),
    car_year INT NOT NULL,
    fuel_type_id INT NOT NULL REFERENCES fuel_types(id),
    engine_size_id INT NOT NULL REFERENCES engine_sizes(id),
    transmission_id INT NOT NULL REFERENCES transmissions(id),
    mileage BIGINT NOT NULL,
    car_colour_id INT NOT NULL REFERENCES car_colours(id),
    number VARCHAR,
    vin VARCHAR
);