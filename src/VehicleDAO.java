import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDAO {

    // Add a vehicle
    public boolean addVehicle(Vehicle vehicle) {

        String sql = "INSERT INTO vehicles " +
                     "(vehicle_number, vehicle_name, vehicle_type, " +
                     "brand, price_per_day, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicle.getVehicleNumber());
            ps.setString(2, vehicle.getVehicleName());
            ps.setString(3, vehicle.getVehicleType());
            ps.setString(4, vehicle.getBrand());
            ps.setDouble(5, vehicle.getPricePerDay());
            ps.setString(6, vehicle.getStatus());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Error adding vehicle!");
            e.printStackTrace();

            return false;
        }
    }


    // View all vehicles
    public void viewAllVehicles() {

        String sql = "SELECT * FROM vehicles";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n==============================================================");
            System.out.printf("%-5s %-15s %-15s %-10s %-12s %-12s%n",
                    "ID", "Number", "Name", "Type", "Price/Day", "Status");
            System.out.println("==============================================================");

            while (rs.next()) {

                System.out.printf("%-5d %-15s %-15s %-10s ₹%-11.2f %-12s%n",
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_name"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("price_per_day"),
                        rs.getString("status"));
            }

            System.out.println("==============================================================");

        } catch (SQLException e) {

            System.out.println("Error retrieving vehicles!");
            e.printStackTrace();
        }
    }
    public void searchAvailableVehicles(String type) {

    String sql = "SELECT * FROM vehicles " +
                 "WHERE vehicle_type = ? AND status = 'Available'";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, type);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n==============================================================");
        System.out.printf("%-5s %-15s %-15s %-10s %-12s%n",
                "ID", "Number", "Name", "Brand", "Price/Day");
        System.out.println("==============================================================");

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.printf("%-5d %-15s %-15s %-10s ₹%-11.2f%n",
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_number"),
                    rs.getString("vehicle_name"),
                    rs.getString("brand"),
                    rs.getDouble("price_per_day"));
        }

        if (!found) {
            System.out.println("No available vehicles found.");
        }

        System.out.println("==============================================================");

    } catch (SQLException e) {
        System.out.println("Error searching vehicles!");
        e.printStackTrace();
    }
}
    public boolean updateVehicle(Vehicle vehicle) {

    String sql = "UPDATE vehicles SET " +
                 "vehicle_number = ?, " +
                 "vehicle_name = ?, " +
                 "vehicle_type = ?, " +
                 "brand = ?, " +
                 "price_per_day = ?, " +
                 "status = ? " +
                 "WHERE vehicle_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, vehicle.getVehicleNumber());
        ps.setString(2, vehicle.getVehicleName());
        ps.setString(3, vehicle.getVehicleType());
        ps.setString(4, vehicle.getBrand());
        ps.setDouble(5, vehicle.getPricePerDay());
        ps.setString(6, vehicle.getStatus());
        ps.setInt(7, vehicle.getVehicleId());

        int rows = ps.executeUpdate();

        return rows > 0;

    } catch (SQLException e) {

        System.out.println("Error updating vehicle!");
        e.printStackTrace();

        return false;
    }
}
    public boolean deleteVehicle(int vehicleId) {

    String checkBooking =
            "SELECT COUNT(*) FROM bookings " +
            "WHERE vehicle_id = ?";

    String deleteVehicle =
            "DELETE FROM vehicles WHERE vehicle_id = ?";

    try (Connection con = DBConnection.getConnection()) {

        // Check whether vehicle has any booking history
        try (PreparedStatement ps = con.prepareStatement(checkBooking)) {

            ps.setInt(1, vehicleId);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                System.out.println(
                        "Cannot delete vehicle! " +
                        "This vehicle has booking history."
                );

                System.out.println(
                        "Please keep the vehicle record for " +
                        "booking history."
                );

                return false;
            }
        }

        // Delete vehicle
        try (PreparedStatement ps = con.prepareStatement(deleteVehicle)) {

            ps.setInt(1, vehicleId);

            int rows = ps.executeUpdate();

            return rows > 0;
        }

    } catch (SQLException e) {

        System.out.println("Error deleting vehicle!");
        e.printStackTrace();

        return false;
    }
}
    public void viewAvailableVehicles() {

    String sql = "SELECT * FROM vehicles " +
                 "WHERE status = 'Available'";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("\n==============================================================");
        System.out.printf("%-5s %-15s %-15s %-10s %-12s %-12s%n",
                "ID", "Number", "Name", "Type", "Brand", "Price/Day");
        System.out.println("==============================================================");

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.printf(
                    "%-5d %-15s %-15s %-10s %-12s ₹%-11.2f%n",
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_number"),
                    rs.getString("vehicle_name"),
                    rs.getString("vehicle_type"),
                    rs.getString("brand"),
                    rs.getDouble("price_per_day")
            );
        }

        if (!found) {
            System.out.println("No vehicles are currently available.");
        }

        System.out.println("==============================================================");

    } catch (SQLException e) {

        System.out.println("Error retrieving available vehicles!");
        e.printStackTrace();
    }
}
}