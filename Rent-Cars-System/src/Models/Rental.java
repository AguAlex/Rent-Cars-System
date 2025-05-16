package Models;

public class Rental {
    private int id;
    private Client client;
    private Car car;
    private String startDate;
    private int days;
    private double totalPrice;
    private boolean active;
    private static int id_cnt = 0;

    public Rental(int id, Client client, Car car, String startDate, int days, double totalPrice, boolean active) {
        this.id = id;
        this.client = client;
        this.car = car;
        this.startDate = startDate;
        this.days = days;
        this.totalPrice = totalPrice;
        this.active = active;
    }

    public Rental(Client client, Car car, String startDate, int days) {
        this.id = ++id_cnt;
        this.client = client;
        this.car = car;
        this.startDate = startDate;
        this.days = days;
        this.totalPrice = car.calculateRentalPrice(days);
        this.active = true;
    }

    public int getCarId() {
        return car.getId();
    }

    public void endRental() {
        this.active = false;
        car.setAvailable(true);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Rental{" + "id=" + id + ", client=" + client.getName() + ", car=" + car.getBrand() + " " + car.getModel() + ", from=" + startDate +
                ", days=" + days + ", totalPrice=" + totalPrice + ", active=" + active + '}';
    }
}
