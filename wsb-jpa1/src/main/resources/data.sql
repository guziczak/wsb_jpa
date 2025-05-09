INSERT INTO ADDRESS (city, addressLine1, addressLine2, postalCode)
VALUES ('Warsaw', 'Street 1', 'Apt 10', '00-001'),
('Kraków', 'ul. Floriańska 5', NULL, '31-019');

INSERT INTO ADDRESS (city, addressLine1, addressLine2, postalCode) VALUES
('Wrocław', 'ul. Testowa 55', NULL, '60-400'),
('Gdańsk', 'ul. Długa 12', NULL, '80-831'),
('Poznań', 'ul. Ratajczaka 20', 'lok. 5', '61-816'),
('Szczecin', 'ul. Pomorska 7', NULL, '70-200'),
('Lublin', 'ul. Lipowa 3', NULL, '20-400'),
('Katowice', 'ul. Młyńska 1', 'apt. 6', '40-098'),
('Białystok', 'ul. Mickiewicza 15', NULL, '15-213'),
('Łódź', 'ul. Piotrkowska 99', 'lok. 8', '90-005'),
('Opole', 'ul. Krakowska 17', NULL, '45-300'),
('Rzeszów', 'ul. Hetmańska 9', NULL, '35-020');

--INSERT INTO ADDRESS (id, city, addressLine1, addressLine2, postalCode)
--VALUES (901, 'Wroclaw', 'ul. Testowa 55', NULL, '60-400');


INSERT INTO DOCTOR (firstName, lastName, telephoneNumber, email, doctorNumber, specialization, address_id) VALUES
('John', 'Doe', '123456789', 'john.doe@example.com', 'D123', 'SURGEON', 1),
('Anna', 'Nowak', '222333444', 'anna.nowak@example.com', 'D124', 'GP', 2),
('Piotr', 'Kowalski', '333444555', 'piotr.k@example.com', 'D125', 'DERMATOLOGIST', 3),
('Ewa', 'Wiśniewska', '444555666', 'ewa.w@example.com', 'D126', 'OCULIST', 4),
('Tomasz', 'Mazur', '555666777', 't.mazur@example.com', 'D127', 'SURGEON', 5),
('Karolina', 'Zawadzka', '666777888', 'karolina.z@example.com', 'D128', 'GP', 6);

INSERT INTO PATIENT (firstName, lastName, telephoneNumber, email, patientNumber, dateOfBirth, address_id) VALUES
('Marek', 'Lewandowski', '111222333', 'marek.l@example.com', 'P001', '1985-06-15', 7),
('Ewa', 'Kwiatkowska', '444555666', 'ewa.k@example.com', 'P002', '1990-01-20', 8),
('Tomasz', 'Kaczmarek', '555666777', 'tomasz.k@example.com', 'P003', '1978-09-05', 9),
('Natalia', 'Grabowska', '777888999', 'natalia.g@example.com', 'P004', '1995-12-12', 10),
('Jan', 'Lewandowski', '888999000', 'jan.l@example.com', 'P005', '1980-03-30', 1),
('Katarzyna', 'Kaczmarek', '999000111', 'k.k@example.com', 'P006', '1988-07-22', 2);


INSERT INTO VISIT (description, time, doctor_id, patient_id) VALUES
('Regular checkup', '2025-03-26 10:00:00', 1, 1),
('Neurological consultation', '2025-03-27 15:30:00', 1, 1),
('Skin check', '2025-04-01 09:00:00', 3, 2),
('Vision test', '2025-04-02 11:00:00', 4, 3),
('Post-surgery control', '2025-04-03 14:30:00', 1, 4),
('General consultation', '2025-04-04 12:00:00', 2, 5),
('Eye infection treatment', '2025-04-05 16:00:00', 4, 6),
('Dermatology follow-up', '2025-04-06 13:15:00', 3, 1),
('GP visit for cold symptoms', '2025-04-07 10:45:00', 6, 2),
('Post-op visit', '2025-04-08 15:00:00', 5, 3);

INSERT INTO MEDICAL_TREATMENT (description, type, visit_id) VALUES
('Blood pressure check', 'EKG', 1),
('MRI scan', 'RTG', 2),
('Skin biopsy', 'RTG', 3),
('Eye acuity test', 'USG', 4),
('Wound healing check', 'USG', 5),
('Basic health screening', 'EKG', 6),
('Eye drops prescription', 'USG', 7),
('Mole removal check', 'RTG', 8),
('Flu diagnostics', 'EKG', 9),
('Surgical wound control', 'USG', 10);