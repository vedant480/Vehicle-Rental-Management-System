CREATE DATABASE IF NOT EXISTS vehicle_rental_db;

USE vehicle_rental_db;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    license_no VARCHAR(50),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_number VARCHAR(20) UNIQUE NOT NULL,
    vehicle_name VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(30) NOT NULL,
    brand VARCHAR(50),
    price_per_day DOUBLE NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    rental_days INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'Active',

    FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(vehicle_id)
);

INSERT INTO vehicles
(vehicle_number, vehicle_name, vehicle_type, brand, price_per_day)
VALUES
('MH01AB1234', 'Swift', 'Car', 'Maruti', 1500),
('MH02CD5678', 'Activa', 'Bike', 'Honda', 500),
('MH03EF9012', 'Creta', 'SUV', 'Hyundai', 2500);