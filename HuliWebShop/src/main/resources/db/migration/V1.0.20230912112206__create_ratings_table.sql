CREATE TABLE ratings (
                         id INT PRIMARY KEY,
                         star INT NOT NULL,
                         product_id INT NOT NULL,
                         FOREIGN KEY (product_id) REFERENCES products(id)

);