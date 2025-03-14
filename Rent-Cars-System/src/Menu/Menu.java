package Menu;

import Models.*;
import Services.*;

import java.util.InputMismatchException;
import java.util.Scanner;

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
            System.out.println("Main menu:");
            System.out.println("1. Add a new car");
            System.out.println("2. Show all cars");
            System.out.println("3. Search cars by brand/model");
            System.out.println("4. Add a new client");
            System.out.println("5. Show all clients");
            System.out.println("6. Rent a car to a client");
            System.out.println("7. Return a rented car");
            System.out.println("8. Show reservation history");
            System.out.println("9. View active rentals for a specific client");
            System.out.println("10. View all active rentals");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

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
                // -----------
                carService.returnRentedCar();
                break;
            case 8:
                // -------------
                carService.showHistory();
                break;
            case 9:
                // -------------
                viewActiveRentals();
                break;
            case 10:
                viewAllActiveRentals();
                break;
            case 11:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

    public void rentCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a client id: ");
        int client_id = scanner.nextInt();

        System.out.println("Enter a car id: ");
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

        System.out.println("Enter the start date: ");
        String startDate = scanner.nextLine();

        System.out.println("How many days do you want to rent for?: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        Rental newRental = new Rental(client, car, startDate, days);
        client.addRental(newRental);
        System.out.printf("Client with id: %d rented car with id: %d.\n", client_id, car_id);
    }

    public void viewActiveRentals() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a client id: ");
        int client_id = scanner.nextInt();
        scanner.nextLine();

        Client client = clientService.getClientById(client_id);
        if (client == null) {
            System.out.println("Invalid Client ID!");
            return;
        }

        if(client.getRentals().isEmpty()){
            System.out.println("This client has no rentals.");
            return;
        }

        for (Rental rental : client.getRentals()) {
            System.out.println(rental);
        }
    }

    public void viewAllActiveRentals() {

    }
}

