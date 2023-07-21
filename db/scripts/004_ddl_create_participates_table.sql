CREATE TABLE if NOT EXISTS participates(
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL REFERENCES auto_user(id),
    post_id int NOT NULL REFERENCES auto_post(id),
    UNIQUE (user_id, post_id)
);