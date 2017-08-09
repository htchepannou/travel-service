-- findById
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
    VALUES(100, 1, 100, 101, 102, 'FLEX', 'Flexible Ticket');

INSERT INTO T_SCHEDULED_TIME(id, travel_product_fk, day_of_week, departure_time, arrival_time) VALUE(101, 100, 1, '8:30', '11:30');
INSERT INTO T_SCHEDULED_TIME(id, travel_product_fk, day_of_week, departure_time, arrival_time) VALUE(102, 100, 1, '10:30', '13:30');
INSERT INTO T_SCHEDULED_TIME(id, travel_product_fk, day_of_week, departure_time, arrival_time) VALUE(103, 100, 0, '5:00', '8:00');

-- shouldUpdateProduct
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
VALUES(200, 2, 100, 1, 1, '???', '???');

-- shouldScheduleTime
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
VALUES(300, 2, 100, 1, 1, '???', '???');

-- shouldNotScheduleTimeTwice
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
VALUES(400, 2, 100, 1, 1, '???', '???');

INSERT INTO T_SCHEDULED_TIME(id, travel_product_fk, day_of_week, departure_time, arrival_time) VALUE(401, 400, 1, '8:30', '11:30');


-- shouldUnscheduleTime
INSERT INTO T_TRAVEL_PRODUCT(id, travel_provider_fk, travel_product_type_fk, origin_city_fk, destination_city_fk, name, description)
VALUES(500, 2, 100, 1, 1, '???', '???');

INSERT INTO T_SCHEDULED_TIME(id, travel_product_fk, day_of_week, departure_time, arrival_time) VALUE(501, 500, 1, '8:30', '11:30');
