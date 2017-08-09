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




CREATE TABLE T_ACCOMODATION_CLASS (
  id          INT          NOT NULL AUTO_INCREMENT,
  name        VARCHAR(64),
  description TEXT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_TRAVEL_ASSET_TYPE (
  id          INT          NOT NULL AUTO_INCREMENT,
  name        VARCHAR(64),
  description TEXT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_TRAVEL_ASSET_TYPE_ACCOMODATION_CLASS (
  id                      INT          NOT NULL AUTO_INCREMENT,

  travel_asset_type_fk    INT NOT NULL REFERENCES T_TRAVEL_ASSET_TYPE(id),
  accomodation_class_fk   INT NOT NULL REFERENCES T_ACCOMODATION_CLASS(id),

  rank        INT,

  UNIQUE(travel_asset_type_fk, accomodation_class_fk),
  PRIMARY KEY (id)
) ENGINE = InnoDB;


CREATE TABLE T_TRAVEL_ASSET (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_asset_type_fk    INT     NOT NULL REFERENCES T_TRAVEL_ASSET_TYPE(id),
  travel_provider_fk      INT     NOT NULL,

  name                    VARCHAR(100),
  description             TEXT,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_ACCOMODATION_MAP (
  id                      INT     NOT NULL AUTO_INCREMENT,

  traval_asset_fk         INT     NOT NULL REFERENCES T_TRAVEL_ASSET(id),
  accomodation_class_fk   INT     REFERENCES T_ACCOMODATION_CLASS(id),

  number_of_space         INT,

  UNIQUE(traval_asset_fk, accomodation_class_fk),
  PRIMARY KEY (id)
) ENGINE = InnoDB;




CREATE TABLE T_SCHEDULED_TRANSPORTATION (
  id                      INT     NOT NULL AUTO_INCREMENT,

  travel_asset_fk         INT     REFERENCES T_TRAVEL_ASSET(id),

  departure_datetime      DATETIME NOT NULL,
  arrival_datetime        DATETIME NOT NULL,

  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE T_SCHEDULED_TRANSPORTATION_OFFER (
  id                      INT     NOT NULL AUTO_INCREMENT,

  scheduled_transportation_fk   INT NOT NULL REFERENCES T_SCHEDULED_TRANSPORTATION_OFFER(id),
  accomodation_class_fk         INT REFERENCES T_ACCOMODATION_CLASS(id),

  number_of_space         INT,

  UNIQUE (scheduled_transportation_fk, accomodation_class_fk),
  PRIMARY KEY (id)
) ENGINE = InnoDB;


---- DATA
INSERT INTO T_PRICE_TYPE(id, name) VALUES(1, 'ONE_WAY');
INSERT INTO T_PRICE_TYPE(id, name) VALUES(2, 'RETURN');

INSERT INTO T_TRAVEL_PRODUCT_TYPE(id, name) VALUES(100, 'BUS_SEAT');

INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(100, 'BUS');
INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(200, 'AIR');
INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(300, 'TRAIN');
INSERT INTO T_TRAVEL_ASSET_TYPE(id, name) VALUES(400, 'CAR');

INSERT INTO T_ACCOMODATION_CLASS(id, name) VALUES(201, 'COACH');
INSERT INTO T_ACCOMODATION_CLASS(id, name) VALUES(202, 'BUSINESS');
INSERT INTO T_ACCOMODATION_CLASS(id, name) VALUES(203, 'FIRST');

INSERT INTO T_TRAVEL_ASSET_TYPE_ACCOMODATION_CLASS(travel_asset_type_fk, accomodation_class_fk, rank) VALUES (200, 201, 1);
INSERT INTO T_TRAVEL_ASSET_TYPE_ACCOMODATION_CLASS(travel_asset_type_fk, accomodation_class_fk, rank) VALUES (200, 202, 2);
INSERT INTO T_TRAVEL_ASSET_TYPE_ACCOMODATION_CLASS(travel_asset_type_fk, accomodation_class_fk, rank) VALUES (200, 203, 3);
