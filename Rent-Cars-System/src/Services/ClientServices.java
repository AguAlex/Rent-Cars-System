package Services;

import Models.Client;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import DB.DBConnection;
import java.sql.*;

public class ClientServices {
    private List<Client> clients;

    public ClientServices() {
        this.clients = new ArrayList<>();

        clients.add(new Client("Cristi 1 test", "1234567890", "123 Elm St"));
        clients.add(new Client("Andrei 2 test", "0987654321", "456 Oak St"));
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

        String query = "INSERT INTO clients (name, phone_number, address) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, address);
            stmt.executeUpdate();

            System.out.println("Client added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void showClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
        } else {
            for (Client client : clients) {
                System.out.println(client.getName() + " (ID: " + client.getId() + ")");
            }
        }
    }

    public void showClientsSorted() {
        List<Client> auxList = new ArrayList<>(clients);
        auxList.sort(Comparator.comparing(Client::getName));

        for (Client client : auxList) {
            System.out.println(client.getName() + " (ID: " + client.getId() + ")");
        }
    }
}
