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

### Create the Database
Open a terminal and open a MySQL client as a user who can create new users.

For example, on a Linux system, use the following command:

`$ sudo mysql --password` -- Connects to MySQL as root and allows access to the user from all hosts

Then create a new database by running the following commands at the mysql prompt:

`mysql> create database db_example;` -- Creates the new database

`mysql> create user 'springuser'@'%' identified by 'ThePassword';` -- Creates the user

`mysql> grant all on db_example.* to 'springuser'@'%';` -- Gives all privileges to the new user on the newly created database
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



**Unit Testing:**

All unit testing is stored [here](https://github.com/YF916/COMSW4156-ASE-project/tree/main/src/test/java/com/example/demotest).

# Event Dispatch Service API Documentation

Welcome to the **Event Dispatch Service** API documentation.

**Service Description**

The Event Dispatch Service (EDS) is designed for effortless integration with other APIs and applications, 
serving as a routing mechanism that matches event requests to the most suitable responders based on criteria like event type, location, and proximity. 
EDS ensures swift request processing, offers clear endpoints for seamless integration, manages both dispatch and historical data retrieval.


## Account Management API

The base URL for **Account management** is `http://localhost:8080/user`.

### Authentication

These APIs do not require authentication.

### 1. Add a new user account

#### Endpoint

- **URL**: `/add`
- **Method**: POST

#### Parameters

- **`name` (required)**: The client's preferred name
- **`phone` (required)**: The client's mobile phone number

#### Example

```http
POST http://localhost:8080/user/add
```
Example Request Body:
```json
{
  "name": "testUser1",
  "phone": "0000000000"
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Saved"


### 2. Find all users account

#### Endpoint

- **URL**: `/all`
- **Method**: GET

#### Parameters

None

#### Example

```http
GET http://localhost:8080/user/all
```
Example Request Body:
```json
{}
```
#### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: application/json
    - **Example output**:
```json
[
    {
      "id": 1,
      "name": "Amy",
      "phone": "12345678"
    },
    {
      "id": 2,
      "name": "Frank",
      "phone": "1234567"
    }
]
```

## Responder Management API 

The base URL for **responder management** is `http://localhost:8080/responder`.

### Authentication

These APIs do not require authentication.

### 1. Add a new responder

#### Endpoint

- **URL**: `/add`
- **Method**: POST

#### Request body

- **`name` (required)**: The responder's preferred name
- **`phone` (optional)**: The responder's mobile phone number
- **`latitude` (required)**: The responder's latitude
- **`longitude` (required)**: The responder's longitude
- **`status` (optional)**: The responder's current status

#### Example

```http
POST http://localhost:8080/responder/add
```
Example Request Body:
```json
{
  "name": "testResponder1",
  "phone": "0000000000",
  "latitude": "40.71",
  "longitude": "40.71",
  "status": "available"
}
```
#### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: String
    - **Example output**: "Saved"

### 2. Search for an available responder, optionally filtered and ordered by rating

#### Endpoint

- **URL**: `/search`
- **Method**: GET

#### Parameters
- **`rating` (optional)**: return only responder with rating larger than this value

#### Example

```http
GET http://localhost:8080/responder/search?rating=0.0
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
[
    {
        "id": 5,
        "name": "responder1",
        "phone": "123445",
        "latitude": null,
        "longitude": null,
        "status": "available",
        "rating": 10.0
    },
    {
        "id": 6,
        "name": "TestResponder1",
        "phone": "9999999999",
        "latitude": 40.71,
        "longitude": 70.89,
        "status": "available",
        "rating": 10.0
    },
    {
        "id": 7,
        "name": "testResponder2",
        "phone": "9999999999",
        "latitude": 50.71,
        "longitude": 80.82,
        "status": "available",
        "rating": 10.0
    }
]
```

### 3. Search for available responders within a geolocation's radius

#### Endpoint

- **URL**: `/search_distance`
- **Method**: GET

#### Parameters
- **`latitude` (required)**: latitude of the client
- **`longitude` (required)**: longitude of the client
- **`radius` (required)**: responders should be within this radius

#### Example

```http
GET http://localhost:8080/responder/search_distance
```
Example Request Body:
```json
{
  "latitude": "51.0",
  "longitude": "82.0",
  "radius": 3
}
```
#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
[
    {
        "id": 7,
        "name": "testResponder2",
        "phone": "9999999999",
        "latitude": 50.71,
        "longitude": 80.82,
        "status": "available",
        "rating": 10.0
    }
]
```

### 4. Dispatch a responder 

#### Endpoint

- **URL**: `/dispatch`
- **Method**: POST

#### Request Body

The request body should be a JSON object containing the following parameters:

- **`id` (required)**: id of the responder that we want to dispatch
- **`status` (required)**: status change of the responder
- **`latitude` (required)**: latitude of the client
- **`longitude` (required)**: longitude of the client

#### Example

```http
POST http://localhost:8080/responder/dispatch
```
Example Request Body:
```json
{
  "id": 6,
  "status": "dispatched",
  "latitude": "33.33",
  "longitude": "22.22"
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK or 409 Conflict (handled by ResponderNotAvailableException)
  - **Content-Type**: Sting
  - **Example output**: "Dispatched" or "Responder does not exist"/"Responder not available at this moment"

### 5. Recommend a responder with the user's highest previous rate 

#### Endpoint

- **URL**: `/recommend/rate`
- **Method**: GET

#### Parameters
- **`id` (required)**: The caller's userid

#### Example

```http
GET http://localhost:8080/responder/recommend/rate?user_id=1
```
Example Request Body:
```json
{
  "id": 1
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**: 
```json
{
    "id": 2,
    "name": "Second",
    "phone": "2345678",
    "latitude": 40.7128,
    "longitude": 77.0,
    "status": "available",
    "rating": null
}
```

### 6. Recommend responder with the highest frequency in user's call history

#### Endpoint

- **URL**: `/recommend/frequency`
- **Method**: GET

#### Parameters

- **`id` (required)**: The caller's userid
#### Example

```http
GET http://localhost:8080/responder/recommend/frequency?user_id=1
```
Example Request Body:
```json
{
  "id": 1
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
{
    "id": 1,
    "name": "First",
    "phone": "1234567",
    "latitude": 40.7128,
    "longitude": 74.006,
    "status": "available",
    "rating": null
}
```


## Dispatch History Management Base URL

The base URL for **dispatch history management** is `http://localhost:8080/dispatch-history`.

### Authentication

These APIs do not require authentication.

### 1. Start a new dispatch and record its dispatch history

#### Endpoint

- **URL**: `/start`
- **Method**: POST

#### Request Body

The request body should be a JSON object containing the following parameters:

- **`user_id` (required)**: The userid of the caller
- **`responder_id` (required)**: id of the responder
- **`start_time` (required)**: the start time of the dispatch

#### Example

```http
POST http://localhost:8080/dispatch-history/start
```
Example Request Body:
```json
{
  "user_id": 1,
  "responder_id": 2,
  "start_time": "2023-10-19T11:42:12"
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Saved"


### 2. Rate a dispatch and provide feedback if wanted

#### Endpoint

- **URL**: `/rate`
- **Method**: POST

#### Request Body

The request body should be a JSON object containing the following parameters:
- **`id` (required)**: The dispatch id
- **`rating` (required)**: rating of this dispatch service
- **`feedback` (optional)**: feedback of this dispatch service
#### Example

```http
POST http://localhost:8080/dispatch-history/rate
```
Example Request Body:
```json
{
  "id": 6,
  "rating": 8.5,
  "feedback": "Good"
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Rated"

### 3. The responder arrives at scene

#### Endpoint

- **URL**: `/arrived`
- **Method**: POST

#### Request Body
- **`id` (required)**: The dispatch id
- **`arrival_time` (required)**: the time when the responder arrives at scene

#### Example

```http
POST http://localhost:8080/dispatch-history/arrived
```
Example Request Body:
```json
{
  "id": 6,
  "arrival_time": "2023-10-19T15:44:18"
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Arrived"

### 4. The responder completes the client's requirement

#### Endpoint

- **URL**: `/finished`
- **Method**: POST

#### Request Body

- **`id` (required)**: The dispatch id

#### Example

```http
POST http://localhost:8080/dispatch-history/finished
```
Example Request Body:
```json
{
  "id": 6
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**: "Finished"

### 5. Search dispatch histories

#### Endpoint

- **URL**: `/search`
- **Method**: GET

#### Parameters
- **`filterBy` (optional)**: can be filtered by 'Responder' or 'User'
- **`id` (optional)**: specify the Responder's id or the User's id

#### Example 1

```http
GET http://localhost:8080/dispatch-history/search
```
Example Request Body:
```json
{}
```
#### Example 2
```http
GET http://localhost:8080/dispatch-history/search?filterBy=Responder&id=9
```
Example Request Body:
```json
{
  "filterBy": "Responder",
  "id": 9
}
```

#### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json
[
    {
        "id": 6,
        "caller": {
            "id": 1,
            "name": "Amy",
            "phone": "12345678"
        },
        "responder": {
            "id": 9,
            "name": "testResponder2",
            "phone": "9999999999",
            "latitude": 50.71,
            "longitude": 80.82,
            "status": "available",
            "rating": 9.5935,
            "hibernateLazyInitializer": {}
        },
        "startTime": "2023-10-19T11:42:12",
        "arrivalTime": "2023-10-19T15:44:18",
        "rating": 9.0,
        "feedback": "Good",
        "status": "finished"
    }
]
```
