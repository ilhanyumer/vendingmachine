# Vending Machine API

This vending machine is build with Java 17 and Spring Boot 3.1.1.

## Documentation

The REST API documentation is available at http://localhost:8080/swagger-ui.html or
http://localhost:8003/swagger-ui.html depending on what port you run the API.

## Docker Commands

To build run `docker build -t vendingmachine .` and then `docker run -p 8003:8080 vendingmachine`

## Some Assumptions

There is a room for improvements. The following points could be improved.

* If electricity is cut the application loses its state. Assume that electricity never goes off.
* Products are defined within the code. They could be defined in a database.
* Currently, there is no authentication for the admin API endpoints.
* When coins are returned it is assumed that there are as much as necessary coins within the machine.
