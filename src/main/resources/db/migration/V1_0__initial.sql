--- SCHEMA
CREATE TABLE T_TRAVEL_PRODUCT_TYPE (
  id          INT          NOT NULL AUTO_INCREMENT,
  name        VARCHAR(64),
  description TEXT,

  UNIQUE(name),
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_TRAVEL_PRODUCT (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_product_type_fk  INT     NOT NULL REFERENCES T_TRAVEL_PRODUCT_TYPE(id),
  travel_provider_fk      INT     NOT NULL,
  origin_city_fk          INT     NOT NULL,
  destination_city_fk     INT     NOT NULL,

  name                    VARCHAR(100),
  description             TEXT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_SCHEDULED_TIME (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_product_fk       INT     NOT NULL REFERENCES T_TRAVEL_PRODUCT(id),

  day_of_week             INT,
  departure_time          TIME,
  arrival_time            TIME,

  UNIQUE (travel_product_fk, day_of_week, departure_time, arrival_time),
  PRIMARY KEY (id)
) ENGINE = InnoDB;



CREATE TABLE T_PRICE_TYPE (
  id          INT          NOT NULL AUTO_INCREMENT,
  name        VARCHAR(64),
  description TEXT,

  UNIQUE(name),
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_PRICE (
  id                INT       NOT NULL AUTO_INCREMENT,

  price_type_fk           INT NOT NULL  REFERENCES T_PRICE_TYPE(id),
  travel_product_fk       INT NOT NULL  REFERENCES T_TRAVEL_PRODUCT(id),

  amount        DOUBLE,
  currency_code VARCHAR(3),
  from_date     DATE,
  to_date       DATE,

  UNIQUE (price_type_fk, travel_product_fk),
  PRIMARY KEY (id)
) ENGINE = InnoDB;



CREATE TABLE T_TRAVEL_ASSET_TYPE (
  id          INT          NOT NULL AUTO_INCREMENT,
  name        VARCHAR(64),
  description TEXT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_TRAVEL_ASSET (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_asset_type_fk    INT     NOT NULL REFERENCES T_TRAVEL_ASSET_TYPE(id),
  travel_provider_fk      INT     NOT NULL,

  immatriculation_number  VARCHAR(30) NOT NULL,
  model                   VARCHAR(100),
  year                    INT,
  capacity                INT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;


CREATE TABLE T_SCHEDULED_TRANSPORTATION (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_asset_fk         INT     REFERENCES T_TRAVEL_ASSET(id),

  departure_datetime      DATETIME NOT NULL,
  arrival_datetime        DATETIME,

  capacity                INT,

  UNIQUE (travel_asset_fk, departure_datetime),
  PRIMARY KEY (id)
) ENGINE = InnoDB;



---- DATA
INSERT INTO T_PRICE_TYPE(id, name) VALUES(1, 'ONE_WAY');
INSERT INTO T_PRICE_TYPE(id, name) VALUES(2, 'RETURN');

INSERT INTO T_TRAVEL_PRODUCT_TYPE(id, name) VALUES(100, 'BUS_SEAT');
INSERT INTO T_TRAVEL_PRODUCT_TYPE(id, name) VALUES(200, 'CAR_SEAT');

INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(100, 'BUS');
INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(200, 'CAR');
