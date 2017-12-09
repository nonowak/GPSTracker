INSERT INTO password (current_hash, modified_on, previous_hash)
VALUES
  ('123456', NULL, NULL);

INSERT INTO user_details(first_name, last_name)
VALUES
  ('Test', 'Testowy');

INSERT INTO "user" (email_address, password_id, user_details_id, enabled)
VALUES
  ('test@test.pl', 1, 1, FALSE);