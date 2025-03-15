package Models;

public class Car implements  Rentable{
    private int id;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean available;
    private static int id_cnt = 0;

    public Car(String brand, String model, int year, double pricePerDay) {
        this.id = ++id_cnt;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public int getId() {
        return id;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }
    public double getPricePerDay() {
        return pricePerDay;
    }
    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public double calculateRentalPrice(int days) {
        return pricePerDay * days;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", year=" + year + ", pricePerDay=" + pricePerDay + ", available=" + available + '}';
    }
}
