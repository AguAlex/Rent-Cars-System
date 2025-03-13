package Services;

import Models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarServices {
    private List<Car> cars;

    public CarServices() {
        this.cars = new ArrayList<>();
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
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
}
