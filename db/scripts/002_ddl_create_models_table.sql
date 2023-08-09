CREATE TABLE if NOT EXISTS car_models(
    id serial PRIMARY KEY,
    make VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    UNIQUE (make, model)
);