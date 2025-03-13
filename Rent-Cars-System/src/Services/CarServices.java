package Services;

import Models.Car;
import java.util.ArrayList;
import java.util.List;

public class CarServices {
    private List<Car> cars;

    public CarServices() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
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
