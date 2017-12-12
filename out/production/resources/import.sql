INSERT INTO password (current_hash, modified_on, previous_hash, locked_during_reset)
VALUES
  ('123456', NULL, NULL, FALSE);

INSERT INTO user_info(first_name, last_name)
VALUES
  ('Test', 'Testowy');

INSERT INTO "user" (email_address, password_id, user_info_id, enabled)
VALUES
  ('test@test.pl', 1, 1, FALSE);