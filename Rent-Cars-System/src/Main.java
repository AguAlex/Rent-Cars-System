import Models.Car;
import Models.Suv;
import Models.Electric;
import Models.Sedan;
import Services.CarServices;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarServices carService = new CarServices();
        int choice;
        System.out.println("Welcome to my Car Rental System!");
        while (true) {
            System.out.println("Main menu:");
            System.out.println("1. Add a new car");
            System.out.println("2. Show all cars");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:

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

                        Car newCar;
                        switch (carType) {
                            case 1:
                                System.out.print("Does it have AWD? (true/false): ");
                                boolean hasAWD = scanner.nextBoolean();
                                newCar = new Suv(brand, model, year, pricePerDay, hasAWD);
                                break;
                            case 2:
                                System.out.print("Enter battery range (km): ");
                                int batteryRange = scanner.nextInt();
                                newCar = new Electric(brand, model, year, pricePerDay, batteryRange);
                                break;
                            case 3:
                                System.out.print("Enter number of doors: ");
                                int numberOfDoors = scanner.nextInt();
                                newCar = new Sedan(brand, model, year, pricePerDay, numberOfDoors);
                                break;
                            default:
                                System.out.println("Invalid choice! Try again.");
                                continue;
                        }

                        // Add the car to the system
                        carService.addCar(newCar);
                        System.out.println("Car added successfully!");
                        break;

                    case 2:
                        System.out.println("List of all cars:");
                        carService.showCars();
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
                System.out.println();
            } catch (InputMismatchException e) {

                System.out.println("Invalid input! Please enter a valid number.");
                System.out.println();
                scanner.nextLine();
            }
        }
    }
}
