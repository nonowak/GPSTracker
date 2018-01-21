INSERT INTO address (country_name, city_name, postal_code, street_name)
VALUES
  ('Poland', 'Poznań', '61-138', 'Piotrowo'),
  ('Poland', 'Poznań', '61-138', 'Piotrowo'),
  ('Poland', 'Poznań', '61-138', 'Piotrowo'),
  ('Poland', 'Poznań', '61-138', 'Piotrowo');

INSERT INTO user_info (first_name, last_name, address_id)
VALUES
  ('Test', 'Testowy', 1),
  ('Test', 'Testowy1', 1),
  ('Test', 'Testowy2', 1),
  ('Admin', 'Adminowy', 2);

INSERT INTO password (current_hash, modified_on, previous_hash, locked_during_reset)
VALUES
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE), --TesT123456--
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE), --TesT123456--
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE), --TesT123456--
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE); --TesT123456--

INSERT INTO "user" (email_address, password_id, user_info_id, enabled, role)
VALUES
  ('test@test.pl', 1, 1, TRUE, 'USER'),
  ('test1@test.pl', 2, 2, TRUE, 'USER'),
  ('test2@test.pl', 3, 3, TRUE, 'USER'),
  ('admin@test.pl', 4, 4, TRUE, 'ADMIN');

INSERT INTO public.device_dictionary (created_on, device_type, enabled, token, created_by_id)
VALUES
  ('2017-12-16 00:02:00.264000', 'GPSTRACKER', FALSE,
   '\xc30d0407030208762d9114fd7e5376d23a014eaab9835d21f55c7808b8379d9b44b750d008e7cbdb60124fd1bb1f6b4f3f6f0b8d1eff604e292dbd2374a72e7287ad24fe1dd18aea67cbd6',
   2),
  ('2017-12-16 00:02:00.264000', 'GPSTRACKER', TRUE,
   '\xc30d0407030220d25147faad56367bd23a01617003b19b716dbc60fb4b545f430f9c141dbb95e31b3bad691aef5795d459e92a8eb8d64dd7c9eb2413f59ab1e4d441ed60218110b63845c9',
   2); --h1rQ-BWZ9--;

INSERT INTO device (device_type, device_dictionary_id, name)
VALUES
  ('GPSTRACKER', 1, 'GPSTRACKER1'),
  ('GPSTRACKER', 2, 'GPSTRACKER2');

INSERT INTO user_device (device_id, user_id, permission)
VALUES
  (1, 1, 'OWNER'),
  (2, 1, 'OWNER'),
  (1, 2, 'USER'),
  (1, 3, 'USER');

INSERT INTO public.measurement_date (date)
VALUES
  ('2017-12-23'),
  ('2017-12-22');

INSERT INTO public.measurement_date_devices (measurement_dates_id, devices_id)
VALUES
  (1, 2),
  (2, 2);

INSERT INTO public.measurement (measurement_type, time, device_id, measurement_date_id)
VALUES
  ('gps', '11:40:00', 2, 1),
  ('gps', '11:41:00', 2, 1),
  ('gps', '11:42:00', 2, 1),
  ('gps', '11:43:00', 2, 1),
  ('gps', '11:44:00', 2, 1),
  ('gps', '11:44:00', 2, 2);

INSERT INTO public.measurementgps (city_name, country_name, street_name, lat, lng, id)
VALUES
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 1),
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 2),
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 3),
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 4),
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 5),
  ('Psary Polskie', 'Poland', '193A', 52.343623300000004, 17.5344841, 6);