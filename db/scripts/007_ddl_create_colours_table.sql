CREATE TABLE if NOT EXISTS car_colours(
    id serial PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);