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

# API Documentation
## Base URL
`http://localhost:8080/sukasa`
## Endpoints
### 1. Get Default Message
#### HTTP Method    -    `GET`
#### Description
Returns a welcome message with the server start time.
#### URL    -    `/`
#### Request Body - No Body Required
#### Response Body
```html
Welcome to the SukasaAirPlotlineApplication!!!
Started Successfully at Time : {current_time}
```
### 2. Login Token
#### HTTP Method    -    `POST`
#### Description
Returns a jwt access token (60 min expiry) for an email
#### URL    -    `/login`
#### Request Body Example -
```json
{
	"email": "asdfg@gmail.com"
}
```
| Field | Type   | Required |
|-------|--------|----------|
| email | String | YES      |

#### Response Body Example -
```json
{
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzdWt"
}
```
| Field | Type   |
|-------|--------|
| token | String |
### 3. Reserve Seat
#### HTTP Method    -    `POST`
#### Description
Confirms and saves seat in the plane
#### URL    -    `/seat/reserve`
#### Authorization    -    Bearer Token (API #2 Login Token)
#### Request Body Example -
```json
{
	"seatNumber":  182,
	"passengerPhone":  "9876543210",
	"passengerName":  "John",
	"passengerAge":  25
}
```
| Field          | Type    | Required |
|----------------|---------|----------|
| seatNumber     | Integer | YES      |
| passengerPhone | String  | YES      |
| passengerName  | String  | YES      |
| passengerAge   | Integer | YES      |
#### Response Body Success Example -
```json
{
    "statusCode":  "10",
    "message":  "SEAT_RESERVED"
}
```
| Field      | Type   |
|------------|--------|
| statusCode | String |
| message    | String |
#### Response Body Failure Example -
```json
{
    "statusCode":  11,
    "message":  "SEAT_UNAVAILABLE"
}
```
| Field      | Type   |
|------------|--------|
| statusCode | String |
| message    | String |
### 4. Reset Seat
#### HTTP Method    -    `GET`
#### Description
Resets **ALL** the seats
#### URL    -    `/seat/reset`
#### Authorization    -    Bearer Token (API #2 Login Token)
#### Authentication    -    Admin access required
#### Request Body Example - No Body Required
#### Response Body Example -
```json
{
    "statusCode":  "00",
    "message":  "RESET_SUCCESSFUL"
}
```
| Field      | Type   |
|------------|--------|
| statusCode | String |
| message    | String |