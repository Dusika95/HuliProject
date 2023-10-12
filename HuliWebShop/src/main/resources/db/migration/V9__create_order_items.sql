CREATE TABLE order_items
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (order_id, product_id)
);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_on_order FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_products
DROP
FOREIGN KEY FK_ORDER_PRODUCTS_ON_ORDER;

ALTER TABLE order_products
DROP
FOREIGN KEY FK_ORDER_PRODUCTS_ON_PRODUCT;

DROP TABLE order_products;