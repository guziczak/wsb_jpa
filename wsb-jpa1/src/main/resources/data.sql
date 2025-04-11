INSERT INTO ADDRESS (city, addressLine1, addressLine2, postalCode)
VALUES ('Warsaw', 'Street 1', 'Apt 10', '00-001'),
('Kraków', 'ul. Floriańska 5', NULL, '31-019');

INSERT INTO DOCTOR (firstName, lastName, telephoneNumber, email, doctorNumber, specialization, address_id)
VALUES ('John', 'Doe', '123456789', 'john.doe@example.com', 'D123', 'SURGEON', 1);


INSERT INTO Patient (firstName, lastName, telephoneNumber, email, patientNumber, dateOfBirth, address_id) VALUES
('Marek', 'Wiśniewski', '111222333', 'marek.w@example.com', 'P001', '1985-06-15', 2);


INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES
('Regular checkup', '2025-03-26 10:00:00', 1, 1),
('Neurological consultation', '2025-03-27 15:30:00', 1, 1);

INSERT INTO Medical_Treatment (description, type, visit_id) VALUES
('Blood pressure check', 'EKG', 1),
('MRI scan', 'RTG', 2);