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

- **GET** `/segment/:id`  
  Retrieves details of a specific road segment by ID.

- **PUT** `/segment/:id`  
  Updates a specific road segment by ID.

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
  Creates a new roadway.

- **GET** `/roadway/:id`  
  Retrieves details of a specific roadway by ID.

- **PUT** `/roadway/:id`  
  Updates a specific roadway by ID.

- **DELETE** `/roadway/:id`  
  Deletes a specific roadway by ID.

### Curb Endpoints

- **GET** `/curb`  
  Retrieves a list of all curbs.

- **POST** `/curb`  
  Creates a new curb.

- **GET** `/curb/:id`  
  Retrieves details of a specific curb by ID.

- **PUT** `/curb/:id`  
  Updates a specific curb by ID.

- **DELETE** `/curb/:id`  
  Deletes a specific curb by ID.

## Project Structure

