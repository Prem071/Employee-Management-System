# Employee Management API

Spring Boot 3 + Spring Data JPA + H2.

## Run
    mvn spring-boot:run        # or ./mvnw spring-boot:run if you add the wrapper (mvn wrapper:wrapper)

App: http://localhost:8080  |  H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:employeedb)

## Endpoints
| Method | URL                    | Description                          |
|--------|------------------------|--------------------------------------|
| GET    | /api/employees         | List (paged). ?department=&page=&size=&sort= |
| GET    | /api/employees/{id}    | Get one                              |
| POST   | /api/employees         | Create                               |
| PUT    | /api/employees/{id}    | Update                               |
| DELETE | /api/employees/{id}    | Delete                               |

## Try it
    curl -X POST http://localhost:8080/api/employees \
      -H "Content-Type: application/json" \
      -d '{"firstName":"Asha","lastName":"Rao","email":"asha@example.com","department":"IT","jobTitle":"Developer","salary":85000,"hireDate":"2024-03-01"}'

    curl "http://localhost:8080/api/employees?department=IT"
