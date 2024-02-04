
# CustomerApp
This is a simple Java application for managing customers, built using Spring Boot, Spring Data JPA, and Spring Security.

## Getting Started
These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites
Make sure you have the following installed:

* JDK 17
* Maven 3.x
* MySQL Server

Installing
Clone the repository:


## git clone [https://github.com/your-username/customerApp.git](https://github.com/misterKumar/sunbase_backend.git)
Build the project:


mvn clean install

## Configure the database by modifying the application.properties file in the src/main/resources directory:

# properties

* spring.datasource.url=jdbc:mysql://localhost:3306/customerdb?useSSL=false&serverTimezone=UTC
* spring.datasource.username=
* spring.datasource.password=

# Run the application:


mvn spring-boot:run

The application will be available at http://localhost:9998.

you can adjust the server port 

## Endpoints
&nbsp;&nbsp;&nbsp;&nbsp;Endpoint&nbsp;&nbsp;&nbsp;&nbsp; Method&nbsp;&nbsp; 	&nbsp;&nbsp;Description
* /api/v1/sunbase/auth/register	POST	Register a new user

* /api/v1/sunbase/auth/login	POST	Login an existing user

* /api/v1/sunbase/customer/add-customer	POST	Add a new customer

* /api/v1/sunbase/customer/get-customerById	GET	Get a customer by UUID

* /api/v1/sunbase/customer/edit-customer	PUT	Edit a customer by UUID

* /api/v1/sunbase/customer/delete	DELETE	Delete a customer by UUID

* /api/v1/sunbase/customer/sync-customer	GET	Sync customers from an external API

## Error Responses
Error&nbsp;&nbsp; Code&nbsp;&nbsp;	Description
* 400	Bad Request - Invalid parameters

* 401	Unauthorized - Unauthorized access

* 404	Not Found - Resource not found

* 500	Internal Server Error - Unexpected error

# Technologies Used
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* Maven

## Dependencies
If you wish to create a new project using Spring Initializer, include the following dependencies:

* Spring Web
* Spring Data JPA
* Spring Security
* MySQL Driver
* Gson
* JJWT (Java JSON Web Tokens)
Feel free to explore and enhance the functionality of this CustomerApp!
