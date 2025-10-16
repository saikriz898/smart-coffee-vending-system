package com.cvs.utils;

import java.io.FileWriter;
import java.io.IOException;

public class ERDiagramGenerator {
    
    public static void generateERDiagram() {
        String plantUMLCode = "@startuml ER_Diagram\n" +
            "!define ENTITY class\n" +
            "!define RELATIONSHIP -->\n" +
            "\n" +
            "skinparam class {\n" +
            "    BackgroundColor LightBlue\n" +
            "    BorderColor DarkBlue\n" +
            "    ArrowColor DarkBlue\n" +
            "}\n" +
            "\n" +
            "ENTITY users {\n" +
            "    + user_id : INT (PK)\n" +
            "    --\n" +
            "    name : VARCHAR(100)\n" +
            "    email : VARCHAR(150) (UNIQUE)\n" +
            "    password : VARCHAR(255)\n" +
            "    balance : DECIMAL(10,2)\n" +
            "    created_at : TIMESTAMP\n" +
            "    updated_at : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "ENTITY admin {\n" +
            "    + admin_id : INT (PK)\n" +
            "    --\n" +
            "    username : VARCHAR(50) (UNIQUE)\n" +
            "    password : VARCHAR(255)\n" +
            "    created_at : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "ENTITY coffee_menu {\n" +
            "    + coffee_id : INT (PK)\n" +
            "    --\n" +
            "    name : VARCHAR(100)\n" +
            "    price : DECIMAL(8,2)\n" +
            "    description : TEXT\n" +
            "    available : BOOLEAN\n" +
            "    created_at : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "ENTITY ingredients {\n" +
            "    + ingredient_id : INT (PK)\n" +
            "    --\n" +
            "    name : VARCHAR(100)\n" +
            "    quantity : INT\n" +
            "    unit : VARCHAR(20)\n" +
            "    min_threshold : INT\n" +
            "    updated_at : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "ENTITY orders {\n" +
            "    + order_id : INT (PK)\n" +
            "    --\n" +
            "    user_id : INT (FK)\n" +
            "    total_amount : DECIMAL(10,2)\n" +
            "    payment_status : ENUM\n" +
            "    order_status : ENUM\n" +
            "    order_time : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "ENTITY order_items {\n" +
            "    + order_item_id : INT (PK)\n" +
            "    --\n" +
            "    order_id : INT (FK)\n" +
            "    coffee_id : INT (FK)\n" +
            "    quantity : INT\n" +
            "    sugar_level : ENUM\n" +
            "    milk_level : ENUM\n" +
            "    size : ENUM\n" +
            "    item_price : DECIMAL(8,2)\n" +
            "}\n" +
            "\n" +
            "ENTITY payments {\n" +
            "    + payment_id : INT (PK)\n" +
            "    --\n" +
            "    order_id : INT (FK)\n" +
            "    amount : DECIMAL(10,2)\n" +
            "    payment_type : ENUM\n" +
            "    payment_status : ENUM\n" +
            "    transaction_id : VARCHAR(100)\n" +
            "    payment_time : TIMESTAMP\n" +
            "}\n" +
            "\n" +
            "' Relationships\n" +
            "users ||--o{ orders : \"places\"\n" +
            "orders ||--o{ order_items : \"contains\"\n" +
            "coffee_menu ||--o{ order_items : \"ordered_as\"\n" +
            "orders ||--o{ payments : \"paid_by\"\n" +
            "\n" +
            "note top of users : \"Registered customers\\nwith wallet balance\"\n" +
            "note top of admin : \"System administrators\"\n" +
            "note top of coffee_menu : \"Available coffee items\\nwith pricing\"\n" +
            "note top of ingredients : \"Inventory management\\nwith low stock alerts\"\n" +
            "note top of orders : \"Customer orders\\nwith status tracking\"\n" +
            "note top of order_items : \"Individual items\\nwith customizations\"\n" +
            "note top of payments : \"Payment transactions\\nwith multiple methods\"\n" +
            "\n" +
            "@enduml";
        
        try (FileWriter writer = new FileWriter("docs/diagrams/ER_Diagram.puml")) {
            writer.write(plantUMLCode);
            System.out.println("✅ ER Diagram PlantUML file generated: docs/diagrams/ER_Diagram.puml");
            System.out.println("📋 To generate PNG: Use PlantUML online editor or local installation");
            System.out.println("🔗 Online: http://www.plantuml.com/plantuml/uml/");
        } catch (IOException e) {
            System.err.println("❌ Error generating ER diagram: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        generateERDiagram();
    }
}