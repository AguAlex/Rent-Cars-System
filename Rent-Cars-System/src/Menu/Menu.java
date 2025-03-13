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
            System.out.println("9. View all active rentals");
            System.out.println("10. Exit");
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
                carService.rentCar();
                break;
            case 7:
                carService.returnRentedCar();
                break;
            case 8:
                carService.showHistory();
                break;
            case 9:
                carService.viewActiveRentals();
                break;
            case 10:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

}

