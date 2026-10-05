import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n========================================");
            System.out.println("      VEHICLE RENTAL MANAGEMENT SYSTEM");
            System.out.println("========================================");

            System.out.println("1. Customer Registration");
            System.out.println("2. Customer Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    registerCustomer();
                    break;

                case 2:
                    customerLogin();
                    break;

                case 3:
                    adminLogin();
                    break;

                case 4:
                    System.out.println("\nThank you for using Vehicle Rental System!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }

    // ==============================
    // CUSTOMER REGISTRATION
    // ==============================

    static void registerCustomer() {

        System.out.println("\n=================================");
        System.out.println("      CUSTOMER REGISTRATION");
        System.out.println("=================================");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter License Number: ");
        String licenseNo = sc.nextLine();

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = new User(
                name,
                email,
                phone,
                licenseNo,
                username,
                password
        );

        UserDAO userDAO = new UserDAO();

        boolean result = userDAO.registerUser(user);

        if (result) {
            System.out.println("\nRegistration successful!");
        } else {
            System.out.println("\nRegistration failed!");
        }
    }

    // ==============================
    // CUSTOMER LOGIN
    // ==============================

    static void customerLogin() {

        System.out.println("\n=================================");
        System.out.println("         CUSTOMER LOGIN");
        System.out.println("=================================");

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        UserDAO userDAO = new UserDAO();

        User user = userDAO.loginUser(username, password);

        if (user != null) {

            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + user.getName() + "!");

            customerMenu(user);

        } else {

            System.out.println("\nInvalid username or password.");
        }
    }

    // ==============================
    // CUSTOMER MENU
    // ==============================

    static void customerMenu(User user) {

        VehicleDAO vehicleDAO = new VehicleDAO();
        BookingDAO bookingDAO = new BookingDAO();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("         CUSTOMER MENU");
            System.out.println("=================================");

            System.out.println("1. View All Vehicles");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    vehicleDAO.viewAllVehicles();

                    break;

                case 2:

                    rentVehicle(user);

                    break;

                case 3:

                    bookingDAO.viewUserBookings(
                            user.getUserId()
                    );

                    break;

                case 4:

                    cancelCustomerBooking(user);

                    break;

                case 5:

                    System.out.println("\nLogged out successfully.");

                    return;

                default:

                    System.out.println("\nInvalid choice!");
            }
        }
    }

    // ==============================
    // RENT VEHICLE
    // ==============================

    static void rentVehicle(User user) {

    VehicleDAO vehicleDAO = new VehicleDAO();

    System.out.println("\n=================================");
    System.out.println("          RENT VEHICLE");
    System.out.println("=================================");

    // Show all available vehicles
    vehicleDAO.viewAvailableVehicles();

    System.out.print("\nEnter Vehicle ID: ");
    int vehicleId = sc.nextInt();

    System.out.print("Enter Rental Days: ");
    int rentalDays = sc.nextInt();

    sc.nextLine();

    if (rentalDays <= 0) {

        System.out.println(
                "\nRental days must be greater than 0."
        );

        return;
    }

    Booking booking = new Booking(
            user.getUserId(),
            vehicleId,
            rentalDays,
            0,
            "Active"
    );

    BookingDAO bookingDAO = new BookingDAO();

    boolean result = bookingDAO.createBooking(booking);

    if (result) {

        System.out.println("\n=================================");
        System.out.println("       BOOKING SUCCESSFUL");
        System.out.println("=================================");

        System.out.println(
                "Vehicle ID: " + vehicleId
        );

        System.out.println(
                "Rental Days: " + rentalDays
        );

        System.out.println(
                "Total Amount: ₹" +
                booking.getTotalAmount()
        );

        System.out.println(
                "Booking Status: Active"
        );

    } else {

        System.out.println("\nBooking failed!");

        System.out.println(
                "Please check that the vehicle exists " +
                "and is available."
        );
    }
}
// ==============================
    // CANCEL BOOKING
    // ==============================

    static void cancelCustomerBooking(User user) {

        BookingDAO bookingDAO = new BookingDAO();

        System.out.println("\n=================================");
        System.out.println("          MY BOOKINGS");
        System.out.println("=================================");

        bookingDAO.viewUserBookings(
                user.getUserId()
        );

        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId = sc.nextInt();

        sc.nextLine();

        boolean result =
                bookingDAO.cancelBooking(
                        bookingId,
                        user.getUserId()
                );

        if (result) {

            System.out.println(
                    "\nBooking cancelled successfully!"
            );

        } else {

            System.out.println(
                    "\nBooking could not be cancelled."
            );
        }
    }

    // ==============================
    // ADMIN LOGIN
    // ==============================

    static void adminLogin() {

        System.out.println("\n=================================");
        System.out.println("           ADMIN LOGIN");
        System.out.println("=================================");

        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();

        // Simple credentials for college project

        if (username.equals("admin") &&
            password.equals("admin123")) {

            System.out.println("\nAdmin login successful!");

            adminMenu();

        } else {

            System.out.println("\nInvalid admin credentials.");
        }
    }

    // ==============================
    // ADMIN MENU
    // ==============================

    static void adminMenu() {

    VehicleDAO vehicleDAO = new VehicleDAO();
    UserDAO userDAO = new UserDAO();
    BookingDAO bookingDAO = new BookingDAO();

    while (true) {

        System.out.println("\n=================================");
        System.out.println("            ADMIN MENU");
        System.out.println("=================================");

        System.out.println("1. Add Vehicle");
        System.out.println("2. View All Vehicles");
        System.out.println("3. Update Vehicle");
        System.out.println("4. Delete Vehicle");
        System.out.println("5. View All Customers");
        System.out.println("6. View All Bookings");
        System.out.println("7. View Revenue");
        System.out.println("8. Logout");

        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                addVehicle();
                break;

            case 2:
                vehicleDAO.viewAllVehicles();
                break;

            case 3:
                updateVehicle();
                break;

            case 4:
                deleteVehicle();
                break;

            case 5:
                userDAO.viewAllCustomers();
                break;

            case 6:
                bookingDAO.viewAllBookings();
                break;

            case 7:
                bookingDAO.viewRevenue();
                break;

            case 8:
                System.out.println("\nAdmin logged out.");
                return;

            default:
                System.out.println("\nInvalid choice!");
        }
    }
}

    // ==============================
    // ADD VEHICLE
    // ==============================

    static void addVehicle() {

        System.out.println("\n=================================");
        System.out.println("           ADD VEHICLE");
        System.out.println("=================================");

        System.out.print("Enter Vehicle Number: ");
        String vehicleNumber = sc.nextLine();

        System.out.print("Enter Vehicle Name: ");
        String vehicleName = sc.nextLine();

        System.out.print("Enter Vehicle Type: ");
        String vehicleType = sc.nextLine();

        System.out.print("Enter Brand: ");
        String brand = sc.nextLine();

        System.out.print("Enter Price Per Day: ");
        double pricePerDay = sc.nextDouble();

        sc.nextLine();

        Vehicle vehicle = new Vehicle(
                vehicleNumber,
                vehicleName,
                vehicleType,
                brand,
                pricePerDay,
                "Available"
        );

        VehicleDAO vehicleDAO = new VehicleDAO();

        boolean result = vehicleDAO.addVehicle(vehicle);

        if (result) {

            System.out.println(
                    "\nVehicle added successfully!"
            );

        } else {

            System.out.println(
                    "\nVehicle could not be added."
            );
        }
    }

    static void updateVehicle() {

    VehicleDAO vehicleDAO = new VehicleDAO();

    System.out.println("\n=================================");
    System.out.println("          UPDATE VEHICLE");
    System.out.println("=================================");

    vehicleDAO.viewAllVehicles();

    System.out.print("\nEnter Vehicle ID to update: ");
    int vehicleId = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter New Vehicle Number: ");
    String vehicleNumber = sc.nextLine();

    System.out.print("Enter New Vehicle Name: ");
    String vehicleName = sc.nextLine();

    System.out.print("Enter New Vehicle Type: ");
    String vehicleType = sc.nextLine();

    System.out.print("Enter New Brand: ");
    String brand = sc.nextLine();

    System.out.print("Enter New Price Per Day: ");
    double pricePerDay = sc.nextDouble();
    sc.nextLine();

    System.out.print("Enter New Status (Available/Rented): ");
    String status = sc.nextLine();

    Vehicle vehicle = new Vehicle(
            vehicleId,
            vehicleNumber,
            vehicleName,
            vehicleType,
            brand,
            pricePerDay,
            status
    );

    boolean result = vehicleDAO.updateVehicle(vehicle);

    if (result) {

        System.out.println(
                "\nVehicle updated successfully!"
        );

    } else {

        System.out.println(
                "\nVehicle update failed!"
        );
    }
}

    static void deleteVehicle() {

    VehicleDAO vehicleDAO = new VehicleDAO();

    System.out.println("\n=================================");
    System.out.println("          DELETE VEHICLE");
    System.out.println("=================================");

    vehicleDAO.viewAllVehicles();

    System.out.print("\nEnter Vehicle ID to delete: ");
    int vehicleId = sc.nextInt();
    sc.nextLine();

    System.out.print(
            "Are you sure you want to delete this vehicle? (yes/no): "
    );

    String confirmation = sc.nextLine();

    if (!confirmation.equalsIgnoreCase("yes")) {

        System.out.println("\nDeletion cancelled.");

        return;
    }

    boolean result = vehicleDAO.deleteVehicle(vehicleId);

    if (result) {

        System.out.println(
                "\nVehicle deleted successfully!"
        );

    } else {

        System.out.println(
                "\nVehicle could not be deleted."
        );
    }
}

}