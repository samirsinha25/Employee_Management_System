FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/employeemanagement.jar employeemanagement.jar
ENTRYPOINT ["java", "-jar", "employeemanagement.jar"]