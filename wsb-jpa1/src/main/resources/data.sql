-- Addresses
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (901, 'Warszawa', 'ul. Marszałkowska 123', 'Apartament 45', '60-400');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (902, 'Poznań', 'ul. Piłsudskiego 77', NULL, '67-100');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (903, 'Kraków', 'ul. Wolności 33', 'Mieszkanie 12', '30-072');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (904, 'Gdańsk', 'ul. Długa 15', NULL, '80-827');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (905, 'Wrocław', 'ul. Podwale 11', 'Lokal 3', '50-043');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (906, 'Katowice', 'ul. Teatralna 8', NULL, '40-007');

-- Doctor addresses
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (907, 'Warszawa', 'ul. Czerniakowska 87', 'Gabinet 12', '00-718');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (908, 'Kraków', 'ul. Szewska 22', NULL, '31-009');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (909, 'Poznań', 'ul. Słowackiego 5', 'Pokój 101', '60-823');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (910, 'Gdańsk', 'ul. Szeroka 35', NULL, '80-887');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (911, 'Wrocław', 'ul. Świdnicka 19', 'Piętro 2', '50-068');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (912, 'Katowice', 'ul. Korfantego 4', NULL, '40-004');

-- Doctors
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (1, 'Jan', 'Kowalski', 'DOC001', '123456789', 'jan.kowalski@example.com', 'CARDIOLOGIST', 907);
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (2, 'Anna', 'Nowak', 'DOC002', '987654321', 'anna.nowak@example.com', 'NEUROLOGIST', 908);
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (3, 'Piotr', 'Wiśniewski', 'DOC003', '555666777', 'piotr.wisniewski@example.com', 'DERMATOLOGIST', 909);
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (4, 'Maria', 'Dąbrowska', 'DOC004', '111222333', 'maria.dabrowska@example.com', 'OPHTHALMOLOGIST', 910);
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (5, 'Tomasz', 'Lewandowski', 'DOC005', '444555666', 'tomasz.lewandowski@example.com', 'PEDIATRICIAN', 911);
INSERT INTO doctor (id, firstname, lastname, doctornumber, telephonenumber, email, specialization, address_id) VALUES (6, 'Magdalena', 'Kowalczyk', 'DOC006', '777888999', 'magdalena.kowalczyk@example.com', 'PSYCHIATRIST', 912);

-- Patients
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (1, 'Adam', 'Kowalczyk', '111222333', 'adam.kowalczyk@example.com', 'PAT001', '1985-05-15', '2023-01-10', 901, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (2, 'Ewa', 'Nowakowska', '222333444', 'ewa.nowakowska@example.com', 'PAT002', '1990-08-22', '2023-02-15', 902, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (3, 'Michał', 'Lewandowski', '333444555', 'michal.lewandowski@example.com', 'PAT003', '1978-11-30', '2023-03-20', 903, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (4, 'Katarzyna', 'Wójcik', '444555666', 'katarzyna.wojcik@example.com', 'PAT004', '1982-04-12', '2023-04-25', 904, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (5, 'Robert', 'Kamiński', '555666777', 'robert.kaminski@example.com', 'PAT005', '1995-07-18', '2023-05-30', 905, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (6, 'Agnieszka', 'Zielińska', '666777888', 'agnieszka.zielinska@example.com', 'PAT006', '1980-09-25', '2023-06-05', 906, 0);

-- Additional patients for testing queries from Lab3
-- Patients with same last name for lastName query testing
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (913, 'Gdynia', 'ul. Portowa 55', NULL, '81-350');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (914, 'Szczecin', 'ul. Nadbrzeżna 12', 'Mieszkanie 7', '70-207');
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (7, 'Krzysztof', 'Lewandowski', '777888999', 'krzysztof.lewandowski@example.com', 'PAT007', '1988-12-10', '2023-07-15', 913, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (8, 'Marta', 'Lewandowski', '888999000', 'marta.lewandowski@example.com', 'PAT008', '1992-02-28', '2023-08-20', 914, 0);

-- Patients with different registration dates for testing dateOfRegister query
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (915, 'Łódź', 'ul. Piotrkowska 67', NULL, '90-422');
INSERT INTO address (id, city, addressline1, addressline2, postalcode) VALUES (916, 'Białystok', 'ul. Sienkiewicza 21', 'Mieszkanie 14', '15-092');
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (9, 'Dominik', 'Szymański', '999000111', 'dominik.szymanski@example.com', 'PAT009', '1975-03-17', '2024-01-10', 915, 0);
INSERT INTO patient (id, firstname, lastname, telephonenumber, email, patientnumber, dateofbirth, date_of_register, address_id, version) VALUES (10, 'Natalia', 'Wysocka', '000111222', 'natalia.wysocka@example.com', 'PAT010', '1998-06-05', '2024-02-15', 916, 0);

-- Visits
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (1, 'Konsultacja kardiologiczna', '2023-07-10 10:00:00', 1, 1);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (2, 'Kontrola neurologiczna', '2023-07-12 14:30:00', 2, 2);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (3, 'Badanie skóry', '2023-07-15 11:15:00', 3, 3);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (4, 'Badanie wzroku', '2023-07-18 09:00:00', 4, 4);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (5, 'Bilans dziecka', '2023-07-20 13:45:00', 5, 5);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (6, 'Konsultacja psychiatryczna', '2023-07-22 16:30:00', 6, 6);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (7, 'Kontrola kardiologiczna', '2023-08-10 10:00:00', 1, 1);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (8, 'Konsultacja neurologiczna', '2023-08-15 15:00:00', 2, 3);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (9, 'Badanie znamion', '2023-08-20 12:30:00', 3, 2);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (10, 'Kontrola okulistyczna', '2023-08-25 09:45:00', 4, 4);

-- Additional visits for patient with ID=3 (to test "more than X visits" query)
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (11, 'Kontrola dermatologiczna', '2023-09-05 11:30:00', 3, 3);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (12, 'Konsultacja neurologiczna', '2023-09-15 14:00:00', 2, 3);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (13, 'Konsultacja kardiologiczna', '2023-09-25 10:30:00', 1, 3);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (14, 'Badanie skóry - kontrola', '2023-10-05 13:15:00', 3, 3);

-- Medical Treatments
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (1, 'EKG', 'Badanie elektrycznej aktywności serca', 'DIAGNOSTIC', 1);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (2, 'Badanie USG głowy', 'Badanie ultrasonograficzne mózgu', 'DIAGNOSTIC', 2);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (3, 'Biopsja skóry', 'Pobranie próbki skóry do badania', 'SURGERY', 3);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (4, 'Badanie dna oka', 'Ocena stanu siatkówki i naczyń krwionośnych oka', 'DIAGNOSTIC', 4);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (5, 'Szczepienie', 'Szczepienie ochronne zgodnie z kalendarzem szczepień', 'PREVENTION', 5);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (6, 'Terapia poznawczo-behawioralna', 'Sesja psychoterapeutyczna', 'THERAPY', 6);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (7, 'Echo serca', 'Badanie echokardiograficzne', 'DIAGNOSTIC', 7);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (8, 'Badanie EMG', 'Elektromiografia', 'DIAGNOSTIC', 8);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (9, 'Usunięcie znamienia', 'Chirurgiczne usunięcie zmiany skórnej', 'SURGERY', 9);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (10, 'Badanie ciśnienia śródgałkowego', 'Pomiar ciśnienia wewnątrz gałki ocznej', 'DIAGNOSTIC', 10);

-- Additional treatments for patient 3 visits
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (11, 'Dermoskopia', 'Badanie znamion przy pomocy dermoskopu', 'DIAGNOSTIC', 11);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (12, 'Rezonans magnetyczny', 'Badanie MRI głowy', 'DIAGNOSTIC', 12);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (13, 'Próba wysiłkowa', 'Test obciążeniowy na bieżni', 'DIAGNOSTIC', 13);
INSERT INTO medical_treatment (id, name, description, treatmenttype, visit_id) VALUES (14, 'Krioterapia', 'Usunięcie zmian skórnych przy pomocy ciekłego azotu', 'THERAPY', 14);

-- Reset sequences to start after the highest ID in each table
ALTER TABLE patient ALTER COLUMN id RESTART WITH 11;
ALTER TABLE address ALTER COLUMN id RESTART WITH 917;
ALTER TABLE doctor ALTER COLUMN id RESTART WITH 7;
ALTER TABLE visit ALTER COLUMN id RESTART WITH 15;
ALTER TABLE medical_treatment ALTER COLUMN id RESTART WITH 15;