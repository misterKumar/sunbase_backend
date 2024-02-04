##CustomerApp
This is a simple Java application that allows you to manage customers. It uses Spring Boot, Spring Data JPA, and Spring Security.

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
What things you need to install and how to install them

JDK 17
Maven 3.x
MySQL Server
Installing

#Clone the repository

git clone [https://github.com/your-username/customerApp.git](https://github.com/misterKumar/sunbase_backend.git)
Build the project

mvn clean install
Configure the database by modifying the application.properties file in the src/main/resources directory

spring.datasource.url=jdbc:mysql://localhost:3306/customerdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>

#Run the application

mvn spring-boot:run
The application will be available at http://localhost:8080.

Endpoints
Endpoint	Method	Description
/api/v1/sunbase/auth/register	POST	Register a new user
/api/v1/sunbase/auth/login	POST	Login an existing user
/api/v1/sunbase/customer/add-customer	POST	Add a new customer
/api/v1/sunbase/customer/get-customerById	GET	Get a customer by UUID
/api/v1/sunbase/customer/edit-customer	PUT	Edit a customer by UUID
/api/v1/sunbase/customer/delete	DELETE	Delete a customer by UUID
/api/v1/sunbase/customer/sync-customer	GET	Sync customers from an external API

Error Responses
Error	Description
400 Bad Request	Invalid request parameters
401 Unauthorized	Unauthorized access
404 Not Found	Resource not found
500 Internal Server Error	Unexpected error

Technologies Used
Spring Boot
Spring Data JPA
Spring Security
MySQL
Maven


dependencies if you widh to create  a new spring initialiser
Spring Web: This is the core of the application. It provides an easy way to create stand-alone, production-grade Spring-based applications.
Spring Data JPA: This is used for database access. It provides a simple and convenient way to interact with databases using Java Persistence API (JPA).
Spring Security: This is used for authentication and authorization. It provides a robust and highly customizable security framework for Java applications.
MySQL Connector/J: This is the JDBC driver for MySQL. It allows the application to connect to a MySQL database.
Gson: This is a Java library for converting Java Objects into JSON and vice versa.
JJWT: This is a Java library for creating and parsing JSON Web Tokens (JWT). It is used for authentication and authorization.
