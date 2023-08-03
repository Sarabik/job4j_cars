CREATE TABLE if NOT EXISTS owners(
    id serial PRIMARY KEY,
    name VARCHAR NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);