# Concert Booking API

---

## Requirements
- **Java 17** or higher
- **Maven 3.8+**
- **PostgreSQL 15+**
- **Flyway** (for database migrations)
- **Git** (to clone the repository)

---

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ahmadhasya/booking-concert-api.git
   cd booking-concert-api
   ```

2. **Create database**
   ```bash
   CREATE DATABASE bsi;
   ```

3. **Run migrations**

    Migration will run automatically when startup.


4. **Build and run**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## Architecture

   ```bash
   
   ├───docs # Contains API Blueprint documentations
   └───src
       └───main
           ├───java
           │   └───com
           │       └───ahmad
           │           └───bsi
           │               ├───config # Contains configuration for migration and security
           │               ├───controller # Contains routing for APIs
           │               ├───exception # Contains Exception Handler
           │               ├───form # Contains form for input data
           │               ├───model # Contains entity to identify table
           │               ├───repository # Contains interfaces to do some CRUD actions
           │               ├───service # Contains method that connect repository and controller
           │               └───util # Utility classes
           └───resources
                └───db
                   └───migration # Contains SQL files for flyway migrations 
   ```