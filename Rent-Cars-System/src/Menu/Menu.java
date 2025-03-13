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
            System.out.println("Main menu:");
            System.out.println("1. Add a new car");
            System.out.println("2. Show all cars");
            System.out.println("3. Add a new client");
            System.out.println("4. Show all clients");
            System.out.println("5. Make a reservation");
            System.out.println("6. Show reservation history");
            System.out.println("7. Exit");
            System.out.print("Please enter your choice: ");

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

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("You selected to add a new car.");
                carService.addNewCar();
                break;
            case 2:
                System.out.println("Showing all cars...");
                carService.showCars();
                break;
            case 3:
                clientService.addNewClient();
                break;
            case 4:
                System.out.println("Showing all clients...");
                clientService.showClients();
                break;
//            case 5:
//                makeReservation();
//                break;
//            case 6:
//                showReservationHistory();
//                break;
//            case 7:
//                System.out.println("Exiting...");
//                break;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

}

