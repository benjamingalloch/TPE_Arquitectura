insert into trip (id, scooter_id, start_time, end_time, kilometers, pause_time, price, user_id) values (1, 6, STR_TO_DATE('2022-05-15T15:46:16Z', '%Y-%m-%dT%H:%i:%sZ'), STR_TO_DATE('2022-05-15T16:20:12Z', '%Y-%m-%dT%H:%i:%sZ'), 1.98, 5, 300, 10);
insert into trip (id, scooter_id, start_time, end_time, kilometers, pause_time, price, user_id) values (2, 2, STR_TO_DATE('2022-05-15T16:46:16Z', '%Y-%m-%dT%H:%i:%sZ'), STR_TO_DATE('2022-05-15T18:20:12Z', '%Y-%m-%dT%H:%i:%sZ'), 3.98, 0, 400, 12);
insert into trip (id, scooter_id, start_time, end_time, kilometers, pause_time, price, user_id) values (3, 9, STR_TO_DATE('2022-05-15T10:46:16Z', '%Y-%m-%dT%H:%i:%sZ'), STR_TO_DATE('2022-05-15T12:20:12Z', '%Y-%m-%dT%H:%i:%sZ'), 5.00, 15, 200, 1);
insert into trip (id, scooter_id, start_time, end_time, kilometers, pause_time, price, user_id) values (4, 12, STR_TO_DATE('2022-05-15T08:46:16Z', '%Y-%m-%dT%H:%i:%sZ'), STR_TO_DATE('2022-05-15T10:20:12Z', '%Y-%m-%dT%H:%i:%sZ'), 1.50, 0, 500, 5);

insert into rate (date, normal_rate, pause_rate) values ('2023-02-07', 10.00, 2.50);
insert into rate (date, normal_rate, pause_rate) values ('2024-07-26', 12.00, 4.30);
insert into rate (date, normal_rate, pause_rate) values ('2023-02-27', 10.80, 1.10);
insert into rate (date, normal_rate, pause_rate) values ('2023-11-08', 15.00, 4.22);
