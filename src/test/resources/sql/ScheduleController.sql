-- search
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk)
    VALUES(1000, 1, 100, 101, 102);

INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk)
    VALUES(1200, 1, 200, 101, 102);

INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk)
    VALUES(1300, 1, 200, 102, 101);

INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk)
    VALUES(2000, 2, 100, 101, 102);



INSERT INTO T_TRAVEL_ASSET(id, travel_asset_type_fk, travel_provider_fk, immatriculation_number)
  VALUES(1000, 100, 1, '1000');

INSERT INTO T_TRAVEL_ASSET(id, travel_asset_type_fk, travel_provider_fk, immatriculation_number)
  VALUES(1200, 200, 1, '1200');

INSERT INTO T_TRAVEL_ASSET(id, travel_asset_type_fk, travel_provider_fk, immatriculation_number)
  VALUES(2000, 100, 2, '2000');



INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(1000, 1000, 1000, '2017-01-01 08:30', '2017-01-01 10:30', 50);

INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(1001, 1000, 1000, '2017-01-01 15:30', '2017-01-01 18:30', 50);

INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(1200, 1200, 1200, '2017-01-01 08:30', '2017-01-01 09:30', 100);

INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(1300, 1000, 1300, '2017-01-01 11:30', '2017-01-01 14:30', 50);


INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(2000, 2000, 2000, '2017-01-01 08:30', '2017-01-01 09:30', 50);

INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(2001, 2000, 2000, '2017-01-02 08:30', '2017-01-02 09:30', 50);

INSERT INTO T_SCHEDULED_TRANSPORTATION(id, travel_asset_fk, travel_product_fk, departure_datetime, arrival_datetime, capacity)
  VALUES(2002, 2000, 2000, '2017-01-03 08:30', '2017-01-03 09:30', 50);

