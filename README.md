# Role-Based Access Control (RBAC) with JWT Authentication - REST API

### Table of Contents:
1. [Introduction](#introduction)
2. [Project Overview](#project-overview)
3. [JWT Authentication Explained](#jwt-authentication-explained)
4. [Setup Instructions](#setup-instructions)
5. [Project Structure](#project-structure)
6. [Concept of Role-Based Access Control (RBAC)](#concept-of-role-based-access-control-rbac)
7. [Running the Project](#running-the-project)

# Introduction:
This project demonstrates Role-Based Access Control (RBAC) using JWT (JSON Web Token) authentication in a Spring Boot REST API. It supports multiple user roles such as Admin, User, and Superuser, and restricts access to specific API endpoints based on user roles.

The project implements the following:
- Spring Boot for the backend.
- Spring Security to handle authentication and authorization.
- JWT (JSON Web Token) for secure token-based authentication.

# Project Overview

This project is designed to show the management of users and their roles using JWT tokens for authentication. It allows for role-based access control to different API endpoints and restricts access based on roles.

The backend provides the following functionalities:
- User authentication with JWT tokens.
- Role-based access control to API endpoints (e.g., Admins can manage users, but normal users cannot).
- Token refresh mechanism to keep users authenticated.

# JWT Authentication Explained:
1. User Login: The user provides their username and password via the login endpoint. If the credentials are valid, the system generates a JWT token.
2. Token Generation: A JWT token containing the user’s roles and other claims is generated and sent back to the client.
3. Access Control: The token is used for subsequent requests to protected API endpoints. The system validates the token and checks if the user has the necessary roles to access the resource.
4. Token Expiry & Refresh: The token has an expiration time. Users can refresh their token using a refresh token to continue using the application without re-authenticating.

### Diagram of JWT Authentication Flow:

![JWT Authentication Flow](https://github.com/Tanvi0901/Role-Based-Access-Control/blob/main/Role-based-access-control/1.jpg "JWT Authentication Flow")

JWT (JSON Web Token) is an open standard for securely transmitting information as a JSON object between parties. It is used in this project to authenticate users and manage access to API endpoints.

A typical JWT consists of three parts:

1. Header: Specifies the algorithm used to sign the token (e.g., HMAC SHA256 or RSA).
2. Payload: Contains claims, which are statements about an entity (typically the user) and additional data (e.g., roles).
3. Signature: Ensures that the sender of the JWT is who it says it is and that the message hasn’t been altered.

#### JWT Flow:
1. The user logs in with their credentials.
2. If the credentials are correct, a JWT token is generated and returned to the user.
3. The user includes the token in the Authorization header for each subsequent request to protected endpoints.
4. The backend verifies the JWT token, extracts the user roles, and grants or denies access based on those roles.

# Setup Instructions
Follow these steps to get this project up and running on your local machine:

### 1. Prerequisites  
- Java 17+ (Java Development Kit)  
- Maven (for building the project)  
- IDE (Integrated Development Environment) like IntelliJ IDEA or Eclipse.  
- MySQL Database (or another database you prefer)  
- Postman (optional, for testing the APIs)

### 2. Clone the Repository

```
git clone https://github.com/your-repo/role-based-access-control-with-jwt.git
``` 
   

### 3. Import Project into IDE  
- Open your IDE (e.g., IntelliJ IDEA or Eclipse).  
- Select File > Open and open the folder you cloned. 

### 4. Setup Database (MySQL)
1. Install MySQL if it’s not already installed on your system.
2. Create a new database called `rbac_db`.
3. Configure the database connection in application.properties or application.yml under `src/main/resources`:
  
```
spring.datasource.url=jdbc:mysql://localhost:3306/rbac_db  
spring.datasource.username=root  
spring.datasource.password=root  
spring.jpa.hibernate.ddl-auto=update
```

### 5. Build and Run the Project  
- Open a terminal and navigate to the project folder.
- Run the following command to build and start the Spring Boot application:
```
mvn spring-boot:run
```

# Project Structure

![Project Structure](https://github.com/Tanvi0901/Role-Based-Access-Control/blob/main/Role-based-access-control/3.jpg "Project Structure")

# Concept of Role-Based Access Control (RBAC)  
Role-Based Access Control (RBAC) is a method of restricting access to resources based on the roles assigned to users. The system grants users permission to perform certain operations depending on the role assigned to them.

### In this project:

- User: Can access their own profile and view personal information.  
- Admin: Can manage users (view, create, delete users).  
- Superuser: Can manage user roles and assign different roles to users.

### Diagram of Role-Based Access Control (RBAC):

- Sample RBAC example diagram
![Role-Based Access Control (RBAC)](https://github.com/Tanvi0901/Role-Based-Access-Control/blob/main/Role-based-access-control/2.jpg "Role-Based Access Control (RBAC)")

# Running the Project
Once the setup is complete, you can interact with the project by making API calls:

1. User Login:
- Endpoint: /api/v1/login 
- Method: POST 
- Request Body:
- Json : 
```
{
 "username": "user",
 "password": "password123"
}
```

- Response: A JWT token will be returned if the credentials are valid.

2. Access Control:  
   Use the JWT token returned from login in the Authorization header for accessing protected endpoints:
   ```
   Authorization: Bearer <your-jwt-token>
   ```
3. Role-Based Endpoints:  
   Admin and Superuser roles can access certain endpoints such as `GET /api/v1/users` to view all users, while normal users can only access their own profile.
