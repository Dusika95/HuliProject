CREATE TABLE carts
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NULL,
    CONSTRAINT pk_carts PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    comment    VARCHAR(255) NULL,
    product_id BIGINT NULL,
    user_id    BIGINT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    user_id          BIGINT NULL,
    total_price      DECIMAL NULL,
    order_date       datetime NULL,
    shipping_address VARCHAR(255) NULL,
    payment_method   VARCHAR(255) NULL,
    order_status     VARCHAR(255) NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    picture       VARCHAR(255) NULL,
    price         INT NOT NULL,
    quantity      INT NOT NULL,
    category_id   BIGINT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE ratings
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    star       INT NOT NULL,
    product_id BIGINT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    address    VARCHAR(255) NULL,
    zip_code   VARCHAR(255) NULL,
    city       VARCHAR(255) NULL,
    `role`     VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE carts
    ADD CONSTRAINT FK_CARTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE ratings
    ADD CONSTRAINT FK_RATINGS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);