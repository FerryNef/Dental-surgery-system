
SELECT * 
FROM Dentist 
ORDER BY last_name ASC;


SELECT 
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    a.status,
    a.is_approved,
    p.patient_id,
    p.first_name AS patient_first_name,
    p.last_name AS patient_last_name,
    p.phone AS patient_phone,
    p.email AS patient_email
FROM Appointment a
JOIN Patient p ON a.patient_id = p.patient_id
WHERE a.dentist_id = 1;  


SELECT 
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    s.surgery_id,
    s.name AS surgery_name,
    s.address AS surgery_address,
    s.phone AS surgery_phone
FROM Appointment a
JOIN Surgery s ON a.surgery_id = s.surgery_id;


SELECT 
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    a.status,
    d.first_name AS dentist_first_name,
    d.last_name AS dentist_last_name
FROM Appointment a
JOIN Dentist d ON a.dentist_id = d.dentist_id
WHERE a.patient_id = 108 
  AND a.appointment_date = '2013-09-14';
