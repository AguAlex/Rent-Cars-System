package Models;

public interface Rentable {

    double calculateRentalPrice(int days);

    boolean isAvailable();
}