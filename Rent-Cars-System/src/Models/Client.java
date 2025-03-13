package Models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private List<Car> rentHistory;
    private static int id_cnt = 0;

    public Client(String name, String phoneNumber, String address) {
        this.id = ++id_cnt;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rentHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Car> getRentHistory() {
        return rentHistory;
    }

    public void rentCar(Car car) {
        rentHistory.add(car);
        car.setAvailable(false);
    }

    public void showRentHistory() {
        if (rentHistory.isEmpty()) {
            System.out.println(name + " has no reservations.");
        } else {
            System.out.println("Reservation history for " + name + ":");
            for (Car car : rentHistory) {
                System.out.println(car);
            }
        }
    }
}
