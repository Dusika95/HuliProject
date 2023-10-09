ALTER TABLE order_products
    ADD quantity INT NULL;

ALTER TABLE order_products
    MODIFY quantity INT NOT NULL;