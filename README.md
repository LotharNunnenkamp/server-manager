
# :rocket: Server-Manager

Project developed in Spring Boot + Angular for practice purposes.

## :hammer_and_wrench: Techonologies 

Back-end: Java 21, Spring Boot, Spring Data JPA, MySQL
Front-end: Angular 19, TypeScript, RxJS

## :arrow_forward: How to Run the Project

### Prerequisites
Make sure you have the following dependencies installed:
- Java 21
- Gradle
- Node.js
- npm
- Angular CLI
- MySQL

### Running the Back-end

1. Configure database in the application.properties file

2. Navigate to the back-end directory:
```
cd server-manager/back-end/server-manager
```
3. Run the Spring Boot application:
```
./gradlew bootRun
```
Or run file:
```
back-end/server-manager/src/main/java/com/example/server_manager/ServerManagerApplication.java
```
The back-end will be available at: http://localhost:8080


###  Running the Front-end

1. Navigate to the front-end directory:
```
server-manager/front-end/serverapp
```
2. Install dependencies:
```
npm install
```
3. Start the Angular application:
```
ng serve
```
The front-end will be available at: http://localhost:4200

## :handshake: Contribution

If you want to contribute, feel free to open issues and pull requests!

## :scroll: License

This project is for learning and practice purposes only.
