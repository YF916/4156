# COMSW4156 - ADVANCED SOFTWARE ENGINEERING

### Team Information

    Team Name: The A

    Team Members:
        Xiaoning Bu (xb2171)
        Xinyu Fu (xf2243)
        Yifan Li (yl5337)
        Zian Song (zs2632)

    Programming Language: Java
    Development Platform: MacOS

    GitHub Repository Link: https://github.com/YF916/COMSW4156-ASE-project

### Build and run the application

in your application.properties file:
``` code spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true
```
Finally build using maven ```mvn clean install``` and run the application from your IDE by running the main application class ```DemotestApplication.java```.  (we used intellij)

**Style Checker:**
<img width="1512" alt="style-checker" src="https://github.com/YF916/COMSW4156-ASE-project/assets/144531191/78b1c561-0bbd-4109-9362-b01fafe81d10">


**API Testing:**

All API entry points are tested using postman: https://www.postman.com/yf916/workspace/ase-api-testing/collection/30513862-0c556156-e066-42fa-8966-b0a5aebeb2d9?action=share&creator=30513862

<img width="1624" alt="api-testing-all" src="https://github.com/YF916/COMSW4156-ASE-project/assets/144531191/c5d2a20e-ebb4-417f-aa75-6bd1fcb40bff">



**Unit Testing, Internal & External Integration Testing:**

All unit testing, internal integration testing and external integration testing are stored [here](https://github.com/YF916/COMSW4156-ASE-project/tree/main/src/test/java/com/example/demotest).

# Event Dispatch Service API Documentation

Welcome to the **Event Dispatch Service** API documentation.

**Service Description**

The Event Dispatch Service (EDS) is designed for effortless integration with other APIs and applications, 
serving as a routing mechanism that matches event requests to the most suitable responders based on criteria like event type, location, and proximity. 
EDS ensures swift request processing, offers clear endpoints for seamless integration, manages both dispatch and historical data retrieval.


## Register API

The base URL for **Account management** is `http://localhost:8080/register`.

### Authentication

These APIs do not require authentication.

### 1. Register a new user account

#### Endpoint

- **URL**: `/user`
- **Method**: POST

#### Parameters

- **`name` (required)**: The client's preferred name
- **`phone` (required)**: The client's mobile phone number
- **`password` (required)**: The client's password

#### Example

```http
POST http://localhost:8080/register/user
```
Example Request Body:
```json
{
  "name":"test1",
  "phone":"2062955128",
  "password":"123"
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: None
  - **Example output**: None


### 2. Register a new responder account

#### Endpoint

- **URL**: `/responder`
- **Method**: POST

#### Parameters

- **`name` (required)**: The client's preferred name
- **`phone` (required)**: The client's mobile phone number
- **`password` (required)**: The client's password 
- **`latitude` (required)**: latitude of the responder
- **`longitude` (required)**: longitude of the responder

#### Example

```http
POST http://localhost:8080/register/responder
```
Example Request Body:
```json
{
  "name": "responder2",
  "phone":"1234567",
  "longitude": 30.71,
  "latitude": 60.82,
  "password": "1234"
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: None
  - **Example output**: None

## Authentication API

The base URL for **Account management** is `http://localhost:8080/authenticate`.

### Authentication

These APIs do not require authentication.

### 1. responder login 

#### Endpoint

- **URL**: `/responder`
- **Method**: POST

#### Parameters

- **`username` (required)**: The responder's username
- **`password` (required)**: The responder's password

#### Example

```http
POST http://localhost:8080/authenticate/responder
```
Example Request Body:
```json
{
  "username": "test1",
  "password": "123"
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String (a jwt secret token)
  - **Example output**: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4aWFvbmluZzEiLCJyb2xlIjoidXNlciIsInBhc3N3b3JkIjoiMTIzIiwiaWF0IjoxNzAxMTk0OTY5LCJleHAiOjE3MDEyODEzNjl9.mnLXUMc6yp9njQFjxDB_xGicY07fwIf8_94WtW8CEgQ

### 2. user login

#### Endpoint

- **URL**: `/user`
- **Method**: POST

#### Parameters

- **`name` (required)**: The client's username
- **`password` (required)**: The client's password

#### Example

```http
POST http://localhost:8080/authenticate/user
```
Example Request Body:
```json
{
  "username": "xiaoning1",
  "password": "123"
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String (a secret token)
  - **Example output**: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4aWFvbmluZzEiLCJyb2xlIjoidXNlciIsInBhc3N3b3JkIjoiMTIzIiwiaWF0IjoxNzAxMTk0OTY5LCJleHAiOjE3MDEyODEzNjl9.mnLXUMc6yp9njQFjxDB_xGicY07fwIf8_94WtW8CEgQ
  

## Responder dispatch API 

The base URL for **Responder dispatch API** is `http://localhost:8080/responder`.


### Authentication

These APIs require the jwt token returned by the responder login endpoint, only authenticated responders can send request 
#### Request headers
Authorization : "your jwt token"

### 1. search for all pending requests

#### Endpoint

- **URL**: `/search`
- **Method**: GET

#### Request params
None



#### Example

```http
GET http://localhost:8080/responder/search
```

#### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**:application/json 
    - **Example output**:
```json
[
  {
    "id": 4,
    "caller": {
      "name": "xiaoning bu",
      "phone": "2062955128",
      "password": "123"
    },
    "responder": null,
    "startTime": "2023-11-27T17:17:49.673141",
    "arrivalTime": null,
    "rating": null,
    "feedback": null,
    "status": "pending",
    "emergencyLevel": 1,
    "emergencyType": "medical",
    "latitude": 11.11,
    "longitude": 22.22,
    "message": null
  },
  {
    "id": 7,
    "caller": {
      "name": "test1",
      "phone": "2062955128",
      "password": "123"
    },
    "responder": null,
    "startTime": "2023-11-27T18:29:40.286256",
    "arrivalTime": null,
    "rating": null,
    "feedback": null,
    "status": "pending",
    "emergencyLevel": 1,
    "emergencyType": "medical",
    "latitude": 11.11,
    "longitude": 22.22,
    "message": null
  }
]
```

### 3. Search for available responders within a geolocation's radius

#### Endpoint

- **URL**: `/search_distance`
- **Method**: GET

#### Parameters
- **`latitude` (required)**: latitude of the responder
- **`longitude` (required)**: longitude of the responder
- **`radius` (required)**: responders should be within this radius

#### Example

```http
GET http://localhost:8080/responder/search_distance?latitude=11&longitude=11&radius=3
```
#### Response
- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
[
  {
    "id": 15,
    "caller": {
      "name": "xiaoning1",
      "phone": "2062955128",
      "password": "123"
    },
    "responder": null,
    "startTime": "2023-11-29T14:35:09.097458",
    "arrivalTime": null,
    "rating": null,
    "feedback": null,
    "status": "pending",
    "emergencyLevel": 3,
    "emergencyType": "medical",
    "latitude": 11.11,
    "longitude": 11.22,
    "message": null
  }
]
```

### 4. responder accept a request

#### Endpoint

- **URL**: `/accept/{id}`
- **Method**: POST
#### Parameters
- **`id` (required, path variable)**: id of the request

#### Example

```http
POST http://localhost:8080/responder/accept/7
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Accepted"
  

## Dispatch History Management API

The base URL for **dispatch history management API** is `http://localhost:8080/dispatch-history`.

### Authentication

These APIs require user and responder to login before sending request. in particular /rate can only be accessed by user, other endpoints in this api can be accessed by user and responder 

### 1. user rate a particular dispatch

#### Endpoint

- **URL**: `/rate`
- **Method**: POST

#### Parameters
- **`id` (required)**: id of the dispatch history user is trying to rate
- **`rating` (required)**: 9

#### Example
```http
POST http://localhost:8080/dispatch-history/rate?id=1&rating=9
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Rated"


### 2. mark arrival of the responder on scene

#### Endpoint

- **URL**: `/arrived`
- **Method**: POST

#### Parameters
- **`id` (required)**: id of the dispatch history

#### Example

```http
POST http://localhost:8080/dispatch-history/arrived?id=12
```


#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "arrived"

### 3. finish a dispatch (if user use this endpoint before any responder accept his request, it means that the user cancelled his request)

#### Endpoint

- **URL**: `/finished`
- **Method**: POST

#### Request Body
- **`id` (required)**: id of the dispatch history

#### Example

```http
POST http://localhost:8080/dispatch-history/finished?id=1
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "finished"
  

### 5. Search dispatch histories

#### Endpoint

- **URL**: `/search`
- **Method**: GET

#### Parameters
- **`status` (required, path variable)**: filter using status, return all dispatch history that belongs to the login user/responder with that status

#### Example 1

```http
GET http://localhost:8080/dispatch-history/search?status=dispatched
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
[
  {
    "id": 4,
    "caller": {
      "name": "xiaoning bu",
      "phone": "2062955128",
      "password": "123"
    },
    "responder": {
      "name": "testResponder",
      "phone": "1111111111",
      "latitude": 11.11,
      "longitude": 10.1,
      "status": "available",
      "rating": 9.9,
      "password": "12341234"
    },
    "startTime": "2023-11-27T17:17:49.673141",
    "arrivalTime": null,
    "rating": null,
    "feedback": null,
    "status": "dispatched",
    "emergencyLevel": 1,
    "emergencyType": "medical",
    "latitude": 11.11,
    "longitude": 22.22,
    "message": null
  }
]
```

## user API

The base URL is `http://localhost:8080/user`.

### Authentication

client needs to login as a user and include the jwt token in the header

### 1. send a new dispatch request

#### Endpoint

- **URL**: `/send_request`
- **Method**: POST

#### Parameters

- **`emergencyLevel` (required)**: level of emergency of your request
- **`emergencyType` (required)**: type of emergency of your request
- **`latitude` (required)**: latitude of the request location
- **`longitude` (required)**: longitude of the request location

#### Example

```http
POST http://localhost:8080/user/send_request
```
Example Request Body:
```json
{
  "emergencyLevel":1,
  "emergencyType": "medical",
  "latitude": 11.11,
  "longitude":22.22
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "request submitted"
