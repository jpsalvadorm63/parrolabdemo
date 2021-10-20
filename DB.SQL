SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS shippingaddress CASCADE;
DROP TABLE IF EXISTS orderitem CASCADE;
DROP TABLE IF EXISTS torder CASCADE;
DROP TABLE IF EXISTS paymenttype CASCADE;
SET FOREIGN_KEY_CHECKS = 1;
CREATE TABLE product (
                         id            BIGINT      NOT NULL,
                         `description` VARCHAR(64) NULL,
                         price         FLOAT       NULL,
                         weight        VARCHAR(16) NULL,
                         refcounter    INT         NULL,
                         CONSTRAINT pk_product PRIMARY KEY (id)
);
ALTER TABLE product ADD CONSTRAINT uc_product_description UNIQUE (`description`);
CREATE TABLE customer (
                          id                  BIGINT      NOT NULL,
                          name                VARCHAR(64) NULL,
                          phone               VARCHAR(32) NULL,
                          email               VARCHAR(64) NULL,
                          shipping_address_id BIGINT      NULL,
                          refcounter          INT         NULL,
                          CONSTRAINT pk_customer PRIMARY KEY (id)
);
ALTER TABLE customer ADD CONSTRAINT uc_customer_email UNIQUE (email);
ALTER TABLE customer ADD CONSTRAINT uc_customer_phone UNIQUE (phone);
CREATE TABLE shippingaddress (
                                 id          BIGINT      NOT NULL,
                                 street      VARCHAR(64) NULL,
                                 number      INT         NULL,
                                 city        VARCHAR(32) NULL,
                                 state       VARCHAR(4)  NULL,
                                 zip         VARCHAR(8)  NULL,
                                 code        VARCHAR(16) NULL,
                                 country     VARCHAR(64) NULL,
                                 customer_id BIGINT      NULL,
                                 CONSTRAINT pk_shippingaddress PRIMARY KEY (id)
);
ALTER TABLE shippingaddress  ADD CONSTRAINT FK_SHIPPINGADDRESS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);
ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_SHIPPING_ADDRESS FOREIGN KEY (shipping_address_id) REFERENCES shippingaddress (id);
CREATE INDEX idx_customer ON customer (shipping_address_id);
CREATE TABLE paymenttype(
                            id   BIGINT      NOT NULL,
                            type VARCHAR(16) NULL,
                            CONSTRAINT pk_paymenttype PRIMARY KEY (id)
);
ALTER TABLE paymenttype ADD CONSTRAINT uc_paymenttype_type UNIQUE (type);
CREATE TABLE torder
(
    id                  BIGINT      NOT NULL,
    date                VARCHAR(24) NULL,
    customer_id         BIGINT      NULL,
    shipping_address_id BIGINT      NULL,
    payment_type_id     BIGINT      NULL,
    ordervalue          FLOAT       NULL,
    CONSTRAINT pk_torder PRIMARY KEY (id)
);
ALTER TABLE torder ADD CONSTRAINT FK_TORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);
ALTER TABLE torder ADD CONSTRAINT FK_TORDER_ON_PAYMENT_TYPE FOREIGN KEY (payment_type_id) REFERENCES paymenttype (id);
ALTER TABLE torder ADD CONSTRAINT FK_TORDER_ON_SHIPPING_ADDRESS FOREIGN KEY (shipping_address_id) REFERENCES shippingaddress (id);
CREATE TABLE orderitem
(
    id         BIGINT NOT NULL,
    quantity   INT    NULL,
    product_id BIGINT NOT NULL,
    order_id   BIGINT NOT NULL,
    CONSTRAINT pk_orderitem PRIMARY KEY (id)
);
ALTER TABLE orderitem ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES torder (id);
ALTER TABLE orderitem ADD CONSTRAINT FK_ORDERITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);
