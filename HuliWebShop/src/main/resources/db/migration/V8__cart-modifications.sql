CREATE TABLE cart_entries
(
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT NULL,
    CONSTRAINT pk_cart_entries PRIMARY KEY (cart_id, product_id)
);


ALTER TABLE cart_entries
    ADD CONSTRAINT fk_cart_entries_on_cart FOREIGN KEY (cart_id) REFERENCES carts (id);

ALTER TABLE cart_entries
    ADD CONSTRAINT fk_cart_entries_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE cart_products
DROP
FOREIGN KEY FK_CART_PRODUCTS_ON_CART;

ALTER TABLE cart_products
DROP
FOREIGN KEY FK_CART_PRODUCTS_ON_PRODUCT;

DROP TABLE cart_products;