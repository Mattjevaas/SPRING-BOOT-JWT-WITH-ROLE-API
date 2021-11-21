
# SPRING-BOOT-JWT-WITH-ROLE-API

Template for JWT authentication with username, password, and role. Build using Spring Boot Java Restfull API




## Deploy With Docker

Create Jar File First

```bash
  mvn clean install
```

Create Docker Image

```bash
  docker build --tag jwt-template-api:0.0.1 .
```

Create Docker Container using Docker Compose

```bash
  docker-compose -f docker-compose.yml up -d 
```
Don't Forget to create your Database

```bash
  mysql > CREATE DATABASE jwttemplate;
```


## Author
- [Myself](https://www.instagram.com/mattjevaas/)

## Reference

- [Get Arrays's Youtube Channel](https://www.youtube.com/watch?v=mYKf4pufQWA)
- [Programmer Zaman Now's Youtube Channel](https://www.youtube.com/watch?v=J3YeD3km68g&t=269s)

