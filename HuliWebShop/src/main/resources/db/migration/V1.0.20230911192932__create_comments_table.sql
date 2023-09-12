CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          comment VARCHAR(255),
                          product_id BIGINT NOT NULL,
                          FOREIGN KEY (product_id) REFERENCES products(id),
                          user_id BIGINT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)

);