CREATE TABLE products (
                          id INT PRIMARY KEY,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          picture VARCHAR(255) NOT NULL,
                          price INT NOT NULL,
                          quantity INT,
                          category_id INT,
                          FOREIGN KEY (category_id) REFERENCES categories(id)
);