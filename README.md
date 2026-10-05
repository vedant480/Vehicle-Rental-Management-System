# Vehicle Rental Management System

A console-based Vehicle Rental Management System developed using
Core Java, JDBC, and MySQL.

The system allows customers to register, log in, search for vehicles,
make rentals, view bookings, and cancel bookings. Administrators can
manage vehicles, customers, bookings, and revenue.

## Features

### Customer
- Customer registration
- Customer login
- View all vehicles
- Search available vehicles
- Rent a vehicle
- View personal bookings
- Cancel bookings
- Logout

### Admin
- Admin login
- Add vehicles
- View all vehicles
- Update vehicle details
- Delete vehicles
- View all customers
- View all bookings
- View revenue
- Logout

## Technologies Used

- Java
- JDBC
- MySQL
- VS Code
- MySQL Connector/J

## Project Structure

```text
Vehicle-Rental-Management-System/
│
├── src/
│   ├── Main.java
│   ├── DBConnection.java
│   ├── User.java
│   ├── UserDAO.java
│   ├── Vehicle.java
│   ├── VehicleDAO.java
│   ├── Booking.java
│   └── BookingDAO.java
│
├── database/
│   └── vehicle_rental_db.sql
│
├── lib/
│   └── mysql-connector-j.jar
│
├── .gitignore
└── README.md