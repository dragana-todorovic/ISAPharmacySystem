-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za adminsystem@example.com je Admin123#
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('adminsystem@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Milica', 'Okiljevic', 'adminsystem@example.com','Republika Srbija', 'Novi Sad', 'Trg Dositeja Obradovica 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email,country, city, address, phone, enabled, last_password_reset_date) VALUES ('user1@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Nikola', 'Nikolic', 'user1@example.com', 'Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 18:57:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user2@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user2@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user3@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'userr@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('user@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Marko', 'Markovic', 'user@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('patient@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Nikola', 'Nikolic', 'patient@example.com','Republika Srbija', 'Nis', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('dermatologist@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.Lrd4j8amcljStog3n4APU5EX3bGJIUK', 'Marko', 'Markovic', 'dermatologist@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('pharmacist@example.com', '$2y$10$RobfH2HQtwTbwlvLgjTxA.', 'Jovan', 'Jovanovic', 'pharmacist@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');
INSERT INTO USERS (username, password, first_name, last_name, email, country, city, address, phone, enabled, last_password_reset_date) VALUES ('adminpharmacyr@example.com', '$2y$10$Dz4zbJOWPOfxHsjy2QgDk.SoBDiwsiQ3.2Vgxw0hhNfoDO4rZZB5i', 'Dragana', 'Todorovic', 'adminpharmacyr@example.com','Republika Srbija', 'Novi Sad', 'NArodnog fronta 60', '0694458924', true, '2017-10-01 21:58:58.508-07');

--medicine
INSERT INTO MEDICINE(code,name,shape,type,content,producer,withprescription,notes,adviseddailydose,contradiction) VALUES ('1234','Brufen',1,1,'nesto','Galenika',false,'Ne konzumirati uz alkohol', 2,'Contradiction');
INSERT INTO MEDICINE(code,name,shape,type,content,producer,withprescription,notes,adviseddailydose,contradiction) VALUES ('12333','Aspirin',1,2,'nesto','Galenika',false,'Ne konzumirati uz alkohol',3,'Contradiction2');
INSERT INTO medicine_substitute_medicine_codes(medicine_id, substitute_medicine_codes) VALUES(1,'12333');
INSERT INTO medicine_substitute_medicine_codes(medicine_id, substitute_medicine_codes) VALUES(2,'1234');


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
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 7);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 6);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 4);
--patient
INSERT INTO PATIENTS (id,user_id,points,category,penal,point) VALUES (1,5,10,0,0,0);
INSERT INTO PATIENTS (id,user_id,points,category,penal,point) VALUES (2,6,100,0,0,0);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (1,1);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (1,2);
INSERT INTO patients_allergies_medicine(patient_id, allergies_medicine_id) values (2,2);

--suplier 
INSERT INTO suplier(id,user_id) VALUES (1,6);

INSERT INTO ADDRESS (street,city) VALUES ('Bulevar oslobodjenja 4', 'Novi Sad');
INSERT INTO ADDRESS (street,city) VALUES ('Kralja Petra 20', 'Beograd');
--pharmacy
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Jankovic', 'Opis Jankovic',2);
INSERT INTO PHARMACY (name, description, address_id) VALUES ('Benu', 'Opis Benu',1);

--pharmacyadmin

INSERT INTO PHARMACYADMIN (id, pharmacy_id, user_id) VALUES (1,2,9);


--dermatologist 
INSERT INTO DERMATOLOGIST (id, pharmacy_id, user_id) VALUES (1,1,7);
INSERT INTO DERMATOLOGIST (id, pharmacy_id, user_id) VALUES (2,2,7);
--appoitment
INSERT INTO APPOITMENT (id, duration, start_date_time, description, dermatologist_id, patient_id) VALUES (1,30,'2017-10-01 21:58:58.508-07','opis1',1,1);
INSERT INTO APPOITMENT (id, duration, start_date_time, description, dermatologist_id, patient_id) VALUES (2,30,'2017-10-02 21:58:58.508-07','opis2',1,2);

--appoitment price
INSERT INTO APPOITMENTPRICE (id,price,appoitment_id) VALUES (1,400,1);
INSERT INTO APPOITMENTPRICE (id,price,appoitment_id) VALUES (2,500,2);

--pharmacy appoitment prices
INSERT INTO pharmacy_appoitment_prices (pharmacy_id, appoitment_prices_id) VALUES (1,1);
INSERT INTO pharmacy_appoitment_prices (pharmacy_id, appoitment_prices_id) VALUES (1,2);


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

--medicine price
INSERT INTO MEDICINEPRICE (id, price,start_date,end_date, medicine_id) VALUES (1, 200, '2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07',1);
INSERT INTO MEDICINEPRICE (id, price,start_date,end_date, medicine_id) VALUES (2, 500, '2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07',1);
INSERT INTO MEDICINEPRICE (id, price,start_date,end_date, medicine_id) VALUES (3, 300, '2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07',2);

--medicine with quantity
INSERT INTO MEDICINEWITHQUANTITY (id, quantity, medicine_id) VALUES (1, 20, 1);
INSERT INTO MEDICINEWITHQUANTITY (id, quantity, medicine_id) VALUES (2, 30, 1);
INSERT INTO MEDICINEWITHQUANTITY (id, quantity, medicine_id) VALUES (3, 50, 2);

--pharmacy medicine prices
INSERT INTO pharmacy_medicine_prices (pharmacy_id, medicine_prices_id) VALUES (1, 1);
INSERT INTO pharmacy_medicine_prices (pharmacy_id, medicine_prices_id) VALUES (1, 3);

--pharmacy medicine with quantity
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 1);
INSERT INTO pharmacy_medicine_with_quantity (pharmacy_id, medicine_with_quantity_id) VALUES (1, 3);

--pharmacist
INSERT INTO PHARMACIST (id, pharmacy_id, user_id) VALUES (1,1,8);
INSERT INTO PHARMACIST (id, pharmacy_id, user_id) VALUES (2,2,8);

--pharmacist rating
INSERT INTO pharmacist_ratings (pharmacist_id, ratings_id) VALUES (1,1);
INSERT INTO pharmacist_ratings (pharmacist_id, ratings_id) VALUES (1,3);

--pharmacy rating
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (1,3);
INSERT INTO pharmacy_ratings (pharmacy_id, ratings_id) VALUES (1,5);

--actions and benefits
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'brufen 20%','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'kupis 2 dobijes 3','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');
INSERT INTO actionandbenefit (description,start_date,end_date) VALUES ( 'paracetamol 10%','2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07');

--pharmacy actions and benefits
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,1);
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,2);
INSERT INTO pharmacy_actions_and_benefits(pharmacy_id,actions_and_benefits_id) VALUES (1,3);


--dermatologist complaint
INSERT INTO dermatologist_complaint(id,content,dermatologist_id) VALUES (1,'Nije ljubazan',1);

--patient dermatologist complaint
INSERT INTO patients_dermatologist_complaints(patient_id,dermatologist_complaints_id) VALUES (1,1);


--holiday request
INSERT INTO holidayrequest(id,start_date,end_date,status) VALUES (1,'2017-10-01 21:58:58.508-07','2017-12-01 21:58:58.508-07',0);

--dermatologist holiday request
INSERT INTO dermatologist_holiday_requests(dermatologist_id,holiday_requests_id) VALUES (1,1);

--pharmacist holiday request
INSERT INTO pharmacist_holiday_requests(pharmacist_id,holiday_requests_id) VALUES (1,1);


--working days
INSERT INTO workingday (day,end_time,start_time) VALUES ('2021-10-01','13:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES ('2021-10-02','13:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES ('2021-10-03','13:00:00.000000', '08:00:00.000000');
INSERT INTO workingday (day,end_time,start_time) VALUES ('2021-10-04','13:00:00.000000', '08:00:00.000000');

--working time
INSERT INTO workingtime (pharmacy_id) VALUES (2);

--working time with working days
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,1);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,2);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,3);
INSERT INTO workingtime_working_days (working_time_id, working_days_id) VALUES (1,4);


--dermatologist working time
INSERT INTO dermatologist_working_times (dermatologist_id, working_times_id) VALUES (2,1); 

--pharmacist working time
INSERT INTO pharmacist_working_times (pharmacist_id, working_times_id) VALUES (2,1); 



--eprescription
INSERT INTO eprescription(id,issued_date,patient_id) VALUES (1,'2017-10-01 21:58:58.508-07',1);
INSERT INTO eprescription(id,issued_date,patient_id) VALUES (2,'2017-10-01 21:58:58.508-07',2);

--eprescription medicine
INSERT INTO eprescription_medicines(eprescription_id,medicines_id) values (1,1);
INSERT INTO eprescription_medicines(eprescription_id,medicines_id) values (2,2);

--medicine rating 

INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,1);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,5);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (1,4);
INSERT INTO medicine_ratings(medicine_id,ratings_id) VALUES (2,3);

--medicine order
INSERT INTO medicineorder(id,status,time_limit) VALUES (1,0,'2017-10-01 21:58:58.508-07');
INSERT INTO medicineorder(id,status,time_limit) VALUES (2,1,'2017-10-29 21:58:58.508-07');
INSERT INTO medicineorder(id,status,time_limit) VALUES (3,0,'2017-12-04 21:58:58.508-07');

--pharmacy medicine order
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (1,1);
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (1,2);
INSERT INTO pharmacy_medicine_orders(pharmacy_id, medicine_orders_id) VALUES (1,3);


--medicine order medicines
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (1,1);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (2,1);
INSERT INTO medicineorder_medicines(medicine_order_id,medicines_id) VALUES (3,2);

--medicine reservation
INSERT INTO medicinereservation(id,due_to,status,medicine_with_quantity_id,patient_id) VALUES (1,'2017-12-04 21:58:58.508-07',0,1,1);
INSERT INTO medicinereservation(id,due_to,status,medicine_with_quantity_id,patient_id) VALUES (2,'2017-10-04 21:58:58.508-07',0,2,1);
INSERT INTO medicinereservation(id,due_to,status,medicine_with_quantity_id,patient_id) VALUES (3,'2017-12-04 21:58:58.508-07',0,1,2);

--pharmacy medicine reservations
INSERT INTO pharmacy_medicine_reservations(pharmacy_id,medicine_reservations_id) VALUES (1,1);
INSERT INTO pharmacy_medicine_reservations(pharmacy_id,medicine_reservations_id) VALUES (1,2);

--pharmacist complaint
INSERT INTO pharmacist_complaint(id,content,pharmacist_id) VALUES (1,'Skup',1);

--patients pharmacist complaint
INSERT INTO patients_pharmacist_complaints(patient_id,pharmacist_complaints_id) VALUES (1,1);

--pharmacy complaint
INSERT INTO pharmacy_complaint(id,content,pharmacy_id) VALUES (1,'Lose osoblje',1);

--patients phamracy complaint
INSERT INTO patients_pharmacy_complaints(patient_id,pharmacy_complaints_id) VALUES (1,1);

--request for medicine availabitlity
INSERT INTO requestformedicineavailability(id,created_at,medicine_with_quantity_id) VALUES (1,'2021-07-04 21:58:58.508-07',1);

--suplier offer 
INSERT INTO suplieroffer(id,delevery_time,price,medicine_order_id) VALUES (1,'2017-10-01 21:58:58.508-07',500,1);
INSERT INTO suplieroffer(id,delevery_time,price,medicine_order_id) VALUES (2,'2017-10-01 21:58:58.508-07',500,2);

--suplier offers 
INSERT INTO suplier_offers(suplier_id,offers_id) VALUES (1,1);
INSERT INTO suplier_offers(suplier_id,offers_id) VALUES (1,2);

--theraphy
INSERT INTO therapy(id,duration,medicine_id) VALUES (1,4,1);
INSERT INTO therapy(id,duration,medicine_id) VALUES (2,6,2);

--loyalty scale
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (1,0,0,0);
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (2,1,20,10);
INSERT INTO loyaltyscale(id,category,needed_points,discount) VALUES (3,2,60,30);

