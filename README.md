# Blackjack API - README

## 📄 Project Overview

This project consists of building an API for a Blackjack game using Java with Spring Boot. The API connects to and manages data within two databases: MongoDB and MySQL. The application covers full Blackjack gameplay functionality, including player management, handling of cards, and adherence to Blackjack rules.

The application is designed to be fully reactive, using Spring WebFlux, and includes testing, documentation, and error handling features.

## 💻 Technologies Used

- **Java**
- **Spring Boot** (for building the REST API)
- **Spring WebFlux** (for reactive programming)
- **MongoDB** (to store player information)
- **MySQL** (to manage game session data)
- **Swagger/OpenAPI** (for API documentation)
- **Maven** (for dependency management)

## 📋 Requirements

- **Java 11** or higher
- **Maven 3.6** or higher
- **MongoDB v4.4+**
- **MySQL v8.0+**
- **IntelliJ IDEA** (recommended)
- **Windows** (development environment)

## 🛠️ Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/arnaufata/BlackJack-API.git
    ```

2. **Navigate to the project directory**:

    ```bash
    cd Blackjack-API
    ```

3. **Install dependencies using Maven**:

    ```bash
    mvn clean install
    ```

4. **Configure database connections**:
   - Update `application.properties` in `src/main/resources` with your MongoDB and MySQL connection details.

## ▶️ Execution

1. Ensure **MongoDB** and **MySQL** are running on your local machine or accessible via a configured server.

2. **Start the application**:

    ```bash
    mvn spring-boot:run
    ```

3. The application will be available at [http://localhost:8080](http://localhost:8080).

## 🌐 API Documentation

Access the API documentation and test the endpoints using **Swagger UI**:

- URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧩 API Endpoints

### Game Management

- **Create a new game**
    - **Method**: `POST`
    - **Endpoint**: `/game/new`
    - **Description**: Creates a new Blackjack game session.
    - **Response**: `201 Created` with game information.

- **Get game details**
    - **Method**: `GET`
    - **Endpoint**: `/game/{id}`
    - **Description**: Fetches details of a specific game session.
    - **Response**: `200 OK` with game information.

- **Make a play**
    - **Method**: `POST`
    - **Endpoint**: `/game/{id}/play`
    - **Description**: Submits a play in an existing game.
    - **Response**: `200 OK` with the result and current game status.

- **Delete a game**
    - **Method**: `DELETE`
    - **Endpoint**: `/game/{id}/delete`
    - **Description**: Deletes an existing game session.
    - **Response**: `204 No Content` on successful deletion.

### Player Management

- **Get player ranking**
    - **Method**: `GET`
    - **Endpoint**: `/ranking`
    - **Description**: Retrieves a ranking list of players based on game performance.
    - **Response**: `200 OK` with a sorted list of players and scores.

- **Change player name**
    - **Method**: `PUT`
    - **Endpoint**: `/player/{playerId}`
    - **Description**: Updates a player’s name in an active game.
    - **Response**: `200 OK` with updated player information.

## ⚙️ Project Configuration

### Database Configuration

Set up the application to use both MongoDB and MySQL:

- **MongoDB**: Manage game session data.
- **MySQL**: Store player and ranking information.

### Global Exception Handling

A `GlobalExceptionHandler` is implemented to manage errors consistently across the application, ensuring 
