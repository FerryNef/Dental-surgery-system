
INSERT INTO address (id, street, city, state, zip_code, country) VALUES
                                                                     (1, '10 Dental St', 'Chicago', 'IL', '60007', 'USA'),
                                                                     (2, '13 Dental St', 'New York', 'NY', '10001', 'USA'),
                                                                     (3, '15 Dental St', 'San Francisco', 'CA', '94105', 'USA');



INSERT INTO surgery (id, name, address_id, phone) VALUES
                                                   (10, 'Surgery 10', 1, '555-0101'),
                                                   (13, 'Surgery 13', 2, '555-1313'),
                                                   (15, 'Surgery 15', 3, '555-1515');

-- Dentists
INSERT INTO dentist (id, first_name, last_name, phone, email, specialization) VALUES
                                                                                  (1, 'Tony', 'Smith', '555-1010', 'tony@ads.com', 'Orthodontics'),
                                                                                  (2, 'Helen', 'Pearson', '555-2020', 'helen@ads.com', 'Pediatric'),
                                                                                  (3, 'Robin', 'Plevin', '555-3030', 'robin@ads.com', 'General');

-- Patients
INSERT INTO patient (id, first_name, last_name, phone, email, address_id, date_of_birth) VALUES
                                                                                             (100, 'Gillian', 'White', '555-1111', 'gillian@ads.com', 1, '1985-04-12'),
                                                                                             (105, 'Jill', 'Bell', '555-2222', 'jill@ads.com', 2, '1990-06-23'),
                                                                                             (108, 'Ian', 'MacKay', '555-3333', 'ian@ads.com', 2, '1978-11-09'),
                                                                                             (110, 'John', 'Walker', '555-4444', 'john@ads.com', 3, '1982-01-01');

INSERT INTO bill (amount, bill_date, patient_id, status)
VALUES
    (120.00, '2025-04-13', 100, 'Paid'),
    (75.50, '2025-04-14', 110, 'Unpaid');


-- Appointments
INSERT INTO appointment (id, date, time, status, is_approved, patient_id, dentist_id, surgery_id) VALUES
                                                                                                                              (1, '2013-09-12', '10:00:00', 'Scheduled', true, 100, 1, 15),
                                                                                                                              (2, '2013-09-12', '12:00:00', 'Scheduled', true, 105, 1, 15),
                                                                                                                              (3, '2013-09-12', '10:00:00', 'Scheduled', true, 108, 2, 10),
                                                                                                                              (4, '2013-09-14', '14:00:00', 'Scheduled', true, 108, 2, 10),
                                                                                                                              (5, '2013-09-14', '16:30:00', 'Scheduled', true, 105, 3, 15),
                                                                                                                              (6, '2013-09-15', '18:00:00', 'Scheduled', true, 110, 3, 13);