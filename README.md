# sims-backend
Smart Inventory Management System (S-IMS) backend Web API

## Overview
S-IMS backend is built using Spring Boot and PostgreSQL as the database server.

S-IMS is an inventory management system equipped with  data visualisation and demand forecasting.

Its frontend is built using Angular and FusionCharts (available in another repository [sims-frontend](https://github.com/ebiggerr/sims-frontend) and both of them communicates using HTTP requests.

## Using Postman

(postman)[postman.png]
 
## Dependencies
1. Auth0 java-jwt
2. Spring Data JPA
3. Hibernate
4. PostgreSQL driver
5. Undertow
6. Spring Boot

## Build and Run
The project is built using Gradle build tool.

## Documentation

##### How to configure the application

- Database connection

The configuration can be done on the application.properties ( location : src/main/resources/application.properties) by specify the database url, account credentials such as username and password using 
```
spring.active.profile=dev (remove this if you want the application.properties to be default)

spring.datasource.url=jdbc:postgresql://{yourDomain}:{yourPort}/{yourDatabaseName}
spring.datasource.username={yourDatabaseUsername}
spring.datasource.password={yourDatabasePassworrd}

spring.datasource.platform=postgres ( in this case I used PostgreSQL )
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform = org.hibernate.dialect.PostgreSQL10Dialect

```

- OpenAPI/Swagger configuration

```$xslt
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.operationsSorter=method
```


