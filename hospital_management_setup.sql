
-- Create Database
CREATE DATABASE IF NOT EXISTS hospital_db;

-- Use the database
USE hospital_db;

-- Create doctor table
CREATE TABLE IF NOT EXISTS doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    specialization VARCHAR(255)
);

-- Insert sample data
INSERT INTO doctor (name, specialization) VALUES 
('Dr. Smith', 'Cardiology'),
('Dr. Johnson', 'Neurology'),
('Dr. Williams', 'Orthopedics');

-- Select all data from doctor table
SELECT * FROM doctor;
