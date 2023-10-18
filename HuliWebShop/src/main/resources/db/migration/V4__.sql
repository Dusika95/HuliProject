CREATE TABLE cart_products
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    cart_id    BIGINT NULL,
    product_id BIGINT NULL,
    CONSTRAINT pk_cart_products PRIMARY KEY (id)
);

CREATE TABLE order_products
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_id   BIGINT NULL,
    product_id BIGINT NULL,
    CONSTRAINT pk_order_products PRIMARY KEY (id)
);

ALTER TABLE cart_products
    ADD CONSTRAINT FK_CART_PRODUCTS_ON_CART FOREIGN KEY (cart_id) REFERENCES carts (id);

ALTER TABLE cart_products
    ADD CONSTRAINT FK_CART_PRODUCTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE order_products
    ADD CONSTRAINT FK_ORDER_PRODUCTS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_products
    ADD CONSTRAINT FK_ORDER_PRODUCTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);