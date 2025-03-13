package Services;

import Models.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientServices {
    private List<Client> clients;

    public ClientServices() {
        this.clients = new ArrayList<>();
        // Sample data
        clients.add(new Client("John Doe", "1234567890", "123 Elm St"));
        clients.add(new Client("Jane Smith", "0987654321", "456 Oak St"));
    }

    public void addNewClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client details:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Client newClient = new Client(name, phoneNumber, address);
        clients.add(newClient);

        System.out.println("Client added successfully!");
    }

//    public Client getClientById(int id) {
//        for (Client client : clients) {
//            if (client.getId() == id) {
//                return client;
//            }
//        }
//        return null;
//    }

    public void showClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
        } else {
            for (Client client : clients) {
                System.out.println(client.getName() + " (ID: " + client.getId() + ")");
            }
        }
    }
}
