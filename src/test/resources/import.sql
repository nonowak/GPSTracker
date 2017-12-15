INSERT INTO password (current_hash, modified_on, previous_hash, locked_during_reset)
VALUES
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE), --TesT123456--
  ('$2a$10$f6d6Rzr30AdZZamZu2DlUu23/8BwwA6i4QsqCNqqq1SgTIUmKDov6', NULL, NULL, FALSE); --TesT123456--

INSERT INTO user_info (first_name, last_name)
VALUES
  ('Test', 'Testowy'),
  ('Admin', 'Adminowy');

INSERT INTO "user" (email_address, password_id, user_info_id, enabled, role)
VALUES
  ('test@test.pl', 1, 1, TRUE, 'USER'),
  ('admin@test.pl', 2, 2, TRUE, 'ADMIN');

INSERT INTO public.device_dictionary (created_on, device_type, enabled, token, created_by_id)
VALUES
  ('2017-12-16 00:02:00.264000', 'GPSTRACKER', false, '\xc30d0407030208762d9114fd7e5376d23a014eaab9835d21f55c7808b8379d9b44b750d008e7cbdb60124fd1bb1f6b4f3f6f0b8d1eff604e292dbd2374a72e7287ad24fe1dd18aea67cbd6', 2);