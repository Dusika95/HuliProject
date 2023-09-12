CREATE TABLE users (
                       id INT PRIMARY KEY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       address VARCHAR(255),
                       zip_code VARCHAR(10),
                       city VARCHAR(255),
                       role VARCHAR(50) NOT NULL
);