# 🍕 Zomato Restaurant Management System

> **A production-ready backend system for restaurant management, order processing, delivery tracking, and payment integration - Inspired by Zomato.** Built with **Spring Boot**, **PostgreSQL**, and **Docker**. Clean Architecture · REST APIs · JWT Ready · Dockerized Database

---

## 📌 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [API Endpoints](#-api-endpoints)
- [Setup & Installation](#-setup--installation)
- [Docker Setup](#-docker-setup)
- [Postman Testing Guide](#-postman-testing-guide)
- [Error Handling](#-error-handling)
- [Key Highlights](#-key-highlights)
- [Future Enhancements](#-future-enhancements)
- [Author](#-author)

---

## 🚀 Overview

This project is a **complete backend solution** for a food delivery platform, similar to Zomato. It manages restaurants, menus, customers, orders, payments, deliveries, and reviews through a robust REST API built with **Spring Boot** and **PostgreSQL**.

The system demonstrates industry-level skills in building **scalable**, **secure**, and **maintainable** backend applications with a clean, layered architecture.

---

## ✨ Features

### 🏪 Restaurant Management
- ✅ Create, Read, Update, Delete (CRUD) restaurants
- ✅ Categorize restaurants (Indian, Chinese, Italian, etc.)
- ✅ Add multiple menus and menu items
- ✅ Toggle menu item availability
- ✅ Search restaurants by city and name
- ✅ Get top-rated restaurants

### 👤 Customer Management
- ✅ Register new customers
- ✅ Add multiple addresses (Home, Office, Other)
- ✅ View order history
- ✅ Write and manage reviews with ratings

### 📦 Order Processing
- ✅ Create orders with multiple items
- ✅ Automatic total amount calculation
- ✅ Order status workflow: **PLACED → ONWAY → DELIVERED**
- ✅ Cancel orders before delivery
- ✅ Process payments (UPI, Card, Cash)
- ✅ Automatic delivery person assignment

### 🚚 Delivery Management
- ✅ CRUD operations for delivery persons
- ✅ Track availability status (AVAILABLE/BUSY)
- ✅ Auto-assign available delivery person on payment

### ⚙️ Backend Features
- ✅ Layered architecture (Controller → Service → Repository)
- ✅ Global exception handling with consistent response format
- ✅ JPA/Hibernate for database mapping
- ✅ PostgreSQL database with Docker support
- ✅ Connection pooling with HikariCP
- ✅ DTO pattern for request/response
- ✅ Transaction management using `@Transactional`
- ✅ Actuator endpoints for monitoring

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 LTS | Main programming language |
| **Spring Boot** | 2.7.18 | Application framework |
| **Spring Data JPA** | 2.7.18 | Database ORM |
| **Spring Web** | 2.7.18 | REST API development |
| **Hibernate** | 5.6.15 | JPA implementation |
| **PostgreSQL** | 15.0 | Relational database |
| **Maven** | 3.8.6 | Dependency management |

### Development Tools
| Tool | Version | Purpose |
|------|---------|---------|
| **Spring Tool Suite 4** | 4.0.5 | IDE for development |
| **Docker Desktop** | 20.10+ | Containerization |
| **Postman** | Latest | API testing |
| **pgAdmin** | 7.0 | Database GUI |

### Key Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```


## 🏗️ Architecture

This project follows **Clean / Layered Architecture** with strict separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│                    Postman / Frontend                    │
│                   (HTTP + JSON Requests)                 │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                   Controllers (API Layer)               │
│         Validates requests · Returns HTTP responses     │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                    Service Layer                        │
│       Business logic · Validation · Transaction Mgmt    │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                 Repository Layer                        │
│         JPA/Hibernate · Database operations             │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                  PostgreSQL Database                    │
│           (12 Tables · Relationships · Indexes)         │
└─────────────────────────────────────────────────────────┘
```

**Design Principles:** SOLID · DRY · Dependency Injection · Interface-driven development

---

## 📂 Project Structure

```
zomato-app/
│
├── 📁 src/main/java/com/zomato/
│   ├── ZomatoApplication.java
│   │
│   ├── 📁 config/
│   │   ├── AppConfig.java                       # CORS configuration
│   │   └── GlobalExceptionHandler.java          # Global exception handling
│   │
│   ├── 📁 controller/
│   │   ├── RestaurantController.java
│   │   ├── CustomerController.java
│   │   ├── OrderController.java
│   │   ├── CategoryController.java
│   │   └── DeliveryPersonController.java
│   │
│   ├── 📁 entity/                               # 11 JPA entities
│   │   ├── Restaurant.java
│   │   ├── Category.java
│   │   ├── Menu.java
│   │   ├── MenuItem.java
│   │   ├── Customer.java
│   │   ├── Address.java
│   │   ├── DeliveryPerson.java
│   │   ├── Orders.java
│   │   ├── OrderItem.java
│   │   ├── Payment.java
│   │   └── Review.java
│   │
│   ├── 📁 repository/                           # 10 JPA repositories
│   │   ├── RestaurantRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── MenuRepository.java
│   │   ├── MenuItemRepository.java
│   │   ├── CustomerRepository.java
│   │   ├── AddressRepository.java
│   │   ├── DeliveryPersonRepository.java
│   │   ├── OrdersRepository.java
│   │   ├── PaymentRepository.java
│   │   └── ReviewRepository.java
│   │
│   └── 📁 service/                              # 3 service classes
│       ├── RestaurantService.java
│       ├── CustomerService.java
│       └── OrderService.java
│
├── 📁 src/main/resources/
│   ├── application.properties                   # Spring Boot config
│   └── init.sql                                 # Database initialization
│
├── docker-compose.yml                           # PostgreSQL + pgAdmin
├── pom.xml                                      # Maven dependencies
└── README.md                                    # Project documentation
```

### File Statistics

| Category | Count |
|----------|-------|
| Entity Classes | 11 |
| Repository Interfaces | 10 |
| Service Classes | 3 |
| Controller Classes | 5 |
| Configuration Classes | 2 |
| **Total Java Files** | **31** |
| **API Endpoints** | **50+** |
| **Database Tables** | **12** |

---

## 🗄️ Database Schema

### Entity Relationship Diagram (ERD)

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  CUSTOMER   │────<│    ORDERS    │>────│ RESTAURANT  │
└─────────────┘     └──────────────┘     └─────────────┘
       │                    │                    │
       ▼                    ▼                    ▼
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  ADDRESS    │     │  ORDER_ITEM  │     │   REVIEW    │
└─────────────┘     └──────────────┘     └─────────────┘
                           │
                           ▼
                    ┌──────────────┐     ┌─────────────┐
                    │  MENU_ITEM   │────<│    MENU     │
                    └──────────────┘     └─────────────┘
                           │                    │
                           ▼                    ▼
                    ┌──────────────┐     ┌─────────────┐
                    │   PAYMENT    │     │  CATEGORY   │
                    └──────────────┘     └─────────────┘
```

### Database Tables

| # | Table Name | Description | Key Fields |
|---|------------|-------------|------------|
| 1 | `category` | Food categories | category_id (PK), category_name |
| 2 | `restaurant` | Restaurant details | restaurant_id (PK), name, city, rating |
| 3 | `restaurant_category` | Junction table (Many-to-Many) | restaurant_id (FK), category_id (FK) |
| 4 | `menu` | Restaurant menus | menu_id (PK), restaurant_id (FK) |
| 5 | `menu_item` | Food items | item_id (PK), menu_id (FK), price |
| 6 | `customer` | Customer information | customer_id (PK), full_name, email, phone |
| 7 | `address` | Customer addresses | address_id (PK), customer_id (FK) |
| 8 | `delivery_person` | Delivery personnel | delivery_id (PK), name, status |
| 9 | `orders` | Order transactions | order_id (PK), customer_id (FK), restaurant_id (FK) |
| 10 | `order_item` | Items in each order | order_id (FK), item_id (FK), quantity |
| 11 | `payment` | Payment details | payment_id (PK), order_id (FK) |
| 12 | `review` | Customer reviews | review_id (PK), customer_id (FK), restaurant_id (FK) |

### Relationship Summary

| Relationship Type | Entities |
|------------------|----------|
| **One-to-Many** | Customer → Address, Customer → Orders, Customer → Review |
| **One-to-Many** | Restaurant → Menu, Restaurant → Orders, Restaurant → Review |
| **One-to-Many** | Menu → MenuItem, Orders → OrderItem |
| **Many-to-Many** | Restaurant ↔ Category (via restaurant_category) |
| **One-to-One** | Orders ↔ Payment |

---

## 🔑 API Endpoints

> **Base URL:** `http://localhost:8080`

### Category APIs (`/api/categories`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/categories` | Create category |
| `GET` | `/api/categories` | Get all categories |
| `GET` | `/api/categories/{id}` | Get category by ID |
| `PUT` | `/api/categories/{id}` | Update category |
| `DELETE` | `/api/categories/{id}` | Delete category |

### Restaurant APIs (`/api/restaurants`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/restaurants` | Create restaurant |
| `GET` | `/api/restaurants` | Get all restaurants |
| `GET` | `/api/restaurants/{id}` | Get restaurant by ID |
| `PUT` | `/api/restaurants/{id}` | Update restaurant |
| `DELETE` | `/api/restaurants/{id}` | Delete restaurant |
| `GET` | `/api/restaurants/city/{city}` | Get by city |
| `GET` | `/api/restaurants/{id}/reviews` | Get restaurant reviews |
| `GET` | `/api/restaurants/{id}/rating` | Get average rating |
| `POST` | `/api/restaurants/{id}/categories/{cid}` | Add category |
| `POST` | `/api/restaurants/{id}/menus` | Add menu |
| `POST` | `/api/restaurants/menus/{mid}/items` | Add menu item |

### Customer APIs (`/api/customers`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/customers/register` | Register customer |
| `GET` | `/api/customers` | Get all customers |
| `GET` | `/api/customers/{id}` | Get customer by ID |
| `PUT` | `/api/customers/{id}` | Update customer |
| `DELETE` | `/api/customers/{id}` | Delete customer |
| `POST` | `/api/customers/{id}/addresses` | Add address |
| `GET` | `/api/customers/{id}/addresses` | Get addresses |
| `GET` | `/api/customers/{id}/orders` | Get customer orders |
| `POST` | `/api/customers/{id}/reviews` | Add review |

### Order APIs (`/api/orders`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders?customerId={cid}&restaurantId={rid}` | Create order |
| `GET` | `/api/orders` | Get all orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `GET` | `/api/orders/customer/{cid}` | Get by customer |
| `PUT` | `/api/orders/{id}/status?status={status}` | Update status |
| `PUT` | `/api/orders/{id}/cancel` | Cancel order |
| `POST` | `/api/orders/{id}/payment?mode={mode}` | Process payment |

### Delivery Person APIs (`/api/delivery-persons`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/delivery-persons` | Create delivery person |
| `GET` | `/api/delivery-persons` | Get all |
| `GET` | `/api/delivery-persons/available` | Get available persons |
| `GET` | `/api/delivery-persons/{id}` | Get by ID |
| `PUT` | `/api/delivery-persons/{id}` | Update |
| `DELETE` | `/api/delivery-persons/{id}` | Delete |
| `PATCH` | `/api/delivery-persons/{id}/status` | Update status |

### Health & Monitoring APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/actuator/health` | Application health |
| `GET` | `/actuator/info` | Application info |
| `GET` | `/actuator/metrics` | Application metrics |

### Standard API Response Format

```json
{
    "success": true,
    "message": "Restaurant created successfully",
    "data": {
        "restaurantId": 1,
        "name": "Spice Garden",
        "city": "Mumbai",
        "rating": 4.5
    }
}
```

---

## ⚙️ Setup & Installation

### Prerequisites

| Software | Version | Download Link |
|----------|---------|---------------|
| Java JDK | 17+ | [Adoptium](https://adoptium.net/) |
| Maven | 3.8+ | [Apache Maven](https://maven.apache.org/) |
| Docker Desktop | 20.10+ | [Docker](https://www.docker.com/) |
| Spring Tool Suite | 4.0.5 | [Spring Tools](https://spring.io/tools) |
| Postman | Latest | [Postman](https://www.postman.com/) |

### Step-by-Step Setup

#### 🔹 Step 1 — Clone the Repository
```bash
git clone https://github.com/AdiGit-12/zomato-app.git
cd zomato-app
```

#### 🔹 Step 2 — Start Database with Docker
```bash
docker-compose up -d
```

#### 🔹 Step 3 — Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/zomato_db
spring.datasource.username=postgres
spring.datasource.password=postgres123
spring.jpa.hibernate.ddl-auto=update
```

#### 🔹 Step 4 — Build and Run
```bash
# Using Maven
mvn clean spring-boot:run

# Or using STS
# Right-click project → Run As → Spring Boot App
```

#### 🔹 Step 5 — Verify Application
```bash
curl http://localhost:8080/actuator/health
# Expected: {"status":"UP"}
```

---

## 🐳 Docker Setup

### Why Docker?
- No manual PostgreSQL installation needed
- One-command database setup
- Consistent environment across all machines
- Easy cleanup and reset

### Docker Compose Configuration

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: zomato_postgres
    environment:
      POSTGRES_DB: zomato_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  pgadmin:
    image: dpage/pgadmin4
    container_name: zomato_pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@zomato.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"
    depends_on:
      - postgres

volumes:
  postgres_data:
```

### Docker Commands

```bash
# Start containers
docker-compose up -d

# Stop containers
docker-compose down

# Stop and delete all data
docker-compose down -v

# View logs
docker-compose logs -f

# Access PostgreSQL
docker exec -it zomato_postgres psql -U postgres -d zomato_db
```

---

## 📮 Postman Testing Guide

### Environment Setup

1. **Create Environment in Postman**
   - Click "Environments" → "Create Environment"
   - Name: `Zomato Local`

2. **Add Variables**
   ```
   baseUrl: http://localhost:8080
   restaurantId: 1
   customerId: 1
   orderId: 1
   categoryId: 1
   menuId: 1
   itemId: 1
   ```

3. **Select Environment** from dropdown (top right)

### Complete Testing Workflow

#### Test 1: Category Management
```http
POST {{baseUrl}}/api/categories
{
    "categoryName": "Indian",
    "description": "Authentic Indian Cuisine"
}

GET {{baseUrl}}/api/categories
```

#### Test 2: Restaurant Management
```http
POST {{baseUrl}}/api/restaurants
{
    "name": "Spice Garden",
    "phone": "9876543210",
    "city": "Mumbai",
    "rating": 4.5
}

POST {{baseUrl}}/api/restaurants/1/menus
{"menuName": "Lunch Special"}

POST {{baseUrl}}/api/restaurants/menus/1/items
{
    "itemName": "Butter Chicken",
    "price": 350.00,
    "availableStatus": "YES"
}
```

#### Test 3: Customer Registration
```http
POST {{baseUrl}}/api/customers/register
{
    "fullName": "Amit Sharma",
    "phone": "9988776655",
    "email": "amit@email.com",
    "gender": "M"
}

POST {{baseUrl}}/api/customers/1/addresses
{
    "houseNo": "A-101",
    "street": "Marine Drive",
    "city": "Mumbai",
    "pincode": "400001",
    "addressType": "HOME"
}
```

#### Test 4: Order Processing
```http
POST {{baseUrl}}/api/orders?customerId=1&restaurantId=1
[
    {"itemId": 1, "quantity": 2}
]

POST {{baseUrl}}/api/orders/1/payment?paymentMode=UPI

PUT {{baseUrl}}/api/orders/1/status?status=DELIVERED
```

#### Test 5: Reviews & Ratings
```http
POST {{baseUrl}}/api/customers/1/reviews?restaurantId=1
{
    "rating": 5,
    "comments": "Excellent food!"
}

GET {{baseUrl}}/api/restaurants/1/rating
```

---

## ⚠️ Error Handling

All exceptions are caught by `GlobalExceptionHandler` — controllers have zero try-catch blocks. Every error returns a consistent JSON envelope:

```json
{
    "timestamp": "2024-01-15T10:30:00",
    "message": "Restaurant not found with id: 999",
    "status": 400
}
```

| Exception Type | HTTP Status | Scenario |
|----------------|-------------|----------|
| `RuntimeException` | `400 Bad Request` | Resource not found, duplicate entry, validation failure |
| `MethodArgumentNotValidException` | `400 Bad Request` | Invalid input data |
| `Exception` (unhandled) | `500 Internal Server Error` | Unexpected server crash |

---

## 🔥 Key Highlights

| Feature | Status |
|---------|--------|
| Clean layered architecture (Controller → Service → Repository) | ✅ |
| JPA/Hibernate for database mapping | ✅ |
| PostgreSQL with Docker support | ✅ |
| Global exception handling middleware | ✅ |
| CRUD operations for all entities | ✅ |
| Many-to-Many relationships (Restaurant ↔ Category) | ✅ |
| Order processing workflow (PLACED → ONWAY → DELIVERED) | ✅ |
| Payment integration (UPI, Card, Cash) | ✅ |
| Delivery person auto-assignment | ✅ |
| Rating and review system | ✅ |
| DTO pattern for request/response | ✅ |
| Transaction management using `@Transactional` | ✅ |
| Actuator endpoints for monitoring | ✅ |
| Postman collection for API testing | ✅ |

---

## 🔮 Future Enhancements

### Short-term
- [ ] JWT Authentication & Authorization
- [ ] Swagger/OpenAPI documentation
- [ ] Unit testing with JUnit and Mockito
- [ ] Redis caching

### Mid-term
- [ ] WebSocket for real-time order tracking
- [ ] Email notification service
- [ ] SMS service integration
- [ ] File upload for restaurant logos

### Long-term
- [ ] Mobile application (React Native)
- [ ] Admin panel (React/Angular)
- [ ] Machine learning for recommendations
- [ ] Cloud deployment (AWS/Azure/GCP)

---

## 👨‍💻 Author

**Aditya Shinde**
- 📧 Email: as3178083@gmail.com
- 🐙 GitHub: [@AdiGit-12](https://github.com/AdiGit-12)

## 📝 License

This project is licensed under the MIT License.

[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
---

**Built with ❤️ by Aditya Shinde**

*Last Updated: April 2026*
