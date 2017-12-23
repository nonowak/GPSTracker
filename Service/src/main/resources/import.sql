INSERT INTO address (country_name, city_name, postal_code, street_name)
VALUES
  ('Poland', 'Poznań', '61-138', 'Piotrowo'),
  ('Poland', 'Poznań', '61-138', 'Piotrowo');

INSERT INTO user_info (first_name, last_name, address_id)
VALUES
  ('Test', 'Testowy', 1),
  ('Admin', 'Adminowy', 2);

INSERT INTO password (current_hash, modified_on, previous_hash, locked_during_reset)
VALUES
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE), --TesT123456--
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE); --TesT123456--

INSERT INTO "user" (email_address, password_id, user_info_id, enabled, role)
VALUES
  ('test@test.pl', 1, 1, TRUE, 'USER'),
  ('admin@test.pl', 2, 2, TRUE, 'ADMIN');

INSERT INTO public.device_dictionary (created_on, device_type, enabled, token, created_by_id)
VALUES
  ('2017-12-16 00:02:00.264000', 'GPSTRACKER', TRUE,
   '\xc30d0407030208762d9114fd7e5376d23a014eaab9835d21f55c7808b8379d9b44b750d008e7cbdb60124fd1bb1f6b4f3f6f0b8d1eff604e292dbd2374a72e7287ad24fe1dd18aea67cbd6',
   2),
  ('2017-12-16 00:02:00.264000', 'GPSTRACKER', TRUE,
   '\xc30d0407030220d25147faad56367bd23a01617003b19b716dbc60fb4b545f430f9c141dbb95e31b3bad691aef5795d459e92a8eb8d64dd7c9eb2413f59ab1e4d441ed60218110b63845c9',
   2); --h1rQ-BWZ9--;

INSERT INTO device (device_type, device_dictionary_id)
VALUES
  ('GPSTRACKER', 1),
  ('GPSTRACKER', 2);

INSERT INTO user_device (device_id, user_id, permission)
VALUES
  (1, 1, 'OWNER'),
  (2, 1, 'OWNER');