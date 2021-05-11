-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za adminsystem@example.com je Admin123#
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('adminsystem@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Milica', 'Okiljevic', 'adminsystem@example.com','Republika Srbija', 'Novi Sad', 'Trg Dositeja Obradovica 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email,country, city, address, phone, enabled, last_password_reset_date) VALUES ('user1@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Nikolic', 'user1@example.com', 'Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 18:57:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user2@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user2@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user3@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Marko', 'Markovic', 'user@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');

-- roles
INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_SYSTEM');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PATIENT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_PHARMACY');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_SUPPLIER');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 3);
