CREATE TABLE if NOT EXISTS participates(
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL REFERENCES users(id),
    post_id int NOT NULL REFERENCES posts(id),
    UNIQUE (user_id, post_id)
);