package Services;

import Models.Client;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import DB.DBConnection;
import java.sql.*;

public class ClientServices {

    public ClientServices() {}

    public void addNewClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client details:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        String query = "INSERT INTO clients (name, phone_number, address) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, address);
            stmt.executeUpdate();

            System.out.println("Client added successfully!");
            AuditService.getInstance().logAction("Add new client");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int id) {
        String query = "SELECT * FROM clients WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(

                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("address")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                clients.add(new Client(

                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void showClients() {
        List<Client> clients = getClients();

        if (clients.isEmpty()) {
            System.out.println("No clients available.");
        } else {
            for (Client client : clients) {
                System.out.println(client.getName() + " (ID: " + client.getId() + ")");
            }
        }
    }

    public void showClientsSorted() {
        List<Client> clients = getClients();

        clients.sort(Comparator.comparing(Client::getName));

        for (Client client : clients) {
            System.out.println(client.getName() + " (ID: " + client.getId() + ")");
        }
    }
}
