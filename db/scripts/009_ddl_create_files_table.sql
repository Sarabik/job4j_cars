CREATE TABLE if NOT EXISTS image_files(
    id serial PRIMARY KEY,
    path VARCHAR NOT NULL UNIQUE,
    file_name VARCHAR NOT NULL
);