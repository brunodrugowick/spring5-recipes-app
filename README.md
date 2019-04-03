# Spring 5 Recipes App

This is an exercise from [Spring 5 - Beginner to Guru](https://www.udemy.com/spring-framework-5-beginner-to-guru/) course.

# About
This project teaches the use of:
- Spring > Core > DevTools
- Spring > Web > Web
- Spring > Templates > Thymeleaf
- Spring > SQL > JPA
- Spring > SQL > H2

# Running
This is a Spring Boot application. You may run using the provided Maven Wrapper (mvnw.cmd on Windows or mvnw on Linux):
```
./mvnw spring-boot:run
```

One can also build and run using Java:
```
./mvnw package
 java -jar target/*.jar
 ```

## Developing
The app can create a sample database to use while developing. 
This process is only triggered if you understand the DataLoader.java class.

Otherwise the app will run normally without creating any sample data.
Go ahead and take a look at the mentioned class to understand what you have to do
to have the sample data created for you.
