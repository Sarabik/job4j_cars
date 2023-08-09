CREATE TABLE if NOT EXISTS transmissions(
    id serial PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);