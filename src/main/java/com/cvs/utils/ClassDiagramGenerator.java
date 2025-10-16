package com.cvs.utils;

import java.io.FileWriter;
import java.io.IOException;

public class ClassDiagramGenerator {
    
    public static void generateClassDiagram() {
        String plantUMLCode = "@startuml Class_Diagram\n" +
            "!define CLASS class\n" +
            "\n" +
            "skinparam class {\n" +
            "    BackgroundColor LightBlue\n" +
            "    BorderColor DarkBlue\n" +
            "    ArrowColor DarkBlue\n" +
            "}\n" +
            "\n" +
            "package \"Models\" {\n" +
            "    CLASS User {\n" +
            "        - userId: int\n" +
            "        - name: String\n" +
            "        - email: String\n" +
            "        - password: String\n" +
            "        - balance: BigDecimal\n" +
            "        + User(name, email, password)\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "\n" +
            "    CLASS Admin {\n" +
            "        - adminId: int\n" +
            "        - username: String\n" +
            "        - password: String\n" +
            "        + Admin(username, password)\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "\n" +
            "    CLASS CoffeeMenu {\n" +
            "        - coffeeId: int\n" +
            "        - name: String\n" +
            "        - price: BigDecimal\n" +
            "        - description: String\n" +
            "        - available: boolean\n" +
            "        + CoffeeMenu(name, price, description)\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "\n" +
            "    CLASS Order {\n" +
            "        - orderId: int\n" +
            "        - userId: int\n" +
            "        - totalAmount: BigDecimal\n" +
            "        - paymentStatus: PaymentStatus\n" +
            "        - orderStatus: OrderStatus\n" +
            "        - orderItems: List<OrderItem>\n" +
            "        + Order(userId, totalAmount)\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "\n" +
            "    CLASS OrderItem {\n" +
            "        - orderItemId: int\n" +
            "        - orderId: int\n" +
            "        - coffeeId: int\n" +
            "        - quantity: int\n" +
            "        - sugarLevel: SugarLevel\n" +
            "        - milkLevel: MilkLevel\n" +
            "        - size: Size\n" +
            "        - itemPrice: BigDecimal\n" +
            "        + OrderItem()\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "\n" +
            "    CLASS Payment {\n" +
            "        - paymentId: int\n" +
            "        - orderId: int\n" +
            "        - amount: BigDecimal\n" +
            "        - paymentType: PaymentType\n" +
            "        - paymentStatus: PaymentStatus\n" +
            "        + Payment(orderId, amount, paymentType)\n" +
            "        + getters/setters()\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "package \"DAO\" {\n" +
            "    CLASS UserDAO {\n" +
            "        + createUser(User): boolean\n" +
            "        + getUserById(int): User\n" +
            "        + getUserByEmail(String): User\n" +
            "        + updateUserBalance(int, BigDecimal): boolean\n" +
            "        + getAllUsers(): List<User>\n" +
            "    }\n" +
            "\n" +
            "    CLASS OrderDAO {\n" +
            "        + createOrder(Order): boolean\n" +
            "        + getOrderById(int): Order\n" +
            "        + getOrdersByUserId(int): List<Order>\n" +
            "        + updateOrderStatus(int, OrderStatus): boolean\n" +
            "        + updatePaymentStatus(int, PaymentStatus): boolean\n" +
            "    }\n" +
            "\n" +
            "    CLASS CoffeeMenuDAO {\n" +
            "        + createCoffeeItem(CoffeeMenu): boolean\n" +
            "        + getCoffeeById(int): CoffeeMenu\n" +
            "        + getAllCoffeeItems(): List<CoffeeMenu>\n" +
            "        + updateCoffeeItem(CoffeeMenu): boolean\n" +
            "        + updateAvailability(int, boolean): boolean\n" +
            "    }\n" +
            "\n" +
            "    CLASS OrderItemDAO {\n" +
            "        + createOrderItem(OrderItem): boolean\n" +
            "        + getOrderItemsByOrderId(int): List<OrderItem>\n" +
            "        + updateOrderItem(OrderItem): boolean\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "package \"Service\" {\n" +
            "    CLASS UserService {\n" +
            "        - userDAO: UserDAO\n" +
            "        + registerUser(String, String, String): boolean\n" +
            "        + authenticateUser(String, String): User\n" +
            "        + addBalance(int, BigDecimal): boolean\n" +
            "        + deductBalance(int, BigDecimal): boolean\n" +
            "    }\n" +
            "\n" +
            "    CLASS AdminService {\n" +
            "        - coffeeMenuDAO: CoffeeMenuDAO\n" +
            "        - orderDAO: OrderDAO\n" +
            "        - userDAO: UserDAO\n" +
            "        + authenticateAdmin(String, String): boolean\n" +
            "        + addCoffeeItem(String, BigDecimal, String): boolean\n" +
            "        + updateCoffeeItem(CoffeeMenu): boolean\n" +
            "        + getAllOrders(): List<Order>\n" +
            "        + getTotalRevenue(): BigDecimal\n" +
            "    }\n" +
            "\n" +
            "    CLASS OrderService {\n" +
            "        - orderDAO: OrderDAO\n" +
            "        - coffeeMenuDAO: CoffeeMenuDAO\n" +
            "        - userService: UserService\n" +
            "        + calculateItemPrice(int, Size, SugarLevel, MilkLevel): BigDecimal\n" +
            "        + createOrder(int, List<OrderItem>): Order\n" +
            "        + processPayment(int, PaymentType): boolean\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "package \"GUI\" {\n" +
            "    CLASS MainUI {\n" +
            "        + main(String[]): void\n" +
            "        - openUserLogin(): void\n" +
            "        - openAdminLogin(): void\n" +
            "        - testDatabaseConnection(): void\n" +
            "    }\n" +
            "\n" +
            "    CLASS LoginUI {\n" +
            "        - userService: UserService\n" +
            "        - adminService: AdminService\n" +
            "        - handleLogin(ActionEvent): void\n" +
            "    }\n" +
            "\n" +
            "    CLASS RegisterUI {\n" +
            "        - userService: UserService\n" +
            "        - handleRegister(): void\n" +
            "    }\n" +
            "\n" +
            "    CLASS UserDashboardUI {\n" +
            "        - currentUser: User\n" +
            "        - userService: UserService\n" +
            "        - orderService: OrderService\n" +
            "        - cart: List<OrderItem>\n" +
            "        - processCheckout(): void\n" +
            "        - showAddToCartDialog(): void\n" +
            "    }\n" +
            "\n" +
            "    CLASS AdminDashboardUI {\n" +
            "        - adminService: AdminService\n" +
            "        - loadMenuData(): void\n" +
            "        - loadOrdersData(): void\n" +
            "        - updateStats(): void\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "package \"Utils\" {\n" +
            "    CLASS DBConnector {\n" +
            "        + {static} getConnection(): Connection\n" +
            "        + {static} testConnection(): boolean\n" +
            "        + {static} closeConnection(Connection): void\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "' Relationships\n" +
            "UserService --> UserDAO\n" +
            "AdminService --> CoffeeMenuDAO\n" +
            "AdminService --> OrderDAO\n" +
            "AdminService --> UserDAO\n" +
            "OrderService --> OrderDAO\n" +
            "OrderService --> OrderItemDAO\n" +
            "OrderService --> CoffeeMenuDAO\n" +
            "OrderService --> UserService\n" +
            "\n" +
            "LoginUI --> UserService\n" +
            "LoginUI --> AdminService\n" +
            "RegisterUI --> UserService\n" +
            "UserDashboardUI --> UserService\n" +
            "UserDashboardUI --> OrderService\n" +
            "AdminDashboardUI --> AdminService\n" +
            "\n" +
            "UserDAO --> User\n" +
            "OrderDAO --> Order\n" +
            "CoffeeMenuDAO --> CoffeeMenu\n" +
            "OrderItemDAO --> OrderItem\n" +
            "\n" +
            "Order --> OrderItem\n" +
            "Order --> Payment\n" +
            "\n" +
            "note top of UserService : \"Handles user authentication\\nand balance management\"\n" +
            "note top of OrderService : \"Manages order creation\\nand payment processing\"\n" +
            "note top of AdminService : \"Provides admin functionality\\nfor menu and order management\"\n" +
            "\n" +
            "@enduml";
        
        try (FileWriter writer = new FileWriter("docs/diagrams/Class_Diagram.puml")) {
            writer.write(plantUMLCode);
            System.out.println("✅ Class Diagram PlantUML file generated: docs/diagrams/Class_Diagram.puml");
            System.out.println("📋 To generate PNG: Use PlantUML online editor or local installation");
            System.out.println("🔗 Online: http://www.plantuml.com/plantuml/uml/");
        } catch (IOException e) {
            System.err.println("❌ Error generating class diagram: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        generateClassDiagram();
    }
}