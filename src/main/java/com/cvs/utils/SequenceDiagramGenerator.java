package com.cvs.utils;

import java.io.FileWriter;
import java.io.IOException;

public class SequenceDiagramGenerator {
    
    public static void generateSequenceDiagram() {
        String plantUMLCode = "@startuml Sequence_Diagram\n" +
            "!theme plain\n" +
            "title Coffee Order & Payment Flow\n" +
            "\n" +
            "actor User\n" +
            "participant \"LoginUI\" as Login\n" +
            "participant \"UserDashboardUI\" as Dashboard\n" +
            "participant \"UserService\" as UserSvc\n" +
            "participant \"OrderService\" as OrderSvc\n" +
            "participant \"OrderDAO\" as OrderDAO\n" +
            "participant \"UserDAO\" as UserDAO\n" +
            "database \"MySQL DB\" as DB\n" +
            "\n" +
            "== Authentication ==\n" +
            "User -> Login: Enter credentials\n" +
            "Login -> UserSvc: authenticateUser(email, password)\n" +
            "UserSvc -> UserDAO: getUserByEmail(email)\n" +
            "UserDAO -> DB: SELECT * FROM users WHERE email=?\n" +
            "DB --> UserDAO: User data\n" +
            "UserDAO --> UserSvc: User object\n" +
            "UserSvc -> UserSvc: Verify SHA-256 hash\n" +
            "UserSvc --> Login: Authentication result\n" +
            "Login -> Dashboard: Open dashboard\n" +
            "\n" +
            "== Order Creation ==\n" +
            "User -> Dashboard: Browse menu & add to cart\n" +
            "User -> Dashboard: Click checkout\n" +
            "Dashboard -> OrderSvc: createOrder(userId, orderItems)\n" +
            "\n" +
            "OrderSvc -> OrderSvc: Calculate total amount\n" +
            "loop For each item\n" +
            "    OrderSvc -> OrderSvc: calculateItemPrice(coffeeId, size, sugar, milk)\n" +
            "end\n" +
            "\n" +
            "OrderSvc -> UserSvc: getUserById(userId)\n" +
            "UserSvc -> UserDAO: getUserById(userId)\n" +
            "UserDAO -> DB: SELECT * FROM users WHERE user_id=?\n" +
            "DB --> UserDAO: User data\n" +
            "UserDAO --> UserSvc: User object\n" +
            "UserSvc --> OrderSvc: User with balance\n" +
            "\n" +
            "OrderSvc -> OrderSvc: Validate balance >= totalAmount\n" +
            "\n" +
            "alt Sufficient Balance\n" +
            "    OrderSvc -> OrderDAO: createOrder(order)\n" +
            "    OrderDAO -> DB: BEGIN TRANSACTION\n" +
            "    OrderDAO -> DB: INSERT INTO orders\n" +
            "    OrderDAO -> DB: INSERT INTO order_items\n" +
            "    OrderDAO -> DB: COMMIT\n" +
            "    DB --> OrderDAO: Order created\n" +
            "    OrderDAO --> OrderSvc: Order object\n" +
            "else Insufficient Balance\n" +
            "    OrderSvc --> Dashboard: Order creation failed\n" +
            "    Dashboard -> User: Show error message\n" +
            "end\n" +
            "\n" +
            "== Payment Processing ==\n" +
            "Dashboard -> OrderSvc: processPayment(orderId, WALLET)\n" +
            "OrderSvc -> UserSvc: deductBalance(userId, amount)\n" +
            "UserSvc -> UserDAO: updateUserBalance(userId, newBalance)\n" +
            "UserDAO -> DB: UPDATE users SET balance=?\n" +
            "DB --> UserDAO: Balance updated\n" +
            "UserDAO --> UserSvc: Success\n" +
            "UserSvc --> OrderSvc: Balance deducted\n" +
            "\n" +
            "OrderSvc -> OrderDAO: updatePaymentStatus(orderId, COMPLETED)\n" +
            "OrderDAO -> DB: UPDATE orders SET payment_status='COMPLETED'\n" +
            "DB --> OrderDAO: Status updated\n" +
            "OrderDAO --> OrderSvc: Success\n" +
            "\n" +
            "OrderSvc -> OrderDAO: updateOrderStatus(orderId, PREPARING)\n" +
            "OrderDAO -> DB: UPDATE orders SET order_status='PREPARING'\n" +
            "DB --> OrderDAO: Status updated\n" +
            "OrderDAO --> OrderSvc: Success\n" +
            "OrderSvc --> Dashboard: Payment successful\n" +
            "\n" +
            "== Receipt Generation ==\n" +
            "Dashboard -> Dashboard: Generate receipt\n" +
            "Dashboard -> User: Show ReceiptUI\n" +
            "Dashboard -> User: Show success message\n" +
            "Dashboard -> Dashboard: Clear cart\n" +
            "Dashboard -> Dashboard: Update balance display\n" +
            "\n" +
            "@enduml";
        
        try (FileWriter writer = new FileWriter("docs/diagrams/Sequence_Diagram.puml")) {
            writer.write(plantUMLCode);
            System.out.println("✅ Sequence Diagram PlantUML file generated: docs/diagrams/Sequence_Diagram.puml");
            System.out.println("📋 To generate PNG: Use PlantUML online editor or local installation");
            System.out.println("🔗 Online: http://www.plantuml.com/plantuml/uml/");
        } catch (IOException e) {
            System.err.println("❌ Error generating sequence diagram: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        generateSequenceDiagram();
    }
}