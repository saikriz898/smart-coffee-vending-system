package com.cvs.utils;

import java.io.FileWriter;
import java.io.IOException;

public class UseCaseDiagramGenerator {
    
    public static void generateUseCaseDiagram() {
        String plantUMLCode = "@startuml UseCase_Diagram\n" +
            "!theme plain\n" +
            "title Coffee Vending System - Use Case Diagram\n" +
            "\n" +
            "left to right direction\n" +
            "\n" +
            "actor \"Customer\" as Customer\n" +
            "actor \"Admin\" as Admin\n" +
            "actor \"System\" as System\n" +
            "\n" +
            "rectangle \"Coffee Vending System\" {\n" +
            "  ' User Management\n" +
            "  usecase \"Register Account\" as UC1\n" +
            "  usecase \"Login\" as UC2\n" +
            "  usecase \"Manage Profile\" as UC3\n" +
            "  usecase \"Add Balance\" as UC4\n" +
            "\n" +
            "  ' Order Management\n" +
            "  usecase \"Browse Menu\" as UC5\n" +
            "  usecase \"Customize Order\" as UC6\n" +
            "  usecase \"Add to Cart\" as UC7\n" +
            "  usecase \"Process Payment\" as UC8\n" +
            "  usecase \"Generate Receipt\" as UC9\n" +
            "  usecase \"View Order History\" as UC10\n" +
            "\n" +
            "  ' Admin Functions\n" +
            "  usecase \"Admin Login\" as UC11\n" +
            "  usecase \"Manage Menu\" as UC12\n" +
            "  usecase \"Monitor Orders\" as UC13\n" +
            "  usecase \"Update Order Status\" as UC14\n" +
            "  usecase \"Manage Inventory\" as UC15\n" +
            "  usecase \"Generate Reports\" as UC16\n" +
            "  usecase \"View Statistics\" as UC17\n" +
            "\n" +
            "  ' System Functions\n" +
            "  usecase \"Validate Payment\" as UC18\n" +
            "  usecase \"Update Inventory\" as UC19\n" +
            "  usecase \"Send Notifications\" as UC20\n" +
            "  usecase \"Calculate Pricing\" as UC21\n" +
            "}\n" +
            "\n" +
            "' Customer Use Cases\n" +
            "Customer --> UC1\n" +
            "Customer --> UC2\n" +
            "Customer --> UC3\n" +
            "Customer --> UC4\n" +
            "Customer --> UC5\n" +
            "Customer --> UC6\n" +
            "Customer --> UC7\n" +
            "Customer --> UC8\n" +
            "Customer --> UC9\n" +
            "Customer --> UC10\n" +
            "\n" +
            "' Admin Use Cases\n" +
            "Admin --> UC11\n" +
            "Admin --> UC12\n" +
            "Admin --> UC13\n" +
            "Admin --> UC14\n" +
            "Admin --> UC15\n" +
            "Admin --> UC16\n" +
            "Admin --> UC17\n" +
            "\n" +
            "' System Use Cases\n" +
            "System --> UC18\n" +
            "System --> UC19\n" +
            "System --> UC20\n" +
            "System --> UC21\n" +
            "\n" +
            "' Relationships\n" +
            "UC6 ..> UC21 : <<include>>\n" +
            "UC8 ..> UC18 : <<include>>\n" +
            "UC8 ..> UC19 : <<include>>\n" +
            "UC9 ..> UC20 : <<include>>\n" +
            "UC12 ..> UC15 : <<extend>>\n" +
            "UC13 ..> UC14 : <<extend>>\n" +
            "\n" +
            "note right of UC1\n" +
            "  Customer creates new account\n" +
            "  with email validation and\n" +
            "  receives $10 welcome bonus\n" +
            "end note\n" +
            "\n" +
            "note right of UC8\n" +
            "  Supports multiple payment methods:\n" +
            "  - Wallet (primary)\n" +
            "  - Cash\n" +
            "  - Card\n" +
            "  - UPI\n" +
            "end note\n" +
            "\n" +
            "note right of UC16\n" +
            "  Generate comprehensive reports:\n" +
            "  - Daily sales\n" +
            "  - Weekly analysis\n" +
            "  - Monthly statistics\n" +
            "  - User analytics\n" +
            "end note\n" +
            "\n" +
            "@enduml";
        
        try (FileWriter writer = new FileWriter("docs/diagrams/UseCase_Diagram.puml")) {
            writer.write(plantUMLCode);
            System.out.println("✅ Use Case Diagram PlantUML file generated: docs/diagrams/UseCase_Diagram.puml");
            System.out.println("📋 To generate PNG: Use PlantUML online editor or local installation");
            System.out.println("🔗 Online: http://www.plantuml.com/plantuml/uml/");
        } catch (IOException e) {
            System.err.println("❌ Error generating use case diagram: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        generateUseCaseDiagram();
    }
}