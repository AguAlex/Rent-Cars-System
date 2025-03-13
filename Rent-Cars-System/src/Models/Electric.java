package Models;

public class Electric extends Car{
    private int batteryRange;

    public Electric(String brand, String model, int year, double pricePerDay, int batteryRange) {
        super(brand, model, year, pricePerDay);
        this.batteryRange = batteryRange;
    }

    public int getBatteryRange() {
        return batteryRange;
    }

    public void setBatteryRange(int batteryRange) {
        this.batteryRange = batteryRange;
    }

    @Override
    public double calculateRentalPrice(int days) {
        return super.calculateRentalPrice(days) * 0.9;
    }

    @Override
    public String toString() {
        return "Electric{" + super.toString() + ", batteryRange=" + batteryRange + " km}";
    }
}

