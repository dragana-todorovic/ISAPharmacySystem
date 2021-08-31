-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za adminsystem@example.com je Admin123#
-- Lozinka za dermatologist@example.com je User123#
-- Lozinka za pharmacist@example.com je User123#
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('adminsystem@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Milica', 'Okiljevic', 'adminsystem@example.com','Republika Srbija', 'Novi Sad', 'Trg Dositeja Obradovica 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email,country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('user1@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Nikola', 'Nikolic', 'user1@example.com', 'Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 18:57:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('user2@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user2@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('user3@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'userr@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('user@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Marko', 'Markovic', 'user@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('maja.tepavcevic133@gmail.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Nikola', 'Nikolic', 'maja.tepavcevic133@gmail.com','Republika Srbija', 'Nis', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('dermatologist@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Marko', 'Markovic', 'dermatologist@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('pharmacist@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Jovan', 'Jovanovic', 'pharmacist@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('adminpharmacyr@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Dragana', 'Todorovic', 'adminpharmacyr@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',false);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('pharmacist1@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Ivana', 'Ivanovic', 'pharmacist1@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('dermatologist1@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Maja', 'Markovic', 'dermatologist1@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('dermatologist2@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Jovana', 'Jovancic', 'dermatologist2@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('dragana123600@gmail.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Jovana', 'Jovancic', 'dragana123600@gmail.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('suplier@gmail.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Jovana', 'Jovancic', 'suplier@gmail.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date,logged) VALUES ('knezevicljiljana12@gmail.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Ljiljana', 'Knezevic', 'knezevicljiljana12@gmail.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07',true);



--medicine

INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code1','Midol',1,'content1','Galenika',false,0,'Ne konzumirati uz alkohol',1,'Contradiction0',0);
INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code2','Rapten DUO',1,'content2','Galenika',false,0,'Ne konzumirati uz alkohol',2,'Contradiction1',2);
INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code3','Paracetamol',1,'content3','HEMOFARM',false,0,'Ne konzumirati uz alkohol',2,'Contradiction2',2);
INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code4','Andol',1,'content4','FARMAVITA',false,0,'Ne konzumirati uz alkohol',3,'Contradiction3',2);
INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code5','Deksomen',1,'content5','Galenika',false,0,'Ne konzumirati uz alkohol',5,'Contradiction4',1);
INSERT INTO MEDICINE(code,name,shape,content,producer,withprescription,type,notes,adviseddailydose,contradiction,buying_points) VALUES ('Code6','Analgin',1,'content6','HEMOFARM',false,0,'Ne konzumirati uz alkohol',4,'Contradiction5',1);

INSERT INTO MEDICINE(code,name,shape,type,content,producer,withprescription,notes,adviseddailydose,contradiction,buying_points) VALUES ('1234','Brufen',1,1,'nesto','Galenika',false,'Ne konzumirati uz alkohol', 2,'Contradiction',1);
INSERT INTO MEDICINE(code,name,shape,type,content,producer,withprescription,notes,adviseddailydose,contradiction,buying_points) VALUES ('12333','Aspirin',1,2,'nesto','Galenika',false,'Ne konzumirati uz alkohol',3,'Contradiction2',1);



INSERT INTO medicine_substitute_medicine_codes(medicine_id, substitute_medicine_codes) VALUES(7,'Code4');
INSERT INTO medicine_substitute_medicine_codes(medicine_id, substitute_medicine_codes) VALUES(7,'Code5');
INSERT INTO medicine_substitute_medicine_codes(medicine_id, substitute_medicine_codes) VALUES(1,'Code3');


-- role
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
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 6);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (11, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (12, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (13, 7);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (14, 7);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (15, 3);
--patient
INSERT INTO PATIENTS (user_id,points,category,penal,point) VALUES (5,10,0,0,0);
INSERT INTO PATIENTS (user_id,points,category,penal,point) VALUES (6,100,1,0,0);
INSERT INTO PATIENTS (user_id,points,category,penal,point) VALUES (15,200,2,0,0);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (1,1);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (2,2);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (2,3);
--suplier 

INSERT INTO suplier(user_id) VALUES (13);
INSERT INTO suplier(user_id) VALUES (14);
INSERT INTO suplier(user_id) VALUES (6);

--system admin
INSERT INTO systemadmin(user_id) VALUES (1);



INSERT INTO ADDRESS (street,city) VALUES ('Bulevar oslobodjenja 4', 'Novi Sad');
INSERT INTO ADDRESS (street,city) VALUES ('Kralja Petra 20', 'Beograd');
INSERT INTO ADDRESS (street,city) VALUES ('Neznanih Junaka 5', 'Beograd');
INSERT INTO ADDRESS (street,city) VALUES ('Narodnog Fronta 37', 'Beograd');
--pharmacy
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Jankovic', 'Opis Jankovic',2);
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Benu', 'Opis Benu',1);

--pharmacy for qr codes
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Zdravlje', 'Opis Zdravlje',3);
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Super', 'Opis Super',4);

--pharmacyadmin

INSERT INTO PHARMACYADMIN (pharmacy_id, user_id) VALUES (2,9);



INSERT INTO patient_subscribe_pharmacy_ids(patient_id,subscribe_pharmacy_ids) VALUES (1,2);
INSERT INTO patient_subscribe_pharmacy_ids(patient_id,subscribe_pharmacy_ids) VALUES (1,1);
INSERT INTO patient_subscribe_pharmacy_ids(patient_id,subscribe_pharmacy_ids) VALUES (2,2);
--dermatologist 
INSERT INTO DERMATOLOGIST ( user_id) VALUES (7);
INSERT INTO DERMATOLOGIST ( user_id) VALUES (11);
INSERT INTO DERMATOLOGIST (user_id) VALUES (12);
--appoitment


INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2021-01-01 21:58:58.508-07','opis1',1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2021-01-01 21:58:58.508-07','opis2',1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2021-03-01 21:58:58.508-07','opis1',1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2020-02-02 21:58:58.508-07','opis2',1,2,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2020-01-01 21:58:58.508-07','opis1',1,1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2019-02-02 21:58:58.508-07','opis2',1,2,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2017-01-01 21:58:58.508-07','opis1',1,1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2018-02-02 21:58:58.508-07','opis2',1,2,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2021-03-15 21:58:58.508-07','opis2',1,2,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2022-08-10 21:58:58.508-07','opis1',1,1,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2022-02-14 21:58:58.508-07','opis2',1,2,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2022-02-15 21:58:58.508-07','opis1',1,1,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2022-03-16 21:58:58.508-07','opis2',1,2,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2022-11-11 21:58:58.508-07','opis2',1,2,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2021-12-12 21:58:58.508-07','opis1',1,1,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2021-10-31 11:58:58.508-07','opis2',1,2,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id, patient_id,pharmacy_id,version) VALUES (30,'2021-10-31 10:58:58.508-07','opis1',1,1,2,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2019-05-16 21:58:58.508-07','opis2',1,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2021-09-01 08:00:00.508-07','opis2',1,1,1);
INSERT INTO APPOITMENT ( duration, start_date_time, description, dermatologist_id,pharmacy_id,version) VALUES (30,'2022-08-31 12:55:00.508-07','opis2',1,2,1);


--appoitment price
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,1);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,2);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,3);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,4);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,5);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,6);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,7);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,8);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,9);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,10);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,11);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,12);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,13);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,14);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,15);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,16);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,17);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,18);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (400,19);
INSERT INTO APPOITMENTPRICE (price,appoitment_id) VALUES (500,20);




--rating
INSERT INTO RATING (id, rating) VALUES (1,1);
INSERT INTO RATING (id, rating) VALUES (2,2);
INSERT INTO RATING (id, rating) VALUES (3,3);
INSERT INTO RATING (id, rating) VALUES (4,4);
INSERT INTO RATING (id, rating) VALUES (5,5);

--dermatologist rating
INSERT INTO dermatologist_ratings (dermatologist_id, ratings_id) VALUES (1,1);
INSERT INTO dermatologist_ratings (dermatologist_id, ratings_id) VALUES (1,2);
INSERT INTO dermatologist_ratings (dermatologist_id, ratings_id) VALUES (1,5);
INSERT INTO dermatologist_ratings (dermatologist_id, ratings_id) VALUES (3,3);
INSERT INTO dermatologist_ratings (dermatologist_id, ratings_id) VALUES (3,4);

--medicine price
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 200, 1);
INSERT INTO MEDICINEPRICE (	price, medicine_id) VALUES ( 300, 2);
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 485, 3);
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 856, 4);
INSERT INTO MEDICINEPRICE (	price, medicine_id) VALUES ( 586, 5);
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 255, 6);
INSERT INTO MEDICINEPRICE (	price, medicine_id) VALUES ( 3005, 7);
INSERT INTO MEDICINEPRICE (	price, medicine_id) VALUES ( 600, 8);
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 350, 1);
INSERT INTO MEDICINEPRICE (	price, medicine_id) VALUES ( 286, 2);
INSERT INTO MEDICINEPRICE ( price, medicine_id) VALUES ( 127, 3);
--medicine with quantity
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (0, 1);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (50, 2);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (100, 3);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (30, 4);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (25, 5);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (28, 6);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (3, 6);

--za qr
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (30, 4);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (25, 5);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (100, 2);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (20, 1);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (28, 6);


--za offers 
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (55, 1);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (44, 4);

-- za supplier list
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (150, 1);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (200, 2);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (300, 3);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (400, 4);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (500, 5);
-- supplier dragana 1
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (600, 3);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (800, 4);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (1000, 5);
-- supplier maja.tepavcevic 3
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (200, 2);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (100, 1);
INSERT INTO MEDICINEWITHQUANTITY (quantity, medicine_id) VALUES (350, 3);

--pharmacy medicine prices

--pharmacy medicine with quantity
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (2, 1);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 3);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (2, 4);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 5);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 6);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 2);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (2, 7);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (3, 8);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (3, 9);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (3, 10);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (4, 11);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (4, 12);


--supplier for medicine list - medicine with quantity
-- suplier@gmail.com
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (2, 15);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (2, 19);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (2, 18);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (2, 17);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (2, 16);
-- suplier dragana suplier_id 1
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (1, 22);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (1, 21);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (1, 20);
-- supplier maja.tepavcevic13 supplier_id 3
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (3, 23);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (3, 24);
INSERT INTO suplier_medicine_with_quantity (suplier_id, medicine_with_quantity_id) VALUES (3, 25);
--price list
INSERT INTO pricelist(start_date) VALUES ('2021-07-07 21:58:58.508-07');
INSERT INTO pricelist(start_date) VALUES ('2021-08-07 21:58:58.508-07');
INSERT INTO pricelist(start_date) VALUES ('2021-08-08 21:58:58.508-07');
INSERT INTO pricelist(start_date) VALUES ('2021-08-14 21:58:58.508-07');

--price list medicine price list
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (1,4);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (1,5);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (1,6);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (1,7);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (1,8);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (2,1);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (2,2);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (2,3);

INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (3,9);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (4,10);
INSERT INTO pricelist_medicine_price_list(price_list_id,medicine_price_list_id) VALUES (4,11);

--price list appoitment price list
--INSERT INTO pricelist_appoitment_price_list(price_list_id,appoitment_price_list_id) VALUES (1,1);
--INSERT INTO pricelist_appoitment_price_list(price_list_id,appoitment_price_list_id) VALUES (1,2);
--pharmacy rating
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (1,3);
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (1,5);
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (2,4);
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (2,2);

--pharmacy price list
INSERT INTO pharmacy_price_list(pharmacy_id, price_list_id) VALUES (2,1);
INSERT INTO pharmacy_price_list(pharmacy_id, price_list_id) VALUES (2,2);
INSERT INTO pharmacy_price_list(pharmacy_id, price_list_id) VALUES (2,3);
INSERT INTO pharmacy_price_list(pharmacy_id, price_list_id) VALUES (2,4);

--actions and benefits
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'brufen 20%','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'kupis 2 dobijes 3','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'paracetamol 10%','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');

--pharmacy actions and benefits
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,1);
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,2);
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,3);


--dermatologist complaint
INSERT INTO dermatologist_complaint(id,content,dermatologist_id,patient_id,is_answered) VALUES (1,'Nije ljubazan',1,1,false);

--patient dermatologist complaint
INSERT INTO patients_dermatologist_complaints(patient_id,dermatologist_complaints_id) VALUES (1,1);

--holiday request
INSERT INTO holidayrequest(pharmacy_id,start_date,end_date,status) VALUES (2,'2021-10-20 21:58:58.508-07','2021-10-29 21:58:58.508-07',0);
INSERT INTO holidayrequest(pharmacy_id,start_date,end_date,status) VALUES (2,'2021-08-27 21:58:58.508-07','2021-08-28 21:58:58.508-07',0);

--dermatologist holiday request
INSERT INTO dermatologist_holiday_requests(dermatologist_id,holiday_requests_id) VALUES (1,1);
INSERT INTO dermatologist_holiday_requests(dermatologist_id,holiday_requests_id) VALUES (2,2);


--working days

INSERT INTO workingday (day,end_time,start_time) VALUES (0,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (1,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (2,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (2,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (2,'19:00:00.000000', '08:00:00.000000');

INSERT INTO workingday (day,end_time,start_time) VALUES (3,'19:00:00.000000', '08:00:00.000000');

INSERT INTO workingday (day,end_time,start_time) VALUES (4,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (5,'19:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (6,'19:30:00.000000', '08:00:00.000000');

INSERT INTO workingday (day,end_time,start_time) VALUES (0,'15:00:00.000000', '07:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (1,'15:00:00.000000', '07:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES (2,'15:00:00.000000', '07:00:00.000000');


--working time
INSERT INTO workingtime (pharmacy_id) VALUES (2);
INSERT INTO workingtime (pharmacy_id) VALUES (2);
INSERT INTO workingtime (pharmacy_id) VALUES (1);
INSERT INTO workingtime (pharmacy_id) VALUES (1);

--working time with working days

INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (2,1);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (2,2);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (2,3);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (3,4);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (3,5);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (4,6);

INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (4,7);

INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,10);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,11);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,12);
--dermatologist working time
INSERT INTO dermatologist_working_times (dermatologist_id, working_times_id) VALUES (1,1); 
INSERT INTO dermatologist_working_times (dermatologist_id, working_times_id) VALUES (1,3); 
INSERT INTO dermatologist_working_times (dermatologist_id, working_times_id) VALUES (2,2); 
INSERT INTO dermatologist_working_times (dermatologist_id, working_times_id) VALUES (3,4); 


--pharmacist

INSERT INTO PHARMACIST (user_id,working_time_id) VALUES (8,2);
INSERT INTO PHARMACIST (user_id,working_time_id) VALUES (10,2);



--pharmacist rating
INSERT INTO pharmacist_ratings (pharmacist_id, ratings_id) VALUES (1,1);
INSERT INTO pharmacist_ratings (pharmacist_id, ratings_id) VALUES (1,3);



--pharmacist y request
INSERT INTO pharmacist_holiday_requests(pharmacist_id,holiday_requests_id) VALUES (1,1);


--eprescription
INSERT INTO eprescription(issued_date,patient_id) VALUES ('2017-10-01',1);
INSERT INTO eprescription(issued_date,patient_id) VALUES ('2017-10-01',2);

--eprescription medicine
INSERT INTO eprescription_medicines(eprescription_id,medicines_id) values (1,1);
INSERT INTO eprescription_medicines(eprescription_id,medicines_id) values (2,2);

--medicine rating 

INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,1);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,5);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,4);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (2,3);

--medicine order
INSERT INTO medicineorder(status,time_limit) VALUES (0,'2017-10-01 21:58:58.508-07');
INSERT INTO medicineorder(status,time_limit) VALUES (1,'2017-10-29 21:58:58.508-07');
INSERT INTO medicineorder(status,time_limit) VALUES (0,'2017-12-04 21:58:58.508-07');

--pharmacy medicine order
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (2,1);
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (2,2);
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (1,3);


--medicine order medicines
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (1,8);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (2,2);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (3,3);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (2,1);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (2,3);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (1,9);

--medicine reservation


INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174000','2021-09-27','14:00:00.000000',0,1,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174004','2021-09-26','08:00:00.000000',0,4,1,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174003','2021-10-04','14:00:00.000000',0,1,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174005','2021-12-04','14:00:00.000000',0,1,1,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174008','2021-10-22','14:00:00.000000',0,3,1,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174011','2021-10-04','14:00:00.000000',2,1,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4568-e89b-12d3-a456-426614174000','2021-08-04','14:00:00.000000',2,4,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('133e4567-e89b-12d3-a456-426614174000','2021-12-04','14:00:00.000000',2,1,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4567-e89b-12d3-a456-426614174030','2021-12-06','14:00:00.000000',2,1,2,1);
INSERT INTO medicinereservation(number_of_reservation,due_to,due_to_time,status,medicine_with_quantity_id,patient_id,version) VALUES ('123e4566-e89b-12d3-a456-426614174000','2021-10-15','14:00:00.000000',2,1,2,1);


--pharmacist complaint
INSERT INTO pharmacist_complaint(id,content,pharmacist_id,patient_id, is_answered) VALUES (1,'Skup',1, 1,false);

--VRV OBRISATI
--patients pharmacist complaint
INSERT INTO patients_pharmacist_complaints(patient_id,pharmacist_complaints_id) VALUES (1,1);

--pharmacy complaint
INSERT INTO pharmacy_complaint(id,content,pharmacy_id,patient_id, is_answered) VALUES (1,'Lose osoblje',1,1, false);
INSERT INTO pharmacy_complaint(id,content,pharmacy_id,patient_id, is_answered) VALUES (2,'Los pristup pacijentu.',1,2, false);

--patients phamracy complaint
INSERT INTO patients_pharmacy_complaints(patient_id,pharmacy_complaints_id) VALUES (1,1);

--request for medicine availabitlity
INSERT INTO requestformedicineavailability(created_at,medicine_with_quantity_id,pharmacy_id) VALUES ('2021-07-05 21:58:58.508-07',1,2);
INSERT INTO requestformedicineavailability(created_at,medicine_with_quantity_id,pharmacy_id) VALUES ('2021-08-04 21:58:58.508-07',2,2);
INSERT INTO requestformedicineavailability(created_at,medicine_with_quantity_id,pharmacy_id) VALUES ('2021-08-21 21:58:58.508-07',3,1);


--suplier offer 
INSERT INTO suplieroffer(delevery_time,price,medicine_order_id, status) VALUES ('2020-10-01 21:58:58.508-07',400,1,0);
INSERT INTO suplieroffer(delevery_time,price,medicine_order_id, status) VALUES ('2021-08-08 21:58:58.508-07',500,1,0);
INSERT INTO suplieroffer(delevery_time,price,medicine_order_id, status) VALUES ('2021-11-11 07:58:58.508-07',300,2,1);

--suplier offers 
INSERT INTO suplier_offers(suplier_id,offers_id) VALUES (1,1);
INSERT INTO suplier_offers(suplier_id,offers_id) VALUES (2,2);
INSERT INTO suplier_offers(suplier_id,offers_id) VALUES (2,3);
--theraphy
INSERT INTO therapy(duration,medicine_id) VALUES (4,1);
INSERT INTO therapy(duration,medicine_id) VALUES (6,2);

--loyalty scale
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (1,0,0,0);
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (2,1,20,10);
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (3,2,60,30);


--pharmacist consulting
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 1',30,'2021-08-23 10:58:58.508-07',1,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 2',30,'2021-08-24 21:58:58.508-07',2,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 3',30,'2021-01-05 21:58:58.508-07',1,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 4',30,'2022-02-15 21:58:58.508-07',1,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 5',30,'2022-12-15 21:58:58.508-07',1,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 6',30,'2021-08-29 21:58:58.508-07',1,1,1);
INSERT INTO pharmacistcounseling(description,duration,start_date_time,patient_id,pharmacist_id,therapy_id) VALUES ('opis 6',30,'2021-08-31 11:58:58.508-07',2,1,1);


INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (200,1);
INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (350,2);
INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (480,3);
INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (600,4);
INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (1000,5);
INSERT INTO pharmacistcounselingprice(price,counseling_id) VALUES (870,6);

INSERT INTO loyaltyprogram(id,appointment_points,advising_points) VALUES (1,5,3);

