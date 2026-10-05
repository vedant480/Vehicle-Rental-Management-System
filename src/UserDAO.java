import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Register a new user
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users " +
                     "(name, email, phone, license_no, username, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getLicenseNo());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Registration failed!");
            e.printStackTrace();

            return false;
        }
    }

    // Login user
    public User loginUser(String username, String password) {

        String sql = "SELECT * FROM users " +
                     "WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setLicenseNo(rs.getString("license_no"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                return user;
            }

        } catch (SQLException e) {

            System.out.println("Login failed!");
            e.printStackTrace();
        }

        return null;
    }

    public void viewAllCustomers() {

    String sql = "SELECT user_id, name, email, phone, " +
                 "license_no, username FROM users";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("\n======================================================================");
        System.out.printf("%-5s %-15s %-25s %-15s %-18s %-15s%n",
                "ID", "Name", "Email", "Phone",
                "License No.", "Username");
        System.out.println("======================================================================");

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.printf("%-5d %-15s %-25s %-15s %-18s %-15s%n",
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("license_no"),
                    rs.getString("username"));
        }

        if (!found) {
            System.out.println("No customers found.");
        }

        System.out.println("======================================================================");

    } catch (SQLException e) {

        System.out.println("Error retrieving customers!");
        e.printStackTrace();
    }
}

}