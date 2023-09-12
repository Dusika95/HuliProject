CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          picture VARCHAR(255) NOT NULL,
                          price BIGINT NOT NULL,
                          quantity BIGINT,
                          category_id BIGINT NOT NULL,
                          FOREIGN KEY (category_id) REFERENCES categories(id)
);