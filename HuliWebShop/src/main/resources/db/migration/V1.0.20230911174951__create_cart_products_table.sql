CREATE TABLE cart_products (
                               id SERIAL PRIMARY KEY,
                               cart_id BIGINT REFERENCES carts(id),
                               product_id BIGINT REFERENCES products(id)
);