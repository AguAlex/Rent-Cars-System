package Services;

import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import DB.DBConnection;

public class CarServices {
    private List<Car> cars;

    public CarServices() {}

    public void addNewCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter car brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter car model: ");
        String model = scanner.nextLine();

        System.out.print("Enter car year: ");
        int year = scanner.nextInt();

        System.out.print("Enter price per day: ");
        double pricePerDay = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Select car type:");
        System.out.println("1. Suv");
        System.out.println("2. Electric");
        System.out.println("3. Sedan");
        int carType = scanner.nextInt();
        scanner.nextLine();

        String car_type = null;
        Integer batteryRange = null;
        Integer numberOfDoors = null;
        Boolean hasAWD = null;

        switch (carType) {
            case 1:
                car_type = "Suv";
                System.out.print("Does it have AWD? (true/false): ");
                hasAWD = scanner.nextBoolean();
                break;
            case 2:
                car_type = "Electric";
                System.out.print("Enter battery range (km): ");
                batteryRange = scanner.nextInt();
                break;
            case 3:
                car_type = "Sedan";
                System.out.print("Enter number of doors: ");
                numberOfDoors = scanner.nextInt();
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                return;
        }

        String query = "INSERT INTO cars (brand, model, year, price_per_day, available, car_type, battery_range, number_of_doors, has_awd) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setDouble(4, pricePerDay);
            stmt.setBoolean(5, true);
            stmt.setString(6, car_type);

            if (batteryRange != null)
                stmt.setInt(7, batteryRange);
            else
                stmt.setNull(7, Types.INTEGER);

            if (numberOfDoors != null)
                stmt.setInt(8, numberOfDoors);
            else
                stmt.setNull(8, Types.INTEGER);

            if (hasAWD != null)
                stmt.setBoolean(9, hasAWD);
            else
                stmt.setNull(9, Types.BOOLEAN);

            stmt.executeUpdate();

            System.out.println("Car added successfully!");
            AuditService.getInstance().logAction("Add new car");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cars.add(rowToClass(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void showCars() {
        List<Car> cars = getAllCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    // Transforma un rand din baza de date in clasa corespunzatoare tipului de masina
    private Car rowToClass(ResultSet rs) throws SQLException {
        String carType = rs.getString("car_type");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        double pricePerDay = rs.getDouble("price_per_day");
        boolean available = rs.getBoolean("available");
        int id = rs.getInt("id");

        switch (carType) {
            case "Electric":
                int batteryRange = rs.getInt("battery_range");
                Electric eCar = new Electric(brand, model, year, pricePerDay, batteryRange);
                eCar.setAvailable(available);

                setCarId(eCar, id);
                return eCar;

            case "Sedan":
                int numberOfDoors = rs.getInt("number_of_doors");
                Sedan sedan = new Sedan(brand, model, year, pricePerDay, numberOfDoors);
                sedan.setAvailable(available);
                setCarId(sedan, id);
                return sedan;

            case "Suv":
                boolean hasAWD = rs.getBoolean("has_awd");
                Suv suv = new Suv(brand, model, year, pricePerDay, hasAWD);
                suv.setAvailable(available);
                setCarId(suv, id);
                return suv;

            default:
                Car car = new Car(brand, model, year, pricePerDay);
                car.setAvailable(available);
                setCarId(car, id);
                return car;
        }
    }

    private void setCarId(Car car, int id) {
        try {
            java.lang.reflect.Field field = Car.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(car, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Car getCarById(int id) {
        String query = "SELECT * FROM cars WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rowToClass(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Car> searchByBrandOrModel(String searchTerm, boolean searchByBrand) {
        List<Car> cars = new ArrayList<>();
        String query = searchByBrand ? "SELECT * FROM cars WHERE brand = ?" : "SELECT * FROM cars WHERE model = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, searchTerm);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cars.add(rowToClass(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void searchByModelOrBrand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select (1-Model, 2-Brand): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter a model: ");
                String model = scanner.nextLine();
                List<Car> modelCars = searchByBrandOrModel(model, false);
                if (modelCars.isEmpty()) {
                    System.out.println("No cars with model '" + model + "' available!");
                } else {
                    modelCars.forEach(System.out::println);
                }
                break;
            case 2:
                System.out.print("Enter a brand: ");
                String brand = scanner.nextLine();
                List<Car> brandCars = searchByBrandOrModel(brand, true);
                if (brandCars.isEmpty()) {
                    System.out.println("No cars with brand '" + brand + "' available!");
                } else {
                    brandCars.forEach(System.out::println);
                }
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

    public List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars WHERE available = true";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cars.add(rowToClass(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void showAvailableCars() {
        List<Car> cars = getAvailableCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
}
