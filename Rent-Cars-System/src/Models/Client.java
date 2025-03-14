package Models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private List<Rental> rentals;
    private static int id_cnt = 0;

    public Client(String name, String phoneNumber, String address) {
        this.id = ++id_cnt;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rentals = new ArrayList<>();
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

    public List<Rental> getRentals() {
        return rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public void showRentHistory() {
        if (rentals.isEmpty()) {
            System.out.println(name + " has no reservations.");
        } else {
            System.out.println("Rental history for " + name + ":");
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        }
    }
}
