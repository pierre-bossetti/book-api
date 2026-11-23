# Book API

A RESTful API for managing books and authors, built with Spring Boot 4.2.0 and Java 21.

## Overview

Book API provides a complete CRUD interface for managing authors and their associated books. The application uses Spring Data JPA with an H2 in-memory database, making it easy to run and test without external database dependencies.

## Features

- **Author Management**: Create, read, update, and delete authors with filtering and sorting capabilities
- **Book Management**: Manage books with associations to authors
- **RESTful Design**: Standard HTTP methods and status codes
- **In-Memory Database**: H2 database with web console for easy development and testing

## Technology Stack

- **Java 21**
- **Spring Boot 4.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok** (for reducing boilerplate code)

## Domain Model

### Author
- `id` (Long): Unique identifier
- `name` (String): Author's name
- `birthdate` (LocalDate): Author's birth date
- `country` (String): Author's country
- `books` (List<Book>): One-to-many relationship with books

### Book
- `id` (Long): Unique identifier
- `name` (String): Book title
- `year` (Integer): Publication year
- `content` (String): Full book content (LOB)
- `summary` (String): Book summary
- `author` (Author): Many-to-one relationship with author

## API Endpoints

### Authors

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/authors` | List all authors (with optional filtering and sorting) |
| GET | `/authors/{id}` | Get a specific author by ID |
| POST | `/authors` | Create a new author |
| PUT | `/authors/{id}` | Update an existing author |
| DELETE | `/authors/{id}` | Delete an author |

#### Query Parameters for GET /authors

| Parameter | Type | Description |
|-----------|------|-------------|
| `name` | String | Filter by author name |
| `birthdate` | LocalDate | Filter by birth date |
| `country` | String | Filter by country |
| `sort-by` | String | Sort by field name |
| `sort-order` | String | Sort order (ascending/descending) |

## Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.6+

### Building the Project

```bash
./mvnw clean install