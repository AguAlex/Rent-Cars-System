package Menu;

import Models.*;
import Services.*;
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
            System.out.println("12. Exit \uD83D\uDEAB");
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
                carService.showHistory();
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

        System.out.print("Enter the start date: ");
        String startDate = scanner.nextLine();

        System.out.print("How many days do you want to rent for?: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        Rental newRental = new Rental(client, car, startDate, days);
        client.addRental(newRental);
        car.setAvailable(false);

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
        for (Client client : clientService.getClients()) {
            System.out.printf("Rentals for client with ID: %d\n", client.getId());

            if(client.getRentals().isEmpty()){
                System.out.println("This client has no rentals.");
            }

            for (Rental rental : client.getRentals()) {
                System.out.println(rental);
            }

            System.out.println();
        }
    }

    public void returnRentedCar() {
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

        client.removeRental(car_id);
        car.setAvailable(true);
    }
}

