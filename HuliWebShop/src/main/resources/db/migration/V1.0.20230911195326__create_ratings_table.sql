CREATE TABLE ratings (
                          id SERIAL PRIMARY KEY,
                          star BIGINT NOT NULL,
                          product_id BIGINT NOT NULL,
                          FOREIGN KEY (product_id) REFERENCES products(id),

);