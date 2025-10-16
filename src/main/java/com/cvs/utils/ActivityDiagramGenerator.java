package com.cvs.utils;

import java.io.FileWriter;
import java.io.IOException;

public class ActivityDiagramGenerator {
    
    public static void generateActivityDiagram() {
        String plantUMLCode = "@startuml Activity_Diagram\n" +
            "!theme plain\n" +
            "title Coffee Order Process Activity Diagram\n" +
            "\n" +
            "start\n" +
            "\n" +
            ":User opens application;\n" +
            "\n" +
            "if (User registered?) then (yes)\n" +
            "  :Login with credentials;\n" +
            "  if (Valid credentials?) then (yes)\n" +
            "    :Access granted;\n" +
            "  else (no)\n" +
            "    :Show error message;\n" +
            "    stop\n" +
            "  endif\n" +
            "else (no)\n" +
            "  :Register new account;\n" +
            "  :Receive $10 welcome bonus;\n" +
            "  :Login with new credentials;\n" +
            "endif\n" +
            "\n" +
            ":Display user dashboard;\n" +
            ":Load coffee menu from database;\n" +
            "\n" +
            "repeat\n" +
            "  :Browse coffee menu;\n" +
            "  :Select coffee item;\n" +
            "  :Customize order (size, sugar, milk);\n" +
            "  :Calculate item price;\n" +
            "  note right\n" +
            "    Price = Base Price × Size Multiplier + Customization Fees\n" +
            "    Size Multipliers: Small(0.8x), Medium(1x), Large(1.3x)\n" +
            "    Fees: High Sugar(+$0.25), High Milk(+$0.50)\n" +
            "  end note\n" +
            "  :Add item to cart;\n" +
            "repeat while (Add more items?) is (yes)\n" +
            "-> no;\n" +
            "\n" +
            "if (Cart empty?) then (yes)\n" +
            "  :Show empty cart message;\n" +
            "  stop\n" +
            "endif\n" +
            "\n" +
            ":Calculate total amount;\n" +
            ":Check user balance;\n" +
            "\n" +
            "if (Sufficient balance?) then (no)\n" +
            "  :Show insufficient balance error;\n" +
            "  :Offer to add balance;\n" +
            "  if (User adds balance?) then (yes)\n" +
            "    :Process balance addition;\n" +
            "    :Update user balance in database;\n" +
            "  else (no)\n" +
            "    stop\n" +
            "  endif\n" +
            "endif\n" +
            "\n" +
            ":Create order in database;\n" +
            "note right\n" +
            "  Transaction includes:\n" +
            "  - Insert order record\n" +
            "  - Insert order items\n" +
            "  - Set status to PENDING\n" +
            "end note\n" +
            "\n" +
            "if (Order creation successful?) then (no)\n" +
            "  :Show order creation error;\n" +
            "  :Rollback transaction;\n" +
            "  stop\n" +
            "endif\n" +
            "\n" +
            ":Process wallet payment;\n" +
            ":Deduct amount from user balance;\n" +
            ":Update payment status to COMPLETED;\n" +
            ":Update order status to PREPARING;\n" +
            "\n" +
            "if (Payment successful?) then (no)\n" +
            "  :Show payment error;\n" +
            "  :Rollback balance deduction;\n" +
            "  stop\n" +
            "endif\n" +
            "\n" +
            ":Generate digital receipt;\n" +
            ":Display receipt to user;\n" +
            ":Clear shopping cart;\n" +
            ":Update balance display;\n" +
            ":Show success message;\n" +
            "\n" +
            "fork\n" +
            "  :Send order to preparation queue;\n" +
            "  note right: Admin can track order status\n" +
            "fork again\n" +
            "  :Update inventory levels;\n" +
            "  note right: Deduct ingredients if implemented\n" +
            "fork again\n" +
            "  :Log transaction for reporting;\n" +
            "  note right: Used for daily/weekly/monthly reports\n" +
            "end fork\n" +
            "\n" +
            ":Order processing complete;\n" +
            "\n" +
            "stop\n" +
            "\n" +
            "@enduml";
        
        try (FileWriter writer = new FileWriter("docs/diagrams/Activity_Diagram.puml")) {
            writer.write(plantUMLCode);
            System.out.println("✅ Activity Diagram PlantUML file generated: docs/diagrams/Activity_Diagram.puml");
            System.out.println("📋 To generate PNG: Use PlantUML online editor or local installation");
            System.out.println("🔗 Online: http://www.plantuml.com/plantuml/uml/");
        } catch (IOException e) {
            System.err.println("❌ Error generating activity diagram: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        generateActivityDiagram();
    }
}