-- findById
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(100, 1, 100, 101, 102, 'FLEX', 'Flexible Ticket');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(100, 1, 100, 101, 'USD', '2017-01-02', '2017-04-05');


-- shouldCreate/shouldUpdate
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(200, 1, 100, 101, 102, 'FLEX', 'Flexible Ticket');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(200, 2, 200, 201, 'USD', null, null);


-- findByProduct
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(300, 1, 100, 101, 102, 'FLEX', 'Flexible Ticket');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(301, 1, 300, 311, 'USD', '2017-01-02', '2017-04-05');
INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(302, 2, 300, 312, 'USD', '2017-01-03', '2017-04-06');
