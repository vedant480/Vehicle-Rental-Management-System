import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAO {

    public boolean createBooking(Booking booking) {

        String checkVehicle =
                "SELECT price_per_day, status FROM vehicles WHERE vehicle_id = ?";

        String insertBooking =
                "INSERT INTO bookings " +
                "(user_id, vehicle_id, rental_days, total_amount, booking_status) " +
                "VALUES (?, ?, ?, ?, ?)";

        String updateVehicle =
                "UPDATE vehicles SET status = 'Rented' WHERE vehicle_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            // Check vehicle
            try (PreparedStatement ps = con.prepareStatement(checkVehicle)) {

                ps.setInt(1, booking.getVehicleId());

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("Vehicle not found!");
                    return false;
                }

                String status = rs.getString("status");

                if (!status.equalsIgnoreCase("Available")) {
                    System.out.println("Vehicle is not available!");
                    return false;
                }

                double pricePerDay = rs.getDouble("price_per_day");

                double totalAmount =
                        pricePerDay * booking.getRentalDays();

                booking.setTotalAmount(totalAmount);
            }

            // Insert booking
            try (PreparedStatement ps = con.prepareStatement(insertBooking)) {

                ps.setInt(1, booking.getUserId());
                ps.setInt(2, booking.getVehicleId());
                ps.setInt(3, booking.getRentalDays());
                ps.setDouble(4, booking.getTotalAmount());
                ps.setString(5, booking.getBookingStatus());

                int rows = ps.executeUpdate();

                if (rows == 0) {
                    return false;
                }
            }

            // Change vehicle status
            try (PreparedStatement ps = con.prepareStatement(updateVehicle)) {

                ps.setInt(1, booking.getVehicleId());

                ps.executeUpdate();
            }

            return true;

        } catch (SQLException e) {

            System.out.println("Booking failed!");
            e.printStackTrace();

            return false;
        }
    }

    public void viewUserBookings(int userId) {

        String sql =
                "SELECT b.booking_id, v.vehicle_number, " +
                "v.vehicle_name, b.rental_days, " +
                "b.total_amount, b.booking_status " +
                "FROM bookings b " +
                "JOIN vehicles v ON b.vehicle_id = v.vehicle_id " +
                "WHERE b.user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n==============================================================");
            System.out.printf("%-5s %-15s %-15s %-10s %-12s %-12s%n",
                    "ID", "Vehicle No.", "Vehicle", "Days",
                    "Amount", "Status");
            System.out.println("==============================================================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.printf("%-5d %-15s %-15s %-10d ₹%-11.2f %-12s%n",
                        rs.getInt("booking_id"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_name"),
                        rs.getInt("rental_days"),
                        rs.getDouble("total_amount"),
                        rs.getString("booking_status"));
            }

            if (!found) {
                System.out.println("No bookings found.");
            }

            System.out.println("==============================================================");

        } catch (SQLException e) {

            System.out.println("Error retrieving bookings!");
            e.printStackTrace();
        }
    }

    public boolean cancelBooking(int bookingId, int userId) {

    String findBooking =
            "SELECT vehicle_id, booking_status " +
            "FROM bookings " +
            "WHERE booking_id = ? AND user_id = ?";

    String cancelBooking =
            "UPDATE bookings " +
            "SET booking_status = 'Cancelled' " +
            "WHERE booking_id = ? AND user_id = ?";

    String updateVehicle =
            "UPDATE vehicles " +
            "SET status = 'Available' " +
            "WHERE vehicle_id = ?";

    try (Connection con = DBConnection.getConnection()) {

        int vehicleId;

        // Find booking
        try (PreparedStatement ps = con.prepareStatement(findBooking)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Booking not found!");
                return false;
            }

            String status = rs.getString("booking_status");

            if (!status.equalsIgnoreCase("Active")) {
                System.out.println("This booking is already cancelled.");
                return false;
            }

            vehicleId = rs.getInt("vehicle_id");
        }

        // Cancel booking
        try (PreparedStatement ps = con.prepareStatement(cancelBooking)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }

        // Make vehicle available again
        try (PreparedStatement ps = con.prepareStatement(updateVehicle)) {

            ps.setInt(1, vehicleId);

            ps.executeUpdate();
        }

        return true;

    } catch (SQLException e) {

        System.out.println("Error cancelling booking!");
        e.printStackTrace();

        return false;
    }
}

    public void viewAllBookings() {

    String sql =
            "SELECT b.booking_id, " +
            "u.name AS customer_name, " +
            "v.vehicle_number, " +
            "v.vehicle_name, " +
            "b.rental_days, " +
            "b.total_amount, " +
            "b.booking_status " +
            "FROM bookings b " +
            "JOIN users u ON b.user_id = u.user_id " +
            "JOIN vehicles v ON b.vehicle_id = v.vehicle_id " +
            "ORDER BY b.booking_id";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("\n================================================================================");
        System.out.printf("%-5s %-18s %-15s %-15s %-8s %-12s %-12s%n",
                "ID", "Customer", "Vehicle No.",
                "Vehicle", "Days", "Amount", "Status");
        System.out.println("================================================================================");

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.printf("%-5d %-18s %-15s %-15s %-8d ₹%-11.2f %-12s%n",
                    rs.getInt("booking_id"),
                    rs.getString("customer_name"),
                    rs.getString("vehicle_number"),
                    rs.getString("vehicle_name"),
                    rs.getInt("rental_days"),
                    rs.getDouble("total_amount"),
                    rs.getString("booking_status"));
        }

        if (!found) {
            System.out.println("No bookings found.");
        }

        System.out.println("================================================================================");

    } catch (SQLException e) {

        System.out.println("Error retrieving bookings!");
        e.printStackTrace();
    }
}

    public void viewRevenue() {

    String sql =
            "SELECT " +
            "COUNT(*) AS total_bookings, " +
            "COALESCE(SUM(total_amount), 0) AS total_revenue " +
            "FROM bookings " +
            "WHERE booking_status = 'Active'";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {

            int totalBookings =
                    rs.getInt("total_bookings");

            double totalRevenue =
                    rs.getDouble("total_revenue");

            System.out.println("\n=================================");
            System.out.println("          REVENUE REPORT");
            System.out.println("=================================");

            System.out.println(
                    "Active Bookings : " + totalBookings
            );

            System.out.printf(
                    "Total Revenue   : ₹%.2f%n",
                    totalRevenue
            );

            System.out.println("=================================");
        }

    } catch (SQLException e) {

        System.out.println("Error calculating revenue!");
        e.printStackTrace();
    }
}
}