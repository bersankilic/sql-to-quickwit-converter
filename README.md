# SQL to Quickwit Query Converter

This project is a prototype developed to convert SQL queries to Quickwit queries. The project is developed using Java programming language and Maven configuration tool. It is configured with Spring Boot framework.

## Features

- Parses SQL queries.
- Converts SQL `SELECT` queries to Quickwit queries.
- Converts `WHERE` conditions to Quickwit format.

## Technologies Used

- Java
- Maven
- Spring Boot
- JSQLParser

## Installation and Run

1. Clone the project:
```sh
git clone https://github.com/bersankilic/sql-to-quickwit-converter.git
cd sql-to-quickwit-converter
```

2. Install Maven dependencies:
```sh
mvn clean install
```

3. Run the application:
```sh
mvn spring-boot:run
```

## Usage

The application parses the given SQL query and converts it to Quickwit query. The `SQLParser` class parses the SQL query, while the `QuickwitQueryConverter` class converts it to Quickwit format.

## Note

This project does not provide full support for all SQL query types and conditions.