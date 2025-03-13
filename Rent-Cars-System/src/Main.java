
import Models.*;
import Services.*;
import Menu.Menu;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        CarServices carService = new CarServices();
        ClientServices clientService = new ClientServices();

        Menu menu = new Menu(carService, clientService);
        menu.displayMenu();
    }
}
