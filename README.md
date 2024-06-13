# Sukasa-Air-Plotline

# Building and Running Project
## Prerequisites
1. **Java Development Kit (JDK)**
    - JDK 17. You can download it online or from Intellij.
2. **Maven**
    - Apache Maven 3.6.3 or higher. You can download it from [Maven's website](https://maven.apache.org/download.cgi).

## Building the Project
1. Open your terminal and navigate to the root directory of the Spring Boot project:
   ```bash
   cd /path/to/your/project
2. Run the following command to clean the project, install dependencies, compile your code and package the application into a JAR file located in the `target` directory:
   ```bash
   mvn clean install
3. To run the Spring Boot application, use the following command:
   ```bash
   mvn spring-boot:run
4. Alternatively, you can run the packaged JAR file directly:
   ```bash
   java -jar target/sukasa-air-plotline-1.0-SNAPSHOT.jar
5. To verify if the server is running, open a web browser and navigate to:
   `http://localhost:8080/sukasa/`

