CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       user_id BIGINT REFERENCES users(id)
);