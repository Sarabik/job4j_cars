CREATE TABLE if NOT EXISTS body_types(
    id serial PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);