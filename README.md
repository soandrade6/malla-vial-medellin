# Road Infrastructure Management API

This API provides a system for managing the city's road segments, including their associated roadways and curbs, to ensure efficient administration and maintenance of the road infrastructure. The system is built using Java and the Play Framework.

## Features

- **Segment Management:** Create, update, delete, and retrieve road segments.
- **Roadway Management:** Manage roadways associated with segments.
- **Curb Management:** Manage curbs associated with segments.
- **RESTful API Design:** The API follows RESTful principles for ease of integration.
- **Play Framework:** Built using Play Framework 2.8 with Jakarta Persistence (JPA) and PostgreSQL.

## API Endpoints

### Base URL: `/v1`

### Segment Endpoints

- **GET** `/segment`  
  Retrieves a list of all road segments.

- **POST** `/segment`  
  Creates a new road segment.  
  **Body Parameters:**
  - `int segmentNumber`: The unique number of the road segment.
  - `double length`: The length of the road segment.
  - `String nomenclature`: The nomenclature of the road segment.

  **Example:**
  ```json
  {
    "segmentNumber": 123,
    "length": 150.5,
    "nomenclature": "Avenida Principal"
  }

- **GET** `/segment/:id`  
  Retrieves details of a specific road segment by ID.

- **PUT** `/segment/:id`  
  Updates a specific road segment by ID.
  
  **Body Parameters:**
  - `int segmentNumber`
  - `double length`
  - `String nomenclature`

- **DELETE** `/segment/:id`  
  Deletes a specific road segment by ID.

- **GET** `/segment/:id/roadways`  
  Retrieves all roadways associated with a specific road segment.

- **GET** `/segment/:id/curbs`  
  Retrieves all curbs associated with a specific road segment.

### Roadway Endpoints

- **GET** `/roadway`  
  Retrieves a list of all roadways.

- **POST** `/roadway`  
  Creates a new roadway associated with a segment.
  
  **Body Parameters:**

  - `double width:` The width of the roadway.
  - `Long segment_id:` The ID of the segment to which the roadway belongs.
 
  **Example:**
  ```json
  {
  "width": 12.5,
  "segment_id": 1
  }

- **GET** `/roadway/:id`  
  Retrieves details of a specific roadway by ID.

- **PUT** `/roadway/:id`  
  Updates a specific roadway by ID.
  
  **Body Parameters:**

  - `double width`
  - `Long segment_id`

- **DELETE** `/roadway/:id`  
  Deletes a specific roadway by ID.

### Curb Endpoints

- **GET** `/curb`  
  Retrieves a list of all curbs.

- **POST** `/curb`  
  Creates a new curb associated with a segment.
  
  **Body Parameters:**

  - `double height:` The height of the curb.
  - `Long segment_id:` The ID of the segment to which the curb belongs.
  
  Example:
  ```json
  {
    "height": 0.25,
    "segment_id": 1
  }



- **GET** `/curb/:id`  
  Retrieves details of a specific curb by ID.

- **PUT** `/curb/:id`  
  Updates a specific curb by ID.

   **Body Parameters:**

  - `double height`
  - `Long segment_id`

- **DELETE** `/curb/:id`  
  Deletes a specific curb by ID.



# How to Run the Project

This project is a RESTful API built with Play Framework, Java, and PostgreSQL.

## Prerequisites

- **Java 8+**
- **SBT 1.5+**
- **PostgreSQL**
- **Git**

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/soandrade6/malla-vial-medellin.git
   cd malla-vial-medellin
   ```

2. Create the database:

   ```bash
   psql -U postgres -c "CREATE DATABASE road_segments;"
   ```

3. Update the database credentials in `application.conf`:

   ```hocon
   db.default.url="jdbc:postgresql://localhost:5432/road_segments"
   db.default.username="postgres"
   db.default.password="your_password"
   ```

## Run the Project

1. Compile and run:

   ```bash
   sbt run
   ```

2. Access the API at:

   ```
   http://localhost:9000/
   ```



