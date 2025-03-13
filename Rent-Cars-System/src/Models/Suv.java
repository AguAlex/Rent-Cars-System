package Models;

public class Suv extends Car{
    private boolean hasAWD;

    public Suv(String brand, String model, int year, double pricePerDay, boolean hasAWD) {
        super(brand, model, year, pricePerDay);
        this.hasAWD = hasAWD;
    }

    @Override
    public double calculateRentalPrice(int days) {
        return super.calculateRentalPrice(days) + 50;
    }

    @Override
    public String toString() {
        return "SUV{" + super.toString() + ", hasAWD=" + hasAWD + '}';
    }
}
