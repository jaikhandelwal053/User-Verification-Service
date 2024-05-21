# User Verification Service

## Overview
The User Verification Service is a Spring Boot application designed to manage and verify user information. It supports creating users, retrieving users with sorting and pagination, and listing all users. The application uses custom validators to ensure the correctness of input parameters.
## Technologies Used
    Spring Boot
    Spring MVC
    Spring Data JPA
    MySQL
    WebClient
    Mockito
    JUnit 5

# Setup Instructions

## Prerequisites
    Java 11 or higher
    Maven
    MySQL

## Steps to run 

### Clone the repository:
    git clone `https://github.com/jaikhandelwal053/User-Verification-Service`
    cd user-verification-service

### Configure the database:
Update the application.properties file with your MySQL database details.
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update

### Build the project:
    mvn clean install

### Run the application:
    mvn spring-boot:run


## API Endpoints

### Create Users
    POST /users?size=2
### Get Users
    GET /users?sortType=name&sortOrder=odd&limit=5&offset=0
### List All Users
    GET /users/listUser
