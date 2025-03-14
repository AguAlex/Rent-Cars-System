
# 🚗 Rent Cars System 📚
A **Java console-based application** that allows you to **manage a car rental system** in a simple and interactive way! Built using **Object-Oriented Programming (OOP)** principles, it features **inheritance**, **interfaces**, **collections**, and **services**.

---

## 🎯 Project Overview
The **Rent Cars System** simulates the management of a car rental business. You can:
- Add 🚘 cars and 👤 clients
- Rent cars 📝
- View available cars, clients, and reservations
- Return cars
- And much more... all from a **user-friendly menu interface** in the terminal! 💻

---

## 💡 Key Features & Services
✅ **Interactive Menu** in the terminal  
✅ Manage a fleet of **Cars**  
✅ Manage **Clients** and their details  
✅ Create and manage **Reservations**  
✅ Track **Car Availability**  
✅ Return cars and finalize reservations  
✅ View rental history per client  
✅ Clean separation of concerns using **Services**  
✅ Collections like **List**, **Map**, and **Set** for efficient data handling  
✅ Easy to extend and maintain, following OOP best practices!  

---

## 🚘 Types of Cars
Our car fleet is organized into **different types**, thanks to **inheritance** and **polymorphism**:
- **🚗 Sedan** – Ideal for city drives and families.
- **🚙 SUV** – Spacious and powerful, perfect for off-road or trips.
- **⚡ Electric** – Eco-friendly with zero emissions and advanced tech.

Each car type extends from the **`Car`** base class and can have specific attributes and behaviors.

---

## 🏗️ Project Structure
```
RentCarsSystem
│
├── models
│   ├── Car.java
│   ├── Sedan.java
│   ├── SUV.java
│   ├── Electric.java
│   ├── Client.java
│   └── Rental.java
│
├── services
│   ├── CarService.java
│   ├── ClientService.java
│  
│
├── menu
│   └── Menu.java
│
└── Main.java
```

---

## ✨ Technologies Used
- **Java** ☕
- **Collections Framework**: `List`, `Map`, `Set`  
- **OOP Principles**: Inheritance, Interfaces, Encapsulation  
- **Scanner** for user input (interactive terminal)

---

## 🚀 How to Use the Application
1. Clone the project:  
   ```
   git clone https://github.com/AguAlex/RentCarsSystem.git
   ```
2. Compile and run the project:  
   ```
   javac Main.java
   java Main
   ```
3. Interact with the **menu system** to perform various actions:  
   - Add cars 🚗  
   - Add clients 👤  
   - Rent cars 📄  
   - View all cars and clients 📋  
   - Return rented cars 🔄  
   - Exit the program ❌

---

## 📌 Notes
✅ Easy to extend: you can add new car types or features!  
✅ Great example of OOP concepts applied in a real-world scenario.  
✅ Clean code and modular design for maintainability.
