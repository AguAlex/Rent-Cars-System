package Menu;

import Models.*;
import Services.*;

import java.sql.*;
import java.util.Scanner;
import DB.DBConnection;

public class Menu {
    private Scanner scanner;
    private CarServices carService;
    private ClientServices clientService;

    public Menu(CarServices carService, ClientServices clientService) {
        this.scanner = new Scanner(System.in);
        this.carService = carService;
        this.clientService = clientService;
    }

    public void displayMenu() {
        int choice;
        System.out.println("Welcome to my Car Rental System!");
        while (true) {
            System.out.println();
            System.out.println("✨Main menu✨");
            System.out.println("1. Add a new car \uD83D\uDE97");
            System.out.println("2. Show all cars \uD83C\uDFDB");
            System.out.println("3. Search cars by brand/model \uD83D\uDD0E");
            System.out.println("4. Add a new client \uD83D\uDE47");
            System.out.println("5. Show all clients \uD83D\uDC68\u200D\uD83D\uDC68\u200D\uD83D\uDC66");
            System.out.println("6. Rent a car to a client \uD83E\uDD1D");
            System.out.println("7. Return a rented car \uD83D\uDC4B");
            System.out.println("8. Show reservation history \uD83D\uDCDC");
            System.out.println("9. View active rentals for a specific client \uD83D\uDE4B\uD83C\uDFFD\u200D♀\uFE0F");
            System.out.println("10. View all active rentals \uD83D\uDEE0\uFE0F");
            System.out.println("11. Show available cars \uD83D\uDE97");
            System.out.println("12. Show all clients in alphabetical order.");
            System.out.println("13. Exit \uD83D\uDEAB");
            System.out.print("\uD83D\uDC40 Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                processChoice(choice);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public void processChoice(int choice) {
        switch (choice) {
            case 1:
                carService.addNewCar();
                break;
            case 2:
                carService.showCars();
                break;
            case 3:
                carService.searchByModelOrBrand();
                break;
            case 4:
                clientService.addNewClient();
                break;
            case 5:
                clientService.showClients();
                break;
            case 6:
                rentCar();
                break;
            case 7:
                returnRentedCar();
                break;
            case 8:
                // De implementat in etapa 2
                //carService.showHistory();
                break;
            case 9:
                viewActiveRentals();
                break;
            case 10:
                viewAllActiveRentals();
                break;
            case 11:
                carService.showAvailableCars();
                break;
            case 12:
                clientService.showClientsSorted();
                break;
            case 13:
                System.out.println("Exiting...");
                System.exit(0);
                return;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

    public void rentCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a client id: ");
        int client_id = scanner.nextInt();

        System.out.print("Enter a car id: ");
        int car_id = scanner.nextInt();
        scanner.nextLine();

        Client client = clientService.getClientById(client_id);
        if (client == null) {
            System.out.println("Invalid Client ID!");
            return;
        }

        Car car = carService.getCarById(car_id);
        if (car == null) {
            System.out.println("Invalid Car ID!");
            return;
        }

        if (!car.getAvailable()) {
            System.out.println("This car is already rented!");
            return;
        }

        System.out.print("Enter the start date (yyyy-mm-dd): ");
        String startDate = scanner.nextLine();

        System.out.print("How many days do you want to rent for?: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        double totalPrice = car.calculateRentalPrice(days);

        String insertRental = "INSERT INTO rentals (client_id, car_id, start_date, days, total_price, active) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(insertRental)) {

            stmt.setInt(1, client_id);
            stmt.setInt(2, car_id);
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setInt(4, days);
            stmt.setDouble(5, totalPrice);
            stmt.setBoolean(6, true);
            stmt.executeUpdate();

            String updateCar = "UPDATE cars SET available = false WHERE id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(updateCar)) {
                updateStmt.setInt(1, car_id);
                updateStmt.executeUpdate();
            }

            System.out.printf("Client %d rented car %d for %d days (%.2f RON).\n", client_id, car_id, days, totalPrice);
            AuditService.getInstance().logAction("Rent car");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewActiveRentals() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a client id: ");
        int client_id = scanner.nextInt();

        String query = "SELECT * FROM rentals WHERE client_id = ? AND active = true";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, client_id);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                int rentalId = rs.getInt("id");
                int carId = rs.getInt("car_id");
                String startDate = rs.getDate("start_date").toString();
                int days = rs.getInt("days");
                double price = rs.getDouble("total_price");

                Car car = carService.getCarById(carId);
                if (car != null) {
                    System.out.printf("Rental #%d: %s %s, Start: %s, Days: %d, Total: %.2f RON\n",
                            rentalId, car.getBrand(), car.getModel(), startDate, days, price);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("This client has no active rentals.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewAllActiveRentals() {
        String query = "SELECT * FROM rentals WHERE active = true ORDER BY client_id";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int lastClientId = -1;
            while (rs.next()) {
                int rentalId = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                int carId = rs.getInt("car_id");
                String startDate = rs.getDate("start_date").toString();
                int days = rs.getInt("days");
                double price = rs.getDouble("total_price");

                if (clientId != lastClientId) {
                    System.out.printf("\nRentals for client ID: %d\n", clientId);
                    lastClientId = clientId;
                }

                Car car = carService.getCarById(carId);
                if (car != null) {
                    System.out.printf("Rental #%d: %s %s, Start: %s, Days: %d, Total: %.2f RON\n",
                            rentalId, car.getBrand(), car.getModel(), startDate, days, price);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void returnRentedCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a client id: ");
        int client_id = scanner.nextInt();

        System.out.print("Enter a car id: ");
        int car_id = scanner.nextInt();
        scanner.nextLine();

        String updateRental = "UPDATE rentals SET active = false WHERE client_id = ? AND car_id = ? AND active = true";
        String updateCar = "UPDATE cars SET available = true WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement rentalStmt = con.prepareStatement(updateRental);
             PreparedStatement carStmt = con.prepareStatement(updateCar)) {

            rentalStmt.setInt(1, client_id);
            rentalStmt.setInt(2, car_id);
            int rows = rentalStmt.executeUpdate();

            if (rows > 0) {
                carStmt.setInt(1, car_id);
                carStmt.executeUpdate();
                System.out.println("Car successfully returned and rental marked as inactive.");
                AuditService.getInstance().logAction("Return car");

            } else {
                System.out.println("No active rental found for this client and car.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

