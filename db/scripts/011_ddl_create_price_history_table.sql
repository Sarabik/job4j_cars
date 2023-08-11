CREATE TABLE if NOT EXISTS price_history(
   id SERIAL PRIMARY KEY,
   price BIGINT NOT NULL,
   created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   post_id INT REFERENCES posts(id)
);