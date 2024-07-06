# ShoeGame API

## Overview
ShoeGame is a Spring Boot-based API designed for creating and managing a catalog of shoes. This application allows users to interact with the database to perform CRUD operations on shoe items through HTTP requests.

## Technology Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3
- **Database**: PostgreSQL
- **API Testing**: Postman

## Setup and Installation

### Prerequisites
- Java 17
- Spring Boot 3
- PostgreSQL
- Maven

### Running the Application
1. Clone the repository:
2. Navigate to the project directory:
3. Start the PostgreSQL database.
4. Update `src/main/resources/application.properties` with your database credentials.
5. Build and run the application using Maven:

## Features
ShoeGame supports several operations, structured around user stories to enhance the management and tracking of shoe items:

### User Stories
- **Create a new shoe item **: As a user, I can add a new shoe to the catalog.
- **View all shoe time**: As a user, I can view a list of all shoes in the catalog.
- **View a single shoe item by id**: As a user, I can view details of a specific shoe by its ID.
- **Update a shoe item**: As a user, I can update the properties of a shoe.
- **Delete a shoe item by ID**: As a user, I can remove a shoe from the catalog by its ID.
- **User Account Creation**: As a user, I can create an account to manage my shoe items.
- **Login**: As a user, I can log into my account.


### Optional Features
- **Role-based Access Control**: Admin users can view all shoes, while standard users can only view their own.
- **Testing**: Utilize JUnit to achieve at least 70% coverage of service layer methods.

## API Documentation
To interact with the ShoeGame API, set up your HTTP requests in Postman as follows:

- `POST shoegame/users/register` - Register a new user.
- `POST shoegame/users/login` - User login.
- `POST shoegameusers/{userId}/shoes` - Create a new shoe item.
- `GET shoegame/users/{userId}/shoes` - Retrieve all shoe items.
- `GET shoegame/users/{userId}/shoes/{shoeId}` - Retrieve a shoe item by ID.
- `PUT shoegame/users/{userId}/shoes/{shoeId}` - Update a shoe item by ID.
- `DELETE /shoegame/users/{userId}/shoes/{shoeId}` - Delete a shoe item by ID.

## Contributing
Contributions to the ShoeGame project are welcome! Please consider the following steps:
1. Fork the repository.
2. Create a new branch for your feature.
3. Commit your changes.
4. Push to the branch.
5. Open a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.