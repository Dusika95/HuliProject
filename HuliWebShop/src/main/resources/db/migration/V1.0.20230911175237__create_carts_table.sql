CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       user_id INT REFERENCES users(id)
);