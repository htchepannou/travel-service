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


-- search
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(1000, 1, 100, 101, 102, 'PROD1000');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(1001, 1, 1000, 1003, 'USD', '2017-01-02', '2017-04-05');
INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(1002, 2, 1000, 1005, 'USD', '2017-01-03', '2017-04-06');



INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(2000, 1, 100, 101, 102, 'PROD2001');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(2001, 1, 2000, 2003, 'USD', null, null);
INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(2002, 2, 2000, 2005, 'USD', null, null);



INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(3000, 1, 200, 101, 102, 'PROD2000');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(3001, 1, 3000, 3003, 'USD', '2017-01-02', '2017-04-15');
INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(3002, 2, 3000, 3005, 'USD', '2017-01-03', '2017-04-16');



INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(29990, 1, 200, 101, 102, 'PROD29990');

INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(29991, 1, 29990, 29993, 'USD', '2018-01-02', '2018-04-05');
INSERT INTO T_PRICE(id, price_type_fk, travel_product_fk, amount, currency_code, from_date, to_date) VALUES(29992, 2, 29990, 29995, 'USD', '2018-01-03', '2018-04-06');

