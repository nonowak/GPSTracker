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