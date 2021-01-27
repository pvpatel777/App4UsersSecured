DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL
);

INSERT INTO user (first_name, last_name, email) VALUES
  ('my first name 1', 'my last name 1', 'email1@test.com'),
  ('my first name 2', 'my last name 2', 'email2@test.com'),
  ('my first name 3', 'my last name 3', 'email3@test.com');