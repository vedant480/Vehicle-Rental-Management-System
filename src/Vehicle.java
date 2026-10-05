public class Vehicle {

    private int vehicleId;
    private String vehicleNumber;
    private String vehicleName;
    private String vehicleType;
    private String brand;
    private double pricePerDay;
    private String status;

    // Default constructor
    public Vehicle() {
    }

    // Constructor without ID
    public Vehicle(String vehicleNumber, String vehicleName,
                   String vehicleType, String brand,
                   double pricePerDay, String status) {

        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    // Constructor with ID
    public Vehicle(int vehicleId, String vehicleNumber,
                   String vehicleName, String vehicleType,
                   String brand, double pricePerDay,
                   String status) {

        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    // Getters and Setters

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}