
CREATE TABLE OfficeManager (
    manager_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE Dentist (
    dentist_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(15),
    email VARCHAR(100),
    specialization VARCHAR(100),
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES OfficeManager(manager_id)
);




CREATE TABLE Patient (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(15),
    email VARCHAR(100),
    address TEXT,
    date_of_birth DATE,
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES OfficeManager(manager_id)
);
CREATE TABLE Surgery (
    surgery_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    address TEXT,
    phone VARCHAR(15)
);

CREATE TABLE Appointment (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_date DATE,
    appointment_time TIME,
    status VARCHAR(50),
    is_approved BOOLEAN,
    patient_id INT,
    dentist_id INT,
    surgery_id INT,
    manager_id INT,
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id),
    FOREIGN KEY (dentist_id) REFERENCES Dentist(dentist_id),
    FOREIGN KEY (surgery_id) REFERENCES Surgery(surgery_id),
    FOREIGN KEY (manager_id) REFERENCES OfficeManager(manager_id)
);


CREATE TABLE Bill (
    bill_id INT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(10, 2),
    status VARCHAR(50),
    patient_id INT,
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);