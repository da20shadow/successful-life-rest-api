# Successful Life App

The Successful Life App is a RESTful API built with Spring Boot that aims to help users achieve their goals by providing features such as goal creation, target setting, task management, idea generation, agenda and calendar management, and more. The API allows users to interact with the app's functionality through HTTP endpoints, making it suitable for integration with various front-end clients such as web, mobile, or desktop applications.

DEMO: http://sourceofanswers.esy.es/ 
NOTE: Registrations are disabled if you want to check it inside contact me:
FB: https://www.facebook.com/profile.php?id=100078498727817
Telegram: https://t.me/da20shadow

## Features

The Successful Life App offers the following main features:

- Goal management: Users can create, update, and delete goals, set deadlines, and track progress.
- Target management: Users can create targets for each goal, set deadlines, and monitor progress.
- Task management: Users can create tasks for each target or as standalone tasks, set deadlines, and mark them as complete.
- Checklist item management: Users can create checklist items for each task, mark them as complete, and track progress.
- Idea management: Users can generate ideas for goals or as standalone ideas, categorize them, and track progress.
- Agenda management: Users can manage their daily, weekly, or monthly agenda, add events, and set reminders.
- Calendar management: Users can manage their personal calendar, add events, set reminders, and view upcoming events.

## Technologies Used

The Successful Life App is built using the following technologies:

- Spring Boot: A powerful framework for building Java-based applications, including RESTful APIs, with ease.
- Spring Data JPA: A part of the Spring framework that simplifies database access and allows for seamless integration with relational databases.
- Spring Security: A powerful and highly customizable security framework for securing your application and implementing authentication and authorization.
- Spring Validation: A module in Spring that provides validation support for validating request data and handling validation errors.
- JSON Web Token (JWT): A compact, URL-safe means of representing claims to be transferred between parties, often used for implementing authentication and authorization in RESTful APIs.
- Guava: A widely-used Java library developed by Google that provides utilities for common programming tasks such as collections, caching, and string manipulation.
- ModelMapper: A Java library for mapping objects between different domains or data models, making it easy to transfer data between objects with different structures.
- MySQL Connector/J: The official MySQL JDBC driver for Java, used for connecting to MySQL database from Java applications.
- Lombok: A Java library that helps reduce boilerplate code by providing annotations to generate common code constructs such as getters, setters, and constructors at compile-time.
- Spring Boot DevTools: A set of development tools provided by Spring Boot that help improve developer productivity during development by providing features such as automatic restarts and enhanced logging.
- Spring Boot Test: A testing framework provided by Spring Boot for writing tests for Spring applications.
- Spring Security Test: A testing framework provided by Spring Security for writing tests for Spring Security configurations and features.

## Getting Started

To get started with the Successful Life App, follow these steps:

1. Clone the repository to your local machine using the following command:
 git clone https://github.com/da20shadow/successful-life-rest-api.git
 
2. Navigate to the project directory:
  cd successful-life-rest-api

3. Update the database configuration in the application.properties file to match your local MySQL database settings.

4. Run the project

5. You can also integrate the Successful Life App API with your front-end clients or other applications by sending HTTP requests to the appropriate endpoints.

## API Documentation

The API endpoints and their respective input/output parameters:
GET /api/v1/goals => returns Page with the last 24 goals added.
GET /api/v1/goals?category=Career => returns Page with the last 24 goals added with category 'Career'.
GET /api/v1/goals/{goalId} => returns GOAL by ID.

TODO: add all or use swager...

## Contributing
If you would like to contribute to Successful Life App, we welcome pull requests and contributions. 
Please refer to our CONTRIBUTING.md file for more information on how to get started.

## License
Successful Life App is open source software licensed under the MIT License. 
See the LICENSE file for more information.

## Support
If you encounter any issues or have any questions about Successful Life App, please contact our support team at support@successfullifeapp.com. 
