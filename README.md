# sims-backend
Smart Inventory Management System (S-IMS)

## Overview
S-IMS backend is built using Spring Boot and PostgreSQL as the database server.

S-IMS is an inventory management system equipped with  data visualisation and demand forecasting.

Its frontend is built using Angular and FusionCharts (available in another repository [sims-frontend](https://github.com/ebiggerr/sims-frontend) and both of them communicates using HTTP requests.
 
## Dependencies
1. Java JWT
2. Spring Data JPA
3. Hibernate
4. PostgreSQL driver
5. Undertow
6. Spring Boot

## Build and Run
The project is built using Gradle build tool.

 
 #### Entities / Domains
 
 accountauthetication - ORM 
    
    -id
    -username
    -password
    -accRoles ( nested Object ) ORM
 
 accountauthenticationDTO - DTO ( FROM accountauthetication )
 
    -id
    -username
    -password
    -accRoles ( Collection <String> LinkedList
 
 accountEntity - ORM ( for registering and logout ) 
 
    -id
    -username 
    -password
    -lastLogin
    -lastActive
    -accountStatus
    
   account - DTO

    -id
    -username
    -password
    -lastLogin
    -lastActive
    -accountStatus 
     
       
   

