# Employee Management System

This project is a **Spring Boot** based Employee Management System with **Redis** for caching, running inside Docker containers.

## Features

- CRUD Operations for Employee Management
- Pagination and Search
- Caching with Redis
- Security with JWT, Spring Security, and Role-Based Access Control (RBAC)
- Swagger API Documentation
- Dockerized for easy deployment

## Prerequisites

Before running the application, make sure you have the following installed in local:

- **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
- **minikube Compose**: [Install minikube](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download)

## Setup Instructions

### 1. Clone the Repository

Clone the project from your GitHub repository (or your local repository):

git clone <repository-url>
cd <repository-folder>

###Using IDE Eclipse:
   Import the project as Maven by selecting pom.xml file
   Run command: mvn clean install
   Start application as JAVA application.

### Using Docker File
### 1. Ensure that your Spring Boot application is packaged into an executable JAR.
       mvn clean package
    
### 2.  Dockerize the Application    
        docker build -t employeemanagement:1.0 .
        
### 3. Execute below commands
kubectl create deployment employeemanagement --image=employeemanagement:1.0 --port=8080 

kubectl expose deployment employeemanagement --type=NodePort

minikube service employeemanagement --url

### Collection is attached in this repository in Collections folder to execute CRUD operation.

###Notes
Redis is used for caching purposes.





      
        
        
        
        
"# Employee_Management_System" 
