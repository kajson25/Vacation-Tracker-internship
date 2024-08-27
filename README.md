# Microservices Application - Admin and Employee Services

## Overview

This project consists of two microservices: `admin-service` and `employee-service`, both of which connect to a shared PostgreSQL database. The application is built using Kotlin with Spring Boot and managed with Gradle Kotlin. Docker is used for containerization.

## Admin Service

- **Privileges:** The admin service has full privileges and can access all client data.
- **Authentication:** The admin service uses basic X-API-Key authentication.

## Employee Service

- **Privileges:** The employee service is limited to accessing data relevant to the authenticated employee.
- **Authentication:** The employee service uses JWT tokens for authentication.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [API Routes](#api-routes)
- [Docker](#docker)
- [Contributing](#contributing)
- [Contact](#contact)

## Installation

### Prerequisites

- Java 11+
- Gradle Kotlin
- Docker and Docker Compose
- PostgreSQL

### Steps

1. **Clone the repository:**

    ```bash
    git clone https://github.com/kajson25/RBT_Project.git
    ```

2. **Build the project:**

    ```bash
    ./gradlew build
    ```

3. **Configure your PostgreSQL database** with the required schema.

4. **Set up the necessary environment variables** in `application.properties` for both services.

5. **Start the services using Docker Compose:**

    ```bash
    docker-compose up --build
    ```

## Usage

### Admin Service

The admin service exposes several endpoints for administrative tasks such as importing employee and vacation data from CSV files.

### Employee Service

The employee service allows employees to authenticate and manage their vacation days, providing endpoints for viewing and adding used vacation days.

## Database - ERD

![Database ERD](https://github.com/user-attachments/assets/415b2387-26fb-403f-914f-6ebda306de4b)

## Configuration

### `application.properties`

Both microservices require configuration settings defined in `application.properties`. Below is an example of key configuration parameters:

```properties
# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=postgres
spring.datasource.password=your-admin
```

# JWT Configuration for Employee Service
jwt.secret=your-jwt-secret
jwt.expiration=3600000

# X-API-Key for Admin Service
api.key=admin


API Routes
## Admin Controller

The `AdminController` is responsible for handling administrative tasks related to employees, vacations, and used vacation days. It provides endpoints to import data from CSV files and retrieve information.

### Base URL

`/api/admin`

### Endpoints

#### 1. Import Employees

**Description:** Imports employee data from a CSV file.

- **URL:** `/importEmployees`
- **Method:** `POST`
- **Consumes:** `text/csv`
- **Request Body:** 
  - **data:** (ByteArray) The CSV file content as a byte array. Required.

#### 2. Import Vacations

**Description:** Imports vacation data from a CSV file.

- **URL:** `/importVacations`
- **Method:** `POST`
- **Consumes:** `text/csv`
- **Request Body:** 
  - **data:** (ByteArray) The CSV file content as a byte array. Required.

#### 3. Import Used Days

**Description:** Imports used vacation days data from a CSV file.

- **URL:** `/importUsedDays`
- **Method:** `POST`
- **Consumes:** `text/csv`
- **Request Body:** 
  - **data:** (ByteArray) The CSV file content as a byte array. Required.

#### 4. Get All Employees

**Description:** Retrieves all employees.

- **URL:** `/allEmployees`
- **Method:** `GET`
- **Produces:** `application/json`

## Authentication Controller

The `AuthController` handles operations related to user authentication, including the generation of JWT tokens.

### Base URL

`/api`

### Endpoints

#### 1. Authenticate User

**Description:** Authenticate the user and generate a JWT token.

- **URL:** `/authenticate`
- **Method:** `POST`
- **Consumes:** `application/json`
- **Request Body:** 
  - **employeeRequest:** (EmployeeRequestDTO) The authentication request containing email and password. Required.

---

## Used Days Controller

The `UsedDaysController` manages operations related to the used vacation days of employees.

### Base URL

`/api/employee`

### Endpoints

#### 1. Add Used Day

**Description:** Add a record of used vacation days by importing data from a CSV file.

- **URL:** `/addUsedDays`
- **Method:** `POST`
- **Consumes:** `text/csv`
- **Request Header:** 
  - **Authorization:** (String) The authorization token. Required.
- **Request Body:** 
  - **data:** (ByteArray) The CSV file content as a byte array. Required.

---

## Vacation Controller

The `VacationController` handles operations related to employee vacation days.

### Base URL

`/api/employee`

### Endpoints

#### 1. Get Used Days

**Description:** Retrieve the number of used vacation days for a given year.

- **URL:** `/usedDays`
- **Method:** `GET`
- **Produces:** `application/json`
- **Request Header:** 
  - **Authorization:** (String) The authorization token. Required.
- **Request Parameters:** 
  - **year:** (Int) The year for which used days are being retrieved. Required.

#### 2. Get All Vacation Days

**Description:** Retrieve the total number of vacation days for a given year.

- **URL:** `/allVacationDays`
- **Method:** `GET`
- **Produces:** `application/json`
- **Request Header:** 
  - **Authorization:** (String) The authorization token. Required.
- **Request Parameters:** 
  - **year:** (Int) The year for which all vacation days are being retrieved. Required.

#### 3. Get Available Days

**Description:** Retrieve the number of available vacation days for a given year.

- **URL:** `/availableDays`
- **Method:** `GET`
- **Produces:** `application/json`
- **Request Header:** 
  - **Authorization:** (String) The authorization token. Required.
- **Request Parameters:** 
  - **year:** (Int) The year for which available days are being retrieved. Required.

#### 4. Get Used Days in Period

**Description:** Retrieve the number of used vacation days in a given period.

- **URL:** `/usedDaysInPeriod`
- **Method:** `GET`
- **Produces:** `application/json`
- **Request Header:** 
  - **Authorization:** (String) The authorization token. Required.
- **Request Parameters:** 
  - **startDate:** (String) The start date of the period (yyyy-MM-dd). Required.
  - **endDate:** (String) The end date of the period (yyyy-MM-dd). Required.


```bash
./gradlew test
```
- **Docker**
  - Building and Running Containers
  - The application is containerized using Docker. You can build and run the containers using Docker Compose:

```bash
docker-compose up --build
```
This command will start both the admin and employee services along with a PostgreSQL database.

- **Docker Files**
  - Admin Service: admin-service/Dockerfile
  - Employee Service: employee-service/Dockerfile
  - Docker Compose: docker-compose.yml
  
- **Contributing**
  - Contributions are welcome! Please fork this repository, create a new branch, and submit a pull request. Make sure to follow the coding standards and include appropriate tests.

- **Contact**
  - For any inquiries, please contact:

Name: Katarina Vucicevic
Email: katarina.vuciceivc@interns.rbt.rs
GitHub: kajson25
