CREATE TABLE if NOT EXISTS fuel_types(
    id serial PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);