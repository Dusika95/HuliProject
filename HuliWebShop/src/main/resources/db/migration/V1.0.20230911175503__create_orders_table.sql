CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        total_price DECIMAL(10, 2),
                        order_date TIMESTAMP,
                        shipping_address VARCHAR(255),
                        payment_method VARCHAR(255),
                        order_status VARCHAR(50)
);