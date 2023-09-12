CREATE TABLE comments (
                          id INT PRIMARY KEY,
                          comment VARCHAR(255),
                          product_id INT NOT NULL,
                          user_id INT NOT NULL,
                          FOREIGN KEY (product_id) REFERENCES products(id),
                          FOREIGN KEY (user_id) REFERENCES users(id)
);