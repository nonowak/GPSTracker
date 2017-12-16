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
  ('2017-12-15 22:33:07', 'GPSTRACKER', FALSE, '$2a$08$0h5XUY.5p.Ad1q2jWDMBR.TA8W.VY6Q8v6FL93eq6uLeHNIdv19ie',
   2);