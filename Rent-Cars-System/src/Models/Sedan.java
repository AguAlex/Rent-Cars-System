package Models;

public class Sedan extends Car{
    private int numberOfDoors;

    public Sedan(String brand, String model, int year, double pricePerDay, int numberOfDoors) {
        super(brand, model, year, pricePerDay);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public double calculateRentalPrice(int days) {
        return super.calculateRentalPrice(days) + numberOfDoors * 5;
    }

    @Override
    public String toString() {
        return "Sedan{" + super.toString() + ", numberOfDoors=" + numberOfDoors + "}";
    }
}
