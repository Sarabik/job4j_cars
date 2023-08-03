CREATE TABLE if NOT EXISTS price_history(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   post_id int REFERENCES posts(id) NOT NULL
);