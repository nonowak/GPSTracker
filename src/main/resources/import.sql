INSERT INTO password (current_hash, modified_on, previous_hash)
VALUES
  ('123456', NULL, NULL);

INSERT INTO "user" (email_address, role, password_id, user_details_id)
VALUES
  ('test@test.pl', 0, 1, NULL);