CREATE DATABASE IF NOT EXISTS shop DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE shop;

CREATE TABLE IF NOT EXISTS product (
   maker VARCHAR(10) NOT NULL,
   model VARCHAR(50) NOT NULL,
   type VARCHAR(50) NOT NULL,
   PRIMARY KEY (model)
);

CREATE TABLE IF NOT EXISTS pc (
    code INT NOT NULL,
    model VARCHAR(50) NOT NULL,
    speed SMALLINT NOT NULL,
    ram SMALLINT NOT NULL,
    hd REAL NOT NULL,
    cd VARCHAR(10) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (code),
    CONSTRAINT pc_model_fk
    FOREIGN KEY (model) REFERENCES product(model)
);

CREATE TABLE IF NOT EXISTS laptop (
    code INT NOT NULL,
    model VARCHAR(50),
    speed SMALLINT,
    ram SMALLINT,
    hd REAL,
    price DECIMAL(10, 2),
    screen TINYINT,
    PRIMARY KEY (code),
    CONSTRAINT laptop_model_fk
    FOREIGN KEY (model) REFERENCES product(model)
);

CREATE TABLE IF NOT EXISTS printer (
    code INT NOT NULL,
    model VARCHAR(50),
    color CHAR(1),
    type VARCHAR(10),
    price DECIMAL(10, 2),
    PRIMARY KEY (code),
    CONSTRAINT printer_model_fk
    FOREIGN KEY (model) REFERENCES product(model)
);
