# Software Requirements Specification (SRS)
## Coffee Vending System
### Version 1.0
### IEEE 830-1998 Standard

---

## Table of Contents
1. [Introduction](#1-introduction)
2. [Overall Description](#2-overall-description)
3. [Specific Requirements](#3-specific-requirements)
4. [System Features](#4-system-features)
5. [External Interface Requirements](#5-external-interface-requirements)
6. [Non-Functional Requirements](#6-non-functional-requirements)
7. [Appendices](#7-appendices)

---

## 1. Introduction

### 1.1 Purpose
This Software Requirements Specification (SRS) document describes the functional and non-functional requirements for the Coffee Vending System. The system is designed to provide an automated coffee ordering and dispensing solution with comprehensive user and administrative functionality.

### 1.2 Scope
The Coffee Vending System is a Java Swing-based desktop application that enables:
- **Users**: Register, login, browse menu, place orders, make payments, view receipts
- **Administrators**: Manage inventory, monitor orders, generate reports, update menu
- **System**: Real-time data updates, transaction processing, inventory management

### 1.3 Definitions and Abbreviations
- **CVS**: Coffee Vending System
- **GUI**: Graphical User Interface
- **JDBC**: Java Database Connectivity
- **DAO**: Data Access Object
- **SRS**: Software Requirements Specification
- **UML**: Unified Modeling Language

### 1.4 References
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications
- Java SE 11 Documentation
- MySQL 8.0 Documentation
- Maven Build Tool Documentation

### 1.5 Overview
This document provides a comprehensive specification of the Coffee Vending System, including functional requirements, system architecture, user interfaces, and performance criteria.

---

## 2. Overall Description

### 2.1 Product Perspective
The Coffee Vending System is a standalone desktop application that interfaces with a MySQL database. The system architecture follows the Model-View-Controller (MVC) pattern with clear separation of concerns:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │   Business      │    │   Data Access   │
│   Layer (GUI)   │◄──►│   Logic Layer   │◄──►│   Layer (DAO)   │
│                 │    │   (Services)    │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                               ┌─────────────────┐
                                               │   MySQL         │
                                               │   Database      │
                                               └─────────────────┘
```

**Reference Diagrams:**
- ER Diagram: `/docs/diagrams/ER_Diagram.png`
- Class Diagram: `/docs/diagrams/Class_Diagram.png`
- Use Case Diagram: `/docs/diagrams/UseCase_Diagram.png`

### 2.2 Product Functions
The system provides the following major functions:

#### 2.2.1 User Management
- User registration with email validation
- Secure authentication with SHA-256 password hashing
- Profile management and balance tracking

#### 2.2.2 Order Management
- Coffee menu browsing with real-time availability
- Order customization (size, sugar level, milk level)
- Shopping cart functionality
- Payment processing with multiple methods

#### 2.2.3 Administrative Functions
- Inventory management with low stock alerts
- Menu item management (add, edit, toggle availability)
- Order monitoring and status updates
- Comprehensive reporting (daily, weekly, monthly)

#### 2.2.4 System Functions
- Real-time data synchronization
- Transaction management with ACID properties
- Automated receipt generation
- Performance monitoring and logging

### 2.3 User Classes and Characteristics

#### 2.3.1 End Users (Customers)
- **Technical Expertise**: Basic computer literacy
- **Usage Frequency**: Daily to weekly
- **Primary Goals**: Quick coffee ordering, payment processing
- **Key Requirements**: Intuitive interface, fast transactions

#### 2.3.2 System Administrators
- **Technical Expertise**: Moderate to advanced
- **Usage Frequency**: Daily monitoring, periodic management
- **Primary Goals**: System maintenance, business analytics
- **Key Requirements**: Comprehensive controls, detailed reporting

### 2.4 Operating Environment
- **Operating System**: Windows 10/11, macOS, Linux
- **Java Runtime**: Java SE 11 or higher
- **Database**: MySQL 8.0 or higher
- **Memory**: Minimum 512MB RAM
- **Storage**: 100MB available disk space
- **Network**: Local network access for database connectivity

### 2.5 Design and Implementation Constraints
- **Programming Language**: Java 11
- **GUI Framework**: Java Swing
- **Database**: MySQL with JDBC connectivity
- **Build Tool**: Apache Maven
- **Architecture**: MVC pattern with layered architecture
- **Security**: SHA-256 password hashing, SQL injection prevention

---

## 3. Specific Requirements

### 3.1 Functional Requirements

#### 3.1.1 User Registration (FR-001)
**Description**: System shall allow new users to create accounts
**Input**: Name, email, password, password confirmation
**Processing**: 
- Validate email format and uniqueness
- Hash password using SHA-256
- Create user record with $10 welcome bonus
**Output**: Registration confirmation and automatic login

#### 3.1.2 User Authentication (FR-002)
**Description**: System shall authenticate users and administrators
**Input**: Email/username and password
**Processing**: 
- Retrieve user credentials from database
- Verify hashed password
- Establish user session
**Output**: Access to appropriate dashboard

#### 3.1.3 Menu Browsing (FR-003)
**Description**: System shall display available coffee items
**Input**: User request to view menu
**Processing**: 
- Retrieve available coffee items from database
- Display with prices and descriptions
**Output**: Interactive menu with selection options

#### 3.1.4 Order Customization (FR-004)
**Description**: System shall allow order customization
**Input**: Coffee selection, size, sugar level, milk level, quantity
**Processing**: 
- Calculate dynamic pricing based on selections
- Apply size multipliers and customization fees
**Output**: Customized order item with calculated price

#### 3.1.5 Payment Processing (FR-005)
**Description**: System shall process payments securely
**Input**: Order total, payment method selection
**Processing**: 
- Validate sufficient balance (for wallet payments)
- Deduct amount from user balance
- Update order and payment status
**Output**: Payment confirmation and receipt

#### 3.1.6 Inventory Management (FR-006)
**Description**: System shall manage ingredient inventory
**Input**: Ingredient updates, quantity changes
**Processing**: 
- Update ingredient quantities
- Check against minimum thresholds
- Generate low stock alerts
**Output**: Updated inventory status and alerts

#### 3.1.7 Report Generation (FR-007)
**Description**: System shall generate comprehensive reports
**Input**: Report type selection (daily, weekly, monthly)
**Processing**: 
- Aggregate order and sales data
- Calculate statistics and trends
- Format report output
**Output**: Detailed analytical reports

### 3.2 Data Requirements

#### 3.2.1 Database Schema
The system utilizes a relational database with the following entities:

**Users Table**
- user_id (PK), name, email (UNIQUE), password, balance, timestamps

**Coffee Menu Table**
- coffee_id (PK), name, price, description, available, created_at

**Orders Table**
- order_id (PK), user_id (FK), total_amount, payment_status, order_status, order_time

**Order Items Table**
- order_item_id (PK), order_id (FK), coffee_id (FK), quantity, customizations, item_price

**Payments Table**
- payment_id (PK), order_id (FK), amount, payment_type, payment_status, transaction_id

**Ingredients Table**
- ingredient_id (PK), name, quantity, unit, min_threshold, updated_at

**Admin Table**
- admin_id (PK), username (UNIQUE), password, created_at

**Reference**: ER Diagram at `/docs/diagrams/ER_Diagram.png`

---

## 4. System Features

### 4.1 User Dashboard
**Priority**: High
**Description**: Comprehensive user interface for coffee ordering

**Functional Requirements**:
- Display coffee menu with real-time availability
- Shopping cart with item management
- Balance display and top-up functionality
- Order history and receipt access

**Sequence Flow**: Reference `/docs/diagrams/Sequence_Diagram.png`

### 4.2 Admin Dashboard
**Priority**: High
**Description**: Administrative control panel for system management

**Functional Requirements**:
- System statistics and analytics dashboard
- Menu item management (CRUD operations)
- Order monitoring and status updates
- Inventory management with alerts
- Report generation and export

### 4.3 Payment System
**Priority**: Critical
**Description**: Secure payment processing with multiple methods

**Functional Requirements**:
- Wallet-based payments (primary)
- Balance validation and deduction
- Transaction logging and receipt generation
- Payment failure handling and rollback

### 4.4 Inventory Management
**Priority**: Medium
**Description**: Real-time inventory tracking and management

**Functional Requirements**:
- Ingredient quantity monitoring
- Low stock threshold alerts
- Bulk quantity updates
- Inventory usage tracking

---

## 5. External Interface Requirements

### 5.1 User Interfaces

#### 5.1.1 Main Interface
- **Window Size**: 800x600 pixels (minimum)
- **Color Scheme**: Coffee-themed (browns, tans, whites)
- **Navigation**: Button-based with clear labeling
- **Accessibility**: Keyboard navigation support

#### 5.1.2 User Dashboard
- **Layout**: Grid-based with menu and cart sections
- **Real-time Updates**: Balance and cart synchronization
- **Responsive Design**: Adapts to window resizing

#### 5.1.3 Admin Dashboard
- **Layout**: Tabbed interface with 5 main sections
- **Data Tables**: Sortable columns with pagination
- **Charts**: Statistical visualizations for reports

### 5.2 Hardware Interfaces
- **Input**: Standard keyboard and mouse
- **Display**: Minimum 1024x768 resolution
- **Storage**: Local file system access for logs and configuration

### 5.3 Software Interfaces

#### 5.3.1 Database Interface
- **Protocol**: JDBC over TCP/IP
- **Driver**: MySQL Connector/J 8.2.0
- **Connection Pooling**: HikariCP (future enhancement)
- **Transaction Management**: ACID compliance

#### 5.3.2 Operating System Interface
- **File System**: Configuration file access
- **Process Management**: JVM integration
- **Network**: TCP/IP for database connectivity

---

## 6. Non-Functional Requirements

### 6.1 Performance Requirements

#### 6.1.1 Response Time
- **Database Queries**: < 500ms for standard operations
- **GUI Responsiveness**: < 100ms for button clicks
- **Order Processing**: < 2 seconds end-to-end
- **Report Generation**: < 5 seconds for monthly reports

#### 6.1.2 Throughput
- **Concurrent Users**: Support up to 10 simultaneous sessions
- **Transaction Rate**: 100 orders per hour peak capacity
- **Database Connections**: Maximum 20 concurrent connections

#### 6.1.3 Resource Utilization
- **Memory Usage**: < 256MB RAM during normal operation
- **CPU Usage**: < 10% during idle, < 50% during peak
- **Disk Space**: < 50MB for application, 100MB for database

### 6.2 Security Requirements

#### 6.2.1 Authentication
- **Password Policy**: Minimum 6 characters
- **Encryption**: SHA-256 hashing for passwords
- **Session Management**: Automatic timeout after inactivity

#### 6.2.2 Data Protection
- **SQL Injection**: Parameterized queries only
- **Input Validation**: All user inputs sanitized
- **Access Control**: Role-based permissions (User/Admin)

#### 6.2.3 Audit Trail
- **Transaction Logging**: All financial transactions logged
- **User Actions**: Login/logout events recorded
- **System Changes**: Administrative actions tracked

### 6.3 Reliability Requirements

#### 6.3.1 Availability
- **Uptime**: 99% availability during business hours
- **Recovery Time**: < 5 minutes for system restart
- **Data Backup**: Automated daily database backups

#### 6.3.2 Error Handling
- **Graceful Degradation**: System continues with reduced functionality
- **Error Messages**: User-friendly error descriptions
- **Logging**: Comprehensive error logging for debugging

### 6.4 Usability Requirements

#### 6.4.1 Ease of Use
- **Learning Curve**: New users productive within 5 minutes
- **Navigation**: Maximum 3 clicks to complete any task
- **Help System**: Context-sensitive help and tooltips

#### 6.4.2 Accessibility
- **Keyboard Navigation**: Full functionality without mouse
- **Visual Design**: High contrast, readable fonts
- **Error Prevention**: Input validation with clear feedback

### 6.5 Maintainability Requirements

#### 6.5.1 Code Quality
- **Documentation**: Comprehensive inline comments
- **Testing**: 80% code coverage with unit tests
- **Architecture**: Modular design with clear interfaces

#### 6.5.2 Deployment
- **Installation**: Single JAR file deployment
- **Configuration**: External properties file
- **Updates**: Hot-swappable components where possible

---

## 7. Appendices

### 7.1 Glossary
- **Balance**: User's available funds for purchases
- **Cart**: Temporary storage for items before checkout
- **Customization**: Order modifications (size, sugar, milk)
- **Receipt**: Digital proof of purchase
- **Session**: User's authenticated connection period

### 7.2 Analysis Models

#### 7.2.1 Use Case Diagram
**Location**: `/docs/diagrams/UseCase_Diagram.png`
**Description**: Shows all system actors and their interactions

#### 7.2.2 Activity Diagram
**Location**: `/docs/diagrams/Activity_Diagram.png`
**Description**: Illustrates the complete order process workflow

#### 7.2.3 Sequence Diagram
**Location**: `/docs/diagrams/Sequence_Diagram.png`
**Description**: Details the order and payment processing sequence

#### 7.2.4 Class Diagram
**Location**: `/docs/diagrams/Class_Diagram.png`
**Description**: Shows system architecture and class relationships

#### 7.2.5 ER Diagram
**Location**: `/docs/diagrams/ER_Diagram.png`
**Description**: Database schema with entity relationships

### 7.3 Requirements Traceability Matrix

| Requirement ID | Description | Implementation | Test Case | Status |
|----------------|-------------|----------------|-----------|---------|
| FR-001 | User Registration | UserService.registerUser() | TC-001 | ✅ Complete |
| FR-002 | Authentication | UserService.authenticateUser() | TC-002 | ✅ Complete |
| FR-003 | Menu Browsing | CoffeeMenuDAO.getAvailableItems() | TC-003 | ✅ Complete |
| FR-004 | Order Customization | OrderService.calculateItemPrice() | TC-004 | ✅ Complete |
| FR-005 | Payment Processing | OrderService.processPayment() | TC-005 | ✅ Complete |
| FR-006 | Inventory Management | IngredientDAO.updateQuantity() | TC-006 | ✅ Complete |
| FR-007 | Report Generation | ReportService.generateReports() | TC-007 | ✅ Complete |

### 7.4 Change History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2024-10-16 | Development Team | Initial SRS document |

---

**Document Status**: Final
**Approval**: Ready for Phase 4 Implementation
**Next Review**: Phase 5 Testing Validation