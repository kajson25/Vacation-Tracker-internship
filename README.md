# Microservices Application - Admin and Employee Services

## Overview

This project consists of two microservices: `admin-service` and `employee-service`, both of which connect to a shared PostgreSQL database. The application is built using Kotlin with Spring Boot and managed with Gradle Kotlin. Docker is used for containerization.

### Admin Service

- **Privileges:** The admin service has full privileges and can access all client data.
- **Authentication:** The admin service uses basic X-API-Key authentication.

### Employee Service

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

1. Clone the repository:

    ```bash
    git clone https://github.com/kajson25/RBT_Project.git
    ```

2. Build the project:

    ```bash
    ./gradlew build
    ```

3. Configure your PostgreSQL database with the required schema.

4. Set up the necessary environment variables in `application.properties` for both services.

5. Start the services using Docker Compose:

    ```bash
    docker-compose up --build
    ```

## Usage

### Admin Service

The admin service exposes several endpoints for administrative tasks such as importing employee and vacation data from CSV files.

### Employee Service

The employee service allows employees to authenticate and manage their vacation days, providing endpoints for viewing and adding used vacation days.

## Database - ERD

![myERD](https://github.com/user-attachments/assets/415b2387-26fb-403f-914f-6ebda306de4b)

## Configuration

### `application.properties`

Both microservices require configuration settings defined in `application.properties`. Below is an example of key configuration parameters:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
```

## API Routes

### Admin Service Routes

- **POST** `/api/admin/importEmployees`  
  Import employee data from a CSV file.

- **POST** `/api/admin/importVacations`  
  Import vacation data from a CSV file.

- **POST** `/api/admin/importUsedDays`  
  Import used vacation days from a CSV file.

- **GET** `/api/admin/allEmployees`  
  Retrieve all employee data.

### Employee Service Routes

- **POST** `/api/authenticate`  
  Authenticate the employee and generate a JWT token.

- **POST** `/api/employee/addUsedDays`  
  Add used vacation days by importing data from a CSV file.

- **GET** `/api/employee/usedDays`  
  Retrieve the number of used vacation days for a given year.

- **GET** `/api/employee/allVacationDays`  
  Retrieve the total number of vacation days for a given year.

- **GET** `/api/employee/availableDays`  
  Retrieve the number of available vacation days for a given year.

- **GET** `/api/employee/usedDaysInPeriod`  
  Retrieve the number of used vacation days in a given period.

### Running Tests

To run the tests, use the following command:

```bash
./gradlew test
```
Docker
Building and Running Containers
The application is containerized using Docker. You can build and run the containers using Docker Compose:

```bash:
docker-compose up --build
```
This command will start both the admin and employee services along with a PostgreSQL database.

Docker Files
Admin Service: admin-service/Dockerfile
Employee Service: employee-service/Dockerfile
Docker Compose: docker-compose.yml
Contributing
Contributions are welcome! Please fork this repository, create a new branch, and submit a pull request. Make sure to follow the coding standards and include appropriate tests.

Contact
For any inquiries, please contact:

Name: Katarina Vucicevic
Email: katarina.vuciceivc@interns.rbt.rs
GitHub: kajson25
