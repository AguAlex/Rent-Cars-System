package Services;

import Models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarServices {
    private List<Car> cars;

    public CarServices() {
        this.cars = new ArrayList<>();
        // Sample data
        cars.add(new Electric("Toyota", "Corolla", 2020, 40.0, 300));
        cars.add(new Suv("Jeep", "Wrangler", 2021, 100.0, true));
    }

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

        Car newCar;
        switch (carType) {
            case 1:
                System.out.print("Does it have AWD? (true/false): ");
                boolean hasAWD = scanner.nextBoolean();
                newCar = new Suv(brand, model, year, pricePerDay, hasAWD);
                cars.add(newCar);
                break;
            case 2:
                System.out.print("Enter battery range (km): ");
                int batteryRange = scanner.nextInt();
                newCar = new Electric(brand, model, year, pricePerDay, batteryRange);
                cars.add(newCar);
                break;
            case 3:
                System.out.print("Enter number of doors: ");
                int numberOfDoors = scanner.nextInt();
                newCar = new Sedan(brand, model, year, pricePerDay, numberOfDoors);
                cars.add(newCar);
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }

        System.out.println("Car added successfully!");
    }

    public void showCars() {
        System.out.println("All available cars:\n");
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    public Car getCarById(int id) {
        for (Car car : cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    public void searchByModelOrBrand() {
        System.out.print("Select (1-Model, 2-Brand): ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        boolean exist = false;
        switch (choice) {
            case 1:
                System.out.print("Enter a model: ");
                String model = scanner.nextLine();

                System.out.printf("%s cars:\n", model);
                for (Car car : cars) {
                    if (car.getModel().equals(model)) {
                        System.out.println(car);
                        exist = true;
                    }
                }
                if (!exist) {
                    System.out.printf("No cars with model '%s' available!", model);
                }
                break;
            case 2:
                System.out.print("Enter a brand: ");
                String brand = scanner.nextLine();

                System.out.printf("%s cars:\n", brand);
                for (Car car : cars) {
                    if (car.getBrand().equals(brand)) {
                        System.out.println(car);
                        exist = true;
                    }
                }
                if (!exist) {
                    System.out.printf("No cars with brand '%s' available!", brand);
                }
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                break;
        }
    }

    public void showHistory() {

    }

    public void showAvailableCars() {
        System.out.println("Available cars: ");
        for (Car car : cars) {
            if (car.getAvailable()) {
                System.out.println(car);
            }
        }
    }
}
