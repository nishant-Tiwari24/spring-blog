# Spring Boot Blog Application

A full-featured blog application built with Spring Boot 3.2.3, featuring user authentication, post management, and image uploads.

## Features

- User registration and authentication
- Blog post creation, editing, and deletion
- Image upload support for posts
- Responsive Bootstrap 5 UI
- Dark theme with Material Design-inspired colors
- Remember-me functionality
- H2 in-memory database
- Pagination for blog posts

## Technologies

- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- H2 Database
- Lombok
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/nishant-Tiwari24/spring-blog.git
   cd spring-blog
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   Open your browser and navigate to `http://localhost:8080`

## Configuration

The application uses the following default configuration:

- H2 in-memory database
- File upload size limit: 10MB
- Remember-me token validity: 24 hours

You can customize these settings in `application.properties`.

## Security

- BCrypt password encryption (strength: 12)
- Remember-me persistence with database storage
- CSRF protection
- Secure file upload handling

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 