# COMSW4156 - ADVANCED SOFTWARE ENGINEERING

### Assignment T0: Team Formation
Part 1: 

    Team Members:
        Xiaoning Bu (xb2171)
        Xinyu Fu (xf2243)
        Yifan Li (yl5337)
        Zian Song (zs2632)

Part 2:

    Team Name: The A

Part 3:

    Programming Language: Java
    Development Platform: MacOS

Part 4: 

    GitHub Repository Link: https://github.com/YF916/COMSW4156-ASE-project
# Weather API Documentation

Welcome to the Weather API documentation. This API allows you to retrieve weather information for a specific location by making a GET request with parameters.

## Base URL

The base URL for this API is `http://localhost:8080/user`.

## Authentication

This API does not require authentication.

## Add a user

### Endpoint

- **URL**: `/add`
- **Method**: POST

### Parameters

- **`name` (required)**: The client's preferred name.
- **`phone` (optional)**: The client's mobile phone number

### Example

```http
POST http://localhost:8080/user/add

Example Request Body:
```
```json
{
  "name": "testUser1",
  "phone": "0000000000",
}
```
### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: string ("Saved")


## Find all user

### Endpoint

- **URL**: `/all`
- **Method**: GET

### Parameters

None

### Example

```http
POST http://localhost:8080/user/all

Example Request Body:
```
```json
{
}
```
### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: application/json
    - **Example output**:
```json{
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
        },
   ]
```

## Find all user

### Endpoint

- **URL**: `/all`
- **Method**: GET

### Parameters

None

### Example

```http
POST http://localhost:8080/user/all

Example Request Body:
```
```json
{
}
```
### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: application/json
    - **Example output**:
```json{
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
        },
   ]
```
## Find all user

### Endpoint

- **URL**: `/all`
- **Method**: GET

### Parameters

None

### Example

```http
POST http://localhost:8080/user/all

Example Request Body:
```
```json
{
}
```
### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: application/json
    - **Example output**:
```json{
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
        },
   ]
```

## Add responder

### Endpoint

- **URL**: `/add`
- **Method**: POST

### Request body



### Example

```http
POST http://localhost:8080/responder/add

Example Request Body:
```
```json
{
  "name": "testResponder1",
  "phone": "0000000000",
  "latitude": "40.71",
  "longitude": "40.71",
  "status": "available"
}
```
### Response

- **Success Response**:
    - **HTTP Status Code**: 200 OK
    - **Content-Type**: String
    - **Example output**: "Saved"

## Search for an available responder, optionally filtered and ordered by rating

### Endpoint

- **URL**: `/search`
- **Method**: Get

### Parameters
- **`rating` (optional)**: return only responder with rating larger than this value

### Example

```http
Get http://localhost:8080/responder/search?rating=0.0
```
### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
```json{
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

## dispatch a responder 

### Endpoint

- **URL**: `/dispatch`
- **Method**: POST

### Request Body

The request body should be a JSON object containing the following parameters:

- **`id` (required)**: id of the responder that we want to dispatch
- **`status` (required)**: status change of the responder
- **`latitude` (required)**: latitude of the client
- **`longitude` (required)**: longitude of the client
### Example

```http
POST http://localhost:8080/responder/dispatch
```
The request body can be a JSON object containing the following parameters:
id:6
status:dispatched
latitude:33.33
longitude:22.22

### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: Sting
  - **Example output**: "Dispatched"

## recommend responder with the user's highest previous rate 

### Endpoint

- **URL**: `/recommend/rate`
- **Method**: Get

### Parameters

- **`id` (required)**: The caller's userid
### Example

```http
GET http://localhost:8080/responder/recommend/rate?user_id=1
```

### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**: 
  - ```json
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

## recommend responder with the highest frequency in user's call history

### Endpoint

- **URL**: `/recommend/frequency`
- **Method**: Get

### Parameters

- **`id` (required)**: The caller's userid
### Example

```http
GET http://localhost:8080/responder/recommend/frequency?user_id=1
```

### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: application/json
  - **Example output**:
  - ```json
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



## Base URL

The base URL for this API is `https://api.example.com/weather`.

## Authentication

This API does not require authentication.

## start a new dispatch and record its dispatch history

### Endpoint

- **URL**: `/start`
- **Method**: POST

### Request Body

The request body should be a JSON object containing the following parameters:

- **`user_id` (required)**: The userid of the caller
- **`responder_id` (required)**: Id of the responder
- **`start_time` (required)**: the start time of the dispatch
### Example

```http
POST http://localhost:8080/dispatch-history/start
```
The request body can be a JSON object containing the following parameters:
user_id:1
responder_id:2
start_time:2023-10-19T11:42:12

### Response

- **Success Response**:
  - **HTTP Status Code**: 200 OK
  - **Content-Type**: String
  - **Example output**:Saved
